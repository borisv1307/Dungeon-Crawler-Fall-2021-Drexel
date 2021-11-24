package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import parser.LevelGenerator;
import tiles.TileType;

public class LevelGeneratorTest {
	private LevelGenerator levelGenerator;
	private Map<Point, TileType> level;
	private final int levelWidth = 10;
	private final int levelHeight = 5;

	@Before
	public void setUp() {
		levelGenerator = new LevelGenerator();
		level = levelGenerator.generateLevel(levelWidth, levelHeight);
	}

	@Test
	public void room_width() {
		assertEquals(levelWidth, getWidth(level));
	}

	@Test
	public void room_height() {
		assertEquals(levelHeight, getHeight(level));
	}

	@Test
	public void walled_in() {
		assertTrue(isWalledIn(level));
	}

	@Test
	public void has_minimum_door_count() {
		assertTrue(getDoorCount(level) >= LevelGenerator.MIN_DOOR_COUNT);
	}

	@Test
	public void no_more_than_maximum_door_count() {
		assertTrue(getDoorCount(level) <= LevelGenerator.MAX_DOOR_COUNT);
	}

	@Test
	public void all_doors_are_accessible() {
		// TODO: Don't love this test. simplify it
		assertTrue(doorsAreAccessible(level));
	}

	private int getDoorCount(Map<Point, TileType> level) {
		int doorCount = 0;
		for (Entry<Point, TileType> entry : level.entrySet()) {
			TileType tile = entry.getValue();
			if (tile.equals(TileType.DOOR)) {
				doorCount++;
			}
		}
		return doorCount;
	}

	private int getWidth(Map<Point, TileType> level) {
		int width = 0;
		for (Entry<Point, TileType> entry : level.entrySet()) {
			Point point = entry.getKey();
			width = Math.max(width, point.x);
		}
		return width + LevelGenerator.COORDINATE_OFFSET;
	}

	private int getHeight(Map<Point, TileType> level) {
		int height = 0;
		for (Entry<Point, TileType> entry : level.entrySet()) {
			Point point = entry.getKey();
			height = Math.max(height, point.y);
		}
		return height + LevelGenerator.COORDINATE_OFFSET;
	}

	private boolean isWalledIn(Map<Point, TileType> level) {
		for (Entry<Point, TileType> entry : level.entrySet()) {
			Point point = entry.getKey();
			TileType tile = entry.getValue();
			if ((point.x == 0 || point.x == levelWidth - LevelGenerator.COORDINATE_OFFSET || point.y == 0
					|| point.y == levelHeight - LevelGenerator.COORDINATE_OFFSET) && tile.equals(TileType.PASSABLE)) {
				return false;
			}
		}
		return true;
	}

	private boolean doorsAreAccessible(Map<Point, TileType> level) {
		for (Entry<Point, TileType> entry : level.entrySet()) {
			Point point = entry.getKey();
			TileType tile = entry.getValue();
			if (isCorner(point) && tile.equals(TileType.DOOR)) {
				return false;
			}
		}
		return true;
	}

	private boolean isCorner(Point point) {
		if ((point.x == 0 && point.y == 0)
				|| (point.x == 0 && point.y == levelHeight - LevelGenerator.COORDINATE_OFFSET)
				|| (point.x == levelWidth - LevelGenerator.COORDINATE_OFFSET && point.y == 0)
				|| (point.x == levelWidth - LevelGenerator.COORDINATE_OFFSET
						&& point.y == levelHeight - LevelGenerator.COORDINATE_OFFSET)) {
			return true;
		}
		return false;
	}
}
