package recommendations.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import recommendations.FakeBookDao;
import recommendations.FakeLinkDao;
import recommendations.FakeTagDao;
import recommendations.dao.ReaderDao;
import recommendations.domain.Tag;
import recommendations.io.StubIO;

public class TagServiceTest {

    ReaderDao readerDaoBook;
    ReaderDao readerDaoLink;
    ReaderDao readerDaoTag;

    Scanner testScanner;
    ArrayList<String> inputLines;
    StubIO io;

    TagService tagService;

    @Before
    public void setUp() {
        readerDaoBook = new FakeBookDao();
        readerDaoLink = new FakeLinkDao();
        readerDaoTag = new FakeTagDao();
        inputLines = new ArrayList<>();
        io = new StubIO(inputLines);
        tagService = new TagService(readerDaoTag, readerDaoBook, readerDaoLink, io);
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

}
