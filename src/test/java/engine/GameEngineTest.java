package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.Component;
import java.awt.Point;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngineTest {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;

	GameEngine gameEngine;
	LevelCreator levelCreator;

	@Before
	public void setUp() throws Exception {
		levelCreator = Mockito.mock(LevelCreator.class);
		gameEngine = new GameEngine(levelCreator);
		int level = 1;
		Mockito.verify(levelCreator, Mockito.times(level)).createLevel(gameEngine, level);
	}

	@Test
	public void run() {
		GameFrame gameFrame = Mockito.mock(GameFrame.class);
		Component component = Mockito.mock(Component.class);
		Mockito.when(gameFrame.getComponents()).thenReturn(new Component[] { component });
		gameEngine.run(gameFrame);
		Mockito.verify(component, Mockito.times(1)).repaint();
	}

	@Test
	public void add_and_get_tile() {
		TileType tileType = TileType.PASSABLE;
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		TileType actual = gameEngine.getTileFromCoordinates(ZERO, ONE);
		assertThat(actual, equalTo(tileType));
	}

	@Test
	public void set_and_get_horizontal_dimension() {
		gameEngine.setLevelHorizontalDimension(ONE);
		int actual = gameEngine.getLevelHorizontalDimension();
		assertThat(actual, equalTo(ONE));
	}

	@Test
	public void set_and_get_vertical_dimension() {
		gameEngine.setLevelVerticalDimension(ONE);
		int actual = gameEngine.getLevelVerticalDimension();
		assertThat(actual, equalTo(ONE));
	}

	@Test
	public void add_and_get_player_coordinates() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void add_and_get_two_players_coordinates() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		int playerOneActualX = gameEngine.getPlayerXCoordinate();
		int playerOneActualY = gameEngine.getPlayerYCoordinate();
		gameEngine.addTile(ONE, TWO, tileType);
		int playerTwoActualX = gameEngine.getPlayerXCoordinate();
		int playerTwoActualY = gameEngine.getPlayerYCoordinate();
		assertThat(playerOneActualX, equalTo(ZERO));
		assertThat(playerOneActualY, equalTo(ONE));
		assertThat(playerTwoActualX, equalTo(ONE));
		assertThat(playerTwoActualY, equalTo(TWO));
	}

	@Test
	public void add_and_get_food_coordinates() {
		TileType tileType = TileType.FOOD;
		gameEngine.addTile(ZERO, ONE, tileType);
		int actualX = gameEngine.getFoodXCoordinate();
		int actualY = gameEngine.getFoodYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void set_and_get_exit() {
		boolean exit = true;
		gameEngine.setExit(exit);
		boolean actual = gameEngine.isExit();
		assertThat(actual, equalTo(exit));
	}

	@Test
	public void set_new_food_location() {
		gameEngine.setLevelHorizontalDimension(ONE);
		gameEngine.setLevelVerticalDimension(TWO);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, TWO, TileType.FOOD);
		gameEngine.keyDown();
		assertThat(gameEngine.getFood(), equalTo(new Point(ONE, ONE)));
	}

	@Test
	public void set_new_food_location_random() {
		gameEngine.setLevelHorizontalDimension(THREE);
		gameEngine.setLevelVerticalDimension(TWO);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, TWO, TileType.FOOD);
		gameEngine.addTile(ONE, THREE, TileType.PASSABLE);
		gameEngine.keyDown();
		assertThat(gameEngine.getFood(),
				equalTo(new Point(gameEngine.getNewFoodXCoordinate(), gameEngine.getNewFoodYCoordinate())));
	}

	@Test
	public void add_tail_to_snake() {
		gameEngine.setLevelHorizontalDimension(ONE);
		gameEngine.setLevelVerticalDimension(THREE);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, TWO, TileType.FOOD);
		gameEngine.keyDown();
		assertThat(gameEngine.getTail(ZERO), equalTo(new Point(ONE, TWO)));
	}

	@Test
	public void one_tail_follows_player() {
		gameEngine.setLevelHorizontalDimension(ONE);
		gameEngine.setLevelVerticalDimension(THREE);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, TWO, TileType.FOOD);
		gameEngine.addTile(ONE, THREE, TileType.PASSABLE);
		gameEngine.keyDown();
		gameEngine.keyDown();
		assertThat(gameEngine.getTileFromCoordinates(ONE, TWO), equalTo(TileType.PLAYER));
		assertThat(gameEngine.getTileFromCoordinates(ONE, THREE), equalTo(TileType.PLAYER));
	}

}
