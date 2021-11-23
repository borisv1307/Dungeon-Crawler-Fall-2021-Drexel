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
	private int[] direction;

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
		direction = new int[] { -1, 0 };
		attemptMove(direction);
	}

	public void keyRight() {
		direction = new int[] { 1, 0 };
		attemptMove(direction);
	}

	public void keyUp() {
		direction = new int[] { 0, -1 };
		attemptMove(direction);
	}

	public void keyDown() {
		direction = new int[] { 0, 1 };
		attemptMove(direction);
	}

	private void attemptMove(int[] direction) {
		int xCoordinate = getPlayerXCoordinate() + direction[0];
		int yCoordinate = getPlayerYCoordinate() + direction[1];
		TileType attemptedTile = getTileFromCoordinates(xCoordinate, yCoordinate);
		if (!isPassableTile(attemptedTile))
			exit = true;
		setPlayer(xCoordinate, yCoordinate);

		if (attemptedTile.equals(TileType.FOOD)) {
			level++;
//			this.levelCreator.createLevel(this, level);
		}

	}

	private boolean isPassableTile(TileType attemptedTile) {
		return attemptedTile.equals(TileType.PASSABLE) || attemptedTile.equals(TileType.FOOD);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
