package timer;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import engine.GameEngine;
import values.TileColorMap;

public class LevelTimer implements Runnable {
	int PLAYER_TIME_LIMIT;
	GameEngine gameEngine;
	Timer timer = new Timer();

	public LevelTimer(int PLAYER_TIME_LIMIT, GameEngine gameEngine) {
		this.PLAYER_TIME_LIMIT = PLAYER_TIME_LIMIT;
		this.gameEngine = gameEngine;
	}

	@Override
	public void run() {
		timer.schedule(new CountDown(), 0, 1000);
	}

	public void stop() {
		timer.cancel();
		timer.purge();
	}

	class CountDown extends TimerTask {
		int countdown = PLAYER_TIME_LIMIT;

		@Override
		public void run() {
			countdown--;
			if (countdown <= 0) {
				TileColorMap.changePlayerColor(Color.RED);
				closeTimer();
				return;
			}
		}

		private void closeTimer() {
			stop();
			gameEngine.timerRunsOut();
		}
	}
}
