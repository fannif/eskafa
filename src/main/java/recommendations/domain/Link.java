package recommendations.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Link implements Readable {

    private int id;
    private String title;
    private String URL;
    private String type;
    private String metadata;
    private ArrayList<Tag> tags;
    private ArrayList<Course> courses;
    private String comment;

    public Link(int id, String title, String URL, String type, String metadata, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) {
        this.id = id;
        this.title = title;
        this.URL = URL;
        this.type = type;
        this.metadata = metadata;
        this.tags = tags;
        this.courses = courses;
        this.comment = comment;
    }

    public Link(int id, String title, String URL, String type, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) {
        this.title = title;
        this.URL = URL;
        this.type = type;
        this.tags = tags;
        this.courses = courses;
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        String tagString = listToString(this.tags);
        String coursesString = listToString(this.courses);
        return "\nType: " + this.type + "\n\tTitle: " + this.title + "\n\tURL: " + Color.CYAN.getCode() + "<" + this.URL
                + ">" + Color.WHITE.getCode() + "\n\tTags:" + tagString + "\n\tRelated courses:"
                + coursesString + "\n\t" + this.comment + "\n"
                + "\nDescription: " + this.metadata;
    }

    public String listToString(ArrayList list) {
        if (!list.isEmpty()) {
            if (list.get(0).equals("")) {
                return "";
            } else {
                String listString = "|";
                for (int i = 0; i < list.size(); i++) {
                    listString = listString + list.get(i) + "|";
                }
                return listString;
            }
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(title, link.title) &&
                Objects.equals(URL, link.URL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, URL);
    }
}
