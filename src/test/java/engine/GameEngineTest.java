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
	public void add_portal_pair_and_get_portal_two_coordinates() {
		TileType tileType = TileType.PORTALONE;
		gameEngine.addTile(ZERO, ONE, tileType);
		gameEngine.addTile(TWO, ONE, tileType);
		Point portalOnePoint = new Point(ZERO, ONE);

		int actualX = gameEngine.getPortalXCoordinate(portalOnePoint, tileType);
		int actualY = gameEngine.getPortalYCoordinate(portalOnePoint, tileType);
		assertThat(actualX, equalTo(TWO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void add_portal_pair_and_get_portal_one_coordinates() {
		TileType tileType = TileType.PORTALONE;
		gameEngine.addTile(ZERO, ONE, tileType);
		gameEngine.addTile(TWO, ONE, tileType);
		Point portalOnePoint = new Point(TWO, ONE);

		int actualX = gameEngine.getPortalXCoordinate(portalOnePoint, tileType);
		int actualY = gameEngine.getPortalYCoordinate(portalOnePoint, tileType);
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void add_two_portal_pair_and_get_correct_portal_coordinates() {
		TileType tileType = TileType.PORTALONE;
		gameEngine.addTile(ZERO, ONE, tileType);
		gameEngine.addTile(TWO, ONE, tileType);

		TileType tileTypeTwo = TileType.PORTALTWO;
		gameEngine.addTile(ZERO, TWO, tileTypeTwo);
		gameEngine.addTile(TWO, TWO, tileTypeTwo);
		Point portalOnePointTwo = new Point(ZERO, TWO);

		int actualX = gameEngine.getPortalXCoordinate(portalOnePointTwo, tileTypeTwo);
		int actualY = gameEngine.getPortalYCoordinate(portalOnePointTwo, tileTypeTwo);
		assertThat(actualX, equalTo(TWO));
		assertThat(actualY, equalTo(TWO));
	}

	@Test
	public void set_and_get_exit() {
		boolean exit = true;
		gameEngine.setExit(exit);
		boolean actual = gameEngine.isExit();
		assertThat(actual, equalTo(exit));
	}
}
