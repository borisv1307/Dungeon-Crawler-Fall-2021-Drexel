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
	private LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private Point stairs;
	private int level;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
	}

	public GameEngine(LevelCreator levelCreator, int level) {
		exit = false;
		this.level = level;
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
		} else if (tileType.equals(TileType.STAIRS)) {
			setStairs(x, y);
			tiles.put(stairs, TileType.STAIRS);
		} else
			tiles.put(new Point(x, y), tileType);

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

	public void setStairs(int x, int y) {
		stairs = new Point(x, y);
	}

	public int getStairsXCoordinate() {
		return (int) stairs.getX();
	}

	public int getStairsYCoordinate() {
		return (int) stairs.getY();
	}

	public void keyLeft() {
		int newX = getPlayerXCoordinate() - 1;
		int newY = getPlayerYCoordinate();
		setPlayerIfPassable(newX, newY);
		checkPlayerEnteredStairs();
		checkPlayerEncounterHostile(newX, newY);
	}

	public void keyRight() {
		int newX = getPlayerXCoordinate() + 1;
		int newY = getPlayerYCoordinate();
		setPlayerIfPassable(newX, newY);
		checkPlayerEnteredStairs();
		checkPlayerEncounterHostile(newX, newY);
	}

	public void keyUp() {
		int newX = getPlayerXCoordinate();
		int newY = getPlayerYCoordinate() - 1;
		setPlayerIfPassable(newX, newY);
		checkPlayerEnteredStairs();
		checkPlayerEncounterHostile(newX, newY);
	}

	public void keyDown() {
		int newX = getPlayerXCoordinate();
		int newY = getPlayerYCoordinate() + 1;
		setPlayerIfPassable(newX, newY);
		checkPlayerEnteredStairs();
		checkPlayerEncounterHostile(newX, newY);
	}

	private void checkPlayerEncounterHostile(int x, int y) {
		if (getTileFromCoordinates(x, y).equals(TileType.HOSTILE)) {
			this.setExit(true);
		}
	}

	private void checkPlayerEnteredStairs() {
		if ((this.getPlayerXCoordinate() == this.getStairsXCoordinate())
				&& (this.getPlayerYCoordinate() == this.getStairsYCoordinate())) {
			this.loadNewLevel(level + 1);
		}
	}

	public void setPlayerIfPassable(int x, int y) {
		if ((getTileFromCoordinates(x, y).equals(TileType.PASSABLE))
				|| (getTileFromCoordinates(x, y).equals(TileType.STAIRS))) {
			setPlayer(x, y);
		}
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	public void loadNewLevel(int newLevel) {
		this.level = newLevel;
		this.levelCreator.createLevel(this, level);
	}

	public int getLevel() {
		return level;
	}

}
