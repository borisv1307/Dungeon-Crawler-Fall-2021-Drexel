package level;

import engine.GameEngine;
import tiles.TileType;
import wrappers.noise.OpenSimplexNoiseWrapper;

public class ProceduralLevelCreator extends LevelCreator {

	private OpenSimplexNoiseWrapper noiseGenerator;

	public ProceduralLevelCreator(OpenSimplexNoiseWrapper noiseGenerator) {
		this.noiseGenerator = noiseGenerator;
	}

	@Override
	public void createLevel(GameEngine gameEngine, int level) {

	}

	public TileType determineTileType(double x, double y) {
		double randomValue = noiseGenerator.eval(x, y);
		if (randomValue > 0) {
			return TileType.PASSABLE;
		} else {
			return TileType.NOT_PASSABLE;
		}
	}
}
