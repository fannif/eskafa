Feature: As a user I can list all saved tags

  Scenario: After giving command list tags, program responds with a list of tags
    Given there are saved tags
    When command list tags is selected
    Then program responds with list of tags




