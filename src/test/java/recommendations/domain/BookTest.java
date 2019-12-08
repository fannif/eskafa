package recommendations.domain;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1,"tag1"));
        tags.add(new Tag(2,"tag2"));
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course(1,"TestCourse"));
        this.book = new Book(1,"Test Author", "TestBook", "Book", "123-456", tags, courses, "This is a test comment.");
    }

    @Test
    public void getAuthorWorks() {
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    public void getTitleWorks() {
        assertEquals("TestBook", book.getTitle());
    }

    @Test
    public void getTypeWorks() {
        assertEquals("Book", book.getType());
    }

    @Test
    public void getISBNWorks() {
        assertEquals("123-456", book.getISBN());
    }

    @Test
    public void getTagsWorks() {
        assertEquals("tag2", book.getTags().get(1).getName());
    }

    @Test
    public void getRelatedCoursesWorks() {
        assertEquals("TestCourse", book.getCourses().get(0).getName());
    }

    @Test
    public void getCommentWorks() {
        assertEquals("This is a test comment.", book.getComment());
    }

    @Test
    public void setAuthorWorks() {
        book.setAuthor("testing");
        assertEquals("testing", book.getAuthor());
    }

    @Test
    public void setTitleWorks() {
        book.setTitle("testing");
        assertEquals("testing", book.getTitle());
    }

    @Test
    public void setTypeWorks() {
        book.setType("testing");
        assertEquals("testing", book.getType());
    }

    @Test
    public void setISBNWorks() {
        book.setISBN("testing");
        assertEquals("testing", book.getISBN());
    }

    @Test
    public void setTagWorks() {
        ArrayList<Tag> testList = new ArrayList<>();
        Tag tag = new Tag(3,"testing");
        testList.add(tag);
        book.setTags(testList);
        assertEquals("testing", book.getTags().get(0).getName());
    }

    @Test
    public void setRelatedCoursesWorks() {
        ArrayList<Course> testList = new ArrayList<>();
        Course course = new Course(3,"testing");
        testList.add(course);
        book.setCourses(testList);
        assertEquals("testing", book.getCourses().get(0).getName());
    }

    @Test
    public void setCommentWorks() {
        book.setComment("testing");
        assertEquals("testing", book.getComment());
    }

    @Test
    public void equalsWorksWhenBooksEqual() {
        Book book2 = new Book(0,"Test Author", "TestBook", "Book", "", new ArrayList<>(), new ArrayList<>(), "");
        assertTrue(book.equals(book2));
    }

    @Test
    public void equalsWorksWhenBooksNotEqual() {
        Book book2 = new Book(0,"Test Author", "AnotherBook", "Book", "", new ArrayList<>(), new ArrayList<>(), "");
        assertFalse(book.equals(book2));
    }

    @Test
    public void listToStringCorrect() {
        ArrayList<String> testList = new ArrayList<>();
        testList.add("first");
        testList.add("second");
        testList.add("third");
        assertEquals("|first|second|third|", book.listToString(testList));
    }

    @Test
    public void listToStringCorrectWhenListEmpty() {
        ArrayList<String> testList = new ArrayList<>();
        assertEquals("", book.listToString(testList));
    }

    @Test
    public void toStringCorrect() {
        String expected = "\tType: Book\n\tTitle: TestBook\n\tAuthor: Test Author\n\tISBN: 123-456\n\tTags:|tag1|tag2|\n\tRelated courses:|TestCourse|\n\tThis is a test comment.\n";
        assertEquals(expected, book.toString());
    }

}
