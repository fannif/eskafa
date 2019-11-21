package recommendations.services;

import java.io.IOException;
import java.util.ArrayList;
import recommendations.domain.Book;
import recommendations.dao.readerDao;
import recommendations.ui.CommandLineUI;


public class BookService {

    private readerDao bookDao;

   
    public BookService(readerDao dao) {
        this.bookDao = dao;
    }

    public void addBook(Book book) throws IOException {
       bookDao.save(book);

    }

    public ArrayList<Book> listBooks() {
       ArrayList<Book> books = bookDao.findAll();
       return books;
    }

    public String remove(String title) throws Exception {
        if (bookDao.findOne(title) != null) {
            return "No such book found. Please check the spelling.";
        } else {
            bookDao.delete(title);
            CommandLineUI.loop = false;
        }
        return "The book has been successfully removed";
    }
}
