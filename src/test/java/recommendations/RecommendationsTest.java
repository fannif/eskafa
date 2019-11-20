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
            ArrayList<String> courses = new ArrayList<>();
            courses.add("Ohjelmistotuotanto");
            tipList.add(new Book("Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1 , courses, "Must have!"));
            courses.remove("Ohjelmistotuotanto");
            courses.add("");
            tipList.add(new Book("Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2 , courses, ""));
            tipList.add(new Book("Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2 , courses, ""));
            return tipList;
        }
        
    };
    

    
    @Before
    public void setUp() {
        
        BookService service = new BookService(readerDaoStub);
    }
    
  

}
