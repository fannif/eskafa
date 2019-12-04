package recommendations.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Book implements Readable {

    private int id;
    private String author;
    private String title;
    private String type;
    private String ISBN;
    private ArrayList<Tag> tags;
    private ArrayList<Course> relatedCourses;
    private String comment;

    public Book(int id, String author, String title, String type, String ISBN, ArrayList<Tag> tags, ArrayList<Course> relatedCourses, String comment) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.type = type;
        this.ISBN = ISBN;
        this.tags = tags;
        this.relatedCourses = relatedCourses;
        this.comment = comment;
    }
    
    public Book(String author, String title) {
        this.author = author;
        this.title = title;
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
    public ArrayList<Tag> getTags() {
        return tags;
    }

    @Override
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
    
    public int getId() {
        return this.id;
    }

    @Override
    public ArrayList<Course> getCourses() {
        return relatedCourses;
    }

    @Override
    public void setCourses(ArrayList<Course> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(author, book.author)
                && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title);
    }

    @Override
    public String toString() {
        String tagString = listToString(this.tags);
        String coursesString = listToString(this.relatedCourses);
        return "Type: " + this.type + "\n\tTitle: " + this.title + "\n\tAuthor: " + this.author + "\n\tISBN: "
                + this.ISBN + "\n\tTags:" + tagString + "\n\tRelated courses:"
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
