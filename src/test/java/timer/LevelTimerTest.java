package timer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import engine.GameEngine;
import ui.GameFrame;
import wrappers.SystemWrapper;

public class LevelTimerTest {

	private static final long FIVE_SECONDS = 5000;
	private static final long TEN_SECONDS = 10000;
	private LevelTimer levelTimer;
	private SystemWrapper systemWrapper;
	private GameFrame gameFrame;
	private GameEngine gameEngine;

	@Before
	public void setUp() throws Exception {
		systemWrapper = Mockito.mock(SystemWrapper.class);
		gameEngine = Mockito.mock(GameEngine.class);
		gameFrame = Mockito.mock(GameFrame.class);
		levelTimer = new LevelTimer(systemWrapper, gameFrame);
	}

	@Test
	public void set_and_get_five_second_timer() {
		levelTimer.setTimerForSeconds(5);
		long timer = levelTimer.getTimer();
		assertThat(timer, equalTo(FIVE_SECONDS));
	}

	@Test
	public void set_and_get_ten_second_timer() {
		levelTimer.setTimerForSeconds(10);
		long timer = levelTimer.getTimer();
		assertThat(timer, equalTo(TEN_SECONDS));
	}

	@Test
	public void start_timer() {
		levelTimer.startTimer();
		boolean started = levelTimer.isStarted();
		assertThat(started, equalTo(true));
	}

	@Test
	public void stop_game_when_timer_is_up() {
		levelTimer.startTimer();
		levelTimer.stopTimer();
		levelTimer.endLevel(gameEngine);
		Mockito.verify(gameEngine).setExit(true);
	}

	@Test
	public void do_not_stop_game_if_timer_is_not_up() {
		levelTimer.startTimer();
		levelTimer.endLevel(gameEngine);
		Mockito.verifyZeroInteractions(gameEngine);
	}

}
