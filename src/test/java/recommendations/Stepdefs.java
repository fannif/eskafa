package recommendations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import recommendations.domain.Book;
import recommendations.services.BookService;
import recommendations.ui.CommandLineUI;
import io.cucumber.java.en.Then;
import java.io.IOException;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import recommendations.dao.readerDao;

public class Stepdefs {

    List<String> inputLines = new ArrayList<>();
    String input;
    Scanner TestScanner;
    readerDao testDao;
    BookService testService;
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
        testDao = createTestDao();
        testService = new BookService(testDao);
        testUI = new CommandLineUI(TestScanner, testService);
        testUI.start();
    } 

    
    @When("User has filled in the title {string} and this book is in memory")
    public void userTriesToRemoveBookThatIsInMemory(String title) throws Throwable {
        
        inputLines.add(title);
        inputLines.add("q");
        makeInputString(inputLines);

        testDao = createTestDao();
        testService = new BookService(testDao);       
        TestScanner = new Scanner(input);
        testUI = new CommandLineUI(TestScanner, testService);

        testUI.start();

    }
    

    @Then("Memory should contain a book with title {string}, author {string} and ISBN {string}")
    public void memoryContainsBook(final String title, final String author, final String isbn) {
        final Book found = (Book) testDao.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getAuthor().equals(author));
        assertTrue(found.getISBN().equals(isbn));
    }

    
    @Then("Memory should not contain a book called {string}")
    public void memoryShouldNotContainTheBook(String title) throws Throwable {
        assertNull(testDao.findOne(title));
    }
    
    
   public readerDao createTestDao() {
       return new readerDao() {
        ArrayList<Book> tips = createListForStub();

        public Object findOne(final Object title) {
            Book searchedTip = null; 
            for (int i= 0; i < tips.size(); i++) {
                if (tips.get(i).getTitle().equals(title)) {
                    searchedTip = tips.get(i);
                }
            }
            return searchedTip;
        }

        public ArrayList findAll() {
            return tips;
        }

        public boolean save(final Object tip) throws IOException {
            final Book tipToAdd = (Book)tip;
            tips.add(tipToAdd);
            return true;
        }

        public void delete(final Object title) throws Exception {
            final Book selectedForDeleting = findWanted(title);
            tips.remove(selectedForDeleting);
        }

        private Book findWanted(final Object title) {
            Book wanted = null;
            for (int i= 0; i < tips.size(); i++) {
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
            tipList.add(new Book("Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1 , courses1, "Must have!"));
            tipList.add(new Book("Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2 , courses2, ""));
            tipList.add(new Book("Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2 , courses2, ""));
            return tipList;
        }
        
    };

   }

    



    
 
}

