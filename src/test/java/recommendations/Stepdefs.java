package recommendations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.sql.SQLException;
import static org.junit.Assert.*;
import java.util.ArrayList;

import recommendations.ui.CommandLineUI;
import recommendations.io.StubIO;
import recommendations.dao.ReaderDao;
import recommendations.domain.Color;
import recommendations.domain.Link;
import recommendations.domain.Book;
import recommendations.services.*;

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

    @Given("Command add a book is selected")
    public void commandAddABookIsSelected() throws Throwable {
        inputLines.add("2");
    }

    @When("User has filled in title {string} and author {string}")
    public void informationIsFilled(final String title, final String author) throws Throwable {
        inputLines.add("");
        inputLines.add(title);
        inputLines.add(author);
        inputLines.add("");
        inputLines.add("");
        inputLines.add("comment");
        inputLines.add("q");

        start();
    }

    @Then("Memory should contain a book with title {string} and author {string}")
    public void memoryContainsBook(final String title, final String author) throws SQLException {
        final Book found = (Book) testDaoBook.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getAuthor().equals(author));
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
        start();
    }

    @Then("Memory should not contain a book called {string}")
    public void memoryShouldNotContainTheBook(final String title) throws Throwable {
        assertNull(testDaoBook.findOne(title));
    }

    @Given("book titled {string} has been added")
    public void bookTitledHasBeenAdded(final String title) {
        inputLines.add("2");
        inputLines.add("");
        inputLines.add(title);
        addEmpties(4);
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
        inputLines.add("y");
        inputLines.add(title);
        inputLines.add(type);
        addEmpties(4);
        inputLines.add("q");
        start();
    }

    @Then("Memory should contain a link with title {string} and url {string}")
    public void memoryShouldContainALinkWithTitleAndUrl(final String title, final String url) throws SQLException {
        final Link found = (Link) testDaoLink.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getURL().equals(url));
    }

    @When("User tries to add link that is already in memory")
    public void aLinkWithUrlIsAlreadyInTheMemory() throws Throwable {
        inputLines.add("http://www.kaleva.fi");
        inputLines.add("y");
        inputLines.add("Kaleva");
        inputLines.add("Link");
        addEmpties(4);
        inputLines.add("q");
        start();
    }

    @Then("System should respond with {string}")
    public void systemShouldRespondWith(String output) throws Throwable {
        ArrayList<String> outputs = io.getOutputs();
        assertTrue(outputs.contains(Color.RED.getCode() + output + Color.ORIGINAL.getCode()));
    }


    @When("User has filled in url {string}")
    public void userHasFilledInUrl(String url) throws Throwable {
        inputLines.add(url);
    }

    @Then("Program should propose a title that user can accept or reject")
    public void programShouldProposeATitleThatUserCanAcceptOrReject() throws Throwable {
        inputLines.add("n");
        addEmpties(5);
        inputLines.add("q");
        start();
        ArrayList<String> outputs = io.getOutputs();
        assertTrue(outputs.contains("Title: Operating system - Wikipedia"));
        assertTrue(outputs.contains(Color.CYAN.getCode() + "Do you want to modify the title? (y/n)" + Color.ORIGINAL.getCode()));
    }

    @When("User has accepted a title proposed")
    public void userHasAcceptedATitleProposed() throws Throwable {
        inputLines.add("https://en.wikipedia.org/wiki/Operating_system");
        inputLines.add("n");
        addEmpties(5);
        inputLines.add("q");
        start();
    }

    @Then("A link containing proposed title should be added to memory")
    public void aLinkContainingProposedTitleShouldBeAddedToMemory() throws Throwable {
        Link found = (Link) testDaoLink.findOne("Operating system - Wikipedia");
        assertTrue(found != null);
    }

    @When("User has rejected a fetched title")
    public void userHasRejectedAFetchedTitle() throws Throwable {
        inputLines.add("https://en.wikipedia.org/wiki/Operating_system");
        inputLines.add("y");
    }

    @Then("User can fill in title manually")
    public void userCanFillInTitleManually() throws Throwable {
        inputLines.add("Manual title");
        addEmpties(5);
        inputLines.add("q");
        start();
        Link found = (Link) testDaoLink.findOne("Manual title");
        assertTrue(found != null);
    }

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
        assertTrue(io.getOutputs().contains(Color.CYAN.getCode() + "\nTags:\n" + Color.ORIGINAL.getCode()));
    }

    @Given("command search by tag is selected")
    public void commandSearchByTagIsSelected() {
        inputLines.add("7");
    }

    @When("user selects existing tag {string}")
    public void userSelectsExistingTag(String tag) throws Exception {
        inputLines.add(tag);
        inputLines.add("q");
        start();
    }

    @Then("recommendations with given tag are listed")
    public void recommendationsWithGivenTagAreListed() {
        String expected = "\tType: Link"
                + "\n\tTitle: Kaleva"
                + "\n\tURL: " + Color.CYAN.getCode() + "<http://www.kaleva.fi"
                + ">" + Color.WHITE.getCode() + "\n\tTags:|news|"
                + "\n\tRelated courses:"
                + "\n\tnews" + "\n"
                + "\nDescription: ";
        assertTrue(io.getOutputs().contains(expected));
    }

    @Given("Command edit recommendation is selected")
    public void commandEditRecommendationSelected() throws Throwable {
        inputLines.add("8");
    }

    @When("User enters type {string} and title {string}")
    public void userEntersTypeAndTitle(String type, String title) throws Throwable {
        inputLines.add(type);
        inputLines.add(title);
        addEmpties(5);
        inputLines.add("q");
        start();
    }

    @Then("The book called {string} is fetched from memory")
    public void rightBookFound(String title) throws Throwable {
        assertTrue(io.getOutputs().contains("Book found: \n\t" + testDaoBook.findOne(title).toString()));
    }

    @Given("The book {string} is chosen for modifying")
    public void modifyingCleanCode(String title) throws Throwable {
        inputLines.add("8");
        inputLines.add("book");
        inputLines.add(title);
    }

    @When("User has filled in modified comment {string}")
    public void userModifiesComment(String newComment) throws Throwable {
        addEmpties(4);
        inputLines.add(newComment);
        inputLines.add("q");
        start();
    }

    @Then("{string} should have a comment {string}")
    public void commentHasBeenModified(String title, String comment) throws Throwable {
        Book testBook = (Book) testDaoBook.findOne(title);
        assertTrue(testBook.toString().contains("modified"));
    }

    @Given("Command search by word is selected")
    public void searchByWordSelected() throws Throwable {
        inputLines.add("9");
    }

    @When("User has filled in word {string}")
    public void searchByWord(String word) throws Throwable {
        inputLines.add(word);
        inputLines.add("q");
        start();
    }

    @Then("Program should return all books containing the word in their information")
    public void returnedListCorrect() throws Throwable {
        int foundBooks = 0;
        for (String output : io.getOutputs()) {
            if (output.contains("Clean Code")) {
                foundBooks++;
            }
            if (output.contains("JavaScript")) {
                foundBooks++;
            }
        }
        assertTrue(foundBooks == 2);
    }

    @When("User does not fill in ISBN")
    public void userDoesNotFillInISBN() {
        inputLines.add("");
    }

    @Then("program should ask the information to be put in manually")
    public void programShouldAskTheInformationToBePutInManually() throws Throwable {
        inputLines.add("title");
        inputLines.add("author");
        addEmpties(3);
        inputLines.add("q");
        start();
        ArrayList<String> outputs = io.getOutputs();
        assertTrue(outputs.contains(Color.CYAN.getCode() +"Title: " + Color.ORIGINAL.getCode()));
        assertTrue(outputs.contains(Color.CYAN.getCode() + "Author(s): " + Color.ORIGINAL.getCode()));        
    }


    private void addEmpties(int amount) {
        //add empty lines for tags, courses, comments...
        for (int i = 0; i < amount; i++) {
            inputLines.add("");
        }
    }

    private void start() throws Exception {
        io = new StubIO(inputLines);

        testDaoBook = new FakeBookDao();
        testDaoLink = new FakeLinkDao();
        testDaoTag = new FakeTagDao();

        testService = new BookService(testDaoBook, io);
        testServiceLink = new LinkService(testDaoLink, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);
        testUI.start();
    }
}
