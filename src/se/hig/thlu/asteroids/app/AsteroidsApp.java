package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.model.entity.RandomizedFactory;
import se.hig.thlu.asteroids.model.entity.EntityFactory;
import se.hig.thlu.asteroids.gamestate.GameController;
import se.hig.thlu.asteroids.gamestate.GameLoop;
import se.hig.thlu.asteroids.gamestate.InputController;
import se.hig.thlu.asteroids.gamestate.ScoreKeeper;
import se.hig.thlu.asteroids.gamestate.command.CommandController;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.AwtImageAdapter;
import se.hig.thlu.asteroids.gui.eventlisteneradapter.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.gui.view.GUI;
import se.hig.thlu.asteroids.gui.view.SwingGUI;
import se.hig.thlu.asteroids.model.entity.PlayerShip;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;
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
			ScoreKeeper scoreKeeper = new ScoreKeeper();
			AbstractImageLoader<AwtImageAdapter> imgLoader = new ImageLoaderAwt();
			EntityFactory factory = new RandomizedFactory();
			GUI<AwtKeyboardAdapter> gui = new SwingGUI(imgLoader);
			PlayerShip playerShip = factory.createPlayerShip();
			CommandController cmdController = CommandController.createCommandController(playerShip);
			GameController gameController = new GameController(factory, cmdController, playerShip);
			GameLoop gameLoop = new GameLoop(gameController);
			KeyAdapter inputController =
					InputController.createInputController(gameController);
			AwtKeyboardAdapter keyboardAdapter = new AwtKeyboardAdapter(inputController);
			gui.addEventListener(keyboardAdapter);

			gameLoop.run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
