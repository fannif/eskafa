package recommendations;

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
        BookDao bookdao = new BookDao(db);
        LinkDao linkDao = new LinkDao(db);
        TagDao tagDao = new TagDao(db);
        //BookService service = new BookService(new fileDao());
        BookService service = new BookService(bookdao);
        LinkService linkService = new LinkService(linkDao);
        TagService tagService = new TagService(tagDao, bookdao, linkDao);
        CommandLineIO io = new CommandLineIO(reader);
        CommandLineUI ui = new CommandLineUI(reader, service, linkService, tagService);

        ui.start();

    }

}
