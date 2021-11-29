package wrappers;

import java.security.SecureRandom;

public class RandomWrapper {
	private SecureRandom random = new SecureRandom();

	public int nextInt(int max) {
		return random.nextInt(max);
	}
}
