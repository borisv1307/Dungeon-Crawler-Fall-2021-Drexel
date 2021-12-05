package level;

import engine.GameEngine;
import tiles.TileType;
import values.TunableParameters;
import wrappers.ReaderWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FileParserLevelCreator extends LevelCreator {

	String fileLocationPrefix;
	String fileNameSuffix = TunableParameters.FILE_NAME_SUFFIX;
	ReaderWrapper readerWrapper;
	List<String> fileContents;

	public FileParserLevelCreator(String fileLocationPrefix, ReaderWrapper readerWrapper) {
		super(0, 0);
		this.fileLocationPrefix = fileLocationPrefix;
		this.readerWrapper = readerWrapper;
	}

	@Override
	public void createLevel(GameEngine gameEngine, int level) {
		this.fileContents = pullLevelDataFromFile(gameEngine, level);
		if (!gameEngine.isExit() && fileContents.size() != 0) {
			super.xRange = fileContents.get(0).length();
			super.yRange = fileContents.size();
			super.createLevel(gameEngine, level);
		}
	}

	@Override
	protected TileType determineTileType(int x, int y) {
		char tileRepresentation = fileContents.get(y).toCharArray()[x];
		return TileType.getTileTypeByChar(tileRepresentation);
	}

	private List<String> pullLevelDataFromFile(GameEngine gameEngine, int level) {
		List<String> fileContents = new ArrayList<>();
		try (BufferedReader reader = readerWrapper.createBufferedReader(getFilePath(level))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContents.add(line);
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			gameEngine.setExit(true);
		}
		return fileContents;
	}

	String getFilePath(int level) {
		return fileLocationPrefix + level + fileNameSuffix;
	}
}