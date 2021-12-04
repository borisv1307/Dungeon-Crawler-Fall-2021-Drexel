package thread;

import engine.GameEngine;
import timer.LevelTimer;

public class CountDownThread {
	private LevelTimer levelTimer;
	private Thread timerThread;

	public CountDownThread(GameEngine gameEngine) {
		levelTimer = new LevelTimer(gameEngine);
	}

	public void startCountDown() {
		timerThread = new Thread(levelTimer);
		timerThread.start();
	}

	public void stopCountDown() {
		levelTimer.stop();
	}

	public boolean isAlive() {
		if (timerThread == null) {
			return false;
		} else {
			return timerThread.isAlive();
		}
	}
}
