package engine;

import tiles.TileType;

public class CollisionCounter {

	private int doorCollisionCounter;
	GameEngine gameEngine;
	private int bridgeCollisionCounter;

	public CollisionCounter(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public void incrementDoorCollisionCounter() {
		doorCollisionCounter++;
		if (doorCollisionCounter == 2) {
			gameEngine.setExit(true);
		}
	}

	public int getDoorCollisionCounter() {
		return doorCollisionCounter;
	}

	public void incrementBridgeCollisionCounter() {
		bridgeCollisionCounter++;
		if (bridgeCollisionCounter == 3) {
			gameEngine.setExit(true);
		}
	}

	public int getBridgeCollisionCounter() {
		return bridgeCollisionCounter;
	}

	public void reinitializeCollisionCounters() {
		doorCollisionCounter = 0;
		bridgeCollisionCounter = 0;
	}

	public void countDoorCollisions(TileType tile) {
		if (tile.equals(TileType.DEACTIVATED_DOOR)) {
			incrementDoorCollisionCounter();
		}
	}
}
