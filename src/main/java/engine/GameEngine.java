package engine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import parser.LevelCreator;
import tiles.TileType;
import timer.LevelTimer;
import ui.GameFrame;

public class GameEngine {

	final static int PLAYER_TIME_LIMIT = 5;
	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private final int level;
	public Thread countDownThread;
	private boolean exit;
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private boolean gameStarted = false;
	private boolean levelClear = true;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
		countDownThread = new Thread(new LevelTimer(PLAYER_TIME_LIMIT, this));
	}

	public void run(GameFrame gameFrame) {
		for (Component component : gameFrame.getComponents()) {
			component.repaint();
		}
	}

	public void addTile(int x, int y, TileType tileType) {
		if (tileType.equals(TileType.PLAYER)) {
			setPlayer(x, y);
			tiles.put(new Point(x, y), TileType.PLAYER);
		} else {
			tiles.put(new Point(x, y), tileType);
		}
	}

	public int getLevelHorizontalDimension() {
		return levelHorizontalDimension;
	}

	public void setLevelHorizontalDimension(int levelHorizontalDimension) {
		this.levelHorizontalDimension = levelHorizontalDimension;
	}

	public int getLevelVerticalDimension() {
		return levelVerticalDimension;
	}

	public void setLevelVerticalDimension(int levelVerticalDimension) {
		this.levelVerticalDimension = levelVerticalDimension;
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
		int newX = getPlayerXCoordinate() - 1;
		int newY = getPlayerYCoordinate();
		if (canMoveTo(newX, newY)) {
			setPlayer(newX, newY);
		}
	}

	public void keyRight() {
		startGameIfPossible();
		int newX = getPlayerXCoordinate() + 1;
		int newY = getPlayerYCoordinate();
		if (canMoveTo(newX, newY)) {
			setPlayer(newX, newY);
		}
	}

	private void startGameIfPossible() {
		if (!gameStarted) {
			startCountDown();
			gameStarted = true;
		}
	}

	public void keyUp() {
		int newX = getPlayerXCoordinate();
		int newY = getPlayerYCoordinate() - 1;
		if (canMoveTo(newX, newY)) {
			setPlayer(newX, newY);
		}
	}

	public void keyDown() {
		int newX = getPlayerXCoordinate();
		int newY = getPlayerYCoordinate() + 1;
		if (canMoveTo(newX, newY)) {
			setPlayer(newX, newY);
		}
	}

	public boolean canMoveTo(int x, int y) {
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		return levelClear && (attemptedLocation.equals(TileType.PASSABLE) || attemptedLocation.equals(TileType.TARGET));
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	private void startCountDown() {
		countDownThread.start();
	}

	public void timerRunsOut() {
		levelClear = false;
	}
}
