package recommendations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import recommendations.domain.Book;
import recommendations.services.BookService;
import recommendations.ui.CommandLineUI;
import io.cucumber.java.en.Then;
import java.sql.SQLException;
import static org.junit.Assert.*;

import java.util.ArrayList;

import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;
import recommendations.io.StubIO;

import recommendations.services.LinkService;
import recommendations.services.TagService;

public class Stepdefs {

    ArrayList<String> inputLines = new ArrayList<>();

    ReaderDao testDao;
    ReaderDao testDaoLink;
    ReaderDao testDaoBook;
    ReaderDao testDaoTag;

    BookService testService;
    LinkService testServiceLink;
    TagService testServiceTag;
    StubIO io;
    CommandLineUI testUI;

    @Given("Command add is selected")
    public void commandAddIsSelected() throws Throwable {
        inputLines.add("2");
    }

    @When("User has filled in title {string}, author {string}, ISBN {string} and type {string}")
    public void informationIsFilled(final String title, final String author, final String isbn, final String type) throws Throwable {

        inputLines.add(title);
        inputLines.add(author);
        inputLines.add(isbn);
        inputLines.add(type);
        inputLines.add("");
        inputLines.add("");
        inputLines.add("comment");
        inputLines.add("q");

        io = new StubIO(inputLines);
        testDao = new FakeBookDao();
        testDaoLink = new FakeLinkDao();
        testDaoBook = new FakeBookDao();
        testServiceLink = new LinkService(testDaoLink, io);
        testService = new BookService(testDao, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);
        testUI.start();
    }

    @Then("Memory should contain a book with title {string}, author {string} and ISBN {string}")
    public void memoryContainsBook(final String title, final String author, final String isbn) throws SQLException {
        final Book found = (Book) testDao.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getAuthor().equals(author));
        assertTrue(found.getISBN().equals(isbn));
    }

    @Given("Command remove book is selected")
    public void commandRemoveSelected() throws Throwable {
        inputLines.add("3");
    }
    

    @When("User has filled in the title {string} and this book is in memory")
    public void userTriesToRemoveBookThatIsInMemory(final String title) throws Throwable {
        inputLines.add("book");
        inputLines.add(title);
        inputLines.add("q");

        testDao = new FakeBookDao();
        io = new StubIO(inputLines);
        testService = new BookService(testDao, io);
        testDaoLink = new FakeLinkDao();
        testDaoBook = new FakeBookDao();
        testServiceLink = new LinkService(testDaoLink, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);

        testUI.start();

    }

    @Then("Memory should not contain a book called {string}")
    public void memoryShouldNotContainTheBook(final String title) throws Throwable {
        assertNull(testDao.findOne(title));
    }

    @Given("book titled {string} has been added")
    public void bookTitledHasBeenAdded(final String title) {
        inputLines.add("2");
        inputLines.add(title);
        inputLines.add("");
        inputLines.add("");
        inputLines.add("Book");
        inputLines.add("");
        inputLines.add("");
        inputLines.add("");
    }

    @When("command list is selected")
    public void commandListIsSelected() throws Throwable {
        inputLines.add("1");
        inputLines.add("q");
        start();
    }

    @Then("system responds with a list of books containing a book titled {string}")
    public void systemRespondsWithAListOfBooksContainingABookTitled(final String title) throws SQLException {
        String expected = "Type: Book\n\tTitle: " + title;
        boolean found = false;
        for (String output : io.getOutputs()) {
            if (output.contains(expected)) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Given("Command add a new link is selected")
    public void commandAddANewLinkIsSelected() {
        inputLines.add("5");
    }

    @When("User has filled in url {string}, title {string} and type {string}")
    public void userHasFilledInTitleUrlAndType(final String url, final String title, final String type)
            throws Exception {
        inputLines.add(url);
        inputLines.add(title);
        inputLines.add(type);
        inputLines.add("");
        inputLines.add("");
        inputLines.add("");
        inputLines.add("");
        inputLines.add("q");

        io = new StubIO(inputLines);
        testDao = new FakeBookDao();
        testDaoLink = new FakeLinkDao();
        testService = new BookService(testDao, io);
        testServiceLink = new LinkService(testDaoLink, io);
        testDaoBook = new FakeBookDao();
        testService = new BookService(testDao, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);
        testUI.start();
    }

    @Then("Memory should contain a link with title {string} and url {string}")
    public void memoryShouldContainALinkWithTitleAndUrl(final String title, final String url) throws SQLException {
        final Link found = (Link) testDaoLink.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getURL().equals(url));
    }

    
    @When("User tries to add url that is already in memory")
    public void aLinkWithUrlIsAlreadyInTheMemory() throws Throwable{
        inputLines.add("http://www.kaleva.fi");
        inputLines.add("Kaleva");
        inputLines.add("Link");
        inputLines.add("");
        inputLines.add("");
        inputLines.add("");
        inputLines.add("");
        inputLines.add("q");

        io = new StubIO(inputLines);
        testDao = new FakeBookDao();
        testDaoLink = new FakeLinkDao();
        testService = new BookService(testDao, io);
        testServiceLink = new LinkService(testDaoLink, io);
        testDaoBook = new FakeBookDao();
        testService = new BookService(testDao, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);
        testUI.start();
    }

    @Then("System should respond with {string}")
    public void memoryShouldContainOnlyOneLinkWithUrl(String respond) throws Throwable {
        ArrayList<String> outputs = io.getOutputs();
        assertTrue(outputs.contains(respond));
    }

    // no internet connection

    @Given("Given command add a new link is selected and there is no internet connection")
    public void givenCommandAddANewLinkIsSelectedAndThereIsNoInternetConnection() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Program should respond with {string}")
    public void programShouldRespondWith(final String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
    
    // list tags
    
     @Given("there are saved tags")
    public void thereAreSavedTags() {
        
    }

    @When("command list tags is selected")
    public void commandListTagsIsSelected() throws Exception {
        inputLines.add("6");
        inputLines.add("q");
        start();
    }

    @Then("program responds with list of tags")
    public void programRespondsWithListOfTags() {
        assertTrue(io.getOutputs().contains("\nTags:\n"));      
    }
    
    @Given("tag {string} has been saved")
    public void tagHasBeenSaved(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("program responds with list including tag {string}")
    public void programRespondsWithListIncludingTag(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    
    private void start() throws Exception {
        io = new StubIO(inputLines);
        
        testDaoBook = new FakeBookDao();
        testDaoLink = new FakeLinkDao();
        testDaoTag = new FakeTagDao();
        
        testService = new BookService(testDaoBook, io);
        testServiceLink = new LinkService(testDaoLink, io);
        testService = new BookService(testDaoBook, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);
        testUI.start();
    }
}
