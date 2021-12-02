@IntegrationTest
Feature: Fire power up

  Scenario: Bomb detonation with breakable walls 2 tile away from it while holding fire power up
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
    And player has fire power up
    And player places a bomb
    When player places a bomb
    Then tile (5, 5) is " "
    And tile (5, 2) is " "
    And tile (8, 5) is " "
    And tile (5, 8) is " "
    And tile (2, 5) is " "

  Scenario: Bomb detonation with 2 breakable walls 1 tile away from it while holding fire power up
    Given level design is:
      | XXXXXXXXX |
      | X   K   X |
      | X   K   X |
      | X       X |
      | XKK P KKX |
      | X       X |
      | X   K   X |
      | X   K   X |
      | XXXXXXXXX |
    And player has bomb power up
    And player has fire power up
    And player places a bomb
    When player places a bomb
    Then tile (5, 5) is " "
    And tile (5, 3) is " "
    And tile (7, 5) is " "
    And tile (5, 7) is " "
    And tile (3, 5) is " "
    And tile (5, 2) is "K"
    And tile (8, 5) is "K"
    And tile (5, 8) is "K"
    And tile (2, 5) is "K"

  Scenario: Bomb detonation with unbreakable wall and then breakable walls while holding fire power up
    Given level design is:
      | XXXXXXXXX |
      | X   K   X |
      | X   X   X |
      | X       X |
      | XKX P XKX |
      | X       X |
      | X   X   X |
      | X   K   X |
      | XXXXXXXXX |
    And player has bomb power up
    And player has fire power up
    And player places a bomb
    When player places a bomb
    Then tile (5, 5) is " "
    And tile (5, 3) is "X"
    And tile (7, 5) is "X"
    And tile (5, 7) is "X"
    And tile (3, 5) is "X"
    And tile (5, 2) is "K"
    And tile (8, 5) is "K"
    And tile (5, 8) is "K"
    And tile (2, 5) is "K"

  Scenario: Bomb detonation with breakable walls 2 tile away from it when adding fire power up before bomb power up
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
    And player has fire power up
    And player has bomb power up
    And player places a bomb
    When player places a bomb
    Then tile (5, 5) is " "
    And tile (5, 2) is " "
    And tile (8, 5) is " "
    And tile (5, 8) is " "
    And tile (2, 5) is " "