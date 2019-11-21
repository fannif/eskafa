/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendations;

import java.io.IOException;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import recommendations.dao.readerDao;
import recommendations.domain.Book;
import recommendations.services.BookService;

public class RecommendationsTest {

    readerDao readerDaoStub = new readerDao() {
        ArrayList<Book> tips = createListForStub();

        public Object findOne(Object title) {
            Book searchedTip = null; 
            for (int i= 0; i < tips.size(); i++) {
                if (tips.get(i).getTitle().equals(title)) {
                    searchedTip = tips.get(i);
                }
            }
            return searchedTip;
        }

        public ArrayList findAll() {
            return tips;
        }

        public boolean save(Object tip) throws IOException {
            Book tipToAdd = (Book)tip;
            tips.add(tipToAdd);
            return true;
        }

        public void delete(Object title) throws Exception {
            Book selectedForDeleting = findWanted(title);
            tips.remove(selectedForDeleting);
        }

        private Book findWanted(Object title) {
            Book wanted = null;
            for (int i= 0; i < tips.size(); i++) {
                if (tips.get(i).getTitle().equals(title)) {
                    wanted = tips.get(i);
                }
            }
            return wanted;
        }
        public ArrayList<Book> createListForStub() {
            ArrayList<Book> tipList = new ArrayList<>();
            ArrayList<String> tags1 = new ArrayList<>();
            ArrayList<String> tags2 = new ArrayList<>();
            tags1.add("clean code");
            tags2.add("Security");
            tags2.add("Popular");
            ArrayList<String> courses1 = new ArrayList<>();
            ArrayList<String> courses2 = new ArrayList<>();            
            courses1.add("Ohjelmistotuotanto");
            courses1.add("OhJa");            
            tipList.add(new Book("Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1 , courses1, "Must have!"));
            tipList.add(new Book("Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2 , courses2, ""));
            tipList.add(new Book("Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2 , courses2, ""));
            return tipList;
        }
        
    };
    

    BookService service;
    @Before
    public void setUp() {
        service = new BookService(readerDaoStub);
    }
    
    @Test
    public void listBooksReturnsBookList() {
        assertEquals(3, service.listBooks().size());
    }
    
    @Test
    public void listBooksReturnsBookListInRightForm() {
        assertEquals("Type: Book\n\tTitle: Clean Code\n\tAuthor: Robert C. Martin\n\tISBN: 978-0-13-235088-4\n\tTags:"
                + "|clean code|\n\tRelated courses:|Ohjelmistotuotanto|OhJa|\n\tMust have!\n" ,service.listBooks().get(0).toString());
    }
    
    public void listBooksReturnsBookListInRightFormWithEmptyCoursesField() {
        assertEquals("Type: Book\n\tTitle: Secret & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:"
                + "|security|\n\tRelated courses:\n\\n" ,service.listBooks().get(2).toString());
    }
     
    
    @Test
    public void removeBookRemovesBookIfBookExistsInList() throws Exception {
        String title = "Beyond Fear";
        Scanner lukija = new Scanner(System.in);
        service.remove(title, lukija);
        assertEquals(2, service.listBooks().size());
        assertEquals(null, readerDaoStub.findOne("Beyond Fear"));
    }
    
//    @Test
//    public void removeBookDoesNothingIfBookNotInList() throws Exception {
//        String title = "Hello world";
//        Scanner lukija = new Scanner(System.in);
//        service.remove(title, lukija);
//        assertEquals(3, service.listBooks().size());
//        //assertEquals(, readerDaoStub.findOne("Beyond Fear"));        
//        
//    }
    
  

}
