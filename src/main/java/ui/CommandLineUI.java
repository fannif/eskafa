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
        

        while (true) {
            System.out.println("\n1 --- List all recommended books");
            System.out.println("2 --- Add a new book");
            System.out.println("q --- Quit");
            System.out.println("Select 1, 2 or q");
            String choice = reader.nextLine();

            if (choice.equals("q")) {
                break;
            }

            if (choice.equals("1")) {
                listBooks();
            }

            if (choice.equals("2")) {
                addBook();
            }

        }

        System.out.println("\nThanks for using Recommendations! Have a nice day!");
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
