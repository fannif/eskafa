package recommendations.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LinkTest {
    Link link;

    @Before
    public void setUp() {
        ArrayList<Tag> tags = new ArrayList();
        tags.add(new Tag(1, "algorithms"));
        tags.add((new Tag(2, "tira")));
        ArrayList<Course> courses = new ArrayList();
        courses.add(new Course(1, "TKT20001 Tietorakenteet ja algoritmit"));
        courses.add(new Course(2, "TKT21012 Algoritmit ongelmanratkaisussa"));


        link = new Link(1,
                "Merge sort algorithm",
                "https://www.youtube.com/watch?v=TzeBrDU-JaY",
                "Url",
                "metadata",
                tags,
                courses,
                "Hyvä selitys merge sortin toiminnasta esimerkin avulla");
    }

    @Test
    public void constructorWorks() {
        assertThat(link, is(notNullValue()));
    }

    @Test
    public void listToStringCorrect() {
        ArrayList<String> testList = new ArrayList<>();
        testList.add("first");
        testList.add("second");
        testList.add("third");
        assertEquals("|first|second|third|", link.listToString(testList));
    }

    @Test
    public void listToStringCorrectWhenListEmpty() {
        ArrayList<String> testList = new ArrayList<>();
        assertEquals("", link.listToString(testList));
    }

    @Test
    public void toStringReturnsRightInformation() {
        assertThat(link.toString(), is(
                "\tType: Url" +
                        "\n\tTitle: Merge sort algorithm" +
                        "\n\tURL: \u001b[36;1m<https://www.youtube.com/watch?v=TzeBrDU-JaY" +
                        ">\u001B[97m\n\tTags:|algorithms|tira|" +
                        "\n\tRelated courses:|TKT20001 Tietorakenteet ja algoritmit|TKT21012 Algoritmit ongelmanratkaisussa|" +
                        "\n\tHyvä selitys merge sortin toiminnasta esimerkin avulla"  + "\n"
                        + "\nDescription: metadata"));
    }
}
