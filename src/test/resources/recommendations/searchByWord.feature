Feature: As a User I can search reading recommendations by a word

  Scenario: Program fetches all recommendations containing the desired word
    Given Command search by word is selected
    When User has filled in word "Code"
    Then Program should return all books containing the word in their information