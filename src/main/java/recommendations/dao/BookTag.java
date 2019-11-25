package recommendations.dao;

public class BookTag {
    private Integer id;
    private Integer bookId;
    private Integer tagId;

    public BookTag(Integer id, Integer bookId, Integer tagId) {
        this.id = id;
        this.bookId = bookId;
        this.tagId = tagId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
