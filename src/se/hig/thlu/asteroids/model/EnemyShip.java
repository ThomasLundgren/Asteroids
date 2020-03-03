package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Missile.MissileSource;
import se.hig.thlu.asteroids.storage.ImageLoader;

import java.util.concurrent.ThreadLocalRandom;

public final class EnemyShip extends Entity implements Shooter {

	private static final double ENEMY_SHIP_SPEED = 4.0;
	private ImageAdapter shipSprite;

	private EnemyShip(ImageLoader<? extends ImageAdapter> imageLoader) {
		super(new Point(0.0, 0.0),
				new Velocity(ENEMY_SHIP_SPEED, (double) ThreadLocalRandom.current().nextInt(3) * 180.0),
				0.0,
				imageLoader);
		loadImages(imageLoader);
		setWidth();
		setHeight();
	}

	public static EnemyShip createEnemyShip(ImageLoader<? extends ImageAdapter> imageLoader) {
		return new EnemyShip(imageLoader);
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics) {
		graphics.drawImage(shipSprite,
				(int) center.getX(),
				(int) center.getY(),
				width,
				height);
	}

	@Override
	public Missile shoot(double direction) {
		return new Missile(center, direction, MissileSource.ENEMY, imageLoader);
	}

	@Override
	protected void loadImages(ImageLoader<? extends ImageAdapter> imageLoader) {
		shipSprite = imageLoader.getImageResource(ImageLoader.ImageResource.ENEMY_SHIP_SMALL);
	}

	@Override
	protected void setWidth() {
		width = shipSprite.getWidth();
	}

	@Override
	protected void setHeight() {
		height = shipSprite.getHeight();
	}

}
