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
			tiles.put(new Point(x, y), TileType.PASSABLE_LIT);
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
		movePlayerIfPassable(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
	}

	public void keyRight() {
		movePlayerIfPassable(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
	}

	public void keyUp() {
		movePlayerIfPassable(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
	}

	public void keyDown() {
		movePlayerIfPassable(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
	}

	public void movePlayerIfPassable(int x, int y) {
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		if (attemptedLocation.equals(TileType.PASSABLE_LIT)) {
			player.setLocation(x, y);
			lightSurroundingTiles(x, y);
		}
	}

	public void lightSurroundingTiles(int xCenter, int yCenter) {
		int xCurrent;
		int yCurrent;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				xCurrent = xCenter + i;
				yCurrent = yCenter + j;
				if (getTileFromCoordinates(xCurrent, yCurrent) == TileType.PASSABLE_UNLIT) {
					addTile(xCurrent, yCurrent, TileType.PASSABLE_LIT);
				}
			}
		}
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}
}
