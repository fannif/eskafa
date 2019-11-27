package recommendations.services;

import java.io.Reader;
import java.sql.SQLException;
import java.util.*;
import recommendations.dao.ReaderDao;
import recommendations.domain.Book;
import recommendations.domain.Link;
import recommendations.domain.Tag;

public class TagService {
    private ReaderDao tagDao;
    private ReaderDao bookDao;
    private ReaderDao linkDao;

    public TagService(ReaderDao tagDao, ReaderDao bookDao, ReaderDao linkDao) {
        this.tagDao = tagDao;
        this.bookDao = bookDao;
        this.linkDao = linkDao;
    }
    
    public List<Tag> listTags() throws SQLException {
        return tagDao.findAll();
    }

    public void findRecommendadtionsByTag(String tagName) throws SQLException {
        List<Book> books = bookDao.findByTag(tagName);
        List<Link> links = linkDao.findByTag(tagName);
        if (!books.isEmpty()) {
            System.out.println("Books found by tag " + tagName + ": ");
            for (Book book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books found by tag " + tagName);
        }
        System.out.println("");
        if (!links.isEmpty()) {
            System.out.println("Links found by tag " + tagName + ": ");
            for (Link link : links) {
                System.out.println(link);
            }
        } else {
            System.out.println("No links found by tag " + tagName + ": ");
        }
    }
}
