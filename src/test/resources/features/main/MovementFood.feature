@IntegrationTest
Feature: Move the player into Food 

  Background: 
    Given the level 1 design is:
      | FFF |
      | FPF |
      | FFF |

  Scenario: Move left into Food
    When the player moves left
    Then advance level

  Scenario: Move right into Food
    When the player moves right
    Then advance level

  Scenario: Move up into Food
    When the player moves up
    Then advance level

  Scenario: Move down into Food
    When the player moves down
    Then advance level
