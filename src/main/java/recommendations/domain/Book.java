package recommendations.domain;

import java.util.ArrayList;
import java.util.Objects;

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
        if (list.get(0).equals("") || list.isEmpty()) {
            return "";
        } else {
            String listString = "|";
            for (int i = 0; i < list.size(); i++) {
                listString = listString + list.get(i) + "|";
            }
            return listString;
        }
    }
}
