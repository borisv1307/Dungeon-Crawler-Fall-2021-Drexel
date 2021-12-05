package values;

import static org.junit.Assert.assertSame;

import java.awt.Color;

import org.junit.Test;

import tiles.TileType;

public class TileColorMapTest {

	@Test
	public void passable() {
		assertSame(Color.WHITE, TileColorMap.get(TileType.PASSABLE));
	}

	@Test
	public void not_passable() {
		assertSame(Color.BLACK, TileColorMap.get(TileType.NOT_PASSABLE));
	}

	@Test
	public void player() {
		assertSame(Color.GREEN, TileColorMap.get(TileType.PLAYER));
	}

	@Test
	public void bomb_power_up() {
		assertSame(Color.LIGHT_GRAY, TileColorMap.get(TileType.BOMB_POWER_UP));
	}

	@Test
	public void fire_power_up() {
		assertSame(Color.YELLOW, TileColorMap.get(TileType.FIRE_POWER_UP));
	}

	@Test
	public void breakable() {
		assertSame(Color.ORANGE, TileColorMap.get(TileType.BREAKABLE));
	}

	@Test
	public void bomb() {
		assertSame(Color.DARK_GRAY, TileColorMap.get(TileType.BOMB));
	}

}
