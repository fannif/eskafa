package recommendations.services;

import recommendations.domain.Link;

import java.io.IOException;
import java.sql.SQLException;
import recommendations.dao.ReaderDao;

public class LinkService {

    private ReaderDao linkDao;

    public LinkService(ReaderDao dao) {
        this.linkDao = dao;
    }

    public void addLink(Link link) throws IOException, SQLException {
        linkDao.save(link);
    }
}
