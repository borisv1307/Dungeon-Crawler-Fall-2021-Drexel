package level;

import engine.GameEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tiles.TileType;
import wrappers.noise.OpenSimplexNoiseWrapper;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;

public class ProceduralLevelCreatorTest {

	private GameEngine gameEngine;
	private OpenSimplexNoiseWrapper noiseGenerator;
	private ProceduralLevelCreator levelCreator;

	private final int LEVEL = 1;
	private final int TILE_X = 3;
	private final int TILE_Y = 4;
	private final int TILE_X_RANGE = 10;
	private final int TILE_Y_RANGE = 10;


	@Before
	public void setUp() {
		gameEngine = Mockito.mock(GameEngine.class);
		noiseGenerator = Mockito.mock(OpenSimplexNoiseWrapper.class);
		levelCreator = new ProceduralLevelCreator(noiseGenerator);
	}

	@Test
	public void convert_below_zero_to_unpassable_tiles() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(-0.0000000000001);

		assertSame(levelCreator.determineTileType(TILE_X, TILE_Y, TILE_X_RANGE, TILE_Y_RANGE), TileType.NOT_PASSABLE);
	}

	@Test
	public void convert_zero_to_an_unpassable_tile() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(0.0);

		assertSame(levelCreator.determineTileType(TILE_X, TILE_Y, TILE_X_RANGE, TILE_Y_RANGE), TileType.NOT_PASSABLE);
	}

	@Test
	public void convert_positive_to_passable_tiles() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(0.0000000000001);

		assertSame(levelCreator.determineTileType(TILE_X, TILE_Y, TILE_X_RANGE, TILE_Y_RANGE), TileType.PASSABLE);
	}

	@Test
	public void lower_boundary_is_non_passable() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(-1.0);

		assertSame(levelCreator.determineTileType(TILE_X, TILE_Y, TILE_X_RANGE, TILE_Y_RANGE), TileType.NOT_PASSABLE);
	}

	@Test
	public void upper_boundary_is_passable() {
		Mockito.when(noiseGenerator.eval(anyDouble(), anyDouble())).thenReturn(1.0);

		assertSame(levelCreator.determineTileType(TILE_X, TILE_Y, TILE_X_RANGE, TILE_Y_RANGE), TileType.PASSABLE);
	}

	@Test
	public void player_spawns_at_the_center() {
		int xRange = 20;
		int yRange = 10;
		int centerX = xRange / 2;
		int centerY = yRange / 2;
		Mockito.when(noiseGenerator.eval(TILE_X, TILE_Y)).thenReturn(0.1);

		assertSame(levelCreator.determineTileType(centerX, centerY, xRange, yRange), TileType.PLAYER);
	}

}
