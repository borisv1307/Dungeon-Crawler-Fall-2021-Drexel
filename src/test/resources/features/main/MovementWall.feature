@IntegrationTest
Feature: Move the player into wall

  Background: 
    Given the level design is:
      | XXX |
      | XPX |
      | XXX |

  Scenario: Move left into wall
    When the player moves left
    Then exit

  Scenario: Move right into wall
    When the player moves right
    Then exit

  Scenario: Move up into wall
    When the player moves up
    Then exit

  Scenario: Move down into wall
    When the player moves down
    Then exit
