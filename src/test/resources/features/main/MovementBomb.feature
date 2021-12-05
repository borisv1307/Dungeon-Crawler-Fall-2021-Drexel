@IntegrationTest
Feature: Move player out of and into bomb

  Background:
    Given level design is:
      | XXXXX |
      | X   X |
      | X P X |
      | X   X |
      | XXXXX |
    And player has bomb power up


  Scenario: Move left from bomb
    Given player places a bomb
    When player moves left
    Then player is located at (2, 3)

  Scenario: Move right from bomb
    Given player moves left
    And player places a bomb
    When player moves right
    Then player is located at (3, 3)

  Scenario: Move up from bomb
    Given player moves down
    And player places a bomb
    When player moves up
    Then player is located at (3, 3)

  Scenario: Move down from bomb
    Given player moves up
    And player moves right
    And player places a bomb
    When player moves down
    Then player is located at (4, 3)

  Scenario: Move away from bomb then move left into it
    Given player moves left
    And player places a bomb
    And player moves right
    When player moves left
    Then player is located at (3, 3)

  Scenario: Move away from bomb then move right into it
    Given player moves right
    And player places a bomb
    And player moves left
    When player moves right
    Then player is located at (3, 3)

  Scenario: Move away from bomb then move down into it
    Given player moves down
    And player places a bomb
    And player moves up
    When player moves down
    Then player is located at (3, 3)

  Scenario: Move away from bomb then move up into it
    Given player moves right
    And player moves up
    And player places a bomb
    And player moves down
    When player moves up
    Then player is located at (4, 3)

