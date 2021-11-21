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
	private Point portal1, portal2;
	private Map<Point, Point> portalMap = new HashMap<>();
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
		} else if (tileType.equals(TileType.PORTAL)) {
			setPortal(x, y);
			tiles.put(new Point(x, y), TileType.PORTAL);
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

	private void setPortal(int x, int y) {
		portal1 = new Point(x, y);
		portalMap.put(portal1, portal2);
		portalMap.put(portal2, portal1);
		portal2 = portal1;

	}

	public int getPlayerXCoordinate() {
		return (int) player.getX();
	}

	public int getPlayerYCoordinate() {
		return (int) player.getY();
	}

	public int getPortalXCoordinate(Point portal) {
		return (int) portalMap.get(portal).getX();
	}

	public int getPortalYCoordinate(Point portal) {
		return (int) portalMap.get(portal).getY();
	}

	public void setPlayerMovementThroughObject(int x, int y) {
		int attemptedX = getPlayerXCoordinate() + x;
		int attemptedY = getPlayerYCoordinate() + y;
		Point point = new Point(attemptedX, attemptedY);
		TileType attemptedLocation = getTileFromCoordinates(attemptedX, attemptedY);

		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(attemptedX, attemptedY);
		} else if (attemptedLocation.equals(TileType.PORTAL)) {
			setPlayer(getPortalXCoordinate(point) + x, getPortalYCoordinate(point) + y);
		}
	}

	public void keyLeft() {
		setPlayerMovementThroughObject(-1, 0);
	}

	public void keyRight() {
		setPlayerMovementThroughObject(1, 0);
	}

	public void keyUp() {
		setPlayerMovementThroughObject(0, -1);
	}

	public void keyDown() {
		setPlayerMovementThroughObject(0, 1);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}
}
