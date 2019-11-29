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
import java.util.Scanner;
import recommendations.domain.Readable;
import recommendations.domain.Book;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.io.IO;
import recommendations.services.BookService;
import recommendations.services.LinkService;
import recommendations.services.TagService;

public class CommandLineUI {

    private Scanner reader;
    private BookService bookService;
    private LinkService linkService;
    private TagService tagService;
    private IO io;

    public CommandLineUI(Scanner reader, BookService service, LinkService linkService, TagService tagService, IO io) {
        this.reader = reader;
        this.bookService = service;
        this.linkService = linkService;
        this.tagService = tagService;
        this.io = io;
    }

    public void start() throws Exception {

        io.print("Welcome!");
        boolean go = true;
        while (go) {
            io.print("\n1 --- List all recommendations");
            io.print("2 --- Add a new book");
            io.print("3 --- Remove a recommendation from the list");
            io.print("4 --- List all links");
            io.print("5 --- Add a new link");
            io.print("6 --- List tags");
            io.print("7 --- Search by tag");
            io.print("q --- Quit");
            io.print("Select 1, 2, 3, 4, 5, 6 or q");
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
                default:
            }
        }
        io.print("\nThanks for using Recommendations! Have a nice day!");
    }

    private void listLinks() throws SQLException {
        ArrayList<Link> links = linkService.listLinks();
        for (Link l : links) {
            io.print(l.toString() + "\n");
            io.print("Do you want to open this link in your browser (y/n)?");
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

//        } else {
//            Runtime runtime = Runtime.getRuntime();
//            try {
//                runtime.exec("xdg-open " + url);
//            } catch (IOException e) {
//               
//                e.printStackTrace();
//            }
//        }
    }

    private void removeRecommendation() throws Exception {
        io.print("\nPlease spesify which recommendation type you would like to remove: book/link");
        io.print("To return back to main menu, enter 'q'");
        String input = io.read();
        String cleanInput = readInput(input);
        if (cleanInput.equals("q")) {
            return;
        } else if (cleanInput.equals("book")) {
            io.print("\nPlease enter the title of the book to be removed: ");
            String title = io.read();
            bookService.remove(title);
        } else if (cleanInput.equals("link")) {
            io.print("\nPlease enter the title of the link to be removed: ");
            String title = io.read();
            linkService.remove(title);
        } else {
            io.print("Could not recognize given command, please check the spelling.");
            removeRecommendation();
        }
    }

    private String readInput(String input) {
        String cleanInput = input.trim().toLowerCase();
        return cleanInput;
    }

    private void listRecommendations() throws SQLException {
        io.print("\nRecommendations:\n");
        ArrayList<Readable> recommendations = new ArrayList<>();
        for (Book book : bookService.listBooks()) {
            recommendations.add(book);
        }
        for (Link link : linkService.listLinks()) {
            recommendations.add(link);
        }

        if (recommendations.isEmpty()) {
            io.print("No recommendations yet. Be the first one to contribute!");
        } else {
            for (Readable recommendation : recommendations) {
                io.print("\t" + recommendation);
            }
        }
    }

    private void addBook() throws IOException, SQLException {
        io.print("\nAdd a new Book");
        io.print("Title: ");
        String title = io.read();
        io.print("Author: ");
        String author = io.read();
        io.print("ISBN: ");
        String isbn = io.read();
        io.print("Type: ");
        String type = io.read();
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

        bookService.addBook(new Book(0, author, title, type, isbn, tags, courses, comment));
        io.print("A new book recommendation was added successfully!");
    }

    private void addLink() throws IOException, SQLException, MalformedURLException, URISyntaxException {

        io.print("\nAdd a new Link");
        io.print("Url: ");
        String url = io.read();

        if (!validateUrl(url)) {
            io.print("Url validation failed! Remember to include a protocol and check for typos.");
            return;
        }

        if (!checkConnection(url)) {
            io.print("Do you want to continue? (y/n)");
            String opt = io.read();
            if (opt.endsWith("n")) {
                return;
            }
        }

        io.print("Title: ");
        String title = io.read();
        io.print("Type: ");
        String type = io.read();
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

        boolean createNew = linkService.addLinkWithMeta(0, title, url, type, tags, courses, comment);
        if (createNew) {
            io.print("A new link recommendation was added successfully!");
        } else {
            io.print("Please, check your input and try again!");
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
        io.print("Please enter the name of the tag to search by: ");
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
            io.print("No internet connection");
            return false;
        } catch (IOException e) {
            io.print("No internet connection");
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

}
