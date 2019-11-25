package recommendations;

import java.io.IOException;
import recommendations.ui.CommandLineUI;
import recommendations.services.BookService;
import recommendations.dao.bookDao;
import java.util.Scanner;
import recommendations.dao.Database;

public class Main {

    public static void main(String[] args) throws Exception {

        Database db = new Database("jdbc:sqlite:recommendations.db");
        
        Scanner reader = new Scanner(System.in);
        bookDao bookdao = new bookDao(db);
        //BookService service = new BookService(new fileDao());
        BookService service = new BookService(bookdao);
        CommandLineUI ui = new CommandLineUI(reader, service);

        ui.start();

    }

}
