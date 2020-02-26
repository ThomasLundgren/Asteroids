package se.hig.thlu.asteroids.app;

import se.hig.thlu.asteroids.controller.*;
import se.hig.thlu.asteroids.gamestate.*;
import se.hig.thlu.asteroids.graphics.polygon.*;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.ui.*;

import javax.swing.*;
import java.util.*;

public class AsteroidsApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {

		}
		EntityController controller = new EntityController();
		State.reset(controller);
		UI ui = new GUI();
		ui.addKeyListener(controller);
		GameLoop gameLoop = new GameLoop();
		ui.render(Arrays.asList(new PlayerShipImage(), new PlayerShipPolygon(new Point(450.0, 450.0), 0)));
		gameLoop.gameLoop();
	}

}
