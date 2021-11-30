package wrappers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemWrapper {
	private static final Logger LOGGER = Logger.getLogger(SystemWrapper.class.getName());

	public long nanoTime() {
		return System.nanoTime();
	}

	public void println(String string) {
		LOGGER.log(Level.INFO, string);
	}
}
