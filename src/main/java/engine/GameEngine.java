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
	private Point portalA;
	private Point portalB;
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
			tiles.put(new Point(x, y), tileType);
		} else if (tileType.equals(TileType.PORTAL)) {
			setPortals(x, y);
			tiles.put(new Point(x, y), tileType);
		} else
			tiles.put(new Point(x, y), tileType);

	}

	private void setPortals(int x, int y) {
		if (portalA == null) {
			portalA = new Point(x, y);
		} else {
			portalB = new Point(x, y);
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
		checkEncounters(newX, newY);
	}

	public void keyRight() {
		int newX = getPlayerXCoordinate() + 1;
		int newY = getPlayerYCoordinate();
		setPlayerIfPassable(newX, newY);
		checkEncounters(newX, newY);
	}

	public void keyUp() {
		int newX = getPlayerXCoordinate();
		int newY = getPlayerYCoordinate() - 1;
		setPlayerIfPassable(newX, newY);
		checkEncounters(newX, newY);
	}

	public void keyDown() {
		int newX = getPlayerXCoordinate();
		int newY = getPlayerYCoordinate() + 1;
		setPlayerIfPassable(newX, newY);
		checkEncounters(newX, newY);
	}

	private void checkEncounters(int x, int y) {
		checkPlayerEncounterHostile(x, y);
		checkPlayerEnteredPortal(x, y);
		checkPlayerEnteredStairs();
	}

	private void checkPlayerEnteredPortal(int x, int y) {
		if (getTileFromCoordinates(x, y).equals(TileType.PORTAL)) {
			if ((x == portalA.x) && (y == portalA.y)) {
				System.out.println("teleporting to B");
				setPlayer(portalB.x, portalB.y);
			} else {
				System.out.println("teleporting to A");
				setPlayer(portalA.x, portalA.y);
			}
		}
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
		TileType tile = getTileFromCoordinates(x, y);
		if (!tile.equals(TileType.NOT_PASSABLE)) {
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

	public Point getPortalACoordinates() {
		return portalA;
	}

	public Point getPortalBCoordinates() {
		return portalB;
	}

}
