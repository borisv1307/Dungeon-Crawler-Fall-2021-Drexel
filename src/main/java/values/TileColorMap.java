package values;

import java.awt.Color;
import java.util.EnumMap;

import tiles.TileType;

public final class TileColorMap {
	private TileColorMap() {
	}

	private static final EnumMap<TileType, Color> tileColors = new EnumMap<>(TileType.class);

	static {
		tileColors.put(TileType.PASSABLE_LIT, Color.WHITE);
		tileColors.put(TileType.PASSABLE_UNLIT, Color.GRAY);
		tileColors.put(TileType.NOT_PASSABLE, Color.BLACK);
		tileColors.put(TileType.PLAYER, Color.GREEN);
	}

	public static Color get(TileType key) {
		return tileColors.get(key);
	}
}