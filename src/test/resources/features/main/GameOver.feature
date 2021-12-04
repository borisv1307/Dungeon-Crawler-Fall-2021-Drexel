@IntegrationTest
Feature: Game over when hit wall or tail

  Background: 
    Given the design is:
      | XXX |
      | XPX |
      | XXX |

  Scenario: Move left into wall or tail
    When the player moves left
    Then exit

  Scenario: Move right into wall or tail
    When the player moves right
    Then exit

  Scenario: Move up into wall or tail
    When the player moves up
    Then exit

  Scenario: Move down into wall or tail
    When the player moves down
    Then exit