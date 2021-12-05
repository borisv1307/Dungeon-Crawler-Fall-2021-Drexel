package level;

import engine.GameEngine;
import engine.GameEngineTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tiles.TileType;
import wrappers.OpenSimplexNoiseWrapper;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

public class ProceduralLevelCreatorTest {

	private GameEngine gameEngine;
	private OpenSimplexNoiseWrapper noiseGenerator;
	private ProceduralLevelCreator levelCreator;

	private static final int LEVEL = 1;
	private static final int TILE_X = 3;
	private static final int TILE_Y = 6;
	private static final int TILE_X_RANGE = 10;
	private static final int TILE_Y_RANGE = 10;
	private static final int CENTER_X = TILE_X_RANGE / 2;
	private static final int CENTER_Y = TILE_Y_RANGE / 2;
	private static final Double TILE_PASSABLE_NOISE_VALUE = 0.1;
	private static final Double TILE_NON_PASSABLE_NOISE_VALUE = -0.1;

	@Before
	public void setUp() {
		gameEngine = Mockito.mock(GameEngine.class);
		noiseGenerator = Mockito.mock(OpenSimplexNoiseWrapper.class);
		levelCreator = new ProceduralLevelCreator(noiseGenerator, TILE_X_RANGE, TILE_Y_RANGE);
	}

	@Test
	public void convert_below_zero_to_unpassable_tiles() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(-0.0000000000001);

		assertSame(TileType.NOT_PASSABLE, levelCreator.determineTileType(TILE_X, TILE_Y));
	}

	@Test
	public void convert_zero_to_an_unpassable_tile() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(0.0);

		assertSame(TileType.NOT_PASSABLE, levelCreator.determineTileType(TILE_X, TILE_Y));
	}

	@Test
	public void convert_positive_to_passable_tiles() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(0.0000000000001);

		assertSame(TileType.PASSABLE, levelCreator.determineTileType(TILE_X, TILE_Y));
	}

	@Test
	public void lower_boundary_is_non_passable() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(-1.0);

		assertSame(TileType.NOT_PASSABLE, levelCreator.determineTileType(TILE_X, TILE_Y));
	}

	@Test
	public void upper_boundary_is_passable() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(1.0);

		assertSame(TileType.PASSABLE, levelCreator.determineTileType(TILE_X, TILE_Y));
	}

	@Test
	public void player_spawns_at_the_center() {
		Mockito.when(noiseGenerator.eval(TILE_X, TILE_Y)).thenReturn(TILE_PASSABLE_NOISE_VALUE);

		assertSame(TileType.PLAYER, levelCreator.determineTileType(CENTER_X, CENTER_Y));
	}

	@Test
	public void level_is_rendered_with_correct_tile_amount() {
		Mockito.when(noiseGenerator.eval(TILE_X, TILE_Y)).thenReturn(TILE_PASSABLE_NOISE_VALUE);
		Mockito.when(gameEngine.getPlayerLocation()).thenReturn(new Point(CENTER_X, CENTER_Y));
		Mockito.when(gameEngine.findClosestLocationOfATileType(anyInt(), anyInt(), anyObject())).thenReturn(new Point(CENTER_X, CENTER_Y));
		levelCreator.createLevel(gameEngine, LEVEL);

		int actual = TILE_X_RANGE * TILE_Y_RANGE;

		Mockito.verify(gameEngine, Mockito.times(actual)).addTile(anyInt(), anyInt(), anyObject());
	}

	@Test
	public void borders_are_filled_in_to_prevent_leaving_level() {
		assertTrue(areBordersFilled());
	}

	@Test
	public void player_can_not_be_trapped_at_start() {
		Mockito.when(noiseGenerator.eval(TILE_X, TILE_Y)).thenReturn(TILE_PASSABLE_NOISE_VALUE);
		gameEngine = new GameEngine(levelCreator);
		GameEngineTest.fillBoardWithNonPassableTiles(gameEngine);
		gameEngine.changeTileType(CENTER_X, CENTER_Y, TileType.PLAYER);
		gameEngine.changeTileType(CENTER_X + 2, CENTER_Y, TileType.PASSABLE);
		levelCreator.adjustLevel(gameEngine);

		TileType actual = gameEngine.getTileFromCoordinates(CENTER_X + 1, CENTER_Y);

		assertSame(TileType.PASSABLE, actual);
	}

	private boolean areBordersFilled() {
		Mockito.when(noiseGenerator.eval(anyInt(), anyInt())).thenReturn(TILE_PASSABLE_NOISE_VALUE);
		for (int y = 0; y < TILE_Y_RANGE; y++) {
			for (int x = 0; x < TILE_X_RANGE; x++) {
				if (x == 0 || y == 0 || x == TILE_X_RANGE - 1 || y == TILE_Y_RANGE - 1) {
					if (levelCreator.determineTileType(x, y) == TileType.PASSABLE) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
