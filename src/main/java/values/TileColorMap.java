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

		tileColors.put(TileType.PORTAL_ZERO, Color.RED);
		tileColors.put(TileType.PORTAL_ONE, Color.YELLOW);
		tileColors.put(TileType.PORTAL_TWO, Color.PINK);
		tileColors.put(TileType.PORTAL_THREE, Color.ORANGE);
		tileColors.put(TileType.PORTAL_FOUR, Color.CYAN);
		tileColors.put(TileType.PORTAL_FIVE, Color.BLUE);
		tileColors.put(TileType.PORTAL_SIX, Color.MAGENTA);
		tileColors.put(TileType.PORTAL_SEVEN, Color.GRAY);
		tileColors.put(TileType.PORTAL_EIGHT, Color.GREEN);
		tileColors.put(TileType.PORTAL_NINE, Color.BLACK);
	}

	public static Color get(TileType key) {
		return tileColors.get(key);
	}
}