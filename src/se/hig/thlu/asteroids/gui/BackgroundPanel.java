package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.graphics.entitydrawer.AnimationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.Drawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.EntityDrawer;
import se.hig.thlu.asteroids.graphics.graphicsadapter.AwtGraphicsAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BackgroundPanel extends JPanel {

	private AwtImageAdapter image;
	private final Map<UUID, Drawer> drawers = new ConcurrentHashMap<>(100);
	private final Collection<AnimationDrawer> animationDrawers = new CopyOnWriteArrayList<>();

	public BackgroundPanel(ImageLoader<AwtImageAdapter> imageLoader) {
		setImage(imageLoader.getImageResource(ImageLoader.ImageResource.BACKGROUND_PNG));
		setLayout(new BorderLayout());
	}

	public void setImage(AwtImageAdapter image) {
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

		drawers.forEach((id, drawer) -> drawer.draw(graphics));
		animationDrawers.forEach(aniDrawer -> {
			if (aniDrawer.isFinished()) {
				animationDrawers.remove(aniDrawer);
			} else {
				aniDrawer.draw(graphics);
			}
		});
		repaint();
	}

	public void addAnimationDrawer(AnimationDrawer animationDrawer) {
		animationDrawers.add(animationDrawer);
	}

	public void addEntityDrawer(UUID id, EntityDrawer entityDrawer) {
		drawers.put(id, entityDrawer);
	}

	public void removeEntityDrawer(UUID id) {
		Drawer er = drawers.remove(id);
	}

}