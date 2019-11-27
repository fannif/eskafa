package recommendations.services;

import java.sql.SQLException;
import java.util.*;
import recommendations.dao.ReaderDao;
import recommendations.domain.Book;
import recommendations.domain.Tag;

public class TagService {
    private ReaderDao tagDao;
    private ReaderDao bookDao;

    public TagService(ReaderDao tagDao, ReaderDao bookDao) {
        this.tagDao = tagDao;
        this.bookDao = bookDao;
    }
    
    public List<Tag> listTags() throws SQLException {
        return tagDao.findAll();
    }

    public void findRecommendadtionsByTag(String tagName) throws SQLException {
        List<Book> books = bookDao.findByTag();

        if (tagDao.findOne(tagName) != null) {

        }
    }
}
