@IntegrationTest
Feature: Move the player into a deactivated door

  Background: 
    Given the level design is:
      | XQX |
      | QPQ |
      | XQX |

  Scenario: Move left into door
    When the player moves left
    Then the player is located at (2, 2)

  Scenario: Move right into door
    When the player moves right
    Then the player is located at (2, 2)

  Scenario: Move up into door
    When the player moves up
    Then the player is located at (2, 2)

  Scenario: Move down into door
    When the player moves down
    Then the player is located at (2, 2)
