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
        
        public Object findOne(Object title) {
            ArrayList<Book> tips = createListForStub();
            Book searchedTip = null; 
            for (int i= 0; i < tips.size(); i++) {
                if (tips.get(i).getTitle().equals(title)) {
                    searchedTip = tips.get(i);
                }
            }
            return searchedTip;
        }

        public ArrayList findAll() {
            ArrayList<Book> tips = createListForStub();
            return tips;
        }

        
        public boolean save(Object tip) throws IOException {
            ArrayList<Book> tips = createListForStub();
            Book tipToAdd = (Book)tip;
            tips.add(tipToAdd);
            return true;
        }

        
        public void delete(Object title) throws Exception {
            ArrayList<Book> tips = createListForStub();
            Book selectedForDeleting = findWanted(title, tips);
            tips.remove(selectedForDeleting);
        }

        private Book findWanted(Object title, ArrayList<Book> tips) {
            Book wanted = null;
            for (int i= 0; i < tips.size(); i++) {
                if (tips.get(i).getTitle().equals(title)) {
                    wanted = tips.get(i);
                }
            }
            return wanted;
        }
        private ArrayList<Book> createListForStub() {
            ArrayList<String> tags = new ArrayList<>();
            ArrayList<String> courses = new ArrayList<>();
            ArrayList<Book> tips = new ArrayList<>();
            tips.add(new Book("Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags , courses, ""));
            tips.add(new Book("Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags , courses, ""));
            tips.add(new Book("Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags , courses, ""));
            return tips;
        }
        
    };
    

    
    @Before
    public void setUp() {
        BookService service = new BookService(readerDaoStub);
    }
    
  

}
