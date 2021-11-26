@IntegrationTest
Feature: Move the player through a door

  Background: 
    Given the level design is:
      | XDX |
      | DPD |
      | XDX |

  Scenario: Move left into door
    When the player moves left
    Then the player is located at (1, 2)

  Scenario: Move right into door
    When the player moves right
    Then the player is located at (3, 2)

  Scenario: Move down into door
    When the player moves down
    Then the player is located at (2, 3)
