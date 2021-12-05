@Integration
Feature: Level to Level Movement

  Background: 
    Given the first level design is:
      | XXXXX |
      | X   X |
      | X P X |
      | XXDXX |
    Given the second level design is:
      | XXDXX |
      | X   X |
      | X   X |
      | XXXXX |
		
  Scenario: Move to new room and back
    When the player moves down to the door
    Then the player is moved to level 2
    And the player x coordinate is 3
    And the player y coordinate is 2
    And tile (1, 1) is "X"
    And tile (2, 1) is "X"
    And tile (3, 1) is "D"
    And tile (4, 1) is "X"
    And tile (5, 1) is "X"
   
    And tile (1, 2) is "X"
    And tile (2, 2) is " "
    And tile (3, 2) is " "
    And tile (4, 2) is " "
    And tile (5, 2) is "X"
  
    And tile (1, 3) is "X"
    And tile (2, 3) is " "
    And tile (3, 3) is " "
    And tile (4, 3) is " "
    And tile (5, 3) is "X"
  
    And tile (1, 4) is "X"
    And tile (2, 4) is "X"
    And tile (3, 4) is "X"
    And tile (4, 4) is "X"
    And tile (5, 4) is "X"

    
		When the player moves up to the door
    Then the player is moved to level 1
    And the player x coordinate is 3
    And the player y coordinate is 3
    And tile (1, 1) is "X"
    And tile (2, 1) is "X"
    And tile (3, 1) is "X"
    And tile (4, 1) is "X"
    And tile (5, 1) is "X"
   
    And tile (1, 2) is "X"
    And tile (2, 2) is " "
    And tile (3, 2) is " "
    And tile (4, 2) is " "
    And tile (5, 2) is "X"
  
    And tile (1, 3) is "X"
    And tile (2, 3) is " "
    And tile (3, 3) is " "
    And tile (4, 3) is " "
    And tile (5, 3) is "X"
  
    And tile (1, 4) is "X"
    And tile (2, 4) is "X"
    And tile (3, 4) is "D"
    And tile (4, 4) is "X"
    And tile (5, 4) is "X"