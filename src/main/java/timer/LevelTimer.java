package timer;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import values.TileColorMap;

public class LevelTimer implements Runnable {
	int PLAYER_TIME_LIMIT;
	Timer timer = new Timer();

	LevelTimer(int PLAYER_TIME_LIMIT) {
		this.PLAYER_TIME_LIMIT = PLAYER_TIME_LIMIT;
	}

	@Override
	public void run() {
		timer.schedule(new CountDown(), 0, 1000);
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
			timer.cancel();
			timer.purge();
		}
	}
}
