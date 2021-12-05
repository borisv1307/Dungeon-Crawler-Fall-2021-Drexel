package wrappers;

import com.github.kdotjpg.noise.OpenSimplexNoise;

public class OpenSimplexNoiseWrapper {

	private OpenSimplexNoise noiseLibrary;

	public OpenSimplexNoiseWrapper() {
		noiseLibrary = new OpenSimplexNoise((long) (Math.random() * 1000000L));
	}

	public OpenSimplexNoiseWrapper(long seed) {
		noiseLibrary = new OpenSimplexNoise(seed);
	}

	public double eval(double x, double y) {
		return noiseLibrary.eval(x, y);
	}
}