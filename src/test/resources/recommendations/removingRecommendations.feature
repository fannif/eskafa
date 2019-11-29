Feature: As a user I can remove a reading recommendation from the list

  Scenario: After giving command "remove book" and filling in the name of a book, the book is removed from memory
    Given Command remove book is selected
    When User has filled in the title "Clean Code" and this book is in memory
    Then Memory should not contain a book called "Clean Code"