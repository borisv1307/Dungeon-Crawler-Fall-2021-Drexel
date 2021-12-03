package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngine {
	private boolean exit;
	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private int level;
	private Point playerInitialPosition;
	private CollisionCounter collisionCounter;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
		collisionCounter = new CollisionCounter(this);
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
			playerInitialPosition = new Point(x, y);
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

	void setPlayer(int x, int y) {
		player = new Point(x, y);
	}

	public int getPlayerXCoordinate() {
		return (int) player.getX();
	}

	public int getPlayerYCoordinate() {
		return (int) player.getY();
	}

	public void keyLeft() {
		TileType nextLocation = getTileFromCoordinates(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
		collisionCounter.countDoorCollisions(nextLocation);
		collisionCounter.countObstacleCollisions(nextLocation);
		changeObstacleToPassable(nextLocation, getPlayerXCoordinate() - 1, getPlayerYCoordinate());
		movePlayerTo(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
	}

	public void keyRight() {
		TileType nextLocation = getTileFromCoordinates(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
		collisionCounter.countDoorCollisions(nextLocation);
		collisionCounter.countObstacleCollisions(nextLocation);
		changeObstacleToPassable(nextLocation, getPlayerXCoordinate() + 1, getPlayerYCoordinate());
		movePlayerTo(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
	}

	public void keyUp() {
		TileType nextLocation = getTileFromCoordinates(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
		if (nextLocation.equals(TileType.NOT_PASSABLE_BRIDGE)) {
			collisionCounter.incrementBridgeCollisionCounter();
			bringPlayerBackToInitialPosition();
		} else {
			collisionCounter.countDoorCollisions(nextLocation);
			collisionCounter.countObstacleCollisions(nextLocation);
			changeObstacleToPassable(nextLocation, getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
			movePlayerVertically(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
		}
	}

	public void keyDown() {
		TileType nextLocation = getTileFromCoordinates(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
		collisionCounter.countDoorCollisions(nextLocation);
		collisionCounter.countObstacleCollisions(nextLocation);
		changeObstacleToPassable(nextLocation, getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
		movePlayerVertically(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	public void bringPlayerBackToInitialPosition() {
		int playerInitialX = (int) playerInitialPosition.getX();
		int playerInitialY = (int) playerInitialPosition.getY();
		setPlayer(playerInitialX, playerInitialY);
	}

	public int getCurrentLevel() {
		return level;
	}

	public void setLevel(int newLevel) {
		level = newLevel;
	}

	public void goToNextLevel() {
		int playerX = getPlayerXCoordinate();
		int playerY = getPlayerYCoordinate();
		TileType potentialDoor = getTileFromCoordinates(playerX, playerY);
		if (potentialDoor.equals(TileType.DOOR)) {
			advanceIfValidLevel();
		}
	}

	private void movePlayerVertically(int x, int y) {
		TileType nextLocation = getTileFromCoordinates(x, y);
		if (nextLocation.equals(TileType.PASSABLE_BRIDGE)) {
			setPlayer(x, y);
		} else {
			movePlayerTo(x, y);
		}
	}

	private void movePlayerTo(int x, int y) {
		TileType nextLocation = getTileFromCoordinates(x, y);
		if (nextLocation.equals(TileType.PASSABLE) || nextLocation.equals(TileType.DOOR)) {
			setPlayer(x, y);
		}
	}

	private void advanceIfValidLevel() {
		if (level < 3) {
			collisionCounter.reinitializeCollisionCounters();
			setLevel(level + 1);
			levelCreator.createLevel(this, level);
		} else {
			setExit(true);
		}
	}

	public void changeObstacleToPassable(TileType tile, int x, int y) {
		int obstacleCollisionCounter = collisionCounter.getObstacleCollision();
		if (tile.equals(TileType.OBSTACLE) && obstacleCollisionCounter % 3 == 0) {
			tile = TileType.PASSABLE;
			addTile(x, y, tile);
		}
	}
}
