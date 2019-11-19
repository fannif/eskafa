package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineUI {

    private Scanner reader;
    private ArrayList<String> recommendations;

    public CommandLineUI(Scanner reader) {
        this.reader = reader;
        this.recommendations = new ArrayList<>();
    }

    public void start() {

        System.out.println("Welcome!");
        
        boolean go = true;
        while (go) {
            System.out.println("\n1 --- List all recommended books");
            System.out.println("2 --- Add a new book");
            System.out.println("3 --- Remove a book from recommendatitons");
            System.out.println("q --- Quit");
            System.out.println("Select 1, 2, 3 or q");
            String choice = reader.nextLine();

            switch(choice) {
                case "q":
                    go=false;
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
                default:
            }
        }

        System.out.println("\nThanks for using Recommendations! Have a nice day!");
    }

    private void removeBook() {
        System.out.println("\nPlease enter the title of the book to be removed: ");
        String title = reader.nextLine();
        /* Sit kun on olemassa Book
        if(fileDao.findOne(title) == null) {
            System.out.println("No such book found. Please check the spelling.");
        } else {
            fileDao.delete(title);
            System.out.println("The book has been successfully removed");
        }
        */
        System.out.println("Nothing happens yet!");
    }

    private void listBooks() {
        System.out.println("\nRecommendations:");
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations yet. Be the first one to contribute!");
        }
        
        recommendations.forEach((s) -> {
            System.out.println(s);
        });

    }

    private void addBook() {
        System.out.println("\nAdd a new Book");
        System.out.print("Title: ");
        String title = reader.nextLine();

        System.out.print("Author: ");
        String author = reader.nextLine();

        System.out.println("ISBN: ");
        String isbn = reader.nextLine();

        recommendations.add(title);
        System.out.println("A new book recommendation was added successfully!");

    }

}
