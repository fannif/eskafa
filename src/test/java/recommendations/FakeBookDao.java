
package recommendations;

import java.sql.SQLException;
import java.util.ArrayList;
import recommendations.dao.ReaderDao;
import recommendations.domain.Book;

public class FakeBookDao implements ReaderDao<Book, String>{
    
    ArrayList<Book> tips = createTipsList();

    @Override
    public Book findOne(String title) {
        Book searchedTip = findWanted(title);
        return searchedTip;
    }

    @Override
    public ArrayList findAll() {
        return tips;
    }

    @Override
    public boolean save(Book tip) {
        tips.add(tip);
        
        return true;
    }

    @Override
    public void delete(String title) throws Exception {
        Book selectedForDeleting = findWanted(title);
        tips.remove(selectedForDeleting);
    }

    private Book findWanted(String title) {
        Book wanted = null;
        for (int i = 0; i < tips.size(); i++) {
            if (tips.get(i).getTitle().equals(title)) {
                wanted = tips.get(i);
            }
        }
        return wanted;
    }

    public ArrayList<Book> createTipsList() {
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
        tipList.add(new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!"));
        tipList.add(new Book(2, "Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2, courses2, ""));
        tipList.add(new Book(3, "Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, ""));
        return tipList;
    }

    @Override
    public boolean edit(Book tip) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
   
}
