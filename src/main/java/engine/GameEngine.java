package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import movement.PlayerMovement;
import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngine {

	private boolean exit;
	private final LevelCreator levelCreator;
	public final Map<Point, TileType> tiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private int level;
	private int coinCount = 0;
	private PlayerMovement playerMovement;

	public GameEngine(LevelCreator levelCreator, PlayerMovement playerMovement) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
		this.playerMovement = playerMovement;
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

	public void setPlayer(int x, int y) {
		player = new Point(x, y);
	}

	public int getPlayerXCoordinate() {
		return (int) player.getX();
	}

	public int getPlayerYCoordinate() {
		return (int) player.getY();
	}

	public void keyLeft() {
		playerMovement.movingToTile(this, -1, 0);
	}

	public void keyRight() {
		playerMovement.movingToTile(this, 1, 0);

	}

	public void keyUp() {
		playerMovement.movingToTile(this, 0, -1);
	}

	public void keyDown() {
		playerMovement.movingToTile(this, 0, 1);

	}

//	private void movingToTile(int xOffset, int yOffset) {
//		int xCoordinate = getPlayerXCoordinate() + xOffset;
//		int yCoordinate = getPlayerYCoordinate() + yOffset;
//		TileType attemptedLocation = getTileFromCoordinates(xCoordinate, yCoordinate);
//		if (attemptedLocation.equals(TileType.PASSABLE)) {
//			setPlayer(xCoordinate, yCoordinate);
//		}
//		if (attemptedLocation.equals(TileType.COIN)) {
//			setPlayer(xCoordinate, yCoordinate);
//			increaseCoinCount();
//			tiles.put(new Point(xCoordinate, yCoordinate), TileType.PASSABLE);
//		}
//		if (attemptedLocation.equals(TileType.OBSTACLE)) {
//			setExit(true);
//		}
//		if (attemptedLocation.equals(TileType.NEXT_LEVEL)) {
//			increaseLevel();
//			loadLevel();
//		}
//		if (attemptedLocation.equals(TileType.PREVIOUS_LEVEL)) {
//			decreaseLevel();
//			loadLevel();
//		}
//	}

	public void increaseCoinCount() {
		this.coinCount++;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	public int getCoinCount() {
		return this.coinCount;
	}

	public int getLevel() {
		return this.level;
	}

	public void increaseLevel() {
		this.level++;
	}

	public void decreaseLevel() {
		this.level--;
	}

	public void loadLevel() {
		this.levelCreator.createLevel(this, level);
	}

}
