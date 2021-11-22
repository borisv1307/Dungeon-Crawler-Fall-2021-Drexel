@IntegrationTest
Feature: Update Fog of War

  Background: 
    Given the level design is:
      | XXXXXXXXXXX |
      | X---------X |
      | X---   ---X |
      | X--- P ---X |
      | X---   ---X |
      | X---------X |      
      | XXXXXXXXXXX |

  Scenario: Move left into empty space
    When the player moves left
    Then the tile located at (4, 3) is lit
    Then the tile located at (4, 4) is lit
    Then the tile located at (4, 5) is lit

  Scenario: Move right into empty space
    When the player moves right
    Then the tile located at (8, 3) is lit
    Then the tile located at (8, 4) is lit
    Then the tile located at (8, 5) is lit

  Scenario: Move up into empty space
    When the player moves up
    Then the tile located at (5, 2) is lit
    Then the tile located at (6, 2) is lit
    Then the tile located at (7, 2) is lit

  Scenario: Move down into empty space
    When the player moves down
    Then the tile located at (5, 6) is lit
    Then the tile located at (6, 6) is lit
    Then the tile located at (7, 6) is lit