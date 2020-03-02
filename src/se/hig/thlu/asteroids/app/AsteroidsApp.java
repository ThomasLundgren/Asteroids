package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.entityfactory.EntityFactory;
import se.hig.thlu.asteroids.entityfactory.RandomEntityFactory;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.entitydrawer.AsteroidDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.PlayerShipDrawer;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.storage.ImageLoader;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;
import se.hig.thlu.asteroids.ui.SwingGUI;
import se.hig.thlu.asteroids.ui.UI;

import javax.swing.*;
import java.io.IOException;

import static se.hig.thlu.asteroids.storage.ImageLoader.ImageResource.*;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
		try {
			EntityFactory factory = RandomEntityFactory.createRandomEntityFactory();
			PlayerShip playerShip = factory.createPlayerShip();
			CommandController cController = CommandController.createCommandController(playerShip);
			GameController gameController = GameController.createGameController(factory, cController, playerShip);
			InputController inputController = InputController.createInputController(cController);

			ImageLoader imageLoader = new ImageLoaderAwt();
			ImageAdapter accel =
					imageLoader.getImageResource(PLAYER_SHIP_ACCEL_PNG);
			ImageAdapter nonAccel = imageLoader.getImageResource(PLAYER_SHIP_PNG);
			ImageAdapter large = imageLoader.getImageResource(ASTEROID_LARGE_PNG);
			ImageAdapter medium = imageLoader.getImageResource(ASTEROID_MEDIUM_PNG);
			ImageAdapter small = imageLoader.getImageResource(ASTEROID_SMALL_PNG);

			PlayerShipDrawer playerShipDrawer = PlayerShipDrawer.createPlayerShipDrawer(accel, nonAccel);
			AsteroidDrawer asteroidDrawer = AsteroidDrawer.createAsteroidDrawer(small, medium, large);

			gameController.addListenerForShip(playerShipDrawer);
			gameController.addListenerForAsteroids(asteroidDrawer);

			UI ui = new SwingGUI(playerShipDrawer, asteroidDrawer);
			ui.addKeyListener(inputController);

			GameLoop gameLoop = new GameLoop(gameController);
			gameLoop.gameLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
