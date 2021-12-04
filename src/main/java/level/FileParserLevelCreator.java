package level;

import engine.GameEngine;
import tiles.TileType;
import values.TunableParameters;
import wrappers.ReaderWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;

public class FileParserLevelCreator extends LevelCreator {

	String fileLocationPrefix;
	String fileNameSuffix = TunableParameters.FILE_NAME_SUFFIX;
	ReaderWrapper readerWrapper;

	public FileParserLevelCreator(String fileLocationPrefix, ReaderWrapper readerWrapper) {
		this.fileLocationPrefix = fileLocationPrefix;
		this.readerWrapper = readerWrapper;
	}

	@Override
	public void createLevel(GameEngine gameEngine, int level) {
		try (BufferedReader reader = readerWrapper.createBufferedReader(getFilePath(level))) {
			String line = null;
			int y = 0;
			while ((line = reader.readLine()) != null) {
				int x = 0;
				for (char ch : line.toCharArray()) {
					gameEngine.addTile(x, y, TileType.getTileTypeByChar(ch));
					x++;
				}
				gameEngine.setLevelHorizontalDimension(x);
				y++;
			}
			gameEngine.setLevelVerticalDimension(y);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			gameEngine.setExit(true);
		}
	}

	String getFilePath(int level) {
		return fileLocationPrefix + level + fileNameSuffix;
	}
}