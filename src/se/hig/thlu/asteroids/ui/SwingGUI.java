package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.entitydrawer.AsteroidDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.PlayerShipDrawer;
import se.hig.thlu.asteroids.storage.ImageLoader.ImageResource;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public class SwingGUI implements UI {

	private BackgroundPanel backgroundPanel;
	private ImageLoaderAwt imageLoader;
	private final JFrame frame = new JFrame();

	public SwingGUI(PlayerShipDrawer playerShipDrawer, AsteroidDrawer asteroidDrawer) {
		try {
			imageLoader = new ImageLoaderAwt();
			backgroundPanel =
					new BackgroundPanel(playerShipDrawer, asteroidDrawer,
							imageLoader.getImageResource(ImageResource.BACKGROUND_PNG));
			frame.add(backgroundPanel);
		} catch (IOException e) {
			frame.add(new JLabel("Could not load image resources."));
		}
		configureFrame();
	}

	// TODO: Abstract keylistener away so that it can be replaced with any event emitter?
	public void addKeyListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}

	private void configureFrame() {
		// TODO: Make JFrame always square and resize all graphics accordingly
		frame.setResizable(false);
		frame.setSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
