package launcher;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import engine.GameEngine;
import main.DungeonCrawler;
import main.ObjectFactory;
import timer.FramesPerSecondHandler;
import ui.GameFrame;
import wrappers.ThreadWrapper;

public class Launcher {

	public static void main(String[] args) {
		ThreadWrapper threadWrapper = ObjectFactory.getDefaultThreadWrapper();
		GameEngine gameEngine = ObjectFactory.getDefaultGameEngine();
		GameFrame gameFrame = ObjectFactory.getDefaultGameFrame();
		FramesPerSecondHandler framesPerSecondHandler = ObjectFactory.getDefaultFramesPerSecondHandler();
		beginButton();
		new DungeonCrawler(threadWrapper, gameEngine, gameFrame, framesPerSecondHandler);
	}

	public static void beginButton() {
		final JFrame parent = new JFrame();
		JButton button = new JButton();
		button.setFont(new Font("Arial", Font.BOLD, 30));
		button.setText("Grab the yellow keys and get to the red exit door!  Get ready, game will start shortly...");
		button.setPreferredSize(new Dimension(1500, 1000));
		parent.add(button);
		parent.pack();
		parent.setVisible(true);
		screenTimer();
		parent.setVisible(false);
	}

	public static void screenTimer() {
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		while (elapsedTime < 11000) {
			elapsedTime = (new Date()).getTime() - startTime;
		}

	}

}
