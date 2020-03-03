package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Missile.MissileSource;
import se.hig.thlu.asteroids.storage.ImageLoader;

public final class EnemyShip extends Entity implements Shooter {

	private static final double ENEMY_SHIP_SPEED = 2.0;
	private ImageAdapter shipSprite;

	private EnemyShip(ImageLoader<? extends ImageAdapter> imageLoader, double direction) {
		super(new Point(0.0, 0.0),
				new Velocity(ENEMY_SHIP_SPEED, direction),
				0.0,
				imageLoader);
		loadImages(imageLoader);
		setWidth();
		setHeight();
	}

	public static EnemyShip createEnemyShip(ImageLoader<? extends ImageAdapter> imageLoader, double direction) {
		return new EnemyShip(imageLoader, direction);
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
