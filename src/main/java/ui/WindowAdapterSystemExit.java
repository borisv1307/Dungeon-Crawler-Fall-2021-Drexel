package ui;

import engine.GameEngine;

import java.awt.event.WindowAdapter;

public class WindowAdapterSystemExit extends WindowAdapter {
	
	GameEngine gameEngine;
	
	public WindowAdapterSystemExit(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	@Override
	public void windowClosing(java.awt.event.WindowEvent e) {
		gameEngine.setExit(true);
	}
}
