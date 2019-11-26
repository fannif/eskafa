
package recommendations;

import java.sql.SQLException;
import java.util.ArrayList;
import recommendations.dao.readerDao;
import recommendations.domain.Book;

public class FakeBookDao implements readerDao<Book, String>{
    
    ArrayList<Book> tips = createListForStub();

        

    @Override
    public Book findOne(String title) {
        Book searchedTip = null;
        for (int i = 0; i < tips.size(); i++) {
            if (tips.get(i).getTitle().equals(title)) {
                searchedTip = tips.get(i);
            }
        }
        return searchedTip;
    }

    @Override
    public ArrayList findAll() {
        return tips;
    }

    @Override
    public boolean save(Book tip) {
        if (tip.getClass().equals(Book.class)) {
            Book tipToAdd = (Book) tip;
            tips.add(tipToAdd);
        }
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
        tipList.add(new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!"));
        tipList.add(new Book(2, "Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2, courses2, ""));
        tipList.add(new Book(3, "Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, ""));
        return tipList;
    }

    @Override
    public void edit(Book tip) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
   
}
