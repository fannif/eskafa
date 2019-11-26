package recommendations.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Scanner;
import recommendations.domain.Book;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.services.BookService;
import recommendations.services.LinkService;
import recommendations.services.TagService;

public class CommandLineUI {

    private Scanner reader;
    private BookService service;
    private LinkService linkService;
    private TagService tagService;

    public CommandLineUI(Scanner reader, BookService service, LinkService linkService, TagService tagService) {
        this.reader = reader;
        this.service = service;
        this.linkService = linkService;
        this.tagService = tagService;

    }

    public void start() throws Exception {

        System.out.println("Welcome!");
        boolean go = true;
        while (go) {
            System.out.println("\n1 --- List all recommended books");
            System.out.println("2 --- Add a new book");
            System.out.println("3 --- Remove a book from recommendations");
            System.out.println("4 --- List all links");
            System.out.println("5 --- Add a new link");
            System.out.println("6 --- List tags");
            System.out.println("q --- Quit");
            System.out.println("Select 1, 2, 3, 4, 5, 6 or q");
            String choice = reader.nextLine();

            switch (choice) {
                case "q":
                    go = false;
                    break;
                case "1":
                    listBooks();
                    break;
                case "2":
                    addBook();
                    break;
                case "3":
                    removeBook();
                    break;
                case "4":
                    listLinks();
                    break;
                case "5":
                    addLink();
                    break;
                case "6":
                    listTags();
                default:
            }
        }
        System.out.println("\nThanks for using Recommendations! Have a nice day!");
    }
    
    private void listLinks() throws SQLException {
        ArrayList<Link> links = linkService.list();
        for (Link l : links) {
            System.out.println(l.toString() + "\n");
        }
    }

    private void removeBook() throws Exception {
        System.out.println("\nPlease enter the title of the book to be removed: ");
        String title = reader.nextLine();
        service.remove(title, reader);
    }

    private void listBooks() throws SQLException {
        System.out.println("\nRecommendations:\n");
        if (service.listBooks().isEmpty()) {
            System.out.println("No recommendations yet. Be the first one to contribute!");
        }

        service.listBooks().forEach((s) -> {
            System.out.println("\t" + s);
        });

    }

    private void addBook() throws IOException, SQLException {
        System.out.println("\nAdd a new Book");
        System.out.print("Title: ");
        String title = reader.nextLine();
        System.out.print("Author: ");
        String author = reader.nextLine();
        System.out.print("ISBN: ");
        String isbn = reader.nextLine();
        System.out.print("Type: ");
        String type = reader.nextLine();
        ArrayList<String> tags = new ArrayList();
        ArrayList<String> courses = new ArrayList();
        System.out.println("Add zero or more tags. Enter tags one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String tag = reader.nextLine();
            if (tag.equals("")) {
                break;
            }
            tags.add(tag);
        }
        System.out.println("Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String course = reader.nextLine();
            if (course.equals("")) {
                break;
            }
            courses.add(course);
        }
        System.out.print("Add a comment: ");
        String comment = reader.nextLine();

        service.addBook(new Book(0, author, title, type, isbn, tags, courses, comment));
        System.out.println("A new book recommendation was added successfully!");
    }

    private void addLink() throws IOException, SQLException {
        // Sit ku on metadata toiminto, täytyy poistaa
       // String metadata = "";

        System.out.println("\nAdd a new Link");
        System.out.print("Title: ");
        String title = reader.nextLine();
        System.out.print("Url: ");
        String url = reader.nextLine();
        System.out.print("Type: ");
        String type = reader.nextLine();
        ArrayList<String> tags = new ArrayList();
        ArrayList<String> courses = new ArrayList();
        System.out.println("Add zero or more tags. Enter tags one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String tag = reader.nextLine();
            if (tag.equals("")) {
                break;
            }
            tags.add(tag);
        }
        System.out.println("Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String course = reader.nextLine();
            if (course.equals("")) {
                break;
            }
            courses.add(course);
        }
        System.out.print("Add a comment: ");
        String comment = reader.nextLine();

        // linkService.addLink(new Link(0, title, url, type, metadata, tags, courses, comment));
        Boolean createNew = linkService.addLinkWithMeta(0, title, url, type, tags, courses, comment);
        if (createNew) {
            System.out.println("A new link recommendation was added successfully!");
        } else {
            System.out.println("Error error");
        }
    }

    private void listTags() throws SQLException {
        if (tagService.listTags().isEmpty()) {
            System.out.println("No added tags.");
        } else {
            System.out.println("\nTags:\n");
            List<Tag> tags = tagService.listTags();
            tags.forEach(tag -> {
                System.out.println("\t" + tag.toString());
            });
        }
    }

}
