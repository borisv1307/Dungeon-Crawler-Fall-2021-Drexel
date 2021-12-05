package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.Collection;
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
	private GameMovement gameMovement;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
		collisionCounter = new CollisionCounter(this);
		gameMovement = new GameMovement(this, collisionCounter);
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
		gameMovement.move(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
	}

	public void keyRight() {
		gameMovement.move(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
	}

	public void keyUp() {
		gameMovement.moveUp(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
	}

	public void keyDown() {
		gameMovement.move(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	public void bringPlayerBackToInitialPosition() {
		player = playerInitialPosition;
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

	public void changeObstacleToPassable(TileType tile, int x, int y) {
		int obstacleCollisionCounter = collisionCounter.getObstacleCollision();
		if (tile.equals(TileType.OBSTACLE) && obstacleCollisionCounter % 3 == 0) {
			tile = TileType.PASSABLE;
			addTile(x, y, tile);
		}
	}

	public boolean checkIfNoObstacles() {
		Collection<TileType> potentialObstacles = tiles.values();
		int obstacle = 0;
		for (TileType tile : potentialObstacles) {
			if (tile.equals(TileType.OBSTACLE)) {
				obstacle++;
			}
		}
		return obstacle == 0;
	}

	public void finishGame(TileType tile) {
		boolean noObstacle = checkIfNoObstacles();
		if (isEndOfGame(tile, noObstacle)) {
			System.out.println("YAY! YOU ESCAPED THE DUNGEON!");
			setExit(true);
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

	private boolean isEndOfGame(TileType tile, boolean noObstacle) {
		return level == 3 && tile.equals(TileType.DEACTIVATED_DOOR) && noObstacle;
	}
}
