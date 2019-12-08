package recommendations.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import recommendations.FakeBookDao;
import recommendations.dao.ReaderDao;
import recommendations.domain.Book;
import recommendations.domain.Color;
import recommendations.domain.Course;
import recommendations.domain.Tag;
import recommendations.io.StubIO;

public class BookServiceTest {

    ReaderDao readerDaoBook;
    BookService service;
    
    Scanner testScanner;
    ArrayList<String> inputLines;
    StubIO io;

    @Before
    public void setUp() {
        readerDaoBook = new FakeBookDao();
        inputLines = new ArrayList<>();
        io = new StubIO(inputLines);
        service = new BookService(readerDaoBook, io);
    }
    
    @Test
    public void listBooksReturnsBookList() throws SQLException {
        assertEquals(4, service.listBooks().size());
    }

    @Test
    public void listBooksReturnsBookListInRightForm() throws SQLException {
        assertEquals("\tType: Book\n\tTitle: Clean Code\n\tAuthor: Robert C. Martin\n\tISBN: 978-0-13-235088-4\n\tTags:"
                + "|clean code|\n\tRelated courses:|Ohjelmistotuotanto|OhJa|\n\tMust have!\n", service.listBooks().get(0).toString());
    }

    @Test
    public void listBooksReturnsBookListInRightFormWithEmptyCoursesField() throws SQLException {
        assertEquals("\tType: Book\n\tTitle: Secrets & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:"
                + "|Security|Popular|\n\tRelated courses:\n\t\n", service.listBooks().get(2).toString());
    }

    @Test
    public void removeBookRemovesBookIfBookExistsInList() throws Exception {
        String title = "Beyond Fear";
        inputLines.add("q");
        service.remove(title);
        assertEquals(3, service.listBooks().size());
        assertEquals(null, readerDaoBook.findOne("Beyond Fear"));
    }

    @Test
    public void removeBookDoesNothingIfBookNotInList() throws Exception {
        String title = "Hello world";
        inputLines.add("q");
        service.remove(title);

        assertEquals(4, service.listBooks().size());
    }
    
    @Test
    public void editingBookWorksCorrectly() throws SQLException {
        inputLines.add("newTitle");
        inputLines.add("newAuthor");
        inputLines.add("newTag1");
        inputLines.add("newTag2");
        inputLines.add("");
        inputLines.add("newCourse");
        inputLines.add("");
        inputLines.add("newComment");

        service.edit("Clean Code");

        Book book = service.findBookWithTitle("newTitle");
        assertEquals("newAuthor", book.getAuthor());
        assertTrue(book.getTags().contains(new Tag(0, "newTag1")));
        assertTrue(book.getTags().contains(new Tag(0, "newTag2")));
        assertTrue(book.getCourses().contains(new Course(0, "newCourse")));
        assertEquals(book.getComment(), "newComment");
    }
    
    @Test
    public void findingBookByWordWorksCorrectlyWhenBookFound() throws SQLException {
        String found = service.findByWord("Fear");
        String book = "\tType: Book\n\tTitle: Beyond Fear\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-79781119092438\n\tTags:|Security|Popular|\n\tRelated courses:\n\t\n";
        String expected = Color.GREEN.getCode() + "\nBooks found by word " + Color.BOLD.getCode() + "'Fear':" + Color.ORIGINAL.getCode() + "\n" + book;
        assertEquals(expected, found);
    }

    @Test
    public void findingBookByWordWorksCorrectlyWhenMultipleBooksFound() throws SQLException {
        String found = service.findByWord("Schneier");
        String book1 = "\tType: Book\n\tTitle: Beyond Fear\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-79781119092438\n\tTags:|Security|Popular|\n\tRelated courses:\n\t\n";
        String book2 = "\tType: Book\n\tTitle: Secrets & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:|Security|Popular|\n\tRelated courses:\n\t\n";
        String expected = Color.GREEN.getCode() + "\nBooks found by word " + Color.BOLD.getCode() + "'Schneier':" + Color.ORIGINAL.getCode() + "\n" + book1 + book2;
        assertEquals(expected, found);
    }

    @Test
    public void findingBookByWordWorksCorrectlyWhenNoBooksFound() throws SQLException {
        String found = service.findByWord("not found");
        String expected = Color.GREEN.getCode() + "\nBooks found by word " + Color.BOLD.getCode() + "'not found':" + Color.ORIGINAL.getCode() + "\nNone :(\nPlease try another word.\n";
        assertEquals(expected, found);
    }

}
