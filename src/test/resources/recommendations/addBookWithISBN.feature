Feature: As a User I can give ISBN and the program will fetch title and author automatically
and fill other attributes manually

  Scenario: User can add information automatically via ISBN
    Given Command add a book is selected
    When User has filled in ISBN that is found by search
    Then program should automatically fill in title and author

  Scenario: User can add information manually by not giving ISBN
    Given Command add a book is selected
    When User does not fill in ISBN
    Then program should ask the information to be put in manually


