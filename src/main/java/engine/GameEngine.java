package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngine {
	private int coinCount = 0;
	private boolean exit;
	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private int level;

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
		movingToTile(getPlayerXCoordinate(), getPlayerYCoordinate(), -1, 0);
	}

	public void keyRight() {
		movingToTile(getPlayerXCoordinate(), getPlayerYCoordinate(), 1, 0);
	}

	public void keyUp() {
		movingToTile(getPlayerXCoordinate(), getPlayerYCoordinate(), 0, -1);
	}

	public void keyDown() {
		movingToTile(getPlayerXCoordinate(), getPlayerYCoordinate(), 0, 1);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	public void increaseLevel() {
		this.level++;
	}

	public void decreaseLevel() {
		this.level--;
	}

	public int getLevel() {
		return this.level;
	}

	private void movingToTile(int playerXCoordinate, int playerYCoordinate, int xOffset, int yOffset) {
		int xCoordinate = playerXCoordinate + xOffset;
		int yCoordinate = playerYCoordinate + yOffset;
		TileType attemptedLocation = getTileFromCoordinates(xCoordinate, yCoordinate);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(xCoordinate, yCoordinate);
		}
		if (attemptedLocation.equals(TileType.PREVIOUS_LEVEL)) {
			decreaseLevel();
			reloadLevel();
		}
		if (attemptedLocation.equals(TileType.NEXT_LEVEL)) {
			increaseLevel();
			reloadLevel();
		}
		if (attemptedLocation.equals(TileType.OBSTACLE)) {
			setExit(true);
		}
		if (attemptedLocation.equals(TileType.COIN)) {
			increaseCoinCount();
			setPlayer(xCoordinate, yCoordinate);
			tiles.put(new Point(xCoordinate, yCoordinate), TileType.PASSABLE);
		}
	}

	private void reloadLevel() {
		this.levelCreator.createLevel(this, level);
	}

	public int getCoinCount() {
		return coinCount;
	}

	public void increaseCoinCount() {
		coinCount++;
	}

}
