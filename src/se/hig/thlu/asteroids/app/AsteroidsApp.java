package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.*;
import se.hig.thlu.asteroids.gamestate.*;
import se.hig.thlu.asteroids.ui.*;

import javax.swing.*;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {

		}
		EntityController controller = new EntityController();
		State.reset(controller);
		UI ui = new GUI();
		GameLoop gameLoop = new GameLoop();
	}

}
