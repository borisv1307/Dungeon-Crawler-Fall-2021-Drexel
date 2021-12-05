@IntegrationTest
Feature: Move the player into fire power up

  Background:
    Given the level design is:
      | XXXXX |
      | X F X |
      | XFPFX |
      | X F X |
      | XXXXX |

  Scenario: Move left into fire power up
    When the player moves left
    Then the player is located at (2, 3)
    And power up at (2, 3) is not longer there
    And the players has "F" added to their power ups

  Scenario: Move right into fire power up
    When the player moves right
    Then the player is located at (4, 3)
    And power up at (4, 3) is not longer there
    And the players has "F" added to their power ups

  Scenario: Move up into fire power up
    When the player moves up
    Then the player is located at (3, 2)
    And power up at (3, 2) is not longer there
    And the players has "F" added to their power ups

  Scenario: Move down into fire power up
    When the player moves down
    Then the player is located at (3, 4)
    And power up at (3, 4) is not longer there
    And the players has "F" added to their power ups

  Scenario: Move left into fire power up with already a fire power up
    Given the players already has "F" added to their power ups
    When the player moves left
    Then the player is located at (2, 3)
    And power up at (2, 3) is not longer there
    And the players has "F" added to their power ups

  Scenario: Move right into fire power up with already a bomb power up
    Given the players already has "B" added to their power ups
    When the player moves right
    Then the player is located at (4, 3)
    And power up at (4, 3) is not longer there
    And the players has "F" added to their power ups
    And the players has "B" added to their power ups

  Scenario: Move up into fire power up with already both power ups added
    Given the players already has "B" added to their power ups
    And the players already has "F" added to their power ups
    When the player moves up
    Then the player is located at (3, 2)
    And power up at (3, 2) is not longer there
    And the players has "F" added to their power ups
    And the players has "B" added to their power ups