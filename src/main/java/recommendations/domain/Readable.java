package recommendations.domain;

import java.util.ArrayList;

public interface Readable {
    String getTitle();
    void setTitle(String title);
    String getType();
    void setType(String type);
    ArrayList<Tag> getTags();
    void setTags(ArrayList<Tag> tags);
    ArrayList<Course> getCourses();
    void setCourses(ArrayList<Course> courses);
    String getComment();
    void setComment(String comment);
    String toString();
}


