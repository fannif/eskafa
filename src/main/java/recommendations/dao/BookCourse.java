package recommendations.dao;

public class BookCourse {
    private Integer id;
    private Integer bookId;
    private Integer courseId;

    public BookCourse(Integer id, Integer bookId, Integer courseId) {
        this.id = id;
        this.bookId = bookId;
        this.courseId = courseId;
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
