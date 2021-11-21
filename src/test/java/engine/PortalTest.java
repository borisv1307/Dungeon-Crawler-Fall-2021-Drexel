package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;

public class PortalTest {
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;

	GameEngine gameEngine;

	@Before
	public void setUp() throws Exception {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		gameEngine = new GameEngine(levelCreator);

		gameEngine.addTile(ONE, ONE, TileType.PORTAL);
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		gameEngine.addTile(TWO, ONE, TileType.PASSABLE);
		gameEngine.addTile(ONE, ZERO, TileType.PASSABLE);
		gameEngine.addTile(ONE, TWO, TileType.PASSABLE);

		gameEngine.addTile(FOUR, ONE, TileType.PORTAL);
		gameEngine.addTile(THREE, ONE, TileType.PASSABLE);
		gameEngine.addTile(FIVE, ONE, TileType.PASSABLE);
		gameEngine.addTile(ONE, ZERO, TileType.PASSABLE);
		gameEngine.addTile(ONE, TWO, TileType.PASSABLE);
	}

	@Test
	public void key_right_portal_left() {
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(FIVE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_left_portal_left() {
		gameEngine.addTile(TWO, ONE, TileType.PLAYER);
		gameEngine.keyLeft();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(THREE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_up_portal_left() {
		gameEngine.addTile(ONE, TWO, TileType.PLAYER);
		gameEngine.keyUp();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(FOUR));
		assertThat(actualY, equalTo(ZERO));
	}

	@Test
	public void key_down_portal_left() {
		gameEngine.addTile(ONE, ZERO, TileType.PLAYER);
		gameEngine.keyDown();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(FOUR));
		assertThat(actualY, equalTo(TWO));
	}

	@Test
	public void key_right_portal_right() {
		gameEngine.addTile(THREE, ONE, TileType.PLAYER);
		gameEngine.keyRight();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(TWO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_left_portal_right() {
		gameEngine.addTile(FIVE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_up_portal_right() {
		gameEngine.addTile(FOUR, TWO, TileType.PLAYER);
		gameEngine.keyUp();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(ZERO));
	}

	@Test
	public void key_down_portal_right() {
		gameEngine.addTile(FOUR, ZERO, TileType.PLAYER);
		gameEngine.keyDown();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ONE));
		assertThat(actualY, equalTo(TWO));
	}

}
