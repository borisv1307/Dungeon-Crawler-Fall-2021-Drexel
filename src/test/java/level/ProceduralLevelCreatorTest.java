package level;

import engine.GameEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tiles.TileType;
import wrappers.noise.OpenSimplexNoiseWrapper;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;

public class ProceduralLevelCreatorTest {

	private GameEngine gameEngine;
	private OpenSimplexNoiseWrapper noiseGenerator;
	private ProceduralLevelCreator levelCreator;

	private static final int LEVEL = 1;
	private static final int TILE_X = 3;
	private static final int TILE_Y = 4;
	private static final int TILE_X_RANGE = 10;
	private static final int TILE_Y_RANGE = 10;
	private static final Double TILE_PASSABLE_NOISE_VALUE = 0.1;

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
		int centerX = TILE_X_RANGE / 2;
		int centerY = TILE_Y_RANGE / 2;
		Mockito.when(noiseGenerator.eval(TILE_X, TILE_Y)).thenReturn(TILE_PASSABLE_NOISE_VALUE);

		assertSame(TileType.PLAYER, levelCreator.determineTileType(centerX, centerY));
	}

	@Test
	public void level_is_rendered_with_correct_tile_amount() {
		Mockito.when(noiseGenerator.eval(TILE_X, TILE_Y)).thenReturn(TILE_PASSABLE_NOISE_VALUE);
		levelCreator.createLevel(gameEngine, LEVEL);

		int actual = TILE_X_RANGE * TILE_Y_RANGE;

		Mockito.verify(gameEngine, Mockito.times(actual)).addTile(anyInt(), anyInt(), anyObject());
	}

	@Test
	public void borders_are_filled_in_to_prevent_leaving_level() {
		assertTrue(areBordersFilled(TILE_X_RANGE, TILE_Y_RANGE));
	}

	private boolean areBordersFilled(int xRange, int yRange) {
		Mockito.when(noiseGenerator.eval(anyInt(), anyInt())).thenReturn(TILE_PASSABLE_NOISE_VALUE);
		for (int y = 0; y < yRange; y++) {
			for (int x = 0; x < xRange; x++) {
				if (x == 0 || y == 0 || x == xRange - 1 || y == yRange - 1) {
					if (levelCreator.determineTileType(x, y) == TileType.PASSABLE) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
