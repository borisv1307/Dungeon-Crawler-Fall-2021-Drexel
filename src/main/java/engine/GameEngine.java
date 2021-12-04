package engine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import parser.LevelCreator;
import thread.CountDownThread;
import tiles.TileType;
import ui.GameFrame;
import ui.WindowsDimension;
import values.GameStats;
import wrappers.SystemWrapper;

public class GameEngine {

	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	public GameStats gameStats;
	private CountDownThread timerThread;
	private boolean exit;
	private WindowsDimension dimensions = new WindowsDimension();
	private Point player;
	private boolean levelStarted = false;
	private boolean levelCanBePlayed = true;
	private SystemWrapper systemWrapper = new SystemWrapper();

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		gameStats = new GameStats();
		this.levelCreator = levelCreator;
		setUpCountDownTimer();
		loadNextLevel();
	}

	public int getLevelHorizontalDimension() {
		return dimensions.getLevelHorizontalDimension();
	}

	public void setLevelHorizontalDimension(int levelHorizontalDimension) {
		dimensions.setLevelHorizontalDimension(levelHorizontalDimension);
	}

	public int getLevelVerticalDimension() {
		return dimensions.getLevelVerticalDimension();
	}

	public void setLevelVerticalDimension(int levelVerticalDimension) {
		dimensions.setLevelVerticalDimension(levelVerticalDimension);
	}

	private void setUpCountDownTimer() {
		timerThread = new CountDownThread(this);
	}

	public void run(GameFrame gameFrame) {
		for (Component component : gameFrame.getComponents()) {
			component.repaint();
		}
	}

	public void addTile(int x, int y, TileType tileType) {
		if (tileType.equals(TileType.PLAYER)) {
			tiles.put(new Point(x, y), TileType.PASSABLE);
			setPlayer(x, y);
		} else {
			tiles.put(new Point(x, y), tileType);
		}
	}

	public TileType getTileFromCoordinates(int x, int y) {
		return tiles.get(new Point(x, y));
	}

	private void setPlayer(int x, int y) {
		player = new Point(x, y);
		checkTargetReached(x, y);
	}

	private void checkTargetReached(int x, int y) {
		TileType possibleTarget = getTileFromCoordinates(x, y);
		if (possibleTarget.equals(TileType.TARGET)) {
			loadNextLevel();
		}
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

	private void startGameIfPossible() {
		if (!levelStarted) {
			startCountDown();
			levelStarted = true;
		}
	}

	public boolean canMoveTo(int x, int y) {
		TileType attemptedLocation = getTileFromCoordinates(x, y);
		return levelCanBePlayed
				&& (attemptedLocation.equals(TileType.PASSABLE) || attemptedLocation.equals(TileType.TARGET));
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	private void loadNextLevel() {
		stopCountDown();
		this.levelCreator.createLevel(this, gameStats.increaseLevel());
		if (!exit) {
			newLevelSetUp();
		} else {
			gameEnds();
		}
	}

	private void newLevelSetUp() {
		gameStats.addScore();
		levelStarted = false;
		systemWrapper.println("Level " + gameStats.getLevel() + ". Press Right-Arrow to start...");
	}

	private void gameEnds() {
		systemWrapper.println("Game ends! You finished all levels. Total score: " + gameStats.getScore());
	}

	public void timerRunsOutCallBack() {
		levelCanBePlayed = false;
		systemWrapper.println("Timer ran out! Total score: " + gameStats.getScore());
	}

	private void stopCountDown() {
		timerThread.stopCountDown();
	}

	private void startCountDown() {
		timerThread.startCountDown();
	}

	public CountDownThread getTimerThread() {
		return timerThread;
	}
}
