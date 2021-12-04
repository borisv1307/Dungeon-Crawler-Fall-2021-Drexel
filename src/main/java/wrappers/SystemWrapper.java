package wrappers;

public class SystemWrapper {

	public long nanoTime() {
		return System.nanoTime();
	}

	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}
