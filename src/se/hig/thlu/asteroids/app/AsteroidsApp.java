package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.*;
import se.hig.thlu.asteroids.gamestate.*;
import se.hig.thlu.asteroids.ui.*;

import javax.swing.*;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
		GameController gameController = new GameController();
		UI ui = new SwingGUI();
		ui.addKeyListener(new InputController(gameController));

		GameLoop gameLoop = new GameLoop(gameController);
		gameLoop.gameLoop();
	}

}
