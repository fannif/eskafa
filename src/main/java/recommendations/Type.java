package recommendations;

public interface Type {
    String getTitle();
    void setTitle(String title);
    String getType();
    void setType(String type);
    String getTag();
    void setTag(String tag);
    String getRelatedCourses();
    void setRelatedCourses(String coureName);
    String getComment();
    void setComment(String comment);
    String getDescription();
    void setDescription(String description);

    // Class-spesific methods:
    // books/blog posts:
    String getWriter();
    void setWriter(String writer);
    String getISBN();
    void setISBN(String ISBN);

    // videos/blog posts:
    String getUrl();
    void setUrl(String url);

    // podcasts: podcast name != otsikko("title")
    String getPodcastName();
    void setPodcastName(String name);

}


