Feature: As a User I can search reading recommendations by a word

  Scenario: Program fetches all recommendations containing the desired word
    Given command search by word is selected
    When user has filled in word "Code"
    Then program should return a list with two entries