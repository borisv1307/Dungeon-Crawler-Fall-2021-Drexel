package thread;

import engine.GameEngine;
import timer.LevelTimer;

public class CountDownThread {
	private LevelTimer levelTimer;
	private Thread timerThread;

	public CountDownThread(int PLAYER_TIME_LIMIT, GameEngine gameEngine) {
		levelTimer = new LevelTimer(PLAYER_TIME_LIMIT, gameEngine);
		timerThread = new Thread(levelTimer);
	}

	public void startCountDown() {
		timerThread.run();
	}

	public void stopCountDown() {
		levelTimer.stop();
	}

	public boolean isAlive() {
		return timerThread.isAlive();
	}
}
