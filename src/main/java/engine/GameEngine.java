package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	private final int level;
	private Point player;
	private Point portalOne;
	private Point portalTwo;
	TileType[] portalArray = { TileType.PORTALZERO, TileType.PORTALONE, TileType.PORTALTWO, TileType.PORTALTHREE,
			TileType.PORTALFOUR, TileType.PORTALFIVE, TileType.PORTALSIX, TileType.PORTALSEVEN, TileType.PORTALEIGHT,
			TileType.PORTALNINE };
	List<TileType> portalList = new ArrayList<>(Arrays.asList(portalArray));
	private Map<TileType, List<Point>> portalMap = new HashMap<>();

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
			player = new Point(x, y);
			tiles.put(new Point(x, y), TileType.PASSABLE);
		} else if (portalList.contains(tileType)) {
			setPortal(x, y, tileType);
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

	private void setPortal(int x, int y, TileType tileType) {
		if (!portalMap.containsKey(tileType)) {
			setPortalOne(x, y, tileType);
			tiles.put(new Point(x, y), tileType);
		} else {
			setPortalTwo(x, y, tileType);
			tiles.put(new Point(x, y), tileType);
		}
	}

	private void setPortalOne(int x, int y, TileType tileType) {
		List<Point> pointList = new ArrayList<>();
		portalOne = new Point(x, y);
		pointList.add(portalOne);
		portalMap.put(tileType, pointList);
	}

	private void setPortalTwo(int x, int y, TileType tileType) {
		portalTwo = new Point(x, y);
		List<Point> pointList = portalMap.get(tileType);
		pointList.add(portalTwo);
		portalMap.put(tileType, pointList);
	}

	public int getPlayerXCoordinate() {
		return (int) player.getX();
	}

	public int getPlayerYCoordinate() {
		return (int) player.getY();
	}

	public boolean isPortalOne(Point portal, TileType tileType) {
		double portalOneX = portalMap.get(tileType).get(0).getX();
		double portalOneY = portalMap.get(tileType).get(0).getY();

		if (portal.getX() == portalOneX && portal.getY() == portalOneY) {
			return true;
		} else {
			return false;
		}
	}

	public int getPortalXCoordinate(Point portal, TileType tileType) {
		double portalOneX = portalMap.get(tileType).get(0).getX();
		double portalTwoX = portalMap.get(tileType).get(1).getX();

		if (isPortalOne(portal, tileType)) {
			return (int) portalTwoX;
		} else {
			return (int) portalOneX;
		}
	}

	public int getPortalYCoordinate(Point portal, TileType tileType) {
		double portalOneY = portalMap.get(tileType).get(0).getY();
		double portalTwoY = portalMap.get(tileType).get(1).getY();

		if (isPortalOne(portal, tileType)) {
			return (int) portalTwoY;
		} else {
			return (int) portalOneY;
		}
	}

	public void setPlayerMovementThroughObject(int x, int y) {
		int attemptedX = getPlayerXCoordinate() + x;
		int attemptedY = getPlayerYCoordinate() + y;
		Point point = new Point(attemptedX, attemptedY);
		TileType attemptedLocation = getTileFromCoordinates(attemptedX, attemptedY);

		if (attemptedLocation.equals(TileType.PASSABLE)) {
			setPlayer(attemptedX, attemptedY);
		} else if (portalList.contains(attemptedLocation)) {
			setPlayer(getPortalXCoordinate(point, attemptedLocation) + x,
					getPortalYCoordinate(point, attemptedLocation) + y);
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
