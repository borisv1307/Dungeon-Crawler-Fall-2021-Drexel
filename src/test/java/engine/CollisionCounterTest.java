package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tiles.TileType;

public class CollisionCounterTest {

	private static final Object ONE = 1;
	private static final Object ZERO = 0;
	CollisionCounter collisionCounter;
	GameEngine gameEngine;

	@Before
	public void setUp() throws Exception {
		gameEngine = Mockito.mock(GameEngine.class);
		collisionCounter = new CollisionCounter(gameEngine);
	}

	@Test
	public void increment_and_get_door_collision_counter() {
		collisionCounter.incrementDoorCollisionCounter();
		int doorCollisionCounter = collisionCounter.getDoorCollisionCounter();
		assertThat(doorCollisionCounter, equalTo(ONE));
	}

	@Test
	public void increment_and_get_bridge_collision_counter() {
		collisionCounter.incrementBridgeCollisionCounter();
		int bridgeCollisionCounter = collisionCounter.getBridgeCollisionCounter();
		assertThat(bridgeCollisionCounter, equalTo(ONE));
	}

	@Test
	public void reinitialize_collision_counters() {
		collisionCounter.incrementBridgeCollisionCounter();
		collisionCounter.incrementDoorCollisionCounter();
		collisionCounter.reinitializeCollisionCounters();
		int doorCollisionCounter = collisionCounter.getDoorCollisionCounter();
		int bridgeCollisionCounter = collisionCounter.getBridgeCollisionCounter();
		assertThat(doorCollisionCounter, equalTo(ZERO));
		assertThat(bridgeCollisionCounter, equalTo(ZERO));
	}

	@Test
	public void increment_door_collision_counter_when_collision_with_deactivated_door() {
		TileType tile = TileType.DEACTIVATED_DOOR;
		collisionCounter.countDoorCollisions(tile);
		int actual = collisionCounter.getDoorCollisionCounter();
		assertThat(actual, equalTo(ONE));
	}

	@Test
	public void do_not_increment_door_collision_counter_when_collision_with_activated_door() {
		TileType tile = TileType.DOOR;
		collisionCounter.countDoorCollisions(tile);
		int actual = collisionCounter.getDoorCollisionCounter();
		assertThat(actual, equalTo(ZERO));
	}

	@Test
	public void exit_game_when_door_collison_counter_equals_two() {
		collisionCounter.incrementDoorCollisionCounter();
		collisionCounter.incrementDoorCollisionCounter();
		Mockito.verify(gameEngine).setExit(true);
	}

	@Test
	public void exit_game_when_bridge_collison_counter_equals_three() {
		collisionCounter.incrementBridgeCollisionCounter();
		collisionCounter.incrementBridgeCollisionCounter();
		collisionCounter.incrementBridgeCollisionCounter();
		Mockito.verify(gameEngine).setExit(true);
	}
}
