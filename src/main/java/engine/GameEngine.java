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
		TileType attemptedLocation = getTileFromCoordinates(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
		}
		movingToTile(attemptedLocation);
	}

	public void keyRight() {
		TileType attemptedLocation = getTileFromCoordinates(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
		}
		movingToTile(attemptedLocation);
	}

	public void keyUp() {
		TileType attemptedLocation = getTileFromCoordinates(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
		}
		movingToTile(attemptedLocation);
	}

	public void keyDown() {
		TileType attemptedLocation = getTileFromCoordinates(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
		}
		movingToTile(attemptedLocation);
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

	private void movingToTile(TileType attemptedLocation) {
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
	}

	private void reloadLevel() {
		this.levelCreator.createLevel(this, level);
	}

}
