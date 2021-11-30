package thread;

import engine.GameEngine;
import timer.LevelTimer;

public class CountDownThread {
	private LevelTimer levelTimer;
	private Thread timerThread;

	public CountDownThread(int timeLimit, GameEngine gameEngine) {
		levelTimer = new LevelTimer(timeLimit, gameEngine);
		timerThread = new Thread(levelTimer);
	}

	public void startCountDown() {
		timerThread.start();
	}

	public void stopCountDown() {
		levelTimer.stop();
	}

	public boolean isAlive() {
		return timerThread.isAlive();
	}
}
