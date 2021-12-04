package movement;

import java.awt.Point;

import engine.GameEngine;
import tiles.TileType;

public class PlayerMovement {

	public void movingToTile(GameEngine gameEngine, int playerXCoordinate, int playerYCoordinate, int xOffset,
			int yOffset) {
		int xCoordinate = playerXCoordinate + xOffset;
		int yCoordinate = playerYCoordinate + yOffset;
		TileType attemptedLocation = gameEngine.getTileFromCoordinates(xCoordinate, yCoordinate);
		if (attemptedLocation.equals(TileType.PASSABLE)) {
			gameEngine.setPlayer(xCoordinate, yCoordinate);
		}
		if (attemptedLocation.equals(TileType.COIN)) {
			gameEngine.setPlayer(xCoordinate, yCoordinate);
			gameEngine.increaseCoinCount();
			gameEngine.tiles.put(new Point(xCoordinate, yCoordinate), TileType.PASSABLE);
		}
		if (attemptedLocation.equals(TileType.OBSTACLE)) {
			gameEngine.setExit(true);
		}
		if (attemptedLocation.equals(TileType.NEXT_LEVEL)) {
			gameEngine.increaseLevel();
			gameEngine.loadLevel();
		}
		if (attemptedLocation.equals(TileType.PREVIOUS_LEVEL)) {
			gameEngine.decreaseLevel();
			gameEngine.loadLevel();
		}

	}

}
