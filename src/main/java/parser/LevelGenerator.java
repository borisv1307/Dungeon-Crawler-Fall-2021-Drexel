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

	public Map<Point, TileType> generateLevel(int width, int height) {
		Map<Point, TileType> level = new HashMap<>();
		// TODO: see if there is a way to refactor this
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				addTile(level, x, y, width, height);
			}
		}
		addDoors(level, width, height);
		return level;
	}

	private void addTile(Map<Point, TileType> level, int x, int y, int width, int height) {
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

	private void addDoors(Map<Point, TileType> level, int width, int height) {
		// TODO: this currently only adds doors to the top and bottom of the level
		// adjust to allow a door on any randomly selected accessible wall
		int offset = findAccessibleWall(width);
		level.replace(new Point(offset, 0), TileType.DOOR);
		offset = findAccessibleWall(width);
		level.replace(new Point(offset, height - COORDINATE_OFFSET), TileType.DOOR);
	}

	private int findAccessibleWall(int width) {
		Random random = new Random();
		return random.nextInt(width - COORDINATE_OFFSET - 1) + COORDINATE_OFFSET;
	}
}
