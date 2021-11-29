package wrappers;

import java.util.Random;

public class RandomWrapper {

	public int nextInt(int max) {
		Random random = new Random();
		return random.nextInt(max);
	}
}
