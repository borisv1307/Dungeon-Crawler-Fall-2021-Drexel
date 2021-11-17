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
	private int playerHealth = 5;
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
		makeAttemptedMoveIfPossibleByCoordinates(getPlayerXCoordinate() - 1, getPlayerYCoordinate());
	}

	public void keyRight() {
		makeAttemptedMoveIfPossibleByCoordinates(getPlayerXCoordinate() + 1, getPlayerYCoordinate());
	}

	public void keyUp() {
		makeAttemptedMoveIfPossibleByCoordinates(getPlayerXCoordinate(), getPlayerYCoordinate() - 1);
	}

	public void keyDown() {
		makeAttemptedMoveIfPossibleByCoordinates(getPlayerXCoordinate(), getPlayerYCoordinate() + 1);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	private void makeAttemptedMoveIfPossibleByCoordinates(int xCoordinate, int yCoordinate) {
		TileType attemptedTileToMoveTo = getTileFromCoordinates(xCoordinate, yCoordinate);
		if (attemptedTileToMoveTo.equals(TileType.PASSABLE)) {
			setPlayer(xCoordinate, yCoordinate);
		} else if (attemptedTileToMoveTo.equals(TileType.TRAP)) {
			setPlayer(xCoordinate, yCoordinate);
			setPlayerHealth(getPlayerHealth() - 1);
		}
	}

	public int getPlayerHealth() {
		return playerHealth;
	}

	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}

}
