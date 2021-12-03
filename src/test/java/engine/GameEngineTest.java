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
	private static final int THREE = 3;

	GameEngine gameEngine;
	CollisionCounter collisionCounter;

	@Before
	public void setUp() throws Exception {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		gameEngine = new GameEngine(levelCreator);
		int level = 1;
		Mockito.verify(levelCreator, Mockito.times(level)).createLevel(gameEngine, level);
		collisionCounter = new CollisionCounter(gameEngine);
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
	public void move_up_to_passable_brige_tile() {
		gameEngine.addTile(ONE, ZERO, TileType.PASSABLE_BRIDGE);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyUp();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ZERO));
	}

	@Test
	public void move_down_to_passable_brige_tile() {
		gameEngine.addTile(ONE, ZERO, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, TileType.PASSABLE_BRIDGE);
		gameEngine.keyDown();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void move_up_to_door() {
		gameEngine.addTile(ONE, ZERO, TileType.DOOR);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyUp();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ZERO));
	}

	@Test
	public void move_down_to_door() {
		gameEngine.addTile(ONE, ZERO, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, TileType.DOOR);
		gameEngine.keyDown();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void move_left_to_door() {
		gameEngine.addTile(ZERO, ONE, TileType.DOOR);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void move_right_to_door() {
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, TileType.DOOR);
		gameEngine.keyRight();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void bring_player_back_to_initial_position() {
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, TileType.PASSABLE);
		gameEngine.keyRight();
		gameEngine.bringPlayerBackToInitialPosition();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void bring_player_back_to_initial_position_if_collision_with_e_tile() {
		gameEngine.addTile(ZERO, ONE, TileType.NOT_PASSABLE_BRIDGE);
		gameEngine.addTile(ZERO, TWO, TileType.PASSABLE);
		gameEngine.addTile(ZERO, THREE, TileType.PLAYER);
		gameEngine.keyUp();
		gameEngine.keyUp();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(THREE));
	}

	@Test
	public void set_and_get_level() {
		int level = TWO;
		gameEngine.setLevel(level);
		int actual = gameEngine.getCurrentLevel();
		assertThat(actual, equalTo(level));
	}

	@Test
	public void player_goes_to_next_level_if_reaches_door() {
		gameEngine.addTile(ZERO, ONE, TileType.DOOR);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();
		gameEngine.goToNextLevel();
		int newLevel = gameEngine.getCurrentLevel();
		assertThat(newLevel, equalTo(TWO));
	}

	@Test
	public void player_does_not_go_to_next_level_if_tile_is_not_door() {
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();
		gameEngine.goToNextLevel();
		int newLevel = gameEngine.getCurrentLevel();
		assertThat(newLevel, equalTo(ONE));
	}

	@Test
	public void player_cannot_go_to_an_invalid_level() {
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, TileType.DOOR);
		gameEngine.setLevel(THREE);
		gameEngine.keyRight();
		gameEngine.goToNextLevel();
		boolean exit = true;
		boolean actual = gameEngine.isExit();
		assertThat(actual, equalTo(exit));
	}

	@Test
	public void reinitialize_collision_counters_when_going_to_next_level() {

		gameEngine.addTile(ONE, ONE, TileType.PLAYER);
		gameEngine.addTile(TWO, ONE, TileType.DEACTIVATED_DOOR);
		gameEngine.addTile(ONE, ZERO, TileType.NOT_PASSABLE_BRIDGE);
		gameEngine.addTile(ZERO, ONE, TileType.DOOR);
		gameEngine.keyRight();
		gameEngine.keyUp();
		gameEngine.keyLeft();
		gameEngine.goToNextLevel();
		int doorCollisionCounter = collisionCounter.getDoorCollisionCounter();
		int bridgeCollisonCounter = collisionCounter.getBridgeCollisionCounter();
		assertThat(doorCollisionCounter, equalTo(ZERO));
		assertThat(bridgeCollisonCounter, equalTo(ZERO));
	}

	@Test
	public void change_obstacle_to_passable_after_three_collisions() {
		TileType obstacle = TileType.OBSTACLE;
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, obstacle);
		gameEngine.keyRight();
		gameEngine.keyRight();
		gameEngine.keyRight();
		gameEngine.changeObstacleToPassable(obstacle, ONE, ONE);
		TileType actual = gameEngine.getTileFromCoordinates(ONE, ONE);
		assertThat(actual, equalTo(TileType.PASSABLE));
	}

	@Test
	public void do_not_change_obstacle_to_passable_after_two_collisions() {
		TileType obstacle = TileType.OBSTACLE;
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, obstacle);
		gameEngine.keyRight();
		gameEngine.keyRight();
		gameEngine.changeObstacleToPassable(obstacle, ONE, ONE);
		TileType actual = gameEngine.getTileFromCoordinates(ONE, ONE);
		assertThat(actual, equalTo(TileType.OBSTACLE));
	}

	@Test
	public void change_two_obstacles_to_passable_after_six_collisions() {
		TileType obstacleOne = TileType.OBSTACLE;
		TileType obstacleTwo = TileType.OBSTACLE;
		gameEngine.addTile(ZERO, ONE, obstacleOne);
		gameEngine.addTile(ZERO, TWO, obstacleTwo);
		gameEngine.addTile(ZERO, THREE, TileType.PLAYER);
		gameEngine.keyUp();
		gameEngine.keyUp();
		gameEngine.keyUp();
		gameEngine.keyUp();
		gameEngine.keyUp();
		gameEngine.keyUp();
		TileType actualObstacleOne = gameEngine.getTileFromCoordinates(ZERO, ONE);
		TileType actualObstacleTwo = gameEngine.getTileFromCoordinates(ZERO, TWO);
		assertThat(actualObstacleOne, equalTo(TileType.PASSABLE));
		assertThat(actualObstacleTwo, equalTo(TileType.PASSABLE));
	}

	@Test
	public void check_for_obstacle_tiles_one_obstacle() {
		gameEngine.addTile(ZERO, ONE, TileType.OBSTACLE);
		boolean noObstacle = gameEngine.checkIfNoObstacles();
		assertThat(noObstacle, equalTo(false));
	}

	@Test
	public void check_for_obstacle_tiles_zero_obstacle() {
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		boolean noObstacle = gameEngine.checkIfNoObstacles();
		assertThat(noObstacle, equalTo(true));
	}

	@Test
	public void door_still_deactivated_if_there_are_still_obstacles_and_level_is_three() {
		gameEngine.setLevel(THREE);
		gameEngine.addTile(ZERO, ZERO, TileType.OBSTACLE);
		gameEngine.addTile(ONE, ZERO, TileType.PLAYER);
		TileType door = TileType.DEACTIVATED_DOOR;
		gameEngine.addTile(TWO, ZERO, door);
		gameEngine.keyRight();
		gameEngine.finishGame(door);
		boolean exit = gameEngine.isExit();
		assertThat(exit, equalTo(false));

	}

	@Test
	public void finish_game_when_zero_obstacles_and_player_reaches_door_and_level_is_three() {
		gameEngine.setLevel(THREE);
		gameEngine.addTile(ZERO, ZERO, TileType.PASSABLE);
		gameEngine.addTile(ONE, ZERO, TileType.PLAYER);
		TileType door = TileType.DEACTIVATED_DOOR;
		gameEngine.addTile(TWO, ZERO, door);
		gameEngine.keyRight();
		gameEngine.finishGame(door);
		boolean exit = gameEngine.isExit();
		assertThat(exit, equalTo(true));
	}

	@Test
	public void do_not_finish_game_if_level_is_not_three() {
		gameEngine.addTile(ZERO, ZERO, TileType.PASSABLE);
		gameEngine.addTile(ONE, ZERO, TileType.PLAYER);
		TileType door = TileType.DEACTIVATED_DOOR;
		gameEngine.addTile(TWO, ZERO, door);
		gameEngine.keyRight();
		gameEngine.finishGame(door);
		boolean exit = gameEngine.isExit();
		assertThat(exit, equalTo(false));
	}

}
