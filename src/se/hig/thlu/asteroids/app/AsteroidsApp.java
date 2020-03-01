package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.entitydrawer.PlayerShipDrawerAwt;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;
import se.hig.thlu.asteroids.ui.SwingGUI;
import se.hig.thlu.asteroids.ui.UI;

import javax.swing.*;
import java.io.IOException;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
		try {
			GameController gameController = new GameController();
			ImageLoaderAwt imageLoader = new ImageLoaderAwt();
			PlayerShipDrawerAwt playerShipDrawer = new PlayerShipDrawerAwt(imageLoader);
			gameController.addListenerForShip(playerShipDrawer);
			UI ui = new SwingGUI(playerShipDrawer);
			ui.addKeyListener(new InputController(gameController));

			GameLoop gameLoop = new GameLoop(gameController);
			gameLoop.gameLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
