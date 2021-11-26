package tiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TileTypeTest {

	private static final char INVALID_CHAR = 'Z';
	private static final char VALID_CHAR = ' ';
	private static final char DOOR_CHAR = 'D';
	private static final char PASSABLE_BRIDGE = 'B';
	private static final char NOT_PASSABLE_BRIDGE = 'E';
	private static final char DEACTIVATED_DOOR = 'Q';

	@Test
	public void value_of() {
		assertThat(TileType.valueOf(TileType.PASSABLE.name()), equalTo(TileType.PASSABLE));
	}

	@Test
	public void get_tile_type_by_char_valid_char() {
		TileType actual = TileType.getTileTypeByChar(VALID_CHAR);
		assertEquals(TileType.PASSABLE, actual);
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
	public void get_tile_type_by_char_door_char() {
		TileType actual = TileType.getTileTypeByChar(DOOR_CHAR);
		assertEquals(TileType.DOOR, actual);
	}

	@Test
	public void get_tile_type_by_char_deactivated_door_char() {
		TileType actual = TileType.getTileTypeByChar(DEACTIVATED_DOOR);
		assertEquals(TileType.DEACTIVATED_DOOR, actual);
	}

	@Test
	public void get_tile_type_by_char_passable_bridge_char() {
		TileType actual = TileType.getTileTypeByChar(PASSABLE_BRIDGE);
		assertEquals(TileType.PASSABLE_BRIDGE, actual);
	}

	@Test
	public void get_tile_type_by_char_not_passable_bridge_char() {
		TileType actual = TileType.getTileTypeByChar(NOT_PASSABLE_BRIDGE);
		assertEquals(TileType.NOT_PASSABLE_BRIDGE, actual);
	}

	@Test
	public void deactivate_door() {
		TileType door = TileType.DOOR;
		TileType deactivatedDoor = door.deactivate();
		assertEquals(deactivatedDoor, TileType.DEACTIVATED_DOOR);
	}
}
