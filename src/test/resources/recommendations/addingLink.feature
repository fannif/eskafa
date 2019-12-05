Feature: As a user I can add a link as recommendation

  Scenario: After giving command add a new link and filling in information, the link is added to memory
    Given Command add a new link is selected
    When User has filled in url "http://www.google.com", title "google" and type "link"
    Then Memory should contain a link with title "google" and url "http://www.google.com"

  Scenario: A link with same title can only be added once
    Given Command add a new link is selected
    When User tries to add link that is already in memory
    Then System should respond with "u001B[91m"+"Please, check your input and try again!"+"\u001B[0m"

