package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;
import values.TunableParameters;

public class GameEngine {

	private boolean exit;
	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private final Map<Point, TileType> visibleTiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	public int playerLightRadius = TunableParameters.playerLightRadius;
	private final int level;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = TunableParameters.startingLevel;
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

	public TileType getVisibleTileFromCoordinates(int x, int y) {
		return visibleTiles.get(new Point(x, y));
	}

	public void addVisibleTiles() {
		int playerX = getPlayerXCoordinate();
		int playerY = getPlayerYCoordinate();
		for (Entry<Point, TileType> entry : tiles.entrySet()) {
			Point point = entry.getKey();
			TileType tile = entry.getValue();
			if (withinLightRadius(point.x, point.y, playerX, playerY, playerLightRadius)) {
				visibleTiles.put(point, tile);
			} else {
				visibleTiles.put(point, TileType.UNLIT);
			}
		}
	}

	private void updateVisibleTiles() {
		int playerX = getPlayerXCoordinate();
		int playerY = getPlayerYCoordinate();
		for (Entry<Point, TileType> entry : tiles.entrySet()) {
			Point point = entry.getKey();
			TileType tile = entry.getValue();
			if (withinLightRadius(point.x, point.y, playerX, playerY, playerLightRadius)) {
				visibleTiles.replace(point, tile);
			} else {
				visibleTiles.replace(point, TileType.UNLIT);
			}
		}
	}

	private boolean withinLightRadius(int tileX, int tileY, int lightX, int lightY, int lightRadius) {
		return (Math.abs(tileX - lightX) <= lightRadius && Math.abs(tileY - lightY) <= lightRadius);
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
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			player.setLocation(x, y);
			updateVisibleTiles();
		}
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}
}
