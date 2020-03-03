package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.entityfactory.EntityFactory;
import se.hig.thlu.asteroids.entityfactory.RandomEntityFactory;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.gui.GUI;
import se.hig.thlu.asteroids.gui.SwingGUI;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.storage.ImageLoader;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.io.IOException;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
		try {
			ImageLoader<? extends ImageAdapter> imgLoader = new ImageLoaderAwt();
			EntityFactory factory = new RandomEntityFactory(imgLoader);
			PlayerShip playerShip = factory.createPlayerShip();
			CommandController cmdController = CommandController.createCommandController(playerShip);
			GUI<AwtKeyboardAdapter> gui = new SwingGUI(imgLoader);
			GameController gameController = new GameController(factory, cmdController, playerShip,
					gui);
			GameLoop gameLoop = new GameLoop(gameController);
			KeyAdapter inputController =
					InputController.createInputController(gameController);
			AwtKeyboardAdapter keyboardAdapter = new AwtKeyboardAdapter(inputController);
			gui.addEventListener(keyboardAdapter);

			gameLoop.gameLoop();

//			EntityFactory factory = RandomEntityFactory.createRandomEntityFactory();
//			PlayerShip playerShip = factory.createPlayerShip();
//			CommandController cController = CommandController.createCommandController(playerShip);
//			GameController gameController = GameController.createGameController(factory, cController, playerShip);
//			InputController inputController = InputController.createInputController(gameController);
//
//			GUI GUI = new SwingGOOOEY();
//			GUI.addEventListener(inputController);
//
//			GameLoop gameLoop = new GameLoop(gameController);
//			gameLoop.gameLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
