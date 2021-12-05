@IntegrationTest
Feature: Move the player into breakable wall

  Background:
    Given the level design is:
      | XXXXX |
      | X K X |
      | XKPKX |
      | X K X |
      | XXXXX |

  Scenario: Move left into breakable wall
    When the player moves left
    Then the player is located at (3, 3)

  Scenario: Move right into breakable wall
    When the player moves right
    Then the player is located at (3, 3)

  Scenario: Move up into breakable wall
    When the player moves up
    Then the player is located at (3, 3)

  Scenario: Move down into breakable wall
    When the player moves down
    Then the player is located at (3, 3)
