package engine;

import level.LevelCreator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tiles.TileType;
import ui.GameFrame;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class GameEngineTest {

	private static final int ZERO = 0;
	private static final int ONE = 1;

	private static final int TILE_X_RANGE = 10;
	private static final int TILE_Y_RANGE = 10;
	private static final int CENTER_X = TILE_X_RANGE / 2;
	private static final int CENTER_Y = TILE_Y_RANGE / 2;

	GameEngine gameEngine;

	@Before
	public void setUp() {
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
	public void update_type_type() {
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		gameEngine.changeTileType(ZERO, ONE, TileType.NOT_PASSABLE);
		TileType actual = gameEngine.getTileFromCoordinates(ZERO, ONE);
		assertThat(actual, equalTo(TileType.NOT_PASSABLE));
	}

	@Test
	public void find_location_of_closest_pocket() {
		fillBoardWithNonPassableTiles();
		Point pocketLocation = new Point(CENTER_X - 1, CENTER_Y);
		gameEngine.changeTileType(pocketLocation.x, pocketLocation.y, TileType.PASSABLE);
		Point actual = gameEngine.findClosestLocationOfATileType(CENTER_X, CENTER_Y, TileType.PASSABLE);

		assertEquals(pocketLocation, actual);
	}

	@Test
	public void pocket_location_found_opposite_side() {
		fillBoardWithNonPassableTiles();
		Point pocketLocation = new Point(CENTER_X + 1, CENTER_Y);
		gameEngine.changeTileType(pocketLocation.x, pocketLocation.y, TileType.PASSABLE);
		Point actual = gameEngine.findClosestLocationOfATileType(CENTER_X, CENTER_Y, TileType.PASSABLE);

		assertEquals(pocketLocation, actual);
	}

	@Test
	public void two_pockets_picks_closer() {
		fillBoardWithNonPassableTiles();
		Point pocketLocation = new Point(CENTER_X + 2, CENTER_Y);
		gameEngine.changeTileType(pocketLocation.x, pocketLocation.y, TileType.PASSABLE);
		gameEngine.changeTileType(CENTER_X + 4, CENTER_Y, TileType.PASSABLE);
		Point actual = gameEngine.findClosestLocationOfATileType(CENTER_X, CENTER_Y, TileType.PASSABLE);

		assertEquals(pocketLocation, actual);
	}

	@Test
	public void not_touching_a_passable() {
		gameEngine.addTile(0, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(2, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 1, TileType.PLAYER);
		gameEngine.addTile(2, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 2, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 2, TileType.NOT_PASSABLE);
		gameEngine.addTile(2, 2, TileType.NOT_PASSABLE);

		assertFalse(gameEngine.isTileTouching(1, 1, TileType.PASSABLE));
	}

	@Test
	public void touching_a_passable() {
		gameEngine.addTile(0, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(2, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 1, TileType.PLAYER);
		gameEngine.addTile(2, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 2, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 2, TileType.PASSABLE);
		gameEngine.addTile(2, 2, TileType.NOT_PASSABLE);

		assertTrue(gameEngine.isTileTouching(1, 1, TileType.PASSABLE));
	}

	@Test
	public void corner_only_touching_a_passable() {
		gameEngine.addTile(0, 0, TileType.PASSABLE);
		gameEngine.addTile(1, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(2, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 1, TileType.PLAYER);
		gameEngine.addTile(2, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 2, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 2, TileType.NOT_PASSABLE);
		gameEngine.addTile(2, 2, TileType.NOT_PASSABLE);

		assertFalse(gameEngine.isTileTouching(1, 1, TileType.PASSABLE));
	}


	@Test
	public void tile_border_touching_check() {
		gameEngine.addTile(0, 0, TileType.PLAYER);
		gameEngine.addTile(1, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 1, TileType.PASSABLE);
		gameEngine.addTile(1, 1, TileType.NOT_PASSABLE);

		assertTrue(gameEngine.isTileTouching(0, 0, TileType.PASSABLE));
	}

	@Test
	public void tile_border_touching_check_without() {
		gameEngine.addTile(0, 0, TileType.PLAYER);
		gameEngine.addTile(1, 0, TileType.NOT_PASSABLE);
		gameEngine.addTile(0, 1, TileType.NOT_PASSABLE);
		gameEngine.addTile(1, 1, TileType.NOT_PASSABLE);

		assertFalse(gameEngine.isTileTouching(0, 0, TileType.PASSABLE));
	}

	private void fillBoardWithNonPassableTiles() {
		for (int y = 0; y < TILE_Y_RANGE; y++) {
			for (int x = 0; x < TILE_X_RANGE; x++) {
				if (x == TILE_X_RANGE / 2 && y == TILE_Y_RANGE / 2) {
					gameEngine.addTile(x, y, TileType.PLAYER);
				} else {
					gameEngine.addTile(x, y, TileType.NOT_PASSABLE);
				}
			}
		}
	}

}
