@IntegrationTest
Feature: Move the player into obstacle

  Background: 
    Given the level design is:
      | XXXXX |
      | X O X |
      | XOPOX |
      | X O X |
      | XXXXX |

  Scenario: Move left into obstacle
    When the player moves left 3 times
    Then the player is located at (2, 3)

  Scenario: Move right into obstacle
    When the player moves right 3 times
    Then the player is located at (4, 3)

  Scenario: Move up into obstacle
    When the player moves up 3 times
    Then the player is located at (3, 2)

  Scenario: Move down into obstacle
    When the player moves down 3 times
    Then the player is located at (3, 4)
