package parser;

import java.awt.Point;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import tiles.TileType;

public class LevelGenerator {
	public static final int MIN_DOOR_COUNT = 2;
	public static final int MAX_DOOR_COUNT = 2;
	public static final int COORDINATE_OFFSET = 1;
	private Map<Point, TileType> level;
	private SecureRandom random = new SecureRandom();

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
		return (x == 0 || x == width - COORDINATE_OFFSET || y == 0 || y == height - COORDINATE_OFFSET);
	}

	private void addDoors(int width, int height) {
		int offset = findAccessibleWall(width, random);
		level.replace(new Point(offset, 0), TileType.DOOR);
		offset = findAccessibleWall(width, random);
		level.replace(new Point(offset, height - COORDINATE_OFFSET), TileType.DOOR);
	}

	private int findAccessibleWall(int length, SecureRandom random) {
		// TODO: this currently only adds doors to the top and bottom of the level
		// adjust to allow a door on any randomly selected accessible wall
		return random.nextInt(length - COORDINATE_OFFSET - 1) + COORDINATE_OFFSET; // TODO: refactor this
	}
}
