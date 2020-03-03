package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.AwtGraphicsAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Entity;
import se.hig.thlu.asteroids.storage.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public final class BackgroundPanel extends JPanel {

	private ImageAdapter image;
	private Collection<Entity> entities = new ArrayList<>(0);

	public BackgroundPanel(ImageLoader<? extends ImageAdapter> imageLoader) {
		setImage(imageLoader.getImageResource(ImageLoader.ImageResource.BACKGROUND_PNG));
		setLayout(new BorderLayout());
	}

	public void setImage(ImageAdapter image) {
		this.image = image;
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(), image.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		GraphicsAdapter graphics = new AwtGraphicsAdapter(g2d);

		Dimension dim = getSize();
		graphics.drawImage(image, 0, 0, dim.width, dim.height);

		entities.forEach(entity -> entity.draw(graphics));

		repaint();
	}

	public void setEntities(Collection<Entity> entities) {
		this.entities = entities;
		repaint();
	}
}