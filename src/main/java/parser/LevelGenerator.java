package parser;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import tiles.TileType;

public class LevelGenerator {
	public final int MIN_DOOR_COUNT = 2;
	public final int MAX_DOOR_COUNT = 2;
	public final int COORDINATE_OFFSET = 1;
	private Map<Point, TileType> level;
	Random random = new Random();

	public Map<Point, TileType> generateLevel(int width, int height) {
		level = new HashMap<>();
		// TODO: see if there is a way to refactor this
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				addTile(x, y, width, height);
			}
		}
		addDoors(width, height);
		return level;
	}

	private void addTile(int x, int y, int width, int height) {
		if (isEdgeOfLevel(x, y, width, height)) {
			level.put(new Point(x, y), TileType.NOT_PASSABLE);
		} else {
			level.put(new Point(x, y), TileType.PASSABLE);
		}
	}

	private boolean isEdgeOfLevel(int x, int y, int width, int height) {
		if (x == 0 || x == width - COORDINATE_OFFSET || y == 0 || y == height - COORDINATE_OFFSET) {
			return true;
		}
		return false;
	}

	private void addDoors(int width, int height) {
		int offset = findAccessibleWall(width, random);
		level.replace(new Point(offset, 0), TileType.DOOR);
		offset = findAccessibleWall(width, random);
		level.replace(new Point(offset, height - COORDINATE_OFFSET), TileType.DOOR);
	}

	private int findAccessibleWall(int length, Random random) {
		// TODO: this currently only adds doors to the top and bottom of the level
		// adjust to allow a door on any randomly selected accessible wall
		random = new Random();
		return random.nextInt(length - COORDINATE_OFFSET - 1) + COORDINATE_OFFSET; // TODO: refactor this
	}
}
