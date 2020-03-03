package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;

public class Explosion extends Entity {

	private ImageAdapter one, two, three, four, activeSprite;
	private ExplosionSize currentSize = ExplosionSize.ONE;
	private ExplosionSize maxSize;
	private int counter;

	public Explosion(Point center, ImageLoader<? extends ImageAdapter> imageLoader, ExplosionSize size) {
		super(center, new Velocity(0.0, 0.0), 0.0, imageLoader);
		this.maxSize = size;
		loadImages(imageLoader);
		setWidth();
		setHeight();
	}

	@Override
	protected void loadImages(ImageLoader<? extends ImageAdapter> imageLoader) {
		one = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_1);
		two = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_2);
		three = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_3);
		four = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_4);
		activeSprite = one;
	}

	@Override
	protected void setWidth() {
		width = activeSprite.getWidth();
	}

	@Override
	protected void setHeight() {
		height = activeSprite.getHeight();
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics) {
		int xCorner = (int) center.getX() - width / 2;
		int yCorner = (int) center.getY() - height / 2;
		graphics.drawImage(activeSprite,
				xCorner,
				yCorner,
				width,
				height);
		// TODO: This is not a good solution
		counter++;
		System.out.println("Explosion drawn. " + toString());
		if (counter >= GameConfig.EXPLOSION_TRANSITION_FRAMES) {
			nextImage();
			counter = 0;
		}
	}

	@Override
	public String toString() {
		return "Explosion{" +
				", currentSize=" + currentSize +
				", counter=" + counter +
				'}';
	}

	private void nextImage() {
		if (currentSize == maxSize) {
			isDestroyed = true;
			return;
		}
		switch (currentSize) {
			case ONE:
				currentSize = ExplosionSize.TWO;
				setActiveSprite(two);
				break;
			case TWO:
				currentSize = ExplosionSize.THREE;
				setActiveSprite(three);
				break;
			case THREE:
				currentSize = ExplosionSize.FOUR;
				setActiveSprite(four);
				break;
			case FOUR:
				break;
		}
	}

	private void setActiveSprite(ImageAdapter sprite) {
		activeSprite = sprite;
		setWidth();
		setHeight();
	}

	public enum ExplosionSize {
		ONE, TWO, THREE, FOUR;
	}
}
