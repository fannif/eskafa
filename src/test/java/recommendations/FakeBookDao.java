
package recommendations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recommendations.dao.ReaderDao;
import recommendations.domain.Book;
import recommendations.domain.Course;
import recommendations.domain.Tag;

public class FakeBookDao implements ReaderDao<Book, String>{
    
    ArrayList<Book> tips = createTipsList();

    @Override
    public List<Book> findByTag(String tag) throws SQLException {
        ArrayList<Book> found = new ArrayList<>();
        for (Book book : tips) {
            for (Tag t : book.getTags()) {
                if (t.getName().equals(tag)) {
                    found.add(book);
                }
            }
        }
        return found;
    }

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
        ArrayList<Tag> tags1 = new ArrayList<>();
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags1.add(new Tag(1,"clean code"));
        tags2.add(new Tag(2,"Security"));
        tags2.add(new Tag(3,"Popular"));
        ArrayList<Course> courses1 = new ArrayList<>();
        ArrayList<Course> courses2 = new ArrayList<>();
        courses1.add(new Course(1,"Ohjelmistotuotanto"));
        courses1.add(new Course(2,"OhJa"));
        tipList.add(new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!"));
        tipList.add(new Book(2, "Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2, courses2, ""));
        tipList.add(new Book(3, "Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, ""));
        tipList.add(new Book(4, "Imaginary Writer", "JavaScript", "Book", "111", tags2, courses2, "Code with javascript"));
        return tipList;
    }

    @Override
    public boolean edit(Book book) {
        boolean found = false;
        Book foundBook = null;
        for (Book tip : tips) {
            if (tip.getId() == book.getId()) {
                foundBook = tip;
                found = true;
                break;
            }
        }
        foundBook.setTitle(book.getTitle());
        foundBook.setAuthor(book.getAuthor());
        foundBook.setTags(book.getTags());
        foundBook.setCourses(book.getCourses());
        foundBook.setComment(book.getComment());
        return found;
    } 

    @Override
    public List<Book> findByWord(String word) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findByCourse(String title) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
