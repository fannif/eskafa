package recommendations;

import org.junit.Before;
import recommendations.dao.ReaderDao;
import recommendations.domain.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeTagDao implements ReaderDao<Tag, String> {

    List<Tag> tags;

    public FakeTagDao() {
        tags = createTags();
    }

    private List<Tag> createTags() {
        List<Tag> tags = new ArrayList();
        tags.add(new Tag(1, "ohpe"));
        tags.add(new Tag(2, "ohja"));
        tags.add(new Tag(3, "ohtu"));
        return tags;
    }

    @Override
    public List<Tag> findByTag(String title) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.;
    }

    @Override
    public Tag findOne(String title) {
        if (tags.contains(new Tag(1, title))) {
            for (int i = 0; i<tags.size(); i++) {
                if (tags.get(i).getName().equals(title)) {
                    return tags.get(i);
                }
            }
        }

        return null;
    }

    @Override
    public List<Tag> findAll() {
        return tags;
    }

    @Override
    public boolean save(Tag tip) {
        tags.add(tip);
        return true;
    }

    @Override
    public void delete(String title) {
        Tag tag = findOne(title);
        if(tag != null) {
            tags.remove(tag);
        } else {
            System.out.println("No tags for " + title + " were found");
        }

    }

    @Override
    public boolean edit(Tag tip) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

}
