package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.entityfactory.EntityFactory;
import se.hig.thlu.asteroids.entityfactory.RandomEntityFactory;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.drawer.AsteroidDrawer;
import se.hig.thlu.asteroids.graphics.drawer.PlayerMissileDrawer;
import se.hig.thlu.asteroids.graphics.drawer.PlayerShipDrawer;
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
			InputController inputController = InputController.createInputController(gameController);




			UI ui = new SwingGUI();
			ui.addKeyListener(inputController);

			GameLoop gameLoop = new GameLoop(gameController);
			gameLoop.gameLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
