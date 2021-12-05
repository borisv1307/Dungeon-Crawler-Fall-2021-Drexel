package level;

import engine.GameEngine;
import tiles.TileType;

import java.util.logging.Logger;

public abstract class LevelCreator {
	protected static final Logger LOGGER = Logger.getLogger(LevelCreator.class.getName());

	protected int xRange;
	protected int yRange;

	protected LevelCreator(int xRange, int yRange) {
		this.xRange = xRange;
		this.yRange = yRange;
	}

	public void createLevel(GameEngine gameEngine, int level) {
		for (int y = 0; y < yRange; y++) {
			for (int x = 0; x < xRange; x++) {
				gameEngine.addTile(x, y, determineTileType(x, y));
			}
		}
		gameEngine.setLevelHorizontalDimension(xRange);
		gameEngine.setLevelVerticalDimension(yRange);
	}

	protected abstract TileType determineTileType(int x, int y);

	protected boolean tileIsCenter(int x, int y) {
		return x == xRange / 2 && y == yRange / 2;
	}

	protected boolean tileIsBorder(int x, int y) {
		return x == 0 || y == 0 || x == xRange - 1 || y == yRange - 1;
	}

}
