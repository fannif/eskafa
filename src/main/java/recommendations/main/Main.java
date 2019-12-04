package recommendations.main;

import java.io.IOException;

import recommendations.dao.LinkDao;
import recommendations.services.LinkService;
import recommendations.ui.CommandLineUI;
import recommendations.services.BookService;
import recommendations.dao.BookDao;
import java.util.Scanner;
import recommendations.dao.Database;
import recommendations.dao.TagDao;
import recommendations.io.CommandLineIO;
import recommendations.services.TagService;

public class Main {

    public static void main(String[] args) throws Exception {

        Database db = new Database("jdbc:sqlite:recommendations.db");
        
        Scanner reader = new Scanner(System.in);
        CommandLineIO io = new CommandLineIO(reader);
        BookDao bookdao = new BookDao(db);
        LinkDao linkDao = new LinkDao(db);
        TagDao tagDao = new TagDao(db);
        //BookService service = new BookService(new fileDao());
        BookService service = new BookService(bookdao, io);
        LinkService linkService = new LinkService(linkDao, io);
        TagService tagService = new TagService(tagDao, bookdao, linkDao, io);
        CommandLineUI ui = new CommandLineUI(service, linkService, tagService, io);

        ui.start();

    }

}
