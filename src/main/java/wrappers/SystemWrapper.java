package wrappers;

public class SystemWrapper {

	public long nanoTime() {
		return System.nanoTime();
	}

	public void println(String string) {
		System.out.println(string);
	}
}
