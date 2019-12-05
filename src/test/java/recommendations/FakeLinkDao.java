
package recommendations;

import java.sql.SQLException;
import java.util.*;
import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;

public class FakeLinkDao implements ReaderDao<Link, String> {
    List<Link> tips = createTips();

    @Override
    public List<Link> findByTag(String tag) throws SQLException {
        ArrayList<Link> found = new ArrayList<>();
        for (Link link : tips) {
            for (Tag t : link.getTags()) {
                if (t.getName().equals(tag)) {
                    found.add(link);
                }
            }
        }
        return found;
    }

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
        if (this.findOne(tip.getTitle()) != null) {
            return false;
        }
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
    public boolean edit(Link link) {
        boolean found = false;
        Link foundLink = null;
        for (Link tip : tips) {
            if (tip.getId() == link.getId()) {
                foundLink = tip;
                found = true;
                break;
            }
        }
        foundLink.setTitle(link.getTitle());
        foundLink.setType(link.getType());
        foundLink.setTags(link.getTags());
        foundLink.setCourses(link.getCourses());
        foundLink.setComment(link.getComment());
        return found;
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
        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags.add(new Tag(1,"news"));
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new Course (1,"Ohjelmistotuotanto"));
        linkTips.add(new Link(1, "Kaleva", "http://www.kaleva.fi", "Link", "", tags, courses, "news"));
        linkTips.add(new Link(2, "", "https://ohjelmistotuotanto-hy.github.io/", "Link", "", tags2, courses2, ""));
        return linkTips;
    }

    @Override
    public List<Link> findByWord(String word) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Link> findByCourse(String title) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
