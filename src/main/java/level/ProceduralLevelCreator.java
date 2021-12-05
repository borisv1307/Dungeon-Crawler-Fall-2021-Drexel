package level;

import tiles.TileType;
import wrappers.OpenSimplexNoiseWrapper;

public class ProceduralLevelCreator extends LevelCreator {

	private OpenSimplexNoiseWrapper noiseGenerator;

	private static final double PASSABLE_THRESHOLD = 0;

	public ProceduralLevelCreator(OpenSimplexNoiseWrapper noiseGenerator, int xRange, int yRange) {
		super(xRange, yRange);
		this.noiseGenerator = noiseGenerator;
	}

	public ProceduralLevelCreator(int xRange, int yRange) {
		this(new OpenSimplexNoiseWrapper(), xRange, yRange);
	}

	@Override
	protected TileType determineTileType(int x, int y) {
		double randomValue = noiseGenerator.eval(x, y);
		if (tileIsCenter(x, y)) {
			return TileType.PLAYER;
		} else if (randomValue <= PASSABLE_THRESHOLD || tileIsBorder(x, y)) {
			return TileType.NOT_PASSABLE;
		} else {
			return TileType.PASSABLE;
		}
	}

}
