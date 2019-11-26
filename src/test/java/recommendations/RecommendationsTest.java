package recommendations;

import java.sql.SQLException;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import recommendations.dao.readerDao;
import recommendations.services.BookService;

public class RecommendationsTest {

    readerDao readerDaoBook;
    readerDao rederDaoLink;

    BookService service;
    @Before
    public void setUp() {
        readerDaoBook = new FakeBookDao();
        rederDaoLink = new FakeLinkDao();
        service = new BookService(readerDaoBook);
    }
    
    @Test
    public void listBooksReturnsBookList() throws SQLException {
        assertEquals(3, service.listBooks().size());
    }
    
    @Test
    public void listBooksReturnsBookListInRightForm() throws SQLException {
        assertEquals("Type: Book\n\tTitle: Clean Code\n\tAuthor: Robert C. Martin\n\tISBN: 978-0-13-235088-4\n\tTags:"
                + "|clean code|\n\tRelated courses:|Ohjelmistotuotanto|OhJa|\n\tMust have!\n" ,service.listBooks().get(0).toString());
    }
    
    @Test
    public void listBooksReturnsBookListInRightFormWithEmptyCoursesField() throws SQLException {
        assertEquals("Type: Book\n\tTitle: Secrets & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:"
                + "|Security|Popular|\n\tRelated courses:\n\t\n" ,service.listBooks().get(2).toString());
    }
     
    
    @Test
    public void removeBookRemovesBookIfBookExistsInList() throws Exception {
        String title = "Beyond Fear";
        Scanner lukija = new Scanner(System.in);
        service.remove(title, lukija);
        assertEquals(2, service.listBooks().size());
        assertEquals(null, readerDaoBook.findOne("Beyond Fear"));
    }
    
    @Test
    public void removeBookDoesNothingIfBookNotInList() throws Exception {
        String title = "Hello world";
        Scanner lukija = new Scanner("q");
        service.remove(title, lukija);
        
        assertEquals(3, service.listBooks().size());
    }
    

}
