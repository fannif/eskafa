
package recommendations;

import java.sql.SQLException;
import java.util.*;
import recommendations.dao.ReaderDao;
import recommendations.domain.Link;

public class FakeLinkDao implements ReaderDao<Link, String> {
    List<Link> tips = createTips();

    @Override
    public Link findOne(String title) throws SQLException {
        Link tip = findWanted(title);
        return tip;
    }

    @Override
    public List<Link> findAll() throws SQLException {
        return tips;
    }

    @Override
    public boolean save(Link tip) throws SQLException {
        tips.add(tip);
        return true;
    }

    @Override
    public void delete(String title) throws Exception {
        Link linkToDelete = findWanted(title);
        if (linkToDelete != null) {
            tips.remove(linkToDelete);
        }
    }

    @Override
    public boolean edit(Link tip) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Link findWanted(String title) {
        Link wanted = null;
        for (int i = 0; i < tips.size(); i++) {
            if (tips.get(i).getTitle().equals(title)) {
                wanted = tips.get(i);
            }
        }
        return wanted;
    }

    private ArrayList<Link> createTips() {
        ArrayList<Link> linkTips = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> tags2 = new ArrayList<>();
        tags.add("news");
        ArrayList<String> courses = new ArrayList<>();
        ArrayList<String> courses2 = new ArrayList<>();
        courses2.add("Ohjelmistotuotanto");
        linkTips.add(new Link(1, "Kaleva", "www.kaleva.fi", "Website", "", tags, courses, "news"));
        linkTips.add(new Link(2, "", "", "Link", "https://ohjelmistotuotanto-hy.github.io/", tags2, courses2, ""));
        return linkTips;
    }
    
}
