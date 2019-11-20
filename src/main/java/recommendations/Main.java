package recommendations;

import recommendations.ui.CommandLineUI;
import recommendations.services.BookService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        //BookService service = new BookService(new fileDao());
        BookService service = new BookService();
        CommandLineUI ui = new CommandLineUI(reader, service);

        ui.start();

    }

}
