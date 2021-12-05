package parser;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import tiles.TileType;

public class LevelMap {
	private final Map<Integer, Map<Point, TileType>> levels = new HashMap<>();

	public void addLevel(int levelNumber, Map<Point, TileType> levelTiles) {
		Map<Point, TileType> level = new HashMap<Point, TileType>();
		level.putAll(levelTiles);
		levels.put(levelNumber, level);
	}

	public Map<Point, TileType> getLevel(int levelNumber) {
		return levels.get(levelNumber);
	}

	public boolean levelExists(int levelNumber) {
		return levels.containsKey(levelNumber);
	}

}
