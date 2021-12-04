package level;

import java.util.logging.Logger;

import engine.GameEngine;

public abstract class LevelCreator {
	protected static final Logger LOGGER = Logger.getLogger(LevelCreator.class.getName());

	public abstract void createLevel(GameEngine gameEngine, int level);

}
