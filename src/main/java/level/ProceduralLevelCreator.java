package level;

import engine.GameEngine;
import tiles.TileType;
import wrappers.noise.OpenSimplexNoiseWrapper;

public class ProceduralLevelCreator extends LevelCreator {

	private static final double PASSABLE_THRESHOLD = 0;
	private OpenSimplexNoiseWrapper noiseGenerator;

	private static final int DEFAULT_TILE_AMOUNT_X = 20;
	private static final int DEFAULT_TILE_AMOUNT_Y = 10;

	public ProceduralLevelCreator(OpenSimplexNoiseWrapper noiseGenerator) {
		this.noiseGenerator = noiseGenerator;
	}

	@Override
	public void createLevel(GameEngine gameEngine, int level) {
		createLevel(gameEngine, level, DEFAULT_TILE_AMOUNT_X, DEFAULT_TILE_AMOUNT_Y);
	}

	public void createLevel(GameEngine gameEngine, int level, int xRange, int yRange) {
		for (int y = 0; y < yRange; y++) {
			for (int x = 0; x < xRange; x++) {
				gameEngine.addTile(x, y, determineTileType(x, y, xRange, yRange));
			}
		}
		gameEngine.setLevelHorizontalDimension(xRange);
		gameEngine.setLevelVerticalDimension(yRange);
	}

	public TileType determineTileType(int x, int y, int xRange, int yRange) {
		double randomValue = noiseGenerator.eval(x, y);
		if (tileIsCenter(x, y, xRange, yRange)) {
			return TileType.PLAYER;
		} else if (randomValue <= PASSABLE_THRESHOLD || tileIsBorder(x, y, xRange, yRange)) {
			return TileType.NOT_PASSABLE;
		} else {
			return TileType.PASSABLE;
		}
	}

	private boolean tileIsCenter(int x, int y, int xRange, int yRange) {
		return x == xRange / 2 && y == yRange / 2;
	}

	private boolean tileIsBorder(int x, int y, int xRange, int yRange) {
		return x == 0 || y == 0 || x == xRange - 1 || y == yRange - 1;
	}

}
