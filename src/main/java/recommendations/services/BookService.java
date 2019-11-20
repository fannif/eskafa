package recommendations.services;

import java.util.ArrayList;
import recommendations.domain.Book;
import recommendations.dao.readerDao;

// String-toteutus rakenteen testausta varten //
public class BookService {

    private readerDao bookDao;

    // tilapäinen
    private ArrayList<String> books;

    // tilapäinen
    public BookService() {
        this.books = new ArrayList<>();
    }

    // Book-olion kanssa käytettävä konstruktori
    public BookService(readerDao dao) {
        this.bookDao = dao;
    }

    public void addBook(String book) {
        this.books.add(book);

    }

    public ArrayList<String> listBooks() {
        return this.books;
    }

}
