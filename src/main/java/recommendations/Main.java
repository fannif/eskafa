package recommendations;

import java.io.IOException;
import recommendations.ui.CommandLineUI;
import recommendations.services.BookService;
import recommendations.dao.fileDao;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner reader = new Scanner(System.in);
        fileDao filedao = new fileDao();
        //BookService service = new BookService(new fileDao());
        BookService service = new BookService(filedao);
        CommandLineUI ui = new CommandLineUI(reader, service);

        ui.start();

    }

}
