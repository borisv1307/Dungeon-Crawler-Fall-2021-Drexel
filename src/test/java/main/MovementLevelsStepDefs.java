package main;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import engine.GameEngine;
import parser.LevelCreationStepDefHelper;
import parser.LevelCreator;
import tiles.TileType;
import values.TestingTunableParameters;
import wrappers.ReaderWrapper;

public class MovementLevelsStepDefs extends LevelCreationStepDefHelper {

	private GameEngine gameEngine;

	@Given("^the first level design is:$")
	public void first_level_is(List<String> levelStrings) throws Throwable {
		writeLevelFile(levelStrings, 1);
		gameEngine = new GameEngine(
				new LevelCreator(TestingTunableParameters.FILE_LOCATION_PREFIX, new ReaderWrapper()));
	}

	@Given("^the second level design is:$")
	public void second_level_is(List<String> levelStrings) throws Throwable {
		writeLevelFile(levelStrings, 2);
	}

	@When("^the player moves down to the door$")
	public void the_player_moves_down_to_the_door() throws Throwable {
		gameEngine.keyDown();
	}

	@When("^the player moves up to the door$")
	public void the_player_moves_up_to_the_door() throws Throwable {
		gameEngine.keyUp();
	}

	@Then("^the player is moved to level (\\d+)$")
	public void the_player_is_moved_to_level(int levelNumber) throws Throwable {
		assertThat(gameEngine.getLevel(), equalTo(levelNumber));
	}

	@Then("^the player x coordinate is (\\d+)$")
	public void players_x_is(int playerX) throws Throwable {
		assertThat(gameEngine.getPlayerXCoordinate(), equalTo(playerX - COORDINATE_OFFSET));
	}

	@Then("^the player y coordinate is (\\d+)$")
	public void players_y_is(int playerY) throws Throwable {
		assertThat(gameEngine.getPlayerYCoordinate(), equalTo(playerY - COORDINATE_OFFSET));
	}

	@Then("^tile \\((\\d+), (\\d+)\\) is \"([^\"]*)\"$")
	public void tile_is(int x, int y, String tileChar) throws Throwable {
		char ch = tileChar.charAt(0);
		TileType actualTileType = gameEngine.getTileFromCoordinates(x - COORDINATE_OFFSET, y - COORDINATE_OFFSET);
		assertThat(actualTileType, equalTo(TileType.getTileTypeByChar(ch)));
	}

}
