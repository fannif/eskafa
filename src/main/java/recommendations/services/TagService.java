package recommendations.services;

import java.io.Reader;
import java.sql.SQLException;
import java.util.*;
import recommendations.dao.ReaderDao;
import recommendations.domain.Book;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.io.IO;

public class TagService {
    private ReaderDao tagDao;
    private ReaderDao bookDao;
    private ReaderDao linkDao;
    private IO io;

    public TagService(ReaderDao tagDao, ReaderDao bookDao, ReaderDao linkDao, IO io) {
        this.tagDao = tagDao;
        this.bookDao = bookDao;
        this.linkDao = linkDao;
        this.io = io;
    }
    
    public List<Tag> listTags() throws SQLException {
        return tagDao.findAll();
    }

    public void findRecommendadtionsByTag(String tagName) throws SQLException {
        List<Book> books = bookDao.findByTag(tagName);
        List<Link> links = linkDao.findByTag(tagName);
        if (!books.isEmpty()) {
            io.print("Books found by tag " + tagName + ": ");
            for (Book book : books) {
                io.print(book.toString());
            }
        } else {
            io.print("No books found by tag " + tagName);
        }
        io.print("");
        if (!links.isEmpty()) {
            io.print("Links found by tag " + tagName + ": ");
            for (Link link : links) {
                io.print(link.toString());
            }
        } else {
            io.print("No links found by tag " + tagName + ": ");
        }
    }
}
