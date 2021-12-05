package engine;

import level.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameEngine {

	private boolean exit;
	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private final int level;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
	}

	public void run(GameFrame gameFrame) {
		for (Component component : gameFrame.getComponents()) {
			component.repaint();
		}
	}

	public void addTile(int x, int y, TileType tileType) {
		if (tileType.equals(TileType.PLAYER)) {
			setPlayer(x, y);
			tiles.put(new Point(x, y), TileType.PASSABLE);
		} else {
			tiles.put(new Point(x, y), tileType);
		}
	}

	public void changeTileType(int x, int y, TileType newTileType) {
		if (newTileType.equals(TileType.PLAYER)) {
			setPlayer(x, y);
			tiles.replace(new Point(x, y), TileType.PASSABLE);
		} else {
			tiles.replace(new Point(x, y), newTileType);
		}
	}

	public Point findClosestLocationOfATileType(int searchCenterX, int searchCenterY, TileType searchTileType) {
		Set<Point> tileLocations = tiles.keySet();
		Point searchCenter = new Point(searchCenterX, searchCenterY);
		Point closestPocket = new Point(searchCenterX, searchCenterY);
		for (Point targetTileLocation : tileLocations) {
			TileType targetTileType = getTileFromCoordinates(targetTileLocation.x, targetTileLocation.y);
			double distanceBetweenTargetAndCenter = searchCenter.distance(targetTileLocation);
			double distanceBetweenClosestPocketAndCenter = searchCenter.distance(closestPocket);
			if (targetTileType.equals(searchTileType) && isTargetTileIsCloser(distanceBetweenTargetAndCenter, distanceBetweenClosestPocketAndCenter)) {
				if (!targetTileLocation.equals(searchCenter)) {
					closestPocket = targetTileLocation;
				}
			}
		}
		return closestPocket;
	}

	public boolean isTileTouching(int tileX, int tileY, TileType sourceTileType) {
		if (areTileTypesEqual(tileX + 1, tileY, sourceTileType)) {
			return true;
		} else if (areTileTypesEqual(tileX - 1, tileY, sourceTileType)) {
			return true;
		} else if (areTileTypesEqual(tileX, tileY + 1, sourceTileType)) {
			return true;
		} else if (areTileTypesEqual(tileX, tileY - 1, sourceTileType)) {
			return true;
		}
		return false;
	}

	private boolean areTileTypesEqual(int targetX, int targetY, TileType sourceTileType) {
		TileType neighborTileType = getTileFromCoordinates(targetX, targetY);
		return neighborTileType != null && neighborTileType.equals(sourceTileType);
	}

	public void setLevelHorizontalDimension(int levelHorizontalDimension) {
		this.levelHorizontalDimension = levelHorizontalDimension;
	}

	public void setLevelVerticalDimension(int levelVerticalDimension) {
		this.levelVerticalDimension = levelVerticalDimension;
	}

	public int getLevelHorizontalDimension() {
		return levelHorizontalDimension;
	}

	public int getLevelVerticalDimension() {
		return levelVerticalDimension;
	}

	public TileType getTileFromCoordinates(int x, int y) {
		return tiles.get(new Point(x, y));
	}

	private void setPlayer(int x, int y) {
		player = new Point(x, y);
	}

	public Point getPlayerLocation() {
		return player;
	}

	public int getPlayerXCoordinate() {
		return (int) player.getX();
	}

	public int getPlayerYCoordinate() {
		return (int) player.getY();
	}

	public void keyLeft() {
		attemptRelativeMovement(-1, 0);
	}

	public void keyRight() {
		attemptRelativeMovement(1, 0);
	}

	public void keyUp() {
		attemptRelativeMovement(0, -1);
	}

	public void keyDown() {
		attemptRelativeMovement(0, 1);
	}

	private void attemptRelativeMovement(int xOffset, int yOffset) {
		Point attemptedDestination = getDestinationLocationFromOffset(xOffset, yOffset);
		if (movementIsPossible(attemptedDestination)) {
			movePlayerToPoint(attemptedDestination);
		}
	}

	private Point getDestinationLocationFromOffset(int xOffset, int yOffset) {
		return new Point(getPlayerXCoordinate() + xOffset, getPlayerYCoordinate() + yOffset);
	}

	private boolean movementIsPossible(Point attemptedDestination) {
		TileType attemptedLocation = getTileFromCoordinates((int) attemptedDestination.getX(), (int) attemptedDestination.getY());
		return attemptedLocation.equals(TileType.PASSABLE);
	}

	private void movePlayerToPoint(Point attemptedDestination) {
		setPlayer((int) attemptedDestination.getX(), (int) attemptedDestination.getY());
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	private boolean isTargetTileIsCloser(double distanceBetweenTargetAndCenter, double distanceBetweenClosestPocketAndCenter) {
		return distanceBetweenTargetAndCenter < distanceBetweenClosestPocketAndCenter || distanceBetweenClosestPocketAndCenter == 0;
	}
}
