package timer;

import java.awt.*;
import java.util.TimerTask;

import values.TileColorMap;
import values.TunableParameters;

class CountDown extends TimerTask {
	private int countDown = TunableParameters.PLAYER_LIMIT_TIME;
	private LevelTimer timerThread = null;

	public CountDown(LevelTimer timerThread) {
		this.timerThread = timerThread;
	}

	@Override
	public void run() {
		countDown--;
		if (countDown <= 0) {
			TileColorMap.changePlayerColor(Color.RED);
			closeTimer();
		}
	}

	private void closeTimer() {
		timerThread.stop();
	}
}