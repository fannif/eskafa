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

import recommendations.services.LinkService;
import recommendations.services.TagService;


public class Stepdefs {

    List<String> inputLines = new ArrayList<>();
    String input;
    Scanner TestScanner;

    ReaderDao testDao;
    ReaderDao testDaoLink;
    ReaderDao testDaoTag;

    BookService testService;
    LinkService testServiceLink;
    TagService testServiceTag;
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

    @Given("Command remove is selected")
    public void commandRemoveSelected() throws Throwable {
        inputLines.add("3");
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
        makeInputString(inputLines);

        TestScanner = new Scanner(input);
        testDao = new FakeBookDao();
        testDaoLink = new FakeLinkDao();
        testServiceLink = new LinkService(testDaoLink);
        testService = new BookService(testDao);
        testServiceTag = new TagService(testDaoTag);

        testUI = new CommandLineUI(TestScanner, testService, testServiceLink, testServiceTag);
        testUI.start();
    }

    @When("User has filled in the title {string} and this book is in memory")
    public void userTriesToRemoveBookThatIsInMemory(String title) throws Throwable {

        inputLines.add(title);
        inputLines.add("q");
        makeInputString(inputLines);

        testDao = new FakeBookDao();
        testService = new BookService(testDao);
        TestScanner = new Scanner(input);
        testDaoLink = new FakeLinkDao();
        testServiceLink = new LinkService(testDaoLink);
        testServiceTag = new TagService(testDaoTag);

        testUI = new CommandLineUI(TestScanner, testService, testServiceLink, testServiceTag);

        testUI.start();

    }

    @Then("Memory should contain a book with title {string}, author {string} and ISBN {string}")
    public void memoryContainsBook(final String title, final String author, final String isbn) throws SQLException {
        final Book found = (Book) testDao.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getAuthor().equals(author));
        assertTrue(found.getISBN().equals(isbn));
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
        makeInputString(inputLines);

        TestScanner = new Scanner(input);
        testDao = new FakeBookDao();
        testService = new BookService(testDao);
        testServiceLink = new LinkService(testDaoLink);
        testService = new BookService(testDao);        
        testServiceTag = new TagService(testDaoTag);

        testUI = new CommandLineUI(TestScanner, testService, testServiceLink, testServiceTag);
        testUI.start();
    }

    @Then("system responds with a list of books containing a book titled {string}")
    public void systemRespondsWithAListOfBooksContainingABookTitled(String title) throws SQLException {
        assertTrue(testDao.findAll().contains(new Book(0,"", title, "Book", "", new ArrayList<String>(), new ArrayList<String>(), "")));
    }

}
