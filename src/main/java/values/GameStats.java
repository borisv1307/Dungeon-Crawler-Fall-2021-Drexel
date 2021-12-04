package values;

public class GameStats {
	private int score;
	private int currentLevel;

	public GameStats() {
		reset();
	}

	public int getLevel() {
		return currentLevel;
	}

	public int increaseLevel() {
		currentLevel++;
		return currentLevel;
	}

	public int getScore() {
		return score;
	}

	public void addScore() {
		score += TunableParameters.SCORE_PER_LEVEL;
	}

	public void reset() {
		currentLevel = 0;
		score = -5;
	}
}
