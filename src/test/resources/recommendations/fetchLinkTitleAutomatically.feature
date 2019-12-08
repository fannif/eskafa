Feature: As a User I can give URL and the program fetch the title which I can Accept or reject

  Scenario: When adding a link, the title can be filled in automatically by searching with URL
    Given Command add a new link is selected
    When User has filled in url "https://en.wikipedia.org/wiki/Operating_system"
    Then Program should propose a title that user can accept or reject

  Scenario: When accepting title, the title is added to the recommendation automatically
    Given Command add a new link is selected
    When User has accepted a title proposed
    Then A link containing proposed title should be added to memory

  Scenario: When rejecting title, the user can add title manually:
    Given Command add a new link is selected
    When User has rejected a fetched title
    Then User can fill in title manually