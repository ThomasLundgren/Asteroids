package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.*;
import se.hig.thlu.asteroids.gamestate.*;
import se.hig.thlu.asteroids.storage.*;
import se.hig.thlu.asteroids.ui.*;

import javax.swing.*;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}

		ImageLoader loader = new ImageLoader();

		PlayerShipController controller = PlayerShipController.getInstance();
		InputController inputController = new InputController();

		UI ui = new GUI();
		ui.addKeyListener(inputController);
		controller.addPropertyChangeListener(ui);

		GameLoop gameLoop = new GameLoop(controller);
		gameLoop.gameLoop();
	}

}
