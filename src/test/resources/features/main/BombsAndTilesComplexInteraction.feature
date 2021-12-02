@IntegrationTest
Feature: Bomb-Wall complex interaction

  Scenario: Bomb placed where detonation fire range would reach out of the level's bounds
    Given level design is:
      | XXX |
      | XPX |
      | XXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (2, 2) is " "
    Then tile (2, 1) is "X"
    Then tile (3, 2) is "X"
    Then tile (2, 3) is "X"
    Then tile (1, 2) is "X"

  Scenario: Bomb placed where detonation fire range is exactly at level's bounds
    Given level design is:
      | XXXXX |
      | X   X |
      | X P X |
      | X   X |
      | XXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (3, 3) is " "
    Then tile (3, 1) is "X"
    Then tile (5, 3) is "X"
    Then tile (3, 5) is "X"
    Then tile (1, 3) is "X"

  Scenario: Bomb placed where detonation fire range just below level's bounds
    Given level design is:
      | XXXXXXX |
      | X     X |
      | X     X |
      | X  P  X |
      | X     X |
      | X     X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    Then tile (4, 1) is "X"
    Then tile (7, 4) is "X"
    Then tile (4, 7) is "X"
    Then tile (1, 4) is "X"

  Scenario: Bomb detonation has two breakable wall in each direction
    Given level design is:
      | XXXXXXX |
      | X  K  X |
      | X  K  X |
      | XKKPKKX |
      | X  K  X |
      | X  K  X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 3) is " "
    And tile (5, 4) is " "
    And tile (4, 5) is " "
    And tile (3, 4) is " "
    And tile (4, 2) is "K"
    And tile (6, 4) is "K"
    And tile (4, 6) is "K"
    And tile (2, 4) is "K"

  Scenario: Bomb detonation has unbreakable and then breakable wall in each direction
    Given level design is:
      | XXXXXXX |
      | X  K  X |
      | X  X  X |
      | XKXPXKX |
      | X  X  X |
      | X  K  X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 3) is "X"
    And tile (5, 4) is "X"
    And tile (4, 5) is "X"
    And tile (3, 4) is "X"
    And tile (4, 2) is "K"
    And tile (6, 4) is "K"
    And tile (4, 6) is "K"
    And tile (2, 4) is "K"

  Scenario: Bomb detonation has breakable and then unbreakable wall in each direction
    Given level design is:
      | XXXXXXX |
      | X  X  X |
      | X  K  X |
      | XXKPKXX |
      | X  K  X |
      | X  X  X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 3) is " "
    And tile (5, 4) is " "
    And tile (4, 5) is " "
    And tile (3, 4) is " "
    And tile (4, 2) is "X"
    And tile (6, 4) is "X"
    And tile (4, 6) is "X"
    And tile (2, 4) is "X"

  Scenario: Bomb detonation has bomb power up tile and then breakable wall in each direction
    Given level design is:
      | XXXXXXX |
      | X  K  X |
      | X  B  X |
      | XKBPBKX |
      | X  B  X |
      | X  K  X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 3) is " "
    And tile (5, 4) is " "
    And tile (4, 5) is " "
    And tile (3, 4) is " "
    And tile (4, 2) is "K"
    And tile (6, 4) is "K"
    And tile (4, 6) is "K"
    And tile (2, 4) is "K"

  Scenario: Bomb detonation has fire power up tile and then bomb power up tile in each direction
    Given level design is:
      | XXXXXXX |
      | X  B  X |
      | X  F  X |
      | XBFPFBX |
      | X  F  X |
      | X  B  X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 3) is " "
    And tile (5, 4) is " "
    And tile (4, 5) is " "
    And tile (3, 4) is " "
    And tile (4, 2) is "B"
    And tile (6, 4) is "B"
    And tile (4, 6) is "B"
    And tile (2, 4) is "B"

  Scenario: Bomb detonation with varied distanced destroyable and non-destroyable blocks
    Given level design is:
      | XXXXXXXXX |
      | X   K   X |
      | X       X |
      | X       X |
      | X  FPXKFX |
      | X       X |
      | X   B   X |
      | X   K   X |
      | XXXXXXXXX |
    And player has bomb power up
    And player places a bomb
    And player moves down
    And player moves left
    When player places a bomb
    Then tile (5, 5) is " "
    And tile (5, 2) is "K"
    And tile (6, 5) is "X"
    And tile (7, 5) is "K"
    And tile (8, 5) is "F"
    And tile (5, 7) is " "
    And tile (5, 8) is "K"
    And tile (4, 5) is " "