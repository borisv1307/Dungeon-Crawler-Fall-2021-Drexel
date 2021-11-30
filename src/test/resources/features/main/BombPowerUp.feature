@IntegrationTest
Feature: Bomb power up

  Background:
    Given level design is:
      | XXXXX |
      | X   X |
      | X P X |
      | X   X |
      | XXXXX |

  Scenario: Place a bomb without the bomb power up
    When player places a bomb
    Then player is located at (3, 3)
    And tile (3, 3) is " "

  Scenario: Place a bomb with the bomb power up
    Given player has bomb power up
    When player places a bomb
    Then player is located at (3, 3)
    And tile (3, 3) is "O"

  Scenario: Placing bomb twice detonates it
    Given player has bomb power up
    And player places a bomb
    When player places a bomb
    And tile (3, 3) is " "

  Scenario: Bomb detonation does not kill the player (friendly fire)
    Given player has bomb power up
    And player places a bomb
    When player places a bomb
    Then player is located at (3, 3)

  Scenario: Remote Bomb detonation
    Given player has bomb power up
    And player places a bomb
    And player moves right
    And player moves up
    When player places a bomb
    Then player is located at (4, 2)
    And tile (3, 3) is " "

  Scenario: Move then place bomb
    Given player has bomb power up
    And player moves up
    When player moves left
    And player places a bomb
    Then player is located at (2, 2)
    And tile (2, 2) is "O"