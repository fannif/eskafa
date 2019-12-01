Feature: As a user I can search reading recommendations by tag

  Scenario: Searching by tag returns correct result
    Given command search by tag is selected
    When user selects existing tag "news"
    Then recommendations with given tag are listed