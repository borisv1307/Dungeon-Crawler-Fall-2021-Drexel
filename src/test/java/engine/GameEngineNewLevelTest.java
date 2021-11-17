package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;

public class GameEngineNewLevelTest {
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
	public void add_and_get_stairs_coordinates() {
		TileType tileType = TileType.STAIRS;
		gameEngine.addTile(ZERO, ONE, tileType);
		int actualX = gameEngine.getStairsXCoordinate();
		int actualY = gameEngine.getStairsYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void load_new_level() {
		gameEngine.loadNewLevel(1);
		assertEquals(1, gameEngine.getLevel());
	}

	@Test
	public void load_next_level_when_player_enter_stairs() {
		gameEngine.addTile(ZERO, ZERO, TileType.PLAYER);
		gameEngine.addTile(ZERO, ONE, TileType.STAIRS);
		gameEngine.keyDown();
		assertEquals(3, gameEngine.getLevel());
	}

}
