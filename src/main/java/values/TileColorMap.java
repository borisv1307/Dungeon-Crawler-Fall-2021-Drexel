package values;

import java.awt.Color;
import java.util.EnumMap;

import tiles.TileType;

public final class TileColorMap {
	public static final Color BROWN = new Color(70, 40, 0);

	private TileColorMap() {
	}

	private static final EnumMap<TileType, Color> tileColors = new EnumMap<>(TileType.class);

	static {
		tileColors.put(TileType.PASSABLE, Color.WHITE);
		tileColors.put(TileType.UNLIT, Color.BLACK);
		tileColors.put(TileType.NOT_PASSABLE, Color.GRAY);
		tileColors.put(TileType.PLAYER, Color.GREEN);
		tileColors.put(TileType.DOOR, TileColorMap.BROWN);
	}

	public static Color get(TileType key) {
		return tileColors.get(key);
	}
}