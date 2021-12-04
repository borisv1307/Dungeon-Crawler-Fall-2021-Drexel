package values;

import static org.junit.Assert.assertSame;

import java.awt.*;

import org.junit.Before;
import org.junit.Test;

import tiles.TileType;

public class TileColorMapTest {
	@Before
	public void setUp() {
		TileColorMap.resetPlayerColor();
	}

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
	public void reset_player_color_should_change_player_tile_green() {
		TileColorMap.changePlayerColor(Color.RED);
		TileColorMap.resetPlayerColor();
		assertSame(Color.GREEN, TileColorMap.get(TileType.PLAYER));
	}

}
