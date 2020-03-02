package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Missile.MissileSource;
import se.hig.thlu.asteroids.storage.ImageLoader;
import se.hig.thlu.asteroids.storage.ImageLoader.ImageResource;

public final class PlayerShip extends Entity implements Shooter {

	private static final double MAX_SPEED = 7.0;
	private static final double ACCELERATION = 0.05;
	private static final double DECELERATION = ACCELERATION / 2.5;
	private ImageAdapter shipSprite ;
	private ImageAdapter accelerationSprite;
	private int lives = 3;
	private boolean isAccelerating = false;

	private PlayerShip(ImageLoader<ImageAdapter> imageLoader) {
		super(new Point(0.0, 0.0),
				new Velocity(0.0, 0.0),
				5.0,
				imageLoader);
	}

	public static PlayerShip createPlayerShip(ImageLoader<ImageAdapter> imageLoader) {
		return new PlayerShip(imageLoader);
	}

	public void accelerate() {
		if (velocity.getSpeed() >= MAX_SPEED) {
			velocity = new Velocity(MAX_SPEED, velocity.getDirection());
			return;
		}
		Velocity acceleration = new Velocity(ACCELERATION, facingDirection);
		velocity.composeWith(acceleration);
		isAccelerating = true;
	}

	public void decelerate() {
		if (velocity.getSpeed() < DECELERATION) {
			velocity = new Velocity(0.0, velocity.getDirection());
			return;
		}
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
		isAccelerating = false;
	}

	@Override
	protected void loadImages() {
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

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		int xCorner = (int) center.getX() - width / 2;
		int yCorner = (int) center.getY() - height / 2;
		graphics.drawImageWithRotation(shipSprite,
				facingDirection,
				center.getX(),
				center.getY(),
				xCorner,
				yCorner);
		if (isAccelerating) {
			xCorner -= accelerationSprite.getWidth();
			yCorner -= accelerationSprite.getHeight();
			double anchorX = 0.0;
			double anchorY = 0.0;
			graphics.drawImageWithRotation(shipSprite,
					facingDirection,
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
	public void collide() {
		if (lives == 1) {
			super.collide();
		} else {
			lives--;
		}
//		notifyListeners(LIVES.getPropertyName(), lives);
	}

	//TODO: remove direction argument from missile
	@Override
	public Missile shoot(double direction) {
		return new Missile(center, velocity.getDirection(), MissileSource.PLAYER, imageLoader);
	}

//	public enum PlayerShipProperty {
//
//		LIVES("LIVES"),
//		IS_ACCELERATING("IS_ACCELERATING");
//
//		private String propertyName;
//
//		PlayerShipProperty(String propertyName) {
//			this.propertyName = propertyName;
//		}
//
//		public String getPropertyName() {
//			return propertyName;
//		}
//	}

}