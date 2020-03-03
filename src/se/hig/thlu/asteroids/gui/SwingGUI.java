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
//	private Collection<Entity> entities = new ArrayList<>(0);
	private BackgroundPanel backgroundPanel;

	public SwingGUI(ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		backgroundPanel = new BackgroundPanel(imageLoader);
		add(backgroundPanel);
		configureFrame();
	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
//		Graphics2D g2d = (Graphics2D) g;
//		GraphicsAdapter graphics = new AwtGraphicsAdapter(g2d);
//		render(graphics);
	}

	@Override
	public void addEventListener(AwtKeyboardAdapter listener) {
		addKeyListener(listener);
	}

//	@Override
//	public void render(GraphicsAdapter<ImageAdapter> graphics) {
//		entities.forEach(entity -> entity.draw(graphics));
//	}

	@Override
	public void setEntities(Collection<Entity> entities) {
//		this.entities = entities;
		backgroundPanel.setEntities(entities);
		repaint();
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
