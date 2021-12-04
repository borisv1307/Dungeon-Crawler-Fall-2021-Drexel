package tiles;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import org.junit.Test;

import values.TileColorMap;

public class TileColorTest {
	@Test
	public void target_tile_should_be_grey() {
		assertEquals(Color.GRAY, TileColorMap.get(TileType.TARGET));
	}
}
