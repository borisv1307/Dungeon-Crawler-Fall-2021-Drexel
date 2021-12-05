package main;

import engine.GameEngine;
import org.junit.Assert;
import org.junit.Test;
import ui.GameFrame;

import java.awt.event.WindowEvent;

public class WindowClosingUITest {

	GameFrame gameFrame;
	GameEngine gameEngine;

	@Test
	public void window_closes_event() {
		gameEngine = ObjectFactory.getDefaultGameEngine();
		gameFrame = ObjectFactory.getDefaultGameFrame();
		gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
		Assert.assertTrue(gameEngine.isExit());
	}

}
