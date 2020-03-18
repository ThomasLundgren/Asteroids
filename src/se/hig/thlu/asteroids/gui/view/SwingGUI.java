package se.hig.thlu.asteroids.gui.view;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.gui.eventlisteneradapter.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;

import javax.swing.*;

public class SwingGUI extends JFrame implements GUI<AwtKeyboardAdapter> {

	private BackgroundPanel backgroundPanel;

	public SwingGUI(AbstractImageLoader<? extends ImageAdapter> imageLoader) {
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

}
