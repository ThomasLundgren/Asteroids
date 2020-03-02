package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class SwingGUI implements UI {

	private final JFrame frame = new JFrame();
	private final BackgroundPanel backgroundPanel;

	public SwingGUI(ImageLoader<ImageAdapter> imageLoader) {
		backgroundPanel = new BackgroundPanel(imageLoader);
		frame.add(backgroundPanel);
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
