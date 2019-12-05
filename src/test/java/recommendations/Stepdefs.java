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

    @Given("Command add a book is selected")
    public void commandAddIsSelected() throws Throwable {
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
        inputLines.add(""); //author
        inputLines.add(""); //tags
        inputLines.add(""); //courses
        inputLines.add(""); //comment
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
    public void aLinkWithUrlIsAlreadyInTheMemory() throws Throwable{
        inputLines.add("http://www.kaleva.fi");
        inputLines.add("Kaleva");
        inputLines.add("Link"); 
        addEmpties(4);
        inputLines.add("q");

        start();
    }

    @Then("System should respond with {string}")
    public void memoryShouldContainOnlyOneLinkWithUrl(String respond) throws Throwable {
        ArrayList<String> outputs = io.getOutputs();
        assertTrue(outputs.contains(respond));
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
        assertTrue(io.getOutputs().contains("\nTags:\n"));      
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
        String expected = "\nType: Link"
                + "\n\tTitle: Kaleva"
                + "\n\tURL: \u001B[36m<http://www.kaleva.fi"
                + ">\u001B[0m\n\tTags:|news|"
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
        testService = new BookService(testDaoBook, io);
        testServiceTag = new TagService(testDaoTag, testDaoBook, testDaoLink, io);

        testUI = new CommandLineUI(testService, testServiceLink, testServiceTag, io);
        testUI.start();
    }
}
