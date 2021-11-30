package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;
import values.TileColorMap;

public class GameEngineTest {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;

	GameEngine gameEngine;

	@Before
	public void setUp() throws Exception {
		LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
		gameEngine = new GameEngine(levelCreator);
		int level = 1;
		Mockito.verify(levelCreator, Mockito.times(level)).createLevel(gameEngine, level);
	}

	@Test
	public void run() {
		GameFrame gameFrame = Mockito.mock(GameFrame.class);
		Component component = Mockito.mock(Component.class);
		Mockito.when(gameFrame.getComponents()).thenReturn(new Component[] { component });
		gameEngine.run(gameFrame);
		Mockito.verify(component, Mockito.times(1)).repaint();
	}

	@Test
	public void add_and_get_tile() {
		TileType tileType = TileType.PASSABLE;
		gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
		TileType actual = gameEngine.getTileFromCoordinates(ZERO, ONE);
		assertThat(actual, equalTo(tileType));
	}

	@Test
	public void set_and_get_horizontal_dimension() {
		gameEngine.setLevelHorizontalDimension(ONE);
		int actual = gameEngine.getLevelHorizontalDimension();
		assertThat(actual, equalTo(ONE));
	}

	@Test
	public void set_and_get_vertical_dimension() {
		gameEngine.setLevelVerticalDimension(ONE);
		int actual = gameEngine.getLevelVerticalDimension();
		assertThat(actual, equalTo(ONE));
	}

	@Test
	public void add_and_get_player_coordinates() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();
		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(ONE));
	}

	@Test
	public void set_and_get_exit() {
		boolean exit = true;
		gameEngine.setExit(exit);
		boolean actual = gameEngine.isExit();
		assertThat(actual, equalTo(exit));
	}

	@Test
	public void default_color_of_player_tile_is_green() {
		Color actualColor = TileColorMap.get(TileType.PLAYER);
		assertThat(actualColor, equalTo(Color.GREEN));
	}

	@Test
	public void change_color_of_player_tile_to_red() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);

		TileColorMap.changePlayerColor(Color.RED);

		tileType = gameEngine.getTileFromCoordinates(gameEngine.getPlayerXCoordinate(),
				gameEngine.getPlayerYCoordinate());
		Color actualColor = TileColorMap.get(tileType);

		assertThat(actualColor, equalTo(Color.RED));
	}

	@Test
	public void reset_player_default_color() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);

		TileColorMap.changePlayerColor(Color.RED);
		TileColorMap.resetPlayerColor();

		tileType = gameEngine.getTileFromCoordinates(gameEngine.getPlayerXCoordinate(),
				gameEngine.getPlayerYCoordinate());
		Color actualColor = TileColorMap.get(tileType);

		assertThat(actualColor, equalTo(Color.GREEN));
	}

	@Test
	public void target_tile_should_be_passable() {
		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);

		tileType = TileType.TARGET;
		gameEngine.addTile(ZERO, TWO, tileType);

		gameEngine.keyDown();

		int actualX = gameEngine.getPlayerXCoordinate();
		int actualY = gameEngine.getPlayerYCoordinate();

		assertThat(actualX, equalTo(ZERO));
		assertThat(actualY, equalTo(TWO));

	}

	@Test
	public void key_right_should_start_the_timer() {
		GameFrame gameFrame = Mockito.mock(GameFrame.class);
		Component component = Mockito.mock(Component.class);
		Mockito.when(gameFrame.getComponents()).thenReturn(new Component[] { component });
		gameEngine.run(gameFrame);

		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		tileType = TileType.PASSABLE;
		gameEngine.addTile(ONE, ONE, tileType);

		gameEngine.keyRight();
		assertThat(gameEngine.timerThread.isAlive(), equalTo(true));
	}

	@Test
	public void timer_should_not_start_before_first_key_right_is_called() {
		GameFrame gameFrame = Mockito.mock(GameFrame.class);
		Component component = Mockito.mock(Component.class);
		Mockito.when(gameFrame.getComponents()).thenReturn(new Component[] { component });
		gameEngine.run(gameFrame);

		assertThat(gameEngine.timerThread.isAlive(), equalTo(false));
	}

	@Test
	public void player_cannot_move_when_timer_runs_out() throws InterruptedException {
		GameFrame gameFrame = Mockito.mock(GameFrame.class);
		Component component = Mockito.mock(Component.class);
		Mockito.when(gameFrame.getComponents()).thenReturn(new Component[] { component });
		gameEngine.run(gameFrame);

		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		tileType = TileType.PASSABLE;
		gameEngine.addTile(ONE, ONE, tileType);
		tileType = TileType.PASSABLE;
		gameEngine.addTile(ZERO, ONE, tileType);

		gameEngine.keyRight();
		assertThat(gameEngine.canMoveTo(ZERO, ONE), equalTo(true));
		Thread.sleep(5000);
		assertThat(gameEngine.canMoveTo(ZERO, ONE), equalTo(false));

	}

	@Test
	public void level_should_increase_when_player_reaches_target_before_timer_runs_out() throws InterruptedException {
		GameFrame gameFrame = Mockito.mock(GameFrame.class);
		Component component = Mockito.mock(Component.class);
		Mockito.when(gameFrame.getComponents()).thenReturn(new Component[] { component });
		gameEngine.run(gameFrame);

		TileType tileType = TileType.PLAYER;
		gameEngine.addTile(ZERO, ONE, tileType);
		tileType = TileType.PASSABLE;
		gameEngine.addTile(ONE, ONE, tileType);
		tileType = TileType.TARGET;
		gameEngine.addTile(TWO, ONE, tileType);

		gameEngine.keyRight();
		Thread.sleep(3000);
		gameEngine.keyRight();
		assertThat(gameEngine.getLevel(), equalTo(2));
	}
}
