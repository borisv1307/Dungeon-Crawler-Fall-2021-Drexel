package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import level.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

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
}
