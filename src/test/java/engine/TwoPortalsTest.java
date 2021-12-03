package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;

public class TwoPortalsTest {
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

		gameEngine.addTile(ONE, ONE, TileType.PORTAL_ONE);
		gameEngine.addTile(ONE, TWO, TileType.PORTAL_TWO);
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		gameEngine.addTile(ZERO, TWO, TileType.PASSABLE);
		gameEngine.addTile(TWO, ONE, TileType.PASSABLE);
		gameEngine.addTile(TWO, TWO, TileType.PASSABLE);

		gameEngine.addTile(FOUR, ONE, TileType.PORTAL_ONE);
		gameEngine.addTile(FOUR, TWO, TileType.PORTAL_TWO);
		gameEngine.addTile(THREE, ONE, TileType.PASSABLE);
		gameEngine.addTile(THREE, TWO, TileType.PASSABLE);
		gameEngine.addTile(FIVE, ONE, TileType.PASSABLE);
		gameEngine.addTile(FIVE, TWO, TileType.PASSABLE);
	}

	@Test
	public void key_right_portal_one_left() {
		gameEngine.addTile(ZERO, ONE, TileType.PLAYER);
		gameEngine.keyRight();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(FIVE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_left_portal_one_left() {
		gameEngine.addTile(TWO, ONE, TileType.PLAYER);
		gameEngine.keyLeft();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(THREE));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_right_portal_two_left() {
		gameEngine.addTile(ZERO, TWO, TileType.PLAYER);
		gameEngine.keyRight();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(FIVE));
		assertThat(actualY, equalTo(TWO));
	}

	@Test
	public void key_left_portal_two_left() {
		gameEngine.addTile(TWO, TWO, TileType.PLAYER);
		gameEngine.keyLeft();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(THREE));
		assertThat(actualY, equalTo(TWO));
	}

	@Test
	public void key_right_portal_one_right() {
		gameEngine.addTile(THREE, ONE, TileType.PLAYER);
		gameEngine.keyRight();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(TWO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_left_portal_one_right() {
		gameEngine.addTile(FIVE, ONE, TileType.PLAYER);
		gameEngine.keyLeft();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void key_right_portal_two_right() {
		gameEngine.addTile(THREE, TWO, TileType.PLAYER);
		gameEngine.keyRight();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(TWO));
		assertThat(actualY, equalTo(TWO));
	}

	@Test
	public void key_left_portal_two_right() {
		gameEngine.addTile(FIVE, TWO, TileType.PLAYER);
		gameEngine.keyLeft();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(TWO));
	}

}
