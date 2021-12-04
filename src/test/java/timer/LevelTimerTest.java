package timer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.*;

import org.junit.Before;
import org.junit.Test;

import engine.GameEngine;
import parser.LevelCreator;
import tiles.TileType;
import values.TileColorMap;
import values.TunableParameters;
import wrappers.ReaderWrapper;

public class LevelTimerTest {
	LevelTimer levelTimer;
	Thread levelTimerThread;

	@Before
	public void setUp() {
		TileColorMap.resetPlayerColor();
		levelTimer = new LevelTimer(5,
				new GameEngine(new LevelCreator(TunableParameters.FILE_LOCATION_PREFIX, new ReaderWrapper())));
		levelTimerThread = new Thread(levelTimer);
		levelTimerThread.run();
	}

	@Test
	public void player_tile_should_turn_red_after_5_seconds() throws InterruptedException {
		Thread.sleep(5000);
		assertThat(TileColorMap.get(TileType.PLAYER), equalTo(Color.RED));
	}

	@Test
	public void player_tile_should_be_green_before_timer_run_out() throws InterruptedException {
		Thread.sleep(3000);
		assertThat(TileColorMap.get(TileType.PLAYER), equalTo(Color.GREEN));
	}

	@Test
	public void stopped_thread_should_stop_the_count_down() throws InterruptedException {
		Thread.sleep(2000);
		levelTimer.stop();
		assertThat(TileColorMap.get(TileType.PLAYER), equalTo(Color.GREEN));
	}
}