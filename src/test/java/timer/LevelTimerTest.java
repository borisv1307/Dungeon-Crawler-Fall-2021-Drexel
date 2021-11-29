package timer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.*;

import org.junit.Before;
import org.junit.Test;

import tiles.TileType;
import values.TileColorMap;

public class LevelTimerTest {
	LevelTimer levelTimer = new LevelTimer(5);
	Thread levelTimerThread = new Thread(levelTimer);

	@Before
	public void setUp() {
		TileColorMap.resetPlayerColor();
		levelTimerThread.start();
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
}
