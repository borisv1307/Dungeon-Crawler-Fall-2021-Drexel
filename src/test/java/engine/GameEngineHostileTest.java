package engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;

public class GameEngineHostileTest {
	private static final int ZERO = 0;
	private static final int ONE = 1;

	GameEngine gameEngine;

	@Before
	public void game_engine_create_different_level() {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		int level = 3;
		gameEngine = new GameEngine(levelCreator, level);
		Mockito.verify(levelCreator, Mockito.times(1)).createLevel(gameEngine, level);
	}

	@Test
	public void exit_when_player_encounter_hostile() {
		gameEngine.addTile(ZERO, ZERO, TileType.PLAYER);
		gameEngine.addTile(ZERO, ONE, TileType.HOSTILE);
		gameEngine.addTile(ONE, ONE, TileType.STAIRS);
		gameEngine.keyDown();
		assertEquals(true, gameEngine.isExit());
	}

}
