package engine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import parser.LevelCreator;
import thread.CountDownThread;
import tiles.TileType;
import ui.GameFrame;
import values.TunableParameters;
import wrappers.SystemWrapper;

public class GameEngine {

	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private CountDownThread timerThread;
	private boolean exit;
	private int level = 0;
	private int score = 0;
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private boolean levelStarted = false;
	private boolean levelCanBePlayed = true;
	private SystemWrapper systemWrapper = new SystemWrapper();

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		this.levelCreator = levelCreator;
		setUpCountDownTimer();
		loadNextLevel();
	}

	private void setUpCountDownTimer() {
		timerThread = new CountDownThread(TunableParameters.PLAYER_LIMIT_TIME, this);
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
		this.levelCreator.createLevel(this, ++level);
		if (!exit) {
			addScore();
			levelStarted = false;
			systemWrapper.println("Level " + level + ". Press Right-Arrow to start...");
		} else {
			systemWrapper.println("Game ends! You finished all levels. Total score: " + score);
		}
	}

	private void addScore() {
		score += TunableParameters.SCORE_PER_LEVEL;
	}

	public void timerRunsOut() {
		levelCanBePlayed = false;
		score -= TunableParameters.SCORE_PER_LEVEL;
		systemWrapper.println("Timer ran out! Total score: " + score);
	}

	private void stopCountDown() {
		timerThread.stopCountDown();
	}

	private void startCountDown() {
		timerThread.startCountDown();
	}

	public int getLevel() {
		return level;
	}

	public CountDownThread getTimerThread() {
		return timerThread;
	}
}
