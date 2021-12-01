package engine;

import java.awt.Component;
import java.awt.Point;
import java.security.SecureRandom;
import java.util.ArrayList;
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
	private ArrayList<Point> snake;
	private Point player;
	private int level;
	private int[] direction;
	private SecureRandom rand;
	private Point food;
	private int newFoodXCoordinate;
	private int newFoodYCoordinate;
	private int tailCounter;

	public GameEngine(LevelCreator levelCreator) {
		exit = false;
		level = 1;
		this.levelCreator = levelCreator;
		this.levelCreator.createLevel(this, level);
		this.rand = new SecureRandom();
		snake = new ArrayList<Point>(5);
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
		} else if (tileType.equals(TileType.FOOD)) {
			setFood(x, y);
			tiles.put(new Point(x, y), TileType.FOOD);

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

	private void setFood(int x, int y) {
		food = new Point(x, y);
	}

	public int getFoodXCoordinate() {
		return (int) food.getX();
	}

	public int getFoodYCoordinate() {
		return (int) food.getY();
	}

	public Point getFood() {
		return this.food;
	}

	private void addTail(int x, int y) {
		snake.add(player);
	}

	public int getTailXCoordinate(int tailNumber) {
		return (int) snake.get(tailNumber).getX();
	}

	public int getTailYCoordinate(int tailNumber) {
		return (int) snake.get(tailNumber).getY();
	}

	public Point getTail(int tailNumber) {
		return snake.get(tailNumber);
	}

	public int getNewFoodXCoordinate() {
		return this.newFoodXCoordinate;
	}

	public int getNewFoodYCoordinate() {
		return this.newFoodYCoordinate;
	}

	public void keyLeft() {
		direction = new int[] { -1, 0 };
		attemptMove(direction);
	}

	public void keyRight() {
		direction = new int[] { 1, 0 };
		attemptMove(direction);
	}

	public void keyUp() {
		direction = new int[] { 0, -1 };
		attemptMove(direction);
	}

	public void keyDown() {
		direction = new int[] { 0, 1 };
		attemptMove(direction);
	}

	private void attemptMove(int[] direction) {
		int currentXCoordinate = getPlayerXCoordinate();
		int currentYCoordinate = getPlayerYCoordinate();
		int attemptedXCoordinate = currentXCoordinate + direction[0];
		int attemptedYCoordinate = currentYCoordinate + direction[1];
		Point currentLeader = new Point(currentXCoordinate, currentYCoordinate);
		Point previousLeader;

		TileType attemptedTile = getTileFromCoordinates(attemptedXCoordinate, attemptedYCoordinate);
		if (!isPassableTile(attemptedTile))
			exit = true;

		setPlayer(attemptedXCoordinate, attemptedYCoordinate);

		if (tailCounter > 0) {
			for (int i = 0; i < tailCounter; i++) {
				previousLeader = snake.get(i);
				snake.set(i, currentLeader);
				currentLeader = previousLeader;
			}

			for (Point tail : snake) {
				System.out.println(tail.getX() + ", " + tail.getY());
			}
			System.out.println("--------------");

		}

		if (attemptedTile.equals(TileType.FOOD)) {
			setNewFoodLocation();
			addTail(currentXCoordinate, currentYCoordinate);
			tailCounter++;
		}

	}

	private boolean isPassableTile(TileType attemptedTile) {
		return attemptedTile.equals(TileType.PASSABLE) || attemptedTile.equals(TileType.FOOD);
	}

	private void setNewFoodLocation() {
		newFoodXCoordinate = getFoodXCoordinate();
		newFoodYCoordinate = getFoodYCoordinate();

		while (getTileFromCoordinates(newFoodXCoordinate, newFoodYCoordinate) != TileType.PASSABLE) {
			newFoodXCoordinate = rand.nextInt(levelHorizontalDimension) + 1;
			newFoodYCoordinate = rand.nextInt(levelVerticalDimension) + 1;
		}

		tiles.put(food, TileType.PASSABLE);
		setFood(newFoodXCoordinate, newFoodYCoordinate);
		tiles.put(food, TileType.FOOD);
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isExit() {
		return exit;
	}

}
