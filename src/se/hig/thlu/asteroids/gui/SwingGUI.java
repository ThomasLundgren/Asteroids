package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.model.Entity;
import se.hig.thlu.asteroids.storage.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class SwingGUI extends JFrame implements GUI<AwtKeyboardAdapter> {

	private final ImageLoader<? extends ImageAdapter> imageLoader;
	private BackgroundPanel backgroundPanel;

	public SwingGUI(ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		backgroundPanel = new BackgroundPanel(imageLoader);
		add(backgroundPanel);
		configureFrame();
	}

	@Override
	public void addEventListener(AwtKeyboardAdapter listener) {
		addKeyListener(listener);
	}

	@Override
	public void addEntities(Collection<Entity> entities) {
		backgroundPanel.addEntities(entities);
	}

	@Override
	public void removeEntity(Entity entity) {
		backgroundPanel.removeEntity(entity);
	}

	@Override
	public void addEntity(Entity entity) {
		backgroundPanel.addEntity(entity);
//		repaint(); // TODO: Not needed?
	}

	private void configureFrame() {
		setResizable(false);
		setSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
