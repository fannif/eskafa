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
import java.util.List;
import java.util.Scanner;

import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Tag;
import recommendations.io.StubIO;

import recommendations.services.LinkService;
import recommendations.services.TagService;

public class Stepdefs {

    ArrayList<String> inputLines = new ArrayList<>();
    String input;
    Scanner TestScanner;

    ReaderDao testDao;
    ReaderDao testDaoLink;
    ReaderDao testDaoBook;
    ReaderDao testDaoTag;

    BookService testService;
    LinkService testServiceLink;
    TagService testServiceTag;
    StubIO io;
    CommandLineUI testUI;

    public void makeInputString(final List<String> inputLines) {
        this.input = "";
        for (int i = 0; i < inputLines.size(); i++) {
            input = input + inputLines.get(i) + "\n";
        }
    }


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

    @Given("Command remove is selected")
    public void commandRemoveSelected() throws Throwable {
        inputLines.add("3");
    }

    @When("User has filled in the title {string} and this book is in memory")
    public void userTriesToRemoveBookThatIsInMemory(String title) throws Throwable {
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
    public void memoryShouldNotContainTheBook(String title) throws Throwable {
        assertNull(testDao.findOne(title));
    }

    @Given("book titled {string} has been added")
    public void bookTitledHasBeenAdded(String title) {
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

    @Then("system responds with a list of books containing a book titled {string}")
    public void systemRespondsWithAListOfBooksContainingABookTitled(String title) throws SQLException {
        assertTrue(testDao.findAll().contains(new Book(0, "", title, "Book", "", new ArrayList<Tag>(), new ArrayList<Course>(), "")));
    }

    @Given("Command add a new link is selected")
    public void commandAddANewLinkIsSelected() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("User has filled in title {string}, url {string} and type {string}")
    public void userHasFilledInTitleUrlAndType(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Memory should contain a link with title {string} and url {string}")
    public void memoryShouldContainALinkWithTitleAndUrl(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("a link with url {string} is already in the memory")
    public void aLinkWithUrlIsAlreadyInTheMemory(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Memory should contain only one link with url {string}")
    public void memoryShouldContainOnlyOneLinkWithUrl(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("Given command add a new link is selected and there is no internet connection")
    public void givenCommandAddANewLinkIsSelectedAndThereIsNoInternetConnection() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Program should respond with {string}")
    public void programShouldRespondWith(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }


}
