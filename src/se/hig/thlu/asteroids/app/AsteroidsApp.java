package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.factory.DefaultFactory;
import se.hig.thlu.asteroids.factory.EntityFactory;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;
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
			ImageLoader<AwtImageAdapter> imgLoader = new ImageLoaderAwt();
			EntityFactory factory = new DefaultFactory();
			PlayerShip playerShip = factory.createPlayerShip();
			CommandController cmdController = CommandController.createCommandController(playerShip);
			GUI<AwtKeyboardAdapter> gui = new SwingGUI(imgLoader);
			GameController gameController = new GameController(factory, cmdController, playerShip);
			gameController.addObserver(gui);
			GameLoop gameLoop = new GameLoop(gameController);
			KeyAdapter inputController =
					InputController.createInputController(gameController);
			AwtKeyboardAdapter keyboardAdapter = new AwtKeyboardAdapter(inputController);
			gui.addEventListener(keyboardAdapter);

			gameLoop.gameLoop();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
