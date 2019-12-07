package recommendations.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

import recommendations.domain.Book;
import recommendations.dao.ReaderDao;
import recommendations.domain.Color;
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

    public boolean addBook(Book book) throws IOException, SQLException {
        return bookDao.save(book);
    }

    public Book findBookWithTitle(String title) throws SQLException {
        Book book = (Book) bookDao.findOne(title);
        return book;
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
                io.print(Color.CYAN.getCode() + "No such book found. Please check the spelling and try again: " + Color.ORIGINAL.getCode());
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                bookDao.delete(input);
                go = false;
            }
        }
        io.print(Color.GREEN.getCode() + "The book has been successfully removed" + Color.ORIGINAL.getCode());
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
                io.print(Color.CYAN.getCode() + "No such book found. Please check the spelling and try again: " + Color.ORIGINAL.getCode());
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                io.print("Book found: \n\t" + book);
                System.out.println("Please update a field or fields. Press enter to skip the field.");
                io.print(Color.CYAN.getCode() + "Title: " + Color.ORIGINAL.getCode());
                String title = io.read();
                io.print(Color.CYAN.getCode() + "Author: " + Color.ORIGINAL.getCode());
                String author = io.read();
                ArrayList<Tag> tags = new ArrayList();
                ArrayList<Course> courses = new ArrayList();
                io.print(Color.CYAN.getCode() + "Add zero or more tags. Enter tags one at a time. Press 'enter'"
                        + "to continue: " + Color.ORIGINAL.getCode());

                while (true) {
                    String tag = io.read();
                    if (tag.equals("")) {
                        break;
                    }
                    Tag newTag = new Tag(0, tag);
                    tags.add(newTag);
                }
                io.print(Color.CYAN.getCode() + "Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                        + "to continue: " + Color.ORIGINAL.getCode());

                while (true) {
                    String course = io.read();
                    if (course.equals("")) {
                        break;
                    }
                    Course newCourse = new Course(0, course);
                    courses.add(newCourse);
                }
                io.print(Color.CYAN.getCode() + "Add a comment: " + Color.ORIGINAL.getCode());
                String comment = io.read();

                Book updated = updateBookInformation(book, author, title, tags, courses, comment);
                if (bookDao.edit(updated)) {
                    io.print(Color.GREEN.getCode() + "The book information has been successfully updated." + Color.ORIGINAL.getCode());
                } else {
                    System.out.println(Color.GREEN.getCode() + "The book you are trying to add is already in the database." + Color.ORIGINAL.getCode());
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
            book.setCourses(courses);
        }

        if (!comment.isEmpty()) {
            book.setComment(comment);
        }

        return book;
    }

    public Book fetchBookDetailsByIsbn(String isbn) {

        String cleanIsbn = isbn.replaceAll("[\\-\\s]", "");
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + cleanIsbn;
        if (!connected(url)) {
            return null;
        }

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
            io.print(Color.RED.getCode() + "Book not found" + Color.ORIGINAL.getCode());
            return null;
        } catch (IOException ex) {
            io.print(Color.RED.getCode() + "Could not retrieve information. Check your Internet connection." + Color.ORIGINAL.getCode());
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

    public String findByWord(String word) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(Color.GREEN.getCode() + "\nBooks found by word " + Color.BOLD.getCode() + "'" + word + "':" + Color.ORIGINAL.getCode());
        sb.append("\n");
        ArrayList<Book> books = (ArrayList<Book>) bookDao.findByWord(word);
        if (books.isEmpty()) {
            return sb.append("None :(\nPlease try another word.\n").toString();
        }
        for (Book book : books) {
            sb.append(book.toString());
        }
        return sb.toString();
    }

    public String listBookTitles() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(Color.CYAN.getCode() + "Added books:" + Color.ORIGINAL.getCode() + "\n");
        sb.append("\n");
        for (Book book : this.listBooks()) {
            sb.append(String.format("%-5s %-5s\n", " ", book.getTitle()));
        }
        return sb.toString();
    }
    
    public boolean connected(String url) {
        try {
            URL check = new URL(url);
            URLConnection connection = check.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            io.print(Color.RED.getCode() + "No connection" + Color.ORIGINAL.getCode());
            return false;
        } catch (IOException e) {
            io.print(Color.RED.getCode() + "No connection" + Color.ORIGINAL.getCode());
            return false;
        }

    }
}
