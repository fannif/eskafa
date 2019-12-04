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

    /* Class-spesific methods:
    1) books/blog posts:
    String getWriter();
    void setWriter(String writer);
    String getISBN();
    void setISBN(String ISBN);

    2) videos/blog posts:
    String getUrl();
    void setUrl(String url);

    3) podcasts: podcast name != otsikko("title")
    String getPodcastName();
    void setPodcastName(String name);
    */
}


