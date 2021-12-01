@IntegrationTest
Feature: Bomb-Wall interaction

  Scenario: Bomb detonation breaks breakable walls immediately next to it
    Given level design is:
      | XXXXXXX |
      | X     X |
      | X  K  X |
      | X KPK X |
      | X  K  X |
      | X     X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 3) is " "
    And tile (5, 4) is " "
    And tile (4, 5) is " "
    And tile (3, 4) is " "

  Scenario: Bomb detonation breaks breakable walls 1 tile away from it
    Given level design is:
      | XXXXXXX |
      | X  K  X |
      | X     X |
      | XK P KX |
      | X     X |
      | X  K  X |
      | XXXXXXX |
    And player has bomb power up
    And player places a bomb
    And player moves down
    And player moves left
    When player places a bomb
    Then tile (4, 4) is " "
    And tile (4, 2) is " "
    And tile (6, 4) is " "
    And tile (4, 6) is " "
    And tile (2, 4) is " "

  Scenario: Bomb detonation not at level's center breaks breakable walls 1 tile away from it
    Given level design is:
      | XXXXXXXXX |
      | X   KK  X |
      | X       X |
      | X  K   KX |
      | XK  P  KX |
      | X    K  X |
      | X       X |
      | X   K   X |
      | XXXXXXXXX |
    And player has bomb power up
    And player moves up
    And player moves right
    And player places a bomb
    When player places a bomb
    Then tile (6, 4) is " "
    And tile (6, 2) is " "
    And tile (8, 4) is " "
    And tile (6, 6) is " "
    And tile (4, 4) is " "


  Scenario: Bomb detonation does not break breakable walls 2 tiles away from it (default fire range is 2)
    Given level design is:
      | XXXXXXXXX |
      | X   K   X |
      | X       X |
      | X       X |
      | XK  P  KX |
      | X       X |
      | X       X |
      | X   K   X |
      | XXXXXXXXX |
    And player has bomb power up
    And player places a bomb
    And player moves down
    And player moves left
    When player places a bomb
    Then tile (5, 5) is " "
    And tile (5, 2) is "K"
    And tile (8, 5) is "K"
    And tile (5, 8) is "K"
    And tile (2, 5) is "K"
