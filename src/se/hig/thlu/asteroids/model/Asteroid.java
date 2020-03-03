package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;

import java.util.concurrent.ThreadLocalRandom;

public final class Asteroid extends Entity {

	private final AsteroidSize asteroidSize;
	private final RotationDirection rotationDirection;
	private ImageAdapter asteroidSprite;

	public Asteroid(Point position, Velocity velocity, AsteroidSize size,
				 ImageLoader<? extends ImageAdapter> imageLoader) {
		super(position,
				velocity,
				velocity.getSpeed(),
				imageLoader);
		asteroidSize = size;
		int r = ThreadLocalRandom.current().nextInt(2);
		rotationDirection = r == 0 ? RotationDirection.LEFT : RotationDirection.RIGHT;
		loadImages(imageLoader);
		setWidth();
		setHeight();
	}

	@Override
	protected void loadImages(ImageLoader<? extends ImageAdapter> imageLoader) {
		switch (asteroidSize) {
			case LARGE:
				asteroidSprite = imageLoader.getImageResource(ImageLoader.ImageResource.ASTEROID_LARGE_PNG);
				break;
			case MEDIUM:
				asteroidSprite = imageLoader.getImageResource(ImageLoader.ImageResource.ASTEROID_MEDIUM_PNG);
				break;
			case SMALL:
				asteroidSprite = imageLoader.getImageResource(ImageLoader.ImageResource.ASTEROID_SMALL_PNG);
				break;
		}
	}

	@Override
	protected void setWidth() {
		width = asteroidSprite.getWidth();
	}

	@Override
	protected void setHeight() {
		height = asteroidSprite.getHeight();
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics) {
		int cornerX = (int) center.getX() - width / 2;
		int cornerY = (int) center.getY() - height / 2;
		graphics.drawImageWithRotation(asteroidSprite,
				facingDirection,
				center.getX(),
				center.getY(),
				(int) cornerX,
				(int) cornerY);
	}

	@Override
	public void updatePosition() {
		switch (rotationDirection) {
			case LEFT:
				turnLeft();
				break;
			case RIGHT:
				turnRight();
				break;
			default:
				break;
		}
		super.updatePosition();
	}

	public AsteroidSize getAsteroidSize() {
		return asteroidSize;
	}

	private enum RotationDirection {LEFT, RIGHT}

	public enum AsteroidSize {
		LARGE(1.0, 2.0), MEDIUM(1.5, 3.0), SMALL(2.0, 4.0);

		private final double minSpeed;
		private final double maxSpeed;

		AsteroidSize(double minSpeed, double maxSpeed) {
			this.minSpeed = minSpeed;
			this.maxSpeed = maxSpeed;
		}

		public double getMaxSpeed() {
			return maxSpeed;
		}

		public double getMinSpeed() {
			return minSpeed;
		}
	}
}
