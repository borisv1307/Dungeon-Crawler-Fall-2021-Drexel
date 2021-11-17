@IntegrationTest
Feature: Move the player into a trap space

  Background: 
    Given the level design is:
      | XXXXXXX |
      | X  T  X |
      | X TPT X |
      | X  T  X |
      | XXXXXXX |

  Scenario: Move right into trap space
    When the player moves right
    Then the player is located at (5, 3)
    And the player lost health

  Scenario: Move left into trap space
    When the player moves left
    Then the player is located at (3, 3)
    And the player lost health

  Scenario: Move up into trap space
    When the player moves up
    Then the player is located at (4, 2)
    And the player lost health

  Scenario: Move down into trap space
    When the player moves down
    Then the player is located at (4, 4)
     And the player lost health
    