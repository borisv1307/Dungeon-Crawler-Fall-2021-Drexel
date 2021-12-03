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
		tileColors.put(TileType.COIN, Color.YELLOW);
		tileColors.put(TileType.OBSTACLE, Color.RED);
		tileColors.put(TileType.NEXT_LEVEL, Color.BLUE);
		tileColors.put(TileType.PREVIOUS_LEVEL, Color.MAGENTA);
	}

	public static Color get(TileType key) {
		return tileColors.get(key);
	}
}