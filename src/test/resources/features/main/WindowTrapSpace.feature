@IntegrationTest
Feature: Move the player into a trap space until all health is lost

  Background: 
    Given the level design is:
      | XXXXXXXXXXX |
      | X         X |
      | X  PTTTTT X |
      | X         X |
      | XXXXXXXXXXX |

  Scenario: Move into a trap space 5 times and lose the game
    When the player moves right 5 times 
    Then the game is over    