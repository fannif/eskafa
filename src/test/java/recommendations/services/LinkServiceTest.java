
package recommendations.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;
import recommendations.dao.ReaderDao;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import recommendations.FakeLinkDao;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.io.StubIO;


public class LinkServiceTest {
    
    ReaderDao readerDaoLink;
    LinkService linkService;
    
    Scanner testScanner;
    ArrayList<String> inputLines;
    StubIO io;

    @Before
    public void setUp() {
        readerDaoLink = new FakeLinkDao();
        inputLines = new ArrayList<>();
        io = new StubIO(inputLines);
        linkService = new LinkService(readerDaoLink, io);
    }
    
    @Test
    public void addLinkWorksCorrectly() throws SQLException, IOException, URISyntaxException {
        inputLines.add("testDescription");

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
    
    @Test
    public void findingLinkByWordWorksCorrectlyWhenLinkFound() throws SQLException {
        String found = linkService.findByWord("Kaleva");
        String link = "\nType: Link\n\tTitle: Kaleva\n\tURL: \u001B[36m<http://www.kaleva.fi>\u001B[0m\n\tTags:|news|\n\tRelated courses:\n\tnews\n\nDescription: ";
        String expected = "\nLinks found by word 'Kaleva':\n" + link;
        assertEquals(expected, found);
    }

    @Test
    public void findingLinkByWordWorksCorrectlyWhenMultipleLinksFound() throws SQLException {
        String found = linkService.findByWord("Link");
        String link1 = "\nType: Link\n\tTitle: Kaleva\n\tURL: \u001B[36m<http://www.kaleva.fi>\u001B[0m\n\tTags:|news|\n\tRelated courses:\n\tnews\n\nDescription: ";
        String link2 = "\nType: Link\n\tTitle: \n\tURL: \u001B[36m<https://ohjelmistotuotanto-hy.github.io/>\u001B[0m\n\tTags:\n\tRelated courses:|Ohjelmistotuotanto|\n\t\n\nDescription: ";
        String expected = "\nLinks found by word 'Link':\n" + link1 + link2;
        assertEquals(expected, found);
    }

    @Test
    public void findingLinkByWordWorksCorrectlyWhenNoLinksFound() throws SQLException {
        String found = linkService.findByWord("not found");
        String expected = "\nLinks found by word 'not found':\nNone :(\nPlease try another word.\n";
        assertEquals(expected, found);
    }
    
}
