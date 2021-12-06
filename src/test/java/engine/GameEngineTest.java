package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.Component;

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
	private static final int THREE = 2;

	GameEngine gameEngine;

	@Before
	public void setUp() throws Exception {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
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
	public void set_and_get_exit() {
		boolean exit = true;
		gameEngine.setExit(exit);
		boolean actual = gameEngine.isExit();
		assertThat(actual, equalTo(exit));
	}

	@Test
	public void key_left() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		int actualX = gameEngine.getPlayerXCoordinate();
		gameEngine.keyLeft();
		assertThat(actualX, equalTo(gameEngine.getPlayerXCoordinate() + 1));
	}

	@Test
	public void key_right() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		gameEngine.addTile(ONE, ONE, TileType.PASSABLE);
		int actualX = gameEngine.getPlayerXCoordinate();
		gameEngine.keyRight();
		assertThat(actualX, equalTo(gameEngine.getPlayerXCoordinate() - 1));
	}

	@Test
	public void accept_passable() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		boolean tF = gameEngine.acceptPassable(loc);
		assertThat(tF, equalTo(true));
	}

	@Test
	public void accept_finish() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.FINISH);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		boolean tF = gameEngine.acceptFinish(loc);
		assertThat(tF, equalTo(true));
	}

	@Test
	public void accept_key1() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.KEY1);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		boolean tF = gameEngine.acceptKey1(loc);
		assertThat(tF, equalTo(true));
	}

	@Test
	public void accept_key2() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.KEY2);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		boolean tF = gameEngine.acceptKey2(loc);
		assertThat(tF, equalTo(true));
	}

	@Test
	public void accept_key3() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.KEY3);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		boolean tF = gameEngine.acceptKey3(loc);
		assertThat(tF, equalTo(true));
	}

	@Test
	public void level_five() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.KEY1);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		gameEngine.levelFive(loc, ZERO, ONE);
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void level_six() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ONE, ONE, tileType);
		gameEngine.addTile(ZERO, ONE, TileType.FINISH);
		TileType loc = gameEngine.attemptedTiles(ZERO, ONE);
		gameEngine.levelSix(loc, ZERO, ONE);
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

}

