package recommendations.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.*;

import recommendations.domain.*;
import recommendations.domain.Readable;
import recommendations.io.IO;
import recommendations.services.*;

public class CommandLineUI {

    private BookService bookService;
    private LinkService linkService;
    private TagService tagService;
    private IO io;

    public CommandLineUI(BookService service, LinkService linkService, TagService tagService, IO io) {
        this.bookService = service;
        this.linkService = linkService;
        this.tagService = tagService;
        this.io = io;
    }

    public void start() throws Exception {

        io.print(Color.BOLD.getCode() + "Welcome!" + Color.ORIGINAL.getCode());
        boolean go = true;
        while (go) {
            io.print(Color.GREEN.getCode() + "\n1 --- List all recommendations" + Color.ORIGINAL.getCode());
            io.print(Color.CYAN.getCode() + "2 --- Add a new book" + Color.ORIGINAL.getCode());
            io.print(Color.GREEN.getCode() + "3 --- Remove a recommendation from the list" + Color.ORIGINAL.getCode());
            io.print(Color.CYAN.getCode() + "4 --- List all links" + Color.ORIGINAL.getCode());
            io.print(Color.GREEN.getCode() + "5 --- Add a new link" + Color.ORIGINAL.getCode());
            io.print(Color.CYAN.getCode() + "6 --- List tags" + Color.ORIGINAL.getCode());
            io.print(Color.GREEN.getCode() + "7 --- Search by tag" + Color.ORIGINAL.getCode());
            io.print(Color.CYAN.getCode() + "8 --- Edit recommendation" + Color.ORIGINAL.getCode());
            io.print(Color.GREEN.getCode() + "9 --- Find a recommendation by word" + Color.ORIGINAL.getCode());
            io.print(Color.CYAN.getCode() + "q --- Quit" + Color.ORIGINAL.getCode());
            io.print(Color.BOLD.getCode() + "Please enter the number corresponding to the command (i.e. 1, 2, ..., 9) or q to stop the application." + Color.ORIGINAL.getCode());
            String choice = io.read();

            switch (choice) {
                case "q":
                    go = false;
                    break;
                case "1":
                    listRecommendations();
                    break;
                case "2":
                    addBook();
                    break;
                case "3":
                    removeRecommendation();
                    break;
                case "4":
                    listLinks();
                    break;
                case "5":
                    addLink();
                    break;
                case "6":
                    listTags();
                    break;
                case "7":
                    searchByTag();
                    break;
                case "8":
                    editRecommendation();
                    break;
                case "9":
                    findRecommendationByWord();
                    break;
                default:
            }
        }
        io.print("\nThanks for using Recommendations! Have a nice day!");
    }

    private void findRecommendationByWord() throws SQLException {
        io.print(Color.CYAN.getCode() + "Please enter the word to search by: " + Color.ORIGINAL.getCode());
        String word = io.read();
        io.print(bookService.findByWord(word));
        io.print(linkService.findByWord(word));
    }

    private void editRecommendation() throws SQLException {
        io.print(Color.CYAN.getCode() + "\nPlease specify which recommendation type you would like to edit: book/link" + Color.ORIGINAL.getCode());
        io.print("To return back to main menu, enter 'q'");
        String input = io.read();
        String cleanInput = readInput(input);
        if (cleanInput.equals("q")) {
            return;
        } else if (cleanInput.equals("book")) {
            io.print(Color.CYAN.getCode() + "\nPlease enter the title of the book to be modified: " + Color.ORIGINAL.getCode());
            String title = io.read();
            bookService.edit(title);
        } else if (cleanInput.equals("link")) {
            io.print(Color.CYAN.getCode() + "\nPlease enter the title of the link to be modified: " + Color.ORIGINAL.getCode());
            String title = io.read();
            linkService.edit(title);
        } else {
            io.print(Color.RED.getCode() + "Could not recognize given command, please check the spelling." + Color.ORIGINAL.getCode());
            editRecommendation();
        }
    }

    private void listLinks() throws SQLException {
        ArrayList<Link> links = linkService.listLinks();
        for (Link l : links) {
            io.print(l.toString() + "\n");
            io.print(Color.CYAN.getCode() + "Do you want to open this link in your browser (y/n)?" + Color.ORIGINAL.getCode());
            String opn = io.read();
            if (opn.equals("y")) {
                openLinkInBrowser(l);
            }
        }
    }

    private void openLinkInBrowser(Link link) {
        String url = link.getURL();
        // if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void removeRecommendation() throws Exception {
        io.print(Color.CYAN.getCode() + "\nPlease specify which recommendation type you would like to remove: book/link" + Color.ORIGINAL.getCode());
        io.print("To return back to main menu, enter 'q'");
        String input = io.read();
        String cleanInput = readInput(input);
        if (cleanInput.equals("q")) {
            return;
        } else if (cleanInput.equals("book")) {
            listTitles(input);
            io.print(Color.CYAN.getCode() + "\nPlease enter the title of the book to be removed: " + Color.ORIGINAL.getCode());
            String title = io.read();
            bookService.remove(title);
        } else if (cleanInput.equals("link")) {
            listTitles(input);
            io.print(Color.CYAN.getCode() + "\nPlease enter the title of the link to be removed: " + Color.ORIGINAL.getCode());
            String title = io.read();
            linkService.remove(title);
        } else {
            io.print(Color.RED.getCode() + "Could not recognize given command, please check the spelling." + Color.ORIGINAL.getCode());
            removeRecommendation();
        }
    }

    private void listTitles(String input) throws SQLException {
        if (input.equals("book")) {
            io.print("");
            io.print(bookService.listBookTitles());
        } else if (input.equals("link")) {
            io.print("");
            io.print(linkService.listLinkTitles());
        }
    }

    private String readInput(String input) {
        String cleanInput = input.trim().toLowerCase();
        return cleanInput;
    }

    private void listRecommendations() throws SQLException {
        io.print("\nRecommendations:\n");
        ArrayList<Book> bookRecommendations = new ArrayList<>();
        ArrayList<Link> linkRecommendations = new ArrayList<>();
        for (Book book : bookService.listBooks()) {
            bookRecommendations.add(book);
        }
        for (Link link : linkService.listLinks()) {
            linkRecommendations.add(link);
        }

        if (linkRecommendations.isEmpty() && bookRecommendations.isEmpty()) {
            io.print("No recommendations yet. Be the first one to contribute!");
        } else {
            if (!bookRecommendations.isEmpty()) {
                for (Book recommendation : bookRecommendations) {
                    io.print(Color.BLACK.getCode() + Color.GREENBG.getCode() + "\t" + recommendation);
                }
                io.print(Color.ORIGINAL.getCode());
            }
            if (!linkRecommendations.isEmpty()) {
                for (Link recommendation : linkRecommendations) {
                    io.print(Color.WHITE.getCode() + Color.BLUEBG.getCode() + "\t" + recommendation);
                }
                io.print(Color.ORIGINAL.getCode());
            }
        }
    }

    private void addBook() throws IOException, SQLException {
        getAdviceText();
        String title = "";
        String author = "";
        io.print(Color.CYAN.getCode() + "ISBN: " + Color.ORIGINAL.getCode());
        String isbn = io.read();
        Book book = null;
        if (!isbn.isEmpty()) {
            book = bookService.fetchBookDetailsByIsbn(isbn);
            if (book != null) {
                title = book.getTitle();
                author = book.getAuthor();
                io.print("Title:\n " + title);
                io.print("Author(s):\n " + author + "\n");
                Book existingBook = bookService.findBookWithTitle(title);
                if (existingBook != null) {
                    io.print(Color.RED.getCode() + "There is already one book with same title. Please modify the title a little\n" + Color.ORIGINAL.getCode());
                    title = askForTitle();
                }
                if (title.equals("")) {
                    title = book.getTitle();
                }
            }
        }
        if (isbn.equals("") || book == null) {
            title = askForTitle();
            isbn = "";
            io.print(Color.CYAN.getCode() + "Author(s): " + Color.ORIGINAL.getCode());
            author = io.read();
        }
        ArrayList<Tag> tags = askForTags();
        ArrayList<Course> courses = askForCourses();

        io.print(Color.CYAN.getCode() + "Add a comment: " + Color.ORIGINAL.getCode());
        String comment = io.read();

        Boolean isAdded = bookService.addBook(new Book(0, author, title, "Book", isbn, tags, courses, comment));
        if (isAdded == true) {
            io.print(Color.GREEN.getCode() + "A new book recommendation was added successfully!" + Color.ORIGINAL.getCode());
        } else {
            io.print(Color.RED.getCode() + "Your book recommendation was not added. Another book with same title exists already" + Color.ORIGINAL.getCode());
        }
    }

    private void addLink() throws IOException, SQLException, MalformedURLException, URISyntaxException {

        io.print("\nAdd a new Link");
        io.print(Color.CYAN.getCode() + "Url: " + Color.ORIGINAL.getCode());
        String url = io.read();

        if (!validateUrl(url)) {
            io.print(Color.RED.getCode() + "Url validation failed! Remember to include a protocol and check for typos." + Color.ORIGINAL.getCode());
            return;
        }

        if (!checkConnection(url)) {
            io.print(Color.CYAN.getCode() + "Do you want to continue? (y/n)" + Color.ORIGINAL.getCode());
            String opt = io.read();
            if (opt.endsWith("n")) {
                return;
            }
        }

        String title = linkService.fetchTitle(url);
        if (title.isEmpty()) {
            title = askForTitle();
        } else {
            title = modifyTitle(title);
        }

        io.print(Color.CYAN.getCode() + "Type: " + Color.ORIGINAL.getCode());
        String type = io.read();
        ArrayList<Tag> tags = askForTags();

        ArrayList<Course> courses = askForCourses();

        io.print(Color.CYAN.getCode() + "Add a comment: " + Color.ORIGINAL.getCode());
        String comment = io.read();

        boolean createNew = linkService.addLinkWithMeta(0, title, url, type, tags, courses, comment);
        if (createNew) {
            io.print(Color.GREEN.getCode() + "A new link recommendation was added successfully!" + Color.ORIGINAL.getCode());
        } else {
            io.print(Color.RED.getCode() + "Please, check your input and try again!" + Color.ORIGINAL.getCode());
        }
    }

    private void listTags() throws SQLException {
        if (tagService.listTags().isEmpty()) {
            io.print("No added tags.");
        } else {
            io.print(Color.CYAN.getCode() + "\nTags:\n" + Color.ORIGINAL.getCode());
            List<Tag> tags = tagService.listTags();
            tags.forEach(tag -> {
                io.print("\t" + tag.toString());
            });
        }
    }

    private void searchByTag() throws SQLException {
        io.print("Added tags: ");
        listTags();
        io.print("");
        io.print(Color.CYAN.getCode() + "Please enter the name of the tag to search by: " + Color.ORIGINAL.getCode());
        String name = io.read();
        tagService.findRecommendadtionsByTag(name);
    }

    public boolean checkConnection(String url) {
        try {
            URL check = new URL(url);
            URLConnection connection = check.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            io.print(Color.RED.getCode() + "Could not reach " + url + Color.ORIGINAL.getCode());
            return false;
        } catch (IOException e) {
            io.print(Color.RED.getCode() + "Could not reach " + url + Color.ORIGINAL.getCode());
            return false;
        }

    }

    public boolean validateUrl(String url) {

        try {
            URL check = new URL(url);
            check.toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }
    }

    private ArrayList<Tag> askForTags() {
        io.print(Color.CYAN.getCode() + "Add zero or more tags. Enter tags one at a time. Press 'enter'"
                + "to continue: " + Color.ORIGINAL.getCode());
        ArrayList<Tag> tags = new ArrayList<>();
        while (true) {
            String tag = io.read();
            if (tag.equals("")) {
                break;
            }
            Tag newTag = new Tag(0, tag);
            tags.add(newTag);
        }
        return tags;
    }

    private ArrayList<Course> askForCourses() {
        io.print(Color.CYAN.getCode() + "Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                + "to continue: " + Color.ORIGINAL.getCode());
        ArrayList<Course> courses = new ArrayList<>();
        while (true) {
            String course = io.read();
            if (course.equals("")) {
                break;
            }
            Course newCourse = new Course(0, course);
            courses.add(newCourse);
        }
        return courses;
    }

    private String modifyTitle(String title) {
        io.print("Title: " + title);
        io.print(Color.CYAN.getCode() + "Do you want to modify the title? (y/n)" + Color.ORIGINAL.getCode());
        if (io.read().equals("n")) {
            return title;
        }
        io.print(Color.CYAN.getCode() + "Title: " + Color.ORIGINAL.getCode());
        title = io.read();
        return title;
    }

    private String askForTitle() {
        io.print(Color.CYAN.getCode() + "Title: " + Color.ORIGINAL.getCode());
        String title = io.read();
        return title;
    }

    private void getAdviceText() throws SQLException {
        io.print("\nAdd a new Book");
        io.print(Color.BOLD.getCode() + "Please note, that the system allows only unique titles. Existing titles:\n" + Color.ORIGINAL.getCode());
        listTitles("book");

        io.print("By giving ISBN, Title and Author(s) are fetched automatically, if exists");
        io.print("If you want to skip this, just press enter without adding anything");
    }

}
