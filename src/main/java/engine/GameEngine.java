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
	private Point portalOne;
	private Point portalTwo;
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
			if (portalOne == null) {
				setPortalOne(x, y);
				tiles.put(new Point(x, y), TileType.PORTAL);
			} else if (portalTwo == null) {
				setPortalTwo(x, y);
				tiles.put(new Point(x, y), TileType.PORTAL);
			}
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

	private void setPortalOne(int x, int y) {
		portalOne = new Point(x, y);
	}

	private void setPortalTwo(int x, int y) {
		portalTwo = new Point(x, y);
	}

	public int getPlayerXCoordinate() {
		return (int) player.getX();
	}

	public int getPlayerYCoordinate() {
		return (int) player.getY();
	}

	public int getPortalXCoordinate(Point portal) {
		if (portal.getX() == portalOne.getX() && portal.getY() == portalOne.getY()) {
			return (int) portalTwo.getX();
		} else {
			return (int) portalOne.getX();
		}
	}

	public int getPortalYCoordinate(Point portal) {
		if (portal.getX() == portalOne.getX() && portal.getY() == portalOne.getY()) {
			return (int) portalTwo.getY();
		} else {
			return (int) portalOne.getY();
		}
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
