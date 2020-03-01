package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.entitydrawer.PlayerShipDrawerAwt;
import se.hig.thlu.asteroids.storage.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public class SwingGUI implements UI {

	private BackgroundPanel backgroundPanel;
	private ImageLoader imageLoader;
	private final JFrame frame = new JFrame();

	public SwingGUI(PlayerShipDrawerAwt playerShipDrawer) {
		try {
			imageLoader = new ImageLoader();
			backgroundPanel =
					new BackgroundPanel(playerShipDrawer,
							imageLoader.getImageResource(ImageLoader.ImageResource.BACKGROUND_PNG));
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
