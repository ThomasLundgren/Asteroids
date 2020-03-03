package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;

public class Explosion extends Entity {

	private ImageAdapter one, two, three, four;
	private ExplosionSize size;

	public Explosion(Point center, Velocity velocity,
				ImageLoader<? extends ImageAdapter> imageLoader, ExplosionSize size) {
		super(center, velocity, 0.0, imageLoader);
		this.size = size;
		loadImages(imageLoader);
		setWidth();
		setHeight();
	}

	@Override
	protected void loadImages(ImageLoader<? extends ImageAdapter> imageLoader) {
//		one = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_1);
//		two = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_2);
//		three = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_3);
//		four = imageLoader.getImageResource(ImageLoader.ImageResource.EXPLOSION_4);
	}

	@Override
	protected void setWidth() {
		// TODO: Depends on active image
	}

	@Override
	protected void setHeight() {
		// TODO: Depends on active image
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics) {
		// TODO
	}

	public enum ExplosionSize {
		ONE, TWO, THREE, FOUR;
	}
}
