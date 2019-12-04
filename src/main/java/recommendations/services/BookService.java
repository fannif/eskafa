package recommendations.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import recommendations.domain.Book;
import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Tag;
import recommendations.io.IO;

import org.apache.http.client.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class BookService {

    private IO io;
    private ReaderDao bookDao;

    public BookService(ReaderDao dao, IO io) {
        this.io = io;
        this.bookDao = dao;
    }

    public void addBook(Book book) throws IOException, SQLException {
        bookDao.save(book);

    }

    public ArrayList<Book> listBooks() throws SQLException {
        ArrayList<Book> books = (ArrayList<Book>) bookDao.findAll();
        return books;
    }

    public void remove(String title) throws Exception {
        boolean go = true;
        String input = title;
        while (go) {
            if (input.equals("q")) {
                return;
            }
            if (bookDao.findOne(input) == null) {
                io.print("No such book found. Please check the spelling and try again: ");
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                bookDao.delete(input);
                go = false;
            }
        }
        io.print("The book has been successfully removed");
    }

    public void edit(String name) throws SQLException {
        boolean go = true;
        String input = name;
        Book book;
        while (go) {
            book = (Book) bookDao.findOne(input);
            if (input.equals("q")) {
                return;
            }
            if (book == null) {
                io.print("No such book found. Please check the spelling and try again: ");
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                System.out.println("Please update a field or fields. Press enter to skip the field.");
                io.print("Title: ");
                String title = io.read();
                io.print("Author: ");
                String author = io.read();
                ArrayList<Tag> tags = new ArrayList();
                ArrayList<Course> courses = new ArrayList();
                io.print("Add zero or more tags. Enter tags one at a time. Press 'enter'"
                        + "to continue: ");

                while (true) {
                    String tag = io.read();
                    if (tag.equals("")) {
                        break;
                    }
                    Tag newTag = new Tag(0, tag);
                    tags.add(newTag);
                }
                io.print("Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                        + "to continue: ");

                while (true) {
                    String course = io.read();
                    if (course.equals("")) {
                        break;
                    }
                    Course newCourse = new Course(0, course);
                    courses.add(newCourse);
                }
                io.print("Add a comment: ");
                String comment = io.read();

                Book updated = updateBookInformation(book, author, title, tags, courses, comment);
                if (bookDao.edit(updated)) {
                    io.print("The book information has been successfully updated.");
                } else {
                    System.out.println("Oops, something went wrong.");
                }
                go = false;
            }
        }
    }

    private Book updateBookInformation(Book book, String author, String title, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) {
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }

        if (!title.isEmpty()) {
            book.setTitle(title);
        }

        if (!tags.isEmpty()) {
            book.setTags(tags);
        }

        if (!courses.isEmpty()) {
            book.getCourses();
        }

        if (!comment.isEmpty()) {
            book.setComment(comment);
        }

        return book;
    }

    public Book fetchBookDetailsByIsbn(String isbn) {
        
        String cleanIsbn = isbn.replaceAll("[\\-\\s]", "");
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + cleanIsbn;

        try {
            String jsonData = Request.Get(url).execute().returnContent().asString();
            JSONObject obj = new JSONObject(jsonData);
            JSONArray arr = obj.getJSONArray("items");
            JSONObject obj2 = arr.getJSONObject(0);
            JSONObject info = obj2.getJSONObject("volumeInfo");
            String title = (String) info.get("title");

            JSONArray authors = info.getJSONArray("authors");
            String author = authorsToString(authors);

            return new Book(author, title);

        } catch (JSONException e) {
            io.print("Book not found");
            return null;
        } catch (IOException ex) {
            io.print("Could not retrieve information. Check your Internet connection.");
            return null;
        }
    }

    private String authorsToString(JSONArray authors) {

        ArrayList<String> list = new ArrayList<>();
        for (Object item : authors) {
            list.add(item.toString());
        }
        String authorsAsString = String.join(", ", list);
        return authorsAsString;
    }

    public List<Book> findByWord(String word) {
        try {
            if (bookDao.findByWord(word).size() == 0) {
                System.out.println("Is EMPTY!");
            }
            return bookDao.findByWord(word);
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
}
