package thread;

import engine.GameEngine;
import timer.LevelTimer;

public class CountDownThread {
	private LevelTimer levelTimer;
	private Thread timerThread;

	public CountDownThread(int timeLimit, GameEngine gameEngine) {
		levelTimer = new LevelTimer(timeLimit, gameEngine);
	}

	public void startCountDown() {
		timerThread = new Thread(levelTimer);
		timerThread.start();
	}

	public void stopCountDown() {
		levelTimer.stop();
	}

	public boolean isAlive() {
		return timerThread.isAlive();
	}
}
