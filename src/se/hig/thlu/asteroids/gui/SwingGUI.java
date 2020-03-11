package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;

import javax.swing.*;

public class SwingGUI extends JFrame implements GUI<AwtKeyboardAdapter> {

	private final AbstractImageLoader<? extends ImageAdapter> imageLoader;
	private BackgroundPanel backgroundPanel;

	public SwingGUI(AbstractImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		backgroundPanel = new BackgroundPanel(imageLoader);
		add(backgroundPanel);
		configureFrame();
	}

	@Override
	public void addEventListener(AwtKeyboardAdapter listener) {
		addKeyListener(listener);
	}

	private void configureFrame() {
		setResizable(false);
		setSize(new java.awt.Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	@Override
	public void notify(String propertyName, Event event) {

	}
}
