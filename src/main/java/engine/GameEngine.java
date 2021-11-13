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
		int x = getXCoordinate("l");
		int y = getYCoordinate("l");
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(x, y);
		}
	}

	public void keyRight() {
		int x = getXCoordinate("r");
		int y = getYCoordinate("r");
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(x, y);
		}
	}

	public void keyUp() {
		int x = getXCoordinate("u");
		int y = getYCoordinate("u");
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(x, y);
		}
	}

	public void keyDown() {
		int x = getXCoordinate("d");
		int y = getYCoordinate("d");
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(x, y);
		}
	}

	public int getXCoordinate(String direction) {
		if (direction.equals("l")) {
			return getPlayerYCoordinate() - 1;
		}
		if (direction.equals("r")) {
			return getPlayerYCoordinate() + 1;
		} else {
			return getPlayerYCoordinate();
		}
	}

	public int getYCoordinate(String direction) {
		if (direction.equals("d")) {
			return getPlayerYCoordinate() + 1;
		}
		if (direction.equals("u")) {
			return getPlayerYCoordinate() - 1;
		} else {
			return getPlayerYCoordinate();
		}
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}
}
