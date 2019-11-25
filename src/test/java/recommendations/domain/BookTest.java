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
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        ArrayList<String> courses = new ArrayList<>();
        courses.add("TestCourse");
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
        assertEquals("tag2", book.getTag().get(1));
    }

    @Test
    public void getRelatedCoursesWorks() {
        assertEquals("TestCourse", book.getRelatedCourses().get(0));
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
        ArrayList<String> testList = new ArrayList<>();
        testList.add("testing");
        book.setTag(testList);
        assertEquals("testing", book.getTag().get(0));
    }

    @Test
    public void setRelatedCoursesWorks() {
        ArrayList<String> testList = new ArrayList<>();
        testList.add("testing");
        book.setRelatedCourses(testList);
        assertEquals("testing", book.getRelatedCourses().get(0));
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
        String expected = "Type: Book\n\tTitle: TestBook\n\tAuthor: Test Author\n\tISBN: 123-456\n\tTags:|tag1|tag2|\n\tRelated courses:|TestCourse|\n\tThis is a test comment.\n";
        assertEquals(expected, book.toString());
    }

}
