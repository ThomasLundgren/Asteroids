package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.mathutil.Trigonometry;
import se.hig.thlu.asteroids.model.Missile.MissileSource;
import se.hig.thlu.asteroids.storage.ImageLoader;
import se.hig.thlu.asteroids.storage.ImageLoader.ImageResource;

import java.util.Optional;

public final class PlayerShip extends Entity implements Shooter {

	private static final double MAX_SPEED = 7.0;
	private static final double ACCELERATION = 0.04;
	private static final double DECELERATION = ACCELERATION / 2.5;
	private ImageAdapter shipSprite;
	private ImageAdapter accelerationSprite;
	private int lives = 3;
	private boolean isAccelerating = false;

	private PlayerShip(ImageLoader<? extends ImageAdapter> imageLoader) {
		super(new Point(0.0, 0.0),
				new Velocity(),
				5.0,
				imageLoader);
		loadImages(imageLoader);
		setWidth();
		setHeight();
	}

	public static PlayerShip createPlayerShip(ImageLoader<? extends ImageAdapter> imageLoader) {
		return new PlayerShip(imageLoader);
	}

	public void accelerate() {
		isAccelerating = true;
		Velocity acceleration = new Velocity(ACCELERATION, rotation);
		velocity.composeWith(acceleration);
		if (velocity.getSpeed() >= MAX_SPEED) {
			velocity = new Velocity(MAX_SPEED, velocity.getDirection());
		}
	}

	public void decelerate() {
		isAccelerating = false;
		if (velocity.getSpeed() < DECELERATION) {
			velocity = new Velocity(0.0, velocity.getDirection());
			return;
		}
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics) {
		// TODO: Fix magic numbers
		int xCorner = (int) center.getX() - width / 2;
		int yCorner = (int) center.getY() - height / 2;
		graphics.drawImageWithRotation(shipSprite,
				rotation,
				center.getX(),
				center.getY(),
				xCorner,
				yCorner);
		if (isAccelerating) {
			xCorner -= accelerationSprite.getWidth() - 1;
			graphics.drawImageWithRotation(accelerationSprite,
					rotation,
					center.getX(),
					center.getY(),
					xCorner,
					yCorner);
		}
	}

	@Override
	public void turnLeft() {
		super.turnLeft();
	}

	@Override
	public void turnRight() {
		super.turnRight();
	}

	@Override
	public Optional<Explosion> collide() {
		if (lives == 1) {
			isDestroyed = true;
			lives--;
		} else {
			velocity = new Velocity();
			lives--;
		}
		return Optional.of(new Explosion(center,
				imageLoader,
				Explosion.ExplosionSize.FOUR));
	}

	//TODO: remove direction argument from missile
	@Override
	public Missile shoot(double direction) {
		double centerToFrontDistance = (double) (width / 2);
		Point missileStart = Trigonometry.rotateAroundPoint(center,
				rotation,
				centerToFrontDistance);
		return new Missile(missileStart, rotation, MissileSource.PLAYER, imageLoader);
	}

	@Override
	protected void loadImages(ImageLoader<? extends ImageAdapter> imageLoader) {
		shipSprite = imageLoader.getImageResource(ImageResource.PLAYER_SHIP_PNG);
		accelerationSprite = imageLoader.getImageResource(ImageResource.PLAYER_SHIP_ACCELERATION_PNG);
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