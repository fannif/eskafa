
package recommendations.domain;

import java.util.ArrayList;

public class Link implements Readable {
    
    private int id;
    private String title;
    private String URL;
    private String type;
    private String metadata;
    private ArrayList<String> tags; //Switch to Tag object when possible
    private ArrayList<String> courses; //Switch to Course object when possible
    private String comment;

    public Link(int id, String title, String URL, String type, String metadata, ArrayList<String> tags, ArrayList<String> courses, String comment) {
        this.title = title;
        this.URL = URL;
        this.type = type;
        this.metadata = metadata;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
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
        return "Type: " + this.type + "\n\tTitle: " + this.title + "\n\tURL: <" + this.URL
                + ">\n\tTags:" + tagString + "\n\tRelated courses:"
                + coursesString + "\n\t" + this.comment + "\n";
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
    
}
