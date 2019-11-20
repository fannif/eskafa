package recommendations.domain;

import java.util.ArrayList;

public interface Readable {
    String getTitle();
    void setTitle(String title);
    String getType();
    void setType(String type);
    ArrayList<String> getTag();
    void setTag(ArrayList<String> tags);
    ArrayList<String> getRelatedCourses();
    void setRelatedCourses(ArrayList<String> courses);
    String getComment();
    void setComment(String comment);
    String getDescription();
    void setDescription(String description);
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


