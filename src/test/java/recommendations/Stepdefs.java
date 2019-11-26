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


public class Stepdefs {

    List<String> inputLines = new ArrayList<>();
    String input;
    Scanner TestScanner;

    ReaderDao testDao;

    readerDao testDaoLink;

    BookService testService;
    LinkService testServiceLink;
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

        testUI = new CommandLineUI(TestScanner, testService, testServiceLink);
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
        testUI = new CommandLineUI(TestScanner, testService, testServiceLink);

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
        testUI = new CommandLineUI(TestScanner, testService, testServiceLink);
        testUI.start();
    }

    @Then("system responds with a list of books containing a book titled {string}")
    public void systemRespondsWithAListOfBooksContainingABookTitled(String title) throws SQLException {
        assertTrue(testDao.findAll().contains(new Book(0,"", title, "Book", "", new ArrayList<String>(), new ArrayList<String>(), "")));
    }


    public ReaderDao createTestDao() {
        return new ReaderDao() {
            ArrayList<Book> tips = createListForStub();

            public Object findOne(final Object title) {
                Book searchedTip = null;
                for (int i = 0; i < tips.size(); i++) {
                    if (tips.get(i).getTitle().equals(title)) {
                        searchedTip = tips.get(i);
                    }
                }
                return searchedTip;
            }

            public ArrayList findAll() {
                return tips;
            }

            public void save(final Object tip) {
                final Book tipToAdd = (Book) tip;
                tips.add(tipToAdd);
            }

            public void delete(final Object title) throws Exception {
                final Book selectedForDeleting = findWanted(title);
                tips.remove(selectedForDeleting);
            }

            private Book findWanted(final Object title) {
                Book wanted = null;
                for (int i = 0; i < tips.size(); i++) {
                    if (tips.get(i).getTitle().equals(title)) {
                        wanted = tips.get(i);
                    }
                }
                return wanted;
            }

            public ArrayList<Book> createListForStub() {
                final ArrayList<Book> tipList = new ArrayList<>();
                final ArrayList<String> tags1 = new ArrayList<>();
                final ArrayList<String> tags2 = new ArrayList<>();
                tags1.add("clean code");
                tags2.add("Security");
                tags2.add("Popular");
                final ArrayList<String> courses1 = new ArrayList<>();
                final ArrayList<String> courses2 = new ArrayList<>();
                courses1.add("Ohjelmistotuotanto");
                tipList.add(new Book(1,"Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!"));
                tipList.add(new Book(2,"Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2, courses2, ""));
                tipList.add(new Book(3,"Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, ""));
                return tipList;
            }

            @Override
            public void edit(Object tip) throws SQLException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };

    }

}
