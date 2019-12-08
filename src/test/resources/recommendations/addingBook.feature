Feature: As a user I can add information of a book as recommended reading

  Scenario: After giving command add and filling in information, the book is added to memory
    Given Command add a book is selected
    When User has filled in title "title" and author "author"
    Then Memory should contain a book with title "title" and author "author"



