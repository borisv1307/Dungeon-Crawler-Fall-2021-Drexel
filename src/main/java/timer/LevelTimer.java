package timer;

import engine.GameEngine;
import ui.GameFrame;
import wrappers.SystemWrapper;

public class LevelTimer {
	private long timer;
	private SystemWrapper systemWrapper;
	private boolean started;
	private boolean finished;
	private GameFrame gameFrame;

	public LevelTimer(SystemWrapper systemWrapper, GameFrame gameFrame) {
		this.systemWrapper = systemWrapper;
		started = false;
		this.gameFrame = gameFrame;
	}

	public long getTimer() {
		return timer;
	}

	public void setTimerForSeconds(int seconds) {
		long start = systemWrapper.currentTimeMillis();
		timer = start + 1000 * seconds;
	}

	public void startTimer() {
		started = true;
	}

	public boolean isStarted() {
		return started;
	}

	public void stopTimer() {
		finished = true;
	}

	public void endLevel(GameEngine gameEngine) {
		if (started && finished) {
			gameEngine.setExit(true);
		}
	}

	public void runLevel(GameEngine gameEngine) {
		while (systemWrapper.currentTimeMillis() < timer && !gameEngine.isExit()) {
			gameEngine.run(gameFrame);
		}
		stopTimer();
	}

	public boolean isFinished() {
		return finished;
	}

}
