package se.hig.thlu.asteroids.graphics.drawer.entitydrawer;

import se.hig.thlu.asteroids.graphics.drawer.Drawer;
import se.hig.thlu.asteroids.graphics.font.FontAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Draws a List of Images from first to last. When to last Image has been drawn,
 * the {@code isFinished} flag is set to true. The {@code AnimationDrawer} can
 * then safely be discarded.
 */
public class AnimationDrawer implements Drawer {

	private final List<ImageAdapter> images = new ArrayList<>(4);
	private final Point center;
	private final int framesPerImage;
	private boolean isFinished = false;
	private int counter = 0;
	private int currentImageIndex = 0;

	public AnimationDrawer(Collection<? extends ImageAdapter> images, Point center, int framesPerImage) {
		this.images.addAll(images);
		this.center = center;
		this.framesPerImage = framesPerImage;
	}

	@Override
	public void draw(GraphicsAdapter<FontAdapter, ImageAdapter> graphics) {
		if (isFinished) {
			return;
		}
		ImageAdapter currentImage = images.get(currentImageIndex);
		int width = currentImage.getWidth();
		int height = currentImage.getHeight();
		int xCorner = (int) center.getX() - width / 2;
		int yCorner = (int) center.getY() - height / 2;
		graphics.drawImage(currentImage, xCorner, yCorner, width, height);
		nextFrame();
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	private void nextFrame() {
		counter++;
		if (counter == framesPerImage) {
			counter = 0;
			if (currentImageIndex < images.size() - 1) {
				currentImageIndex++;
			} else {
				isFinished = true;
			}
		}
	}


}
