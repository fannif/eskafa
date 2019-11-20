package recommendations.services;

import java.io.IOException;
import java.util.ArrayList;
import recommendations.domain.Book;
import recommendations.dao.readerDao;


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

}
