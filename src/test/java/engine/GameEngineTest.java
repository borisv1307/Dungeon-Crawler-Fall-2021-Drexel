package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.Component;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import movement.PlayerMovement;
import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngineTest {

	private static final int ZERO = 0;
	private static final int ONE = 1;

	private GameEngine gameEngine;
	private PlayerMovement playerMovement;

	@Before
	public void setUp() throws Exception {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		playerMovement = new PlayerMovement();
		gameEngine = new GameEngine(levelCreator, playerMovement);
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
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_right() {
		gameEngine.addTile(ONE, ONE, TileType.PASSABLE);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_up() {
		gameEngine.addTile(ZERO, ZERO, TileType.PASSABLE);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyUp();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ZERO));
	}

	@Test
	public void key_down() {
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		gameEngine.addTile(ZERO, ZERO, TileType.PLAYER);
		gameEngine.keyDown();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void get_coin_count() {
		int actual = gameEngine.getCoinCount();
		assertThat(actual, equalTo(ZERO));
	}

	@Test
	public void player_moving_to_coin_increases_coin_count() {
		gameEngine.addTile(ONE, ONE, TileType.COIN);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		int actual = gameEngine.getCoinCount();
		assertThat(actual, equalTo(ONE));
	}

	@Test
	public void player_moves_over_coin_tile() {
		gameEngine.addTile(ZERO, ONE, TileType.COIN);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void coin_tile_becomes_passable_once_collected_by_player_and_player_leaves() {
		TileType tileType = TileType.PASSABLE;
		gameEngine.addTile(ONE, ONE, TileType.COIN);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		gameEngine.keyLeft();
		TileType actual = gameEngine.getTileFromCoordinates(ONE, ONE);
		assertThat(actual, equalTo(tileType));
	}

	@Test
	public void player_moves_on_obstacle_and_game_ends() {
		gameEngine.addTile(ONE, ONE, TileType.OBSTACLE);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		boolean actual = gameEngine.isExit();
		assertThat(actual, equalTo(true));

	}

	@Test
	public void get_level() {
		int level = 1;
		int actual = gameEngine.getLevel();
		assertThat(actual, equalTo(level));
	}

	@Test
	public void increase_level() {
		int level = 2;
		gameEngine.increaseLevel();
		int actual = gameEngine.getLevel();
		assertThat(actual, equalTo(level));
	}

	@Test
	public void decrease_level() {
		int level = 0;
		gameEngine.decreaseLevel();
		int actual = gameEngine.getLevel();
		assertThat(actual, equalTo(level));
	}

	@Test
	public void increase_level_on_player_move_over_tile() {
		int level = 2;
		gameEngine.addTile(ONE, ONE, TileType.NEXT_LEVEL);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		int actual = gameEngine.getLevel();
		assertThat(actual, equalTo(level));
	}

	@Test
	public void decrease_level_on_player_move_over_tile() {
		int level = 0;
		gameEngine.addTile(ONE, ONE, TileType.PREVIOUS_LEVEL);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		int actual = gameEngine.getLevel();
		assertThat(actual, equalTo(level));
	}

	@Test
	public void next_level_created_when_moving_on_next_level_tile() {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		gameEngine = new GameEngine(levelCreator, playerMovement);
		gameEngine.addTile(ONE, ONE, TileType.NEXT_LEVEL);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		Mockito.verify(levelCreator, Mockito.times(ONE)).createLevel(gameEngine, 2);
	}

	@Test
	public void previous_level_created_when_moving_on_previous_level_tile() {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		gameEngine = new GameEngine(levelCreator, playerMovement);
		gameEngine.addTile(ONE, ONE, TileType.PREVIOUS_LEVEL);
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();
		int level = 0;
		Mockito.verify(levelCreator, Mockito.times(ONE)).createLevel(gameEngine, level);
	}

}