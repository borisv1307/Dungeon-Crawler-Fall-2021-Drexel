package level;

import engine.GameEngine;
import tiles.TileType;
import wrappers.OpenSimplexNoiseWrapper;

import java.awt.*;

public class ProceduralLevelCreator extends LevelCreator {

	private OpenSimplexNoiseWrapper noiseGenerator;

	private static final double PASSABLE_THRESHOLD = 0;
	private static final double FEATURE_SIZE = 4;

	public ProceduralLevelCreator(OpenSimplexNoiseWrapper noiseGenerator, int xRange, int yRange) {
		super(xRange, yRange);
		this.noiseGenerator = noiseGenerator;
	}

	public ProceduralLevelCreator(int xRange, int yRange) {
		this(new OpenSimplexNoiseWrapper(), xRange, yRange);
	}

	@Override
	public void createLevel(GameEngine gameEngine, int level) {
		super.createLevel(gameEngine, level);
		adjustLevel(gameEngine);
	}

	void adjustLevel(GameEngine gameEngine) {
		if (isPlayerStranded(gameEngine)) {
			Point closestPocket = gameEngine.findClosestLocationOfATileType(gameEngine.getPlayerXCoordinate(), gameEngine.getPlayerYCoordinate(), TileType.PASSABLE);
			createEmptyConnection(gameEngine, gameEngine.getPlayerLocation(), closestPocket);
		}
	}

	private boolean isPlayerStranded(GameEngine gameEngine) {
		Point playerLocation = gameEngine.getPlayerLocation();
		return !gameEngine.isTileTouching(playerLocation.x, playerLocation.y, TileType.PASSABLE);
	}

	private void createEmptyConnection(GameEngine gameEngine, Point startingPoint, Point endPoint) {
		Point currentPoint = new Point(startingPoint.x, startingPoint.y);
		while (!currentPoint.equals(endPoint)) {
			int newX = currentPoint.x;
			if (currentPoint.x < endPoint.x) {
				 newX = currentPoint.x + 1;
			} else if (currentPoint.x > endPoint.x) {
				newX = currentPoint.x - 1;
			}
			gameEngine.changeTileType(newX, startingPoint.y, TileType.PASSABLE);
			int newY = currentPoint.y;
			if (currentPoint.y < endPoint.y) {
				newY = currentPoint.y + 1;
			} else if (currentPoint.y > endPoint.y) {
				newY = currentPoint.y - 1;
			}
			currentPoint = new Point(newX, newY);
			gameEngine.changeTileType(newX, newY, TileType.PASSABLE);
		}

	}

	@Override
	protected TileType determineTileType(int x, int y) {
		double randomValue = noiseGenerator.eval(x / FEATURE_SIZE, y / FEATURE_SIZE);
		if (tileIsCenter(x, y)) {
			return TileType.PLAYER;
		} else if (randomValue <= PASSABLE_THRESHOLD || tileIsBorder(x, y)) {
			return TileType.NOT_PASSABLE;
		} else {
			return TileType.PASSABLE;
		}
	}
}
