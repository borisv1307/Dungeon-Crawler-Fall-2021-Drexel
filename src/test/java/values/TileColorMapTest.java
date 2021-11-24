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
	public void door() {
		assertSame(Color.RED, TileColorMap.get(TileType.DOOR));
	}

	@Test
	public void deactivated_door() {
		assertSame(Color.RED, TileColorMap.get(TileType.DEACTIVATED_DOOR));
	}

	@Test
	public void passable_bridge() {
		assertSame(Color.BLUE, TileColorMap.get(TileType.PASSABLE_BRIDGE));
	}

	@Test
	public void not_passable_bridge() {
		assertSame(Color.BLUE, TileColorMap.get(TileType.NOT_PASSABLE_BRIDGE));
	}

}
