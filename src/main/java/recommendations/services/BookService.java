package recommendations.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import recommendations.domain.Book;
import recommendations.dao.ReaderDao;
import recommendations.io.IO;

public class BookService {

    private IO io;
    private ReaderDao bookDao;

    public BookService(ReaderDao dao, IO io) {
        this.io = io;
        this.bookDao = dao;
    }

    public void addBook(Book book) throws IOException, SQLException {
        bookDao.save(book);

    }

    public ArrayList<Book> listBooks() throws SQLException {
        ArrayList<Book> books = (ArrayList<Book>) bookDao.findAll();
        return books;
    }

    public void remove(String title) throws Exception {
        boolean go = true;
        String input = title;
        while (go) {
            if (input.equals("q")) {
                return;
            }
            if (bookDao.findOne(input) == null) {
                io.print("No such book found. Please check the spelling and try again: ");
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                bookDao.delete(input);
                go = false;
            }
        }
        io.print("The book has been successfully removed");
    }
}
