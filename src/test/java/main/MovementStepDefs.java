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

public class MovementStepDefs extends LevelCreationStepDefHelper {

	private GameEngine gameEngine;

	@Given("^the level design is:$")
	public void level_is(List<String> levelStrings) throws Throwable {
		writeLevelFile(levelStrings);
		gameEngine = new GameEngine(
				new LevelCreator(TestingTunableParameters.FILE_LOCATION_PREFIX, new ReaderWrapper()));
	}

	@When("^the player moves left$")
	public void the_player_moves_left() throws Throwable {
		gameEngine.keyLeft();
	}

	@When("^the player moves right$")
	public void the_player_moves_right() throws Throwable {
		gameEngine.keyRight();
	}

	@When("^the player moves up$")
	public void the_player_moves_up() throws Throwable {
		gameEngine.keyUp();
	}

	@When("^the player moves down$")
	public void the_player_moves_down() throws Throwable {
		gameEngine.keyDown();
	}

	@When("^the player light radius is (\\d+)$")
	public void the_player_light_radius_is(int radius) throws Throwable {
		gameEngine.playerLightRadius = 1;
	}

	@Then("^the player is located at \\((\\d+), (\\d+)\\)$")
	public void the_player_is_located_at(int playerX, int playerY) throws Throwable {
		assertThat(gameEngine.getPlayerXCoordinate(), equalTo(playerX - COORDINATE_OFFSET));
		assertThat(gameEngine.getPlayerYCoordinate(), equalTo(playerY - COORDINATE_OFFSET));
	}

	@Then("^the tile located at \\((\\d+), (\\d+)\\) is lit$")
	public void the_tile_located_at_is_lit(int x, int y) throws Throwable {
		assertThat(gameEngine.getVisibleTileFromCoordinates(x - COORDINATE_OFFSET, y - COORDINATE_OFFSET),
				equalTo(TileType.PASSABLE));
	}

	@Then("^the tile located at \\((\\d+), (\\d+)\\) is unlit$")
	public void the_tile_located_at_is_unlit(int x, int y) throws Throwable {
		assertThat(gameEngine.getVisibleTileFromCoordinates(x - COORDINATE_OFFSET, y - COORDINATE_OFFSET),
				equalTo(TileType.UNLIT));
	}
}
