package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.entitydrawer.PlayerShipDrawer;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;
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
			ImageLoader imageLoader = new ImageLoaderAwt();
			ImageAdapter accel =
					imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_ACCEL_PNG);
			ImageAdapter nonAccel = imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_PNG);
			PlayerShipDrawer playerShipDrawer = PlayerShipDrawer.createPlayerShipDrawer(accel, nonAccel);
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
