package engine;

import tiles.TileType;

class GameMovement {
	GameEngine gameEngine;
	CollisionCounter collisionCounter;

	GameMovement(GameEngine gameEngine, CollisionCounter collisionCounter) {
		this.gameEngine = gameEngine;
		this.collisionCounter = collisionCounter;
	}

	void move(int nextX, int nextY) {
		TileType nextLocation = gameEngine.getTileFromCoordinates(nextX, nextY);
		makeMove(nextLocation, nextX, nextY);
	}

	void moveUp(int nextX, int nextY) {
		TileType nextLocation = gameEngine.getTileFromCoordinates(nextX, nextY);
		if (nextLocation.equals(TileType.NOT_PASSABLE_BRIDGE)) {
			collisionCounter.incrementBridgeCollisionCounter();
			gameEngine.bringPlayerBackToInitialPosition();
		} else {
			makeMove(nextLocation, nextX, nextY);
		}
	}

	private void makeMove(TileType nextLocation, int nextX, int nextY) {
		collisionCounter.countDoorCollisions(nextLocation);
		collisionCounter.countObstacleCollisions(nextLocation);
		gameEngine.changeObstacleToPassable(nextLocation, nextX, nextY);
		movePlayerTo(nextX, nextY);
		gameEngine.finishGame(nextLocation);
	}

	private void movePlayerTo(int nextX, int nextY) {
		TileType nextLocation = gameEngine.getTileFromCoordinates(nextX, nextY);
		if (nextLocation.equals(TileType.PASSABLE) || nextLocation.equals(TileType.DOOR)
				|| nextLocation.equals(TileType.PASSABLE_BRIDGE)) {
			gameEngine.setPlayer(nextX, nextY);
		}
	}
}
