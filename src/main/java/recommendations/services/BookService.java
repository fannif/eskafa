package recommendations.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import recommendations.domain.Book;
import recommendations.dao.readerDao;

public class BookService {

    private readerDao bookDao;

    public BookService(readerDao dao) {
        this.bookDao = dao;
    }

    public void addBook(Book book) throws IOException, SQLException {
        bookDao.save(book);

    }

    public ArrayList<Book> listBooks() throws SQLException {
        ArrayList<Book> books = (ArrayList<Book>) bookDao.findAll();
        return books;
    }

    public void remove(String title, Scanner lukija) throws Exception {
        boolean go = true;
        Scanner reader = lukija;
        String input = title;
        while (go) {
            if (input.equals("q")) {
                return;
            }
            if (bookDao.findOne(input) == null) {
                System.out.println("No such book found. Please check the spelling and try again: ");
                System.out.println("To return back to main menu, enter q");
                input = reader.nextLine();
            } else {
                bookDao.delete(input);
                go = false;
            }
        }
        System.out.println("The book has been successfully removed");
    }
}
