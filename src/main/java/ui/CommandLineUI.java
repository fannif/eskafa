
package ui;

import java.util.Scanner;

public class CommandLineUI {
    private Scanner reader;

    public CommandLineUI(Scanner reader) {
        this.reader = reader;
    }

    public void start() {
        
        System.out.println("Welcome!");
        System.out.println("1 --- List all recommended books");
        System.out.println("2 --- Add a new book");
        System.out.println("q --- Quit");

        while (true) {
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

        System.out.println("Thanks for using Recommendations! Have a nice day!");
    }
   
    
    private static void listBooks() {
        System.out.println("Järjestelmässä ei ole vielä kirjoja...");
    }

    private static void addBook() {
        System.out.println("Not supported yet...");
    }
    
    
}
