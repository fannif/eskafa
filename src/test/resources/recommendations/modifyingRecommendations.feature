Feature: As a User I can  modify reading recommendations

  Scenario: The recommendation to be modified is indentified by title
    Given Command edit recommendation is selected
    When User enters type "book" and title "Clean Code"
    Then The book called "Clean Code" is fetched from memory

  Scenario: User can modify a recommendation
    Given The book "Clean Code" is chosen for modifying
    When User has filled in modified comment "modified"
    Then "Clean Code2 should have a comment "modified"


