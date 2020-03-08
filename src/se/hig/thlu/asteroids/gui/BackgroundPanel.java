package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.graphics.entitydrawer.Drawer;
import se.hig.thlu.asteroids.graphics.graphicsadapter.AwtGraphicsAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;
import se.hig.thlu.asteroids.storage.ImageResource;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BackgroundPanel extends JPanel {

	private final Collection<Drawer> drawers = new CopyOnWriteArrayList<>();
	private ImageAdapter image;

	public BackgroundPanel(AbstractImageLoader<? extends ImageAdapter> imageLoader) {
		setImage(imageLoader.getImage(ImageResource.BACKGROUND_PNG));
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

		drawers.forEach(drawer -> {
			drawer.draw(graphics);
			if (drawer.isFinished()) {
				drawers.remove(drawer);
			}
		});
		repaint();
	}

	public void addDrawer(Drawer drawer) {
		drawers.add(drawer);
	}

}