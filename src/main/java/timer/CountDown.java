package timer;

import java.awt.*;
import java.util.TimerTask;

import values.TileColorMap;
import values.TunableParameters;

class CountDown extends TimerTask {
	private int countdown = TunableParameters.PLAYER_LIMIT_TIME;
	private LevelTimer timerThread = null;

	public CountDown(LevelTimer timerThread) {
		this.timerThread = timerThread;
	}

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
		timerThread.stop();
	}
}