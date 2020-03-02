package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.storage.ImageLoader;

public final class Missile extends Entity {

	private final MissileSource missileSource;
	private ImageAdapter missileSprite;
	private final Point startingPosition;

	Missile(Point position, double direction, MissileSource source, ImageLoader<ImageAdapter> imageLoader) {
		super(position,
				new Velocity(source.getMissileSpeed(),
						direction),
				0.0,
				imageLoader);
		startingPosition = position;
		missileSource = source;
	}

	@Override
	protected void loadImages() {
		switch (missileSource) {
			case PLAYER:
				missileSprite = imageLoader.getImageResource(ImageLoader.ImageResource.MISSILE_PLAYER);
				break;
			case ENEMY:
				missileSprite = imageLoader.getImageResource(ImageLoader.ImageResource.MISSILE_ENEMY);
				break;
		}
	}

	@Override
	protected void setWidth() {
		width = missileSprite.getWidth();
	}

	@Override
	protected void setHeight() {
		height = missileSprite.getHeight();
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		int cornerX = (int) center.getX() - width / 2;
		int cornerY = (int) center.getY() - height / 2;
		graphics.drawImageWithRotation(missileSprite,
				facingDirection,
				center.getX(),
				center.getY(),
				(int) cornerX,
				(int) cornerY);
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		if (center.distanceTo(startingPosition) > 0.6 * GameConfig.WINDOW_WIDTH) {
			collide();
		}
	}

	public enum MissileSource {
		PLAYER(1.0), ENEMY(5.0);

		final double missileSpeed;

		MissileSource(double missileSpeed) {
			this.missileSpeed = missileSpeed;
		}

		private double getMissileSpeed() {
			return missileSpeed;
		}
	}
}