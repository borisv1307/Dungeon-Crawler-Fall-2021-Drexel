package wrappers;

import com.github.kdotjpg.noise.OpenSimplexNoise;

import java.util.Random;

public class OpenSimplexNoiseWrapper {

	private OpenSimplexNoise noiseLibrary;

	public OpenSimplexNoiseWrapper() {
		noiseLibrary = new OpenSimplexNoise((new Random()).nextLong());
	}

	public OpenSimplexNoiseWrapper(long seed) {
		noiseLibrary = new OpenSimplexNoise(seed);
	}

	public double eval(double x, double y) {
		return noiseLibrary.eval(x, y);
	}
}