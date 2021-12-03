package values;

import java.awt.Color;
import java.util.EnumMap;

import tiles.TileType;

public final class TileColorMap {
	private TileColorMap() {
	}

	private static final EnumMap<TileType, Color> tileColors = new EnumMap<>(TileType.class);

	static {
		tileColors.put(TileType.PASSABLE, Color.WHITE);
		tileColors.put(TileType.NOT_PASSABLE, Color.BLACK);
		tileColors.put(TileType.PLAYER, Color.GREEN);
		tileColors.put(TileType.WALL, Color.BLUE);
		tileColors.put(TileType.KEY1, Color.YELLOW);
		tileColors.put(TileType.KEY2, Color.YELLOW);
		tileColors.put(TileType.KEY3, Color.YELLOW);
		tileColors.put(TileType.FINISH, Color.RED);
		tileColors.put(TileType.OBSTACLE, Color.ORANGE);
	}

	public static Color get(TileType key) {
		return tileColors.get(key);
	}
}