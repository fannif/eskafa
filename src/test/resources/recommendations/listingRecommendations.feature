Feature: As a user I can list all the reading recommendations

    Scenario: Saved book is included in list
        Given book titled "testBook" has been added
        When command list is selected
        Then system responds with a list of books containing a book titled "testBook"





    