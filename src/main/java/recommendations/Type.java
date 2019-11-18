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

    // book: check the class

    // videos/blog posts:
    // String getUrl();
    // void setUrl(String url);

    // podcasts: podcast name != otsikko("title")
    // String getPodcastName();
    // void setPodcastName(String name);

}


