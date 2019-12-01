Feature: As a user I can add a link as recommendation

  Scenario: After giving command add a new link and filling in information, the link is added to memory
    Given Command add a new link is selected
    When User has filled in url "http://www.google.com", title "google" and type "link"
    Then Memory should contain a link with title "google" and url "http://www.google.com"

  Scenario: A link with same url can only be added once
    Given Command add a new link is selected
    When User tries to add url that is already in memory
    Then System should respond with "Please, check your input and try again!"

  Scenario: If metadata cannot be loaded, the program should ask if user wants to add the link without metadata
   Given Given command add a new link is selected and there is no internet connection
   When User has filled in url "url", title "title" and type "link"
   Then Program should respond with "Do you want to add link without metadata?"
