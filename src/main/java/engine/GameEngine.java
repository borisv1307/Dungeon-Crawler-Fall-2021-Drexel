package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngine {
	private final int BOMB_DEFAULT_RANGE = 2;

	private boolean exit;
	private final LevelCreator levelCreator;
	private final Map<Point, TileType> tiles = new HashMap<>();
	private int levelHorizontalDimension;
	private int levelVerticalDimension;
	private Point player;
	private final int level;
	private Set<TileType> playerPowerUps;
	private Point bomb;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
		this.playerPowerUps = new HashSet<>();
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

	public Set<TileType> getPlayerPowerUps() {
		return this.playerPowerUps;
	}

	public void addPowerUp(TileType powerUp) {
		this.playerPowerUps.add(powerUp);
	}

	public void keyLeft() {
		movePlayer(-1, 0);
	}

	public void keyRight() {
		movePlayer(1, 0);
	}

	public void keyUp() {
		movePlayer(0, -1);
	}

	public void keyDown() {
		movePlayer(0, 1);
	}

	public void keyB() {
		if (getPlayerPowerUps().contains(TileType.BOMB_POWER_UP)) {
			if (bomb == null) {
				addTile(getPlayerXCoordinate(), getPlayerYCoordinate(), TileType.BOMB);
				bomb = new Point(getPlayerXCoordinate(), getPlayerYCoordinate());
			} else {
				detonateBomb();
			}
		}
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

	private void movePlayer(int deltaX, int deltaY) {
		int newX = getPlayerXCoordinate() + deltaX;
		int newY = getPlayerYCoordinate() + deltaY;

		TileType attemptedLocation = getTileFromCoordinates(newX, newY);
		if (canMoveInto(attemptedLocation)) {
			setPlayer(newX, newY);
		}
		if (isPowerUp(attemptedLocation)) {
			playerPowerUps.add(attemptedLocation);
			addTile(newX, newY, TileType.PASSABLE);
		}
	}

	private boolean canMoveInto(TileType attemptedLocation) {
		return attemptedLocation.equals(TileType.PASSABLE) || isPowerUp(attemptedLocation);
	}

	private boolean isPowerUp(TileType attemptedLocation) {
		return attemptedLocation.equals(TileType.BOMB_POWER_UP) || attemptedLocation.equals(TileType.FIRE_POWER_UP);
	}

	private void detonateBomb() {
		breakWallsInFourDirections();
		removeBomb();
	}

	private void breakWallsInFourDirections() {
		int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

		for (int i = 0; i < 4; i++) {
			int xDirection = directions[i][0];
			int yDirection = directions[i][1];

			breakWallsInDirection(xDirection, yDirection);
		}
	}

	private void breakWallsInDirection(int xDirection, int yDirection) {
		for (int delta = 0; delta <= BOMB_DEFAULT_RANGE; delta++) {
			int xDelta = xDirection * delta;
			int yDelta = yDirection * delta;

			int x = bomb.x + xDelta;
			int y = bomb.y + yDelta;

			TileType toBeDestroyed = getTileFromCoordinates(x, y);
			if (toBeDestroyed.equals(TileType.NOT_PASSABLE)) {
				break;
			}
			if (toBeDestroyed.equals(TileType.BREAKABLE)) {
				addTile(x, y, TileType.PASSABLE);
				break;
			}
		}
	}

	private void removeBomb() {
		addTile(bomb.x, bomb.y, TileType.PASSABLE);
		bomb = null;
	}
}
