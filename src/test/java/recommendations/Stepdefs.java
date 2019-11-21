package recommendations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import recommendations.dao.fileDao;
import recommendations.domain.Book;
import recommendations.services.BookService;
import recommendations.ui.CommandLineUI;
import io.cucumber.java.en.Then;
import java.io.IOException;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import recommendations.dao.readerDao;

public class Stepdefs {

    List<String> inputLines = new ArrayList<>();
    String input;
    Scanner TestScanner;
    readerDao testDao;
    BookService testService;
    CommandLineUI testUI;

    

    public void makeInputString(List<String> inputLines) {
        this.input = "";
        for (int i = 0; i < inputLines.size(); i++) {
            input = input + "\n" + inputLines.get(i);
        }
    }

    @Given("Command add is selected")
    public void commandAddIsSelected() throws Throwable {
        inputLines.add("2");
    }

    @When("User has filled in title {string}, author {string}, ISBN {string} and type {string}")
    public void informationIsFilled(String title, String author, String isbn, String type) throws Throwable {
        
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

    @Then("Memory should contain a book with title {string}, author {string} and ISBN {string}")
    public void memoryContainsBook(String title, String author, String isbn) {
        Book found = (Book) testDao.findOne(title);
        assertTrue(found.getTitle().equals(title));
        assertTrue(found.getAuthor().equals(author));
        assertTrue(found.getISBN().equals(isbn));
    }
    
   public readerDao createTestDao() {
       return new readerDao() {
        ArrayList<Book> tips = createListForStub();

        public Object findOne(Object title) {
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

        public boolean save(Object tip) throws IOException {
            Book tipToAdd = (Book)tip;
            tips.add(tipToAdd);
            return true;
        }

        public void delete(Object title) throws Exception {
            Book selectedForDeleting = findWanted(title);
            tips.remove(selectedForDeleting);
        }

        private Book findWanted(Object title) {
            Book wanted = null;
            for (int i= 0; i < tips.size(); i++) {
                if (tips.get(i).getTitle().equals(title)) {
                    wanted = tips.get(i);
                }
            }
            return wanted;
        }
        public ArrayList<Book> createListForStub() {
            ArrayList<Book> tipList = new ArrayList<>();
            ArrayList<String> tags1 = new ArrayList<>();
            ArrayList<String> tags2 = new ArrayList<>();
            tags1.add("clean code");
            tags2.add("Security");
            tags2.add("Popular");
            ArrayList<String> courses = new ArrayList<>();
            courses.add("Ohjelmistotuotanto");
            tipList.add(new Book("Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1 , courses, "Must have!"));
            courses.remove("Ohjelmistotuotanto");
            courses.add("");
            tipList.add(new Book("Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2 , courses, ""));
            tipList.add(new Book("Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2 , courses, ""));
            return tipList;
        }
        
    };

   }

    



    
 
}

