package recommendations.services;

import recommendations.dao.readerDao;
import recommendations.domain.Link;

import java.io.IOException;
import java.sql.SQLException;

public class LinkService {

    private readerDao linkDao;

    public LinkService(readerDao dao) {
        this.linkDao = dao;
    }

    public void addLink(Link link) throws IOException, SQLException {
        linkDao.save(link);
    }
}
