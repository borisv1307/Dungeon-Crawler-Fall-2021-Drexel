package main;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import engine.GameEngine;
import parser.LevelCreationStepDefHelper;
import parser.LevelCreator;
import tiles.TileType;
import values.TestingTunableParameters;
import wrappers.ReaderWrapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BombsStepDefs extends LevelCreationStepDefHelper {
	private GameEngine gameEngine;

	//Most of the steps are duplicate from other StepDefs files, since we can't share the steps between files.
	@Given("^level design is:$")
	public void levelDesignIs(List<String> levelStrings) throws Throwable {
		writeLevelFile(levelStrings);
		gameEngine = new GameEngine(
				new LevelCreator(TestingTunableParameters.FILE_LOCATION_PREFIX, new ReaderWrapper()));
	}

	@When("^player places a bomb$")
	public void playerPlacesABomb() {
		gameEngine.keyEnter();
	}

	@Then("^player is located at \\((\\d+), (\\d+)\\)$")
	public void playerIsLocatedAt(int playerX, int playerY) throws Throwable {
		assertThat(gameEngine.getPlayerXCoordinate(), equalTo(playerX - COORDINATE_OFFSET));
		assertThat(gameEngine.getPlayerYCoordinate(), equalTo(playerY - COORDINATE_OFFSET));
	}

	@And("^tile \\((\\d+), (\\d+)\\) is \"([^\"]*)\"$")
	public void tileIs(int x, int y, String tileChar) throws Throwable {
		char ch = tileChar.charAt(0);
		TileType actualTileType = gameEngine.getTileFromCoordinates(x - COORDINATE_OFFSET, y - COORDINATE_OFFSET);
		assertThat(actualTileType, equalTo(TileType.getTileTypeByChar(ch)));
	}


	@Given("^player has bomb power up$")
	public void playerHasBombPowerUp() {
		gameEngine.addPowerUp(TileType.BOMB_POWER_UP);
	}

	@When("^player moves left$")
	public void player_moves_left() throws Throwable {
		gameEngine.keyLeft();
	}

	@When("^player moves right$")
	public void player_moves_right() throws Throwable {
		gameEngine.keyRight();
	}

	@When("^player moves up$")
	public void player_moves_up() throws Throwable {
		gameEngine.keyUp();
	}

	@When("^player moves down$")
	public void player_moves_down() throws Throwable {
		gameEngine.keyDown();
	}

	@And("^player has fire power up$")
	public void playerHasFirePowerUp() {
		gameEngine.addPowerUp(TileType.FIRE_POWER_UP);
	}
}
