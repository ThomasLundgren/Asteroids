package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.gamestate.GameController;
import se.hig.thlu.asteroids.gamestate.InputController;
import se.hig.thlu.asteroids.gamestate.command.CommandController;
import se.hig.thlu.asteroids.factory.DefaultFactory;
import se.hig.thlu.asteroids.factory.EntityFactory;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;
import se.hig.thlu.asteroids.gui.GUI;
import se.hig.thlu.asteroids.gui.SwingGUI;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.model.entity.PlayerShip;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.io.IOException;

public class AsteroidsApp {

	// TODO: Create score keeping.
	// TODO: Create game start menu.
	// TODO: Create game end menu (score submission).
	// TODO: Create animation and logic for when player loses a life or dies.
	// TODO: New class FontLoader to load fonts.
	// TODO: Use polymorfism or just Factory Methods to create Asteroids and Explosions of different sizes instead of
	//  Enums.
	// TODO: Refactor GameController.
	// TODO: Refactor SwingGUI: split into different classes. Make loading of Animations/Images run at startup
	//  to avoid lag.
	// TODO: Uncouple GUI from Swing/AWT (by creating an ABC?).
	// TODO: Change PlayerShip image to a better looking one.
	// TODO: Refactor to Entity-Component-like model?

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
		try {
			AbstractImageLoader<AwtImageAdapter> imgLoader = new ImageLoaderAwt();
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
