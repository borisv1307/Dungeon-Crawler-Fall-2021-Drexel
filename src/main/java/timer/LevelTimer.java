package timer;

import java.util.Timer;

import engine.GameEngine;

public class LevelTimer implements Runnable {
	GameEngine gameEngine;
	Timer timer;

	public LevelTimer(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public void run() {
		timer = new Timer();
		timer.schedule(new CountDown(this), 0, 1000);
	}

	public void stop() {
		try {
			timer.cancel();
			timer.purge();
			gameEngine.timerRunsOutCallBack();
		} catch (NullPointerException e) {
		}
	}
}
