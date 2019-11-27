package recommendations.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Scanner;
import recommendations.domain.Readable;
import recommendations.domain.Book;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.services.BookService;
import recommendations.services.LinkService;
import recommendations.services.TagService;

public class CommandLineUI {

    private Scanner reader;
    private BookService bookService;
    private LinkService linkService;
    private TagService tagService;

    public CommandLineUI(Scanner reader, BookService service, LinkService linkService, TagService tagService) {
        this.reader = reader;
        this.bookService = service;
        this.linkService = linkService;
        this.tagService = tagService;

    }

    public void start() throws Exception {

        System.out.println("Welcome!");
        boolean go = true;
        while (go) {
            System.out.println("\n1 --- List all recommendations");
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
                    listRecommendations();
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
        bookService.remove(title, reader);
    }

    private void listRecommendations() throws SQLException {
        System.out.println("\nRecommendations:\n");
        ArrayList<Readable> recommendations = new ArrayList<>();
        for (Book book : bookService.listBooks()) {
            recommendations.add(book);
        }
        for (Link link : linkService.listLinks()) {
            recommendations.add(link);
        }
       
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations yet. Be the first one to contribute!");
        } else {
            for (Readable recommendation : recommendations) {
                System.out.println("\t" + recommendation);
            }
        }
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
        ArrayList<Tag> tags = new ArrayList();
        ArrayList<Course> courses = new ArrayList();
        System.out.println("Add zero or more tags. Enter tags one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String tag = reader.nextLine();
            if (tag.equals("")) {
                break;
            }
            Tag newTag = new Tag(0, tag);
            tags.add(newTag);
        }
        System.out.println("Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String course = reader.nextLine();
            if (course.equals("")) {
                break;
            }
            Course newCourse = new Course(0, course);
            courses.add(newCourse);
        }
        System.out.print("Add a comment: ");
        String comment = reader.nextLine();

        bookService.addBook(new Book(0, author, title, type, isbn, tags, courses, comment));
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
        ArrayList<Tag> tags = new ArrayList();
        ArrayList<Course> courses = new ArrayList();
        System.out.println("Add zero or more tags. Enter tags one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String tag = reader.nextLine();
            if (tag.equals("")) {
                break;
            }
            Tag newTag = new Tag(0, tag);
            tags.add(newTag);
        }
        System.out.println("Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                + "to continue: ");

        while (true) {
            String course = reader.nextLine();
            if (course.equals("")) {
                break;
            }
            Course newCourse = new Course(0, course);
            courses.add(newCourse);
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

    private void searchByTag() throws SQLException {
        System.out.println("Added tags: ");
        listTags();
        System.out.println("");
        System.out.println("Please enter the name of the tag to search by: ");
        String name = reader.nextLine();

    }

}
