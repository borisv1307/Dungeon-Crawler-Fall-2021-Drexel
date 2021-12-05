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
		tileColors.put(TileType.BOMB_POWER_UP, Color.LIGHT_GRAY);
		tileColors.put(TileType.FIRE_POWER_UP, Color.YELLOW);
		tileColors.put(TileType.BREAKABLE, Color.ORANGE);
		tileColors.put(TileType.BOMB, Color.DARK_GRAY);
	}

	public static Color get(TileType key) {
		return tileColors.get(key);
	}
}