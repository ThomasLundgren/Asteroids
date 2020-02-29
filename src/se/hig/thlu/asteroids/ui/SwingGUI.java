package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.config.*;
import se.hig.thlu.asteroids.graphics.awtdrawer.*;
import se.hig.thlu.asteroids.storage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SwingGUI implements UI {

	private BackgroundPanel backgroundPanel;
	private final JFrame frame = new JFrame();
	private ImageLoader imageLoader;

	public SwingGUI(AwtPlayerShipDrawer playerShipDrawer) {
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
