package recommendations.services;

import java.sql.SQLException;
import java.util.*;
import recommendations.dao.ReaderDao;
import recommendations.domain.Tag;


public class TagService {
    private ReaderDao tagDao;

    public TagService(ReaderDao tagDao) {
        this.tagDao = tagDao;
    }
    
    public List<Tag> listTags() throws SQLException {
        return tagDao.findAll();
    }
}
