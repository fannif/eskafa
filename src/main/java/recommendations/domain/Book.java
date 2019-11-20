package recommendations.domain;

import java.util.ArrayList;

public class Book implements Readable {

    private String author;
    private String title;
    private String type;
    private String ISBN;
    private ArrayList<String> tags;
    private ArrayList<String> relatedCourses;
    private String description;
    private String comment;

    public Book(String author, String title, String type, String ISBN, ArrayList<String> tags, ArrayList<String> relatedCourses, String comment) {
        this.author = author;
        this.title = title;
        this.type = type;
        this.ISBN = ISBN;
        this.tags = tags;
        this.relatedCourses = relatedCourses;
        this.comment = comment;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public ArrayList<String> getTag() {
        return tags;
    }

    @Override
    public void setTag(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public ArrayList<String> getRelatedCourses() {
        return relatedCourses;
    }

    @Override
    public void setRelatedCourses(ArrayList<String> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

}
