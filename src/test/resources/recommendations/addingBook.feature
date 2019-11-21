Feature: As a user I can add information of a book as recommended reading

  Scenario: After giving command add and filling in information, the book is added to memory
    Given Command add is selected
    When User has filled in title "title", author "author", ISBN "111" and type "book"
    Then Memory should contain a book with title "title", author "author" and ISBN "111"



