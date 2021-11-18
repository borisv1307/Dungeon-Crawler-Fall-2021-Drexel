package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;

public class GameEnginePortalTest {

	private static final int ZERO = 0;
	private static final int ONE = 1;

	GameEngine gameEngine;

	@Before
	public void game_engine_create_different_level() {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		int level = 2;
		gameEngine = new GameEngine(levelCreator, level);
		Mockito.verify(levelCreator, Mockito.times(1)).createLevel(gameEngine, level);
	}

	@Test
	public void get_both_portal_coordinates() {
		gameEngine.addTile(ZERO, ONE, TileType.PORTAL);
		gameEngine.addTile(ONE, ZERO, TileType.PORTAL);
		Point actualA = gameEngine.getPortalACoordinates();
		Point actualB = gameEngine.getPortalBCoordinates();
		assertEquals(actualA, new Point(0, 1));
		assertEquals(actualB, new Point(1, 0));
	}

	@Test
	public void teleport_player() {
		gameEngine.addTile(ZERO, ONE, TileType.PORTAL);
		gameEngine.addTile(ONE, ZERO, TileType.PORTAL);
		gameEngine.addTile(ZERO, ZERO, TileType.PLAYER);
		gameEngine.addTile(ONE, ONE, TileType.STAIRS);
		gameEngine.keyDown();
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ZERO));
	}

}
