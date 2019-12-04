Feature: As a User I can  modify reading recommendations

  Scenario: The recommendation to be modified is indentified by title
    Given command modify is selected
    When user enters title "Clean Code"
    Then the book called "Clean Code" is fetched from memory

  Scenario: User can modify a recommendation
    Given the book "Clean Code" is chosen for modifying
    When user has filled in modified comment "modified"
    Then "Clean Code2 should have a comment "modified"


