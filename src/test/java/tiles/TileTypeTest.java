package tiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TileTypeTest {

	private static final char INVALID_CHAR = 'Z';
	private static final char PASSABLE_CHAR = ' ';
	private static final char COIN_CHAR = 'C';
	private static final char OBSTACLE_CHAR = 'D';
	private static final char NEXT_LEVEL_CHAR = 'N';
	private static final char PREVIOUS_LEVEL_CHAR = 'B';

	@Test
	public void value_of() {
		assertThat(TileType.valueOf(TileType.PASSABLE.name()), equalTo(TileType.PASSABLE));
	}

	@Test
	public void get_tile_type_by_char_valid_char() {
		TileType actual = TileType.getTileTypeByChar(PASSABLE_CHAR);
		assertEquals(TileType.PASSABLE, actual);
	}

	@Test
	public void get_coin_tile_type_by_char() {
		TileType actual = TileType.getTileTypeByChar(COIN_CHAR);
		assertEquals(TileType.COIN, actual);
	}

	@Test
	public void get_tile_type_by_char_invalid_char() {
		try {
			TileType.getTileTypeByChar(INVALID_CHAR);
		} catch (IllegalArgumentException exception) {
			assertEquals(exception.getMessage(), TileType.INVALID_CHARACTER_PROVIDED_MESSAGE + "Z");
		}
	}

	@Test
	public void get_obstacle_by_char_d() {
		TileType actual = TileType.getTileTypeByChar(OBSTACLE_CHAR);
		assertEquals(TileType.OBSTACLE, actual);
	}

	@Test
	public void get_next_level_by_char_n() {
		TileType actual = TileType.getTileTypeByChar(NEXT_LEVEL_CHAR);
		assertEquals(TileType.NEXT_LEVEL, actual);
	}

	@Test
	public void get_previous_level_by_char_b() {
		TileType actual = TileType.getTileTypeByChar(PREVIOUS_LEVEL_CHAR);
		assertEquals(TileType.PREVIOUS_LEVEL, actual);
	}

}
