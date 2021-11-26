package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import engine.GameEngine;
import tiles.TileType;
import values.TunableParameters;
import wrappers.ReaderWrapper;

public class LevelCreator {
	private static final Logger LOGGER = Logger.getLogger(LevelCreator.class.getName());

	String fileLocationPrefix;
	String fileNameSuffix = TunableParameters.FILE_NAME_SUFFIX;
	ReaderWrapper readerWrapper;

	public LevelCreator(String fileLocationPrefix, ReaderWrapper readerWrapper) {
		this.fileLocationPrefix = fileLocationPrefix;
		this.readerWrapper = readerWrapper;
	}

	public void createLevel(GameEngine gameEngine, int level) {
		int doorCharCounter = 0;
		BufferedReader reader;
		try {
			reader = readerWrapper.createBufferedReader(getFilePath(level));
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			gameEngine.setExit(true);
			return;
		}
		try {
			String line = null;
			int y = 0;
			while ((line = reader.readLine()) != null) {
				int x = 0;
				for (char ch : line.toCharArray()) {
					doorCharCounter = addTilesDependingOnLevel(gameEngine, level, doorCharCounter, x, y, ch);
					x++;
				}
				gameEngine.setLevelHorizontalDimension(x);
				y++;
			}
			gameEngine.setLevelVerticalDimension(y);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			gameEngine.setExit(true);
		} finally {
			closeBufferedReader(reader, gameEngine);
		}
	}

	private int addTilesDependingOnLevel(GameEngine gameEngine, int level, int doorCharCounter, int x, int y, char ch) {
		if (level == 1) {
			doorCharCounter = deactivateLeftmostDoor(gameEngine, doorCharCounter, x, y, ch);
		} else {
			gameEngine.addTile(x, y, TileType.getTileTypeByChar(ch));
		}
		return doorCharCounter;
	}

	private int deactivateLeftmostDoor(GameEngine gameEngine, int doorCharCounter, int x, int y, char ch) {
		if (doorCharCounter < 1 && ch == 'D') {
			doorCharCounter++;
			TileType door = TileType.getTileTypeByChar(ch);
			TileType deactivatedDoor = door.deactivate();
			gameEngine.addTile(x, y, deactivatedDoor);
		} else {
			gameEngine.addTile(x, y, TileType.getTileTypeByChar(ch));
		}
		return doorCharCounter;
	}

	private void closeBufferedReader(BufferedReader reader, GameEngine gameEngine) {
		try {
			reader.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			gameEngine.setExit(true);
		}
	}

	String getFilePath(int level) {
		return fileLocationPrefix + level + fileNameSuffix;
	}
}
