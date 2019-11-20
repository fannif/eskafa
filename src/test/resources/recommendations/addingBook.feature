Feature: As a user I can add information of a book as recommended reading

  Scenario: User can fill in information of a book by giving the command add
    Given: Command add is selected
    When: User has filled in three words
    Then: The program should have asked for title, author and ISBN

  Scenario: After filling in information, the book is added to memory
    Given: Command add is selected
    When: User has filled in title "title", author "author" and ISBN "111"
    Then:  Memory should contain a book with title "title", author "author" and ISBN "111"



