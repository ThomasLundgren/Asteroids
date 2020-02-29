package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.*;
import se.hig.thlu.asteroids.gamestate.*;
import se.hig.thlu.asteroids.graphics.awtdrawer.*;
import se.hig.thlu.asteroids.storage.*;
import se.hig.thlu.asteroids.ui.*;

import javax.swing.*;
import java.io.*;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
		try {
			GameController gameController = new GameController();
			ImageLoader imageLoader = new ImageLoader();
			AwtPlayerShipDrawer playerShipDrawer = new AwtPlayerShipDrawer(imageLoader);
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
