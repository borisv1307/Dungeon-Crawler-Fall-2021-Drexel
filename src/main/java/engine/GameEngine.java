package engine;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

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
	private int level;

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

	public TileType attemptedTiles(int x, int y) {
		return getTileFromCoordinates(x, y);
	}

	public boolean acceptPassable(TileType attemptedLocation) {
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			return true;
		}
		return false;
	}

	public boolean acceptFinish(TileType attemptedLocation) {
		if (attemptedLocation.equals(TileType.FINISH)) {
			return true;
		}
		return false;
	}

	public boolean acceptKey1(TileType attemptedLocation) {
		if (attemptedLocation.equals(TileType.KEY1)) {
			return true;
		}
		return false;
	}

	public boolean acceptKey2(TileType attemptedLocation) {
		if (attemptedLocation.equals(TileType.KEY2)) {
			return true;
		}
		return false;
	}

	public boolean acceptKey3(TileType attemptedLocation) {
		if (attemptedLocation.equals(TileType.KEY3)) {
			return true;
		}
		return false;
	}

	public void keyLeft() {
		int x = getPlayerXCoordinate() - 1;
		int y = getPlayerYCoordinate();
		if (acceptPassable(attemptedTiles(x, y))) {
			setPlayer(x, y);
		}
	}

	public void keyRight() {
		int x = getPlayerXCoordinate() + 1;
		int y = getPlayerYCoordinate();
		TileType attemptLoc = attemptedTiles(x, y);
		if (acceptPassable(attemptLoc) || acceptFinish(attemptLoc)) {
			setPlayer(x, y);
		}
		if (acceptFinish(attemptLoc)) {
			this.levelCreator.createLevel(this, 5);
			level = 5;
		}
	}

	public void levelFive(TileType attemptLoc, int x, int y) {
		if (acceptPassable(attemptLoc) || acceptKey1(attemptLoc)) {
			setPlayer(x, y);
		}
		if (acceptKey1(attemptLoc)) {
			this.levelCreator.createLevel(this, 6);
			level = 6;
			secretWeapon();
		}
	}

	public void levelSix(TileType attemptLoc, int x, int y) {
		if (acceptPassable(attemptLoc) || acceptFinish(attemptLoc)) {
			setPlayer(x, y);
		}
		if (acceptFinish(attemptLoc)) {
			youWon();
		}
	}

	public void otherLevels(TileType attemptLoc, int x, int y) {
		if (acceptPassable(attemptLoc) || acceptKey1(attemptLoc) || acceptKey2(attemptLoc) || acceptKey3(attemptLoc)) {
			setPlayer(x, y);
		}
		if (acceptKey1(attemptLoc)) {
			this.levelCreator.createLevel(this, 2);
		}
		if (acceptKey2(attemptLoc)) {
			this.levelCreator.createLevel(this, 3);
		}
		if (acceptKey3(attemptLoc)) {
			this.levelCreator.createLevel(this, 4);
		}
	}

	public void keyUp() {
		int x = getPlayerXCoordinate();
		int y = getPlayerYCoordinate() - 1;
		int yB = getPlayerYCoordinate() - 2;
		TileType attempLocNot6 = attemptedTiles(x, y);
		if (level == 6) {
			levelSix(attemptedTiles(x, yB), x, yB);
		} else if (level == 5) {
			levelFive(attempLocNot6, x, y);
		} else {
			otherLevels(attempLocNot6, x, y);
		}
	}

	public void keyDown() {
		int x = getPlayerXCoordinate();
		int y = getPlayerYCoordinate() + 1;
		if (acceptPassable(attemptedTiles(x, y))) {
			setPlayer(x, y);
		}
	}

	public void screenTimer() {
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		while (elapsedTime < 5000) {
			elapsedTime = (new Date()).getTime() - startTime;
		}
	}

	public void secretWeapon() {
		final JFrame parent = new JFrame();
		JButton button = new JButton();
		button.setFont(new Font("Arial", Font.BOLD, 20));
		button.setText(
				"Hint: Your new secret weapon, the ability to jump, is one of your four arrow keys! Close this window when ready to procede...");
		button.setPreferredSize(new Dimension(1500, 1000));
		parent.add(button);
		parent.pack();
		parent.setVisible(true);
		screenTimer();
	}

	public void youWon() {
		final JFrame parent = new JFrame();
		JButton button = new JButton();
		button.setFont(new Font("Arial", Font.BOLD, 50));
		button.setText("Success!");
		button.setPreferredSize(new Dimension(1500, 1000));
		parent.add(button);
		parent.pack();
		parent.setVisible(true);
		screenTimer();
		setExit(true);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}
}
