Feature: As a user I can add a link as recommendation

  Scenario: After giving command add a new link and filling in information, the link is added to memory
    Given Command add a new link is selected
    When User has filled in title "title", url "url" and type "link"
    Then Memory should contain a link with title "title" and url "url"

  Scenario: A link with same url can only be added once
    Given Command add a new link is selected
    When User has filled in title "title", url "url" and type "link"
    And a link with url "url" is already in the memory
    Then Memory should contain only one link with url "url"

  Scenario: If metadata cannot be loaded, the program should ask if user wants to add the link without metadata
   Given Given command add a new link is selected and there is no internet connection
   When User has filled in title "title", url "url" and type "link"
   Then Program should respond with "Do you want to add link without metadata?"
