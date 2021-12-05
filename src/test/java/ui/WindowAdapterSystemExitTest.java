package ui;

import engine.GameEngine;
import org.junit.Test;
import org.mockito.Mockito;

public class WindowAdapterSystemExitTest {

	@Test
	public void window_closing_sets_exit_in_game() {
		GameEngine gameEngine = Mockito.mock(GameEngine.class);
		WindowAdapterSystemExit windowAdapterSystemExit = new WindowAdapterSystemExit(gameEngine);

		windowAdapterSystemExit.windowClosing(null);

		Mockito.verify(gameEngine).setExit(true);
	}

}
