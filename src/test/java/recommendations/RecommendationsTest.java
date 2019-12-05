package recommendations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;
import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import recommendations.domain.Course;
import recommendations.domain.Tag;
import recommendations.services.BookService;
import recommendations.dao.ReaderDao;
import recommendations.domain.Book;
import recommendations.domain.Link;
import recommendations.io.StubIO;
import recommendations.services.LinkService;
import recommendations.services.TagService;

public class RecommendationsTest {

    ReaderDao readerDaoBook;
    ReaderDao readerDaoLink;
    ReaderDao readerDaoTag;

    Scanner testScanner;
    ArrayList<String> inputLines;
    StubIO io;

    BookService service;
    TagService tagService;
    LinkService linkService;

    @Before
    public void setUp() {
        readerDaoBook = new FakeBookDao();
        readerDaoLink = new FakeLinkDao();
        readerDaoTag = new FakeTagDao();
        inputLines = new ArrayList<>();
        io = new StubIO(inputLines);
        service = new BookService(readerDaoBook, io);
        tagService = new TagService(readerDaoTag, readerDaoBook, readerDaoLink, io);
        linkService = new LinkService(readerDaoLink, io);
    }

    @Test
    public void listBooksReturnsBookList() throws SQLException {
        assertEquals(4, service.listBooks().size());
    }

    @Test
    public void listBooksReturnsBookListInRightForm() throws SQLException {
        assertEquals("Type: Book\n\tTitle: Clean Code\n\tAuthor: Robert C. Martin\n\tISBN: 978-0-13-235088-4\n\tTags:"
                + "|clean code|\n\tRelated courses:|Ohjelmistotuotanto|OhJa|\n\tMust have!\n", service.listBooks().get(0).toString());
    }

    @Test
    public void listBooksReturnsBookListInRightFormWithEmptyCoursesField() throws SQLException {
        assertEquals("Type: Book\n\tTitle: Secrets & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:"
                + "|Security|Popular|\n\tRelated courses:\n\t\n", service.listBooks().get(2).toString());
    }

    @Test
    public void removeBookRemovesBookIfBookExistsInList() throws Exception {
        String title = "Beyond Fear";
        //Scanner lukija = new Scanner(System.in);
        inputLines.add("q");
        service.remove(title);
        assertEquals(3, service.listBooks().size());
        assertEquals(null, readerDaoBook.findOne("Beyond Fear"));
    }

    @Test
    public void removeBookDoesNothingIfBookNotInList() throws Exception {
        String title = "Hello world";
        //Scanner lukija = new Scanner("q");
        inputLines.add("q");
        service.remove(title);

        assertEquals(4, service.listBooks().size());
    }

    @Test
    public void listTagsReturnsListOfAddedTags() throws SQLException {
        List<Tag> tags = new ArrayList();
        tags.add(new Tag(1, "ohpe"));
        tags.add(new Tag(2, "ohja"));
        tags.add(new Tag(3, "ohtu"));
        assertThat(tagService.listTags(), is(tags));
    }

    @Test
    public void listTagsReturnsEmptyListIfNoTagsAdded() throws Exception {
        readerDaoTag.delete("ohtu");
        readerDaoTag.delete("ohpe");
        readerDaoTag.delete("ohja");
        assertThat(tagService.listTags().size(), is(0));
    }

    @Test
    public void searchByTagGivesCorrectResult() throws SQLException {
        tagService.findRecommendadtionsByTag("news");
        assertEquals("No books found by tag news", io.getOutputs().get(0));
        assertEquals("Links found by tag news: ", io.getOutputs().get(2));
        String expected = "\nType: Link"
                + "\n\tTitle: Kaleva"
                + "\n\tURL: \u001B[36m<http://www.kaleva.fi"
                + ">\u001B[0m\n\tTags:|news|"
                + "\n\tRelated courses:"
                + "\n\tnews" + "\n"
                + "\nDescription: ";
        assertEquals(expected, io.getOutputs().get(3));
    }

    @Test
    public void addLinkWorksCorrectly() throws SQLException, IOException, URISyntaxException {
        String input = "testDescription";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        linkService = new LinkService(readerDaoLink, this.io);

        ArrayList<Tag> tags = new ArrayList();
        tags.add(new Tag(3, "bubble_sort"));
        ArrayList<Course> courses = new ArrayList();
        courses.add(new Course(6, "Tira"));
        linkService.addLinkWithMeta(5,
                "Bubble sort algorithm",
                "https://www.youtube.com/watch?v=TzeBrDU",
                "Url",
                tags,
                courses,
                "Bubble sort");

        assertThat(linkService.listLinks().size(), is(3));
    }

    @Test
    public void editingBookWorksCorrectly() throws SQLException {
        inputLines.add("newTitle");
        inputLines.add("newAuthor");
        inputLines.add("newTag1");
        inputLines.add("newTag2");
        inputLines.add("");
        inputLines.add("newCourse");
        inputLines.add("");
        inputLines.add("newComment");
        
        service.edit("Clean Code");
        
        Book book = service.findBookWithTitle("newTitle");
        assertEquals("newAuthor", book.getAuthor());
        assertTrue(book.getTags().contains(new Tag(0, "newTag1")));
        assertTrue(book.getTags().contains(new Tag(0, "newTag2")));
        assertTrue(book.getCourses().contains(new Course(0, "newCourse")));
        assertEquals(book.getComment(), "newComment");           
    }

    @Test
    public void editingLinkWorksCorrectly() throws SQLException {
        inputLines.add("newTitle");
        inputLines.add("newType");
        inputLines.add("newTag1");
        inputLines.add("newTag2");
        inputLines.add("");
        inputLines.add("newCourse");
        inputLines.add("");
        inputLines.add("newComment");
        
        linkService.edit("Kaleva");
        
        Link link = null;
        for (Link l : linkService.listLinks()) {
            if (l.getTitle().equals("newTitle")) {
                link = l;
            }
        }
        assertEquals(link.getType(), "newType");
        assertTrue(link.getTags().contains(new Tag(0, "newTag1")));
        assertTrue(link.getTags().contains(new Tag(0, "newTag2")));
        assertTrue(link.getCourses().contains(new Course(0, "newCourse")));
        assertEquals(link.getComment(), "newComment");
        
    }
}
