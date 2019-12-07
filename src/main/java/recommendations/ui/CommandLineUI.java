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
import recommendations.domain.Book;
import recommendations.domain.Readable;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.io.IO;
import recommendations.services.*;

public class CommandLineUI {

    private BookService bookService;
    private LinkService linkService;
    private TagService tagService;
    private IO io;
    private final String red = "\u001B[91m";
    private final String green = "\u001b[32;1m";
    private final String cyan = "\u001b[36;1m";
    private final String black = "\u001B[30;1m";
    private final String original = "\u001B[0m";
    private final String bold = "\u001b[37;1m";
    private final String greenBg = "\u001B[102m";
    private final String blueBg = "\u001B[104m";

    public CommandLineUI(BookService service, LinkService linkService, TagService tagService, IO io) {
        this.bookService = service;
        this.linkService = linkService;
        this.tagService = tagService;
        this.io = io;
    }

    public void start() throws Exception {

        io.print(bold + "Welcome!" + original);
        boolean go = true;
        while (go) {
            io.print(green + "\n1 --- List all recommendations" + original);
            io.print(cyan + "2 --- Add a new book" + original);
            io.print(green + "3 --- Remove a recommendation from the list" + original);
            io.print(cyan + "4 --- List all links" + original);
            io.print(green + "5 --- Add a new link" + original);
            io.print(cyan + "6 --- List tags" + original);
            io.print(green + "7 --- Search by tag" + original);
            io.print(cyan + "8 --- Edit recommendation" + original);
            io.print(green + "9 --- Find a recommendation by word" + original);
            io.print(cyan + "q --- Quit" + original);
            io.print(bold + "Please enter the number corresponding to the command (i.e. 1, 2, ..., 9) or q to stop the application." + original);
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
        io.print(cyan+"Please enter the word to search by: "+original);
        String word = io.read();
        io.print(bookService.findByWord(word));
        io.print(linkService.findByWord(word));
    }

    private void editRecommendation() throws SQLException {
        io.print(cyan+"\nPlease specify which recommendation type you would like to edit: book/link"+original);
        io.print("To return back to main menu, enter 'q'");
        String input = io.read();
        String cleanInput = readInput(input);
        if (cleanInput.equals("q")) {
            return;
        } else if (cleanInput.equals("book")) {
            io.print(cyan +"\nPlease enter the title of the book to be modified: "+original);
            String title = io.read();
            bookService.edit(title);
        } else if (cleanInput.equals("link")) {
            io.print(cyan +"\nPlease enter the title of the link to be modified: "+original);
            String title = io.read();
            linkService.edit(title);
        } else {
            io.print(red + "Could not recognize given command, please check the spelling." + original);
            editRecommendation();
        }
    }

    private void listLinks() throws SQLException {
        ArrayList<Link> links = linkService.listLinks();
        for (Link l : links) {
            io.print(l.toString() + "\n");
            io.print(cyan+"Do you want to open this link in your browser (y/n)?"+original);
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
        io.print(cyan+"\nPlease specify which recommendation type you would like to remove: book/link"+original);
        io.print("To return back to main menu, enter 'q'");
        String input = io.read();
        String cleanInput = readInput(input);
        if (cleanInput.equals("q")) {
            return;
        } else if (cleanInput.equals("book")) {
            listTitles(input);
            io.print(cyan+"\nPlease enter the title of the book to be removed: "+original);
            String title = io.read();
            bookService.remove(title);
        } else if (cleanInput.equals("link")) {
            listTitles(input);
            io.print(cyan+"\nPlease enter the title of the link to be removed: "+original);
            String title = io.read();
            linkService.remove(title);
        } else {
            io.print(red + "Could not recognize given command, please check the spelling." + original);
            removeRecommendation();
        }
    }

    private void listTitles(String input) throws SQLException {
        if (input.equals("book")) {
            io.print(bookService.listBookTitles());
        } else if (input.equals("link")) {
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
                for (Readable recommendation : bookRecommendations) {
                    io.print(black + greenBg + "\t" + recommendation);
                }
                io.print(original);
            }
            if (!linkRecommendations.isEmpty()) {
                for (Readable recommendation : linkRecommendations) {
                    io.print(black + blueBg + "\t" + recommendation);
                }
                io.print(original);
            }
        }
    }

    private void addBook() throws IOException, SQLException {
        getAdviceText();
        String title = "";
        String author = "";
        io.print(cyan+"ISBN: "+original);
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
                    io.print(red+"There is already one book with same title. Please modify the title a little\n"+original);
                    title = askForTitle();
                }
                if (title.equals("")) {
                    title = book.getTitle();
                }
            }
        }
        if (isbn.equals("") || book == null) {
            title = askForTitle();
            io.print(cyan+"Author(s): "+original);
            author = io.read();
        }
        ArrayList<Tag> tags = askForTags();
        ArrayList<Course> courses = askForCourses();

        io.print(cyan+"Add a comment: "+original);
        String comment = io.read();

        Boolean isAdded = bookService.addBook(new Book(0, author, title, "Book", isbn, tags, courses, comment));
        if (isAdded == true) {
            io.print(green+"A new book recommendation was added successfully!"+original);
        } else {
            io.print(red + "Your book recommendation was not added. Another book with same title exists already" + original);
        }
    }

    private void addLink() throws IOException, SQLException, MalformedURLException, URISyntaxException {

        io.print("\nAdd a new Link");
        io.print(cyan+"Url: "+original);
        String url = io.read();

        if (!validateUrl(url)) {
            io.print(red + "Url validation failed! Remember to include a protocol and check for typos." + original);
            return;
        }

        if (!checkConnection(url)) {
            io.print(cyan+"Do you want to continue? (y/n)"+original);
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

        io.print(cyan +"Type: "+original);
        String type = io.read();
        ArrayList<Tag> tags = askForTags();

        ArrayList<Course> courses = askForCourses();

        io.print(cyan+"Add a comment: "+original);
        String comment = io.read();

        boolean createNew = linkService.addLinkWithMeta(0, title, url, type, tags, courses, comment);
        if (createNew) {
            io.print(green+"A new link recommendation was added successfully!"+original);
        } else {
            io.print(red + "Please, check your input and try again!" + original);
        }
    }

    private void listTags() throws SQLException {
        if (tagService.listTags().isEmpty()) {
            io.print("No added tags.");
        } else {
            io.print("\nTags:\n");
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
        io.print(cyan +"Please enter the name of the tag to search by: "+ original);
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
            io.print(red + "No internet connection" + original);
            return false;
        } catch (IOException e) {
            io.print(red + "No internet connection" + original);
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
        io.print(cyan + "Add zero or more tags. Enter tags one at a time. Press 'enter'"
                + "to continue: "+ original);
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
        io.print(cyan + "Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                + "to continue: "+ original);
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
        io.print(cyan+"Do you want to modify the title? (y/n)"+original);
        if (io.read().equals("n")) {
            return title;
        }
        io.print(cyan+"Title: "+original);
        title = io.read();
        return title;
    }

    private String askForTitle() {
        io.print(cyan+"Title: "+original);
        String title = io.read();
        return title;
    }

    private void getAdviceText() throws SQLException {
        io.print("\nAdd a new Book");
        io.print(bold+"Please note, that the system allows only unique titles. Existing titles:\n"+original);
        listTitles("book");

        io.print("By giving ISBN, Title and Author(s) are fetched automatically, if exists");
        io.print("If you want to skip this, just press enter without adding anything");
    }

}
