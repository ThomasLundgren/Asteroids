package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.mathutil.Trigonometry;
import se.hig.thlu.asteroids.storage.ImageLoader;

import java.util.Optional;

public abstract class Entity {

	protected final double turningDegree;
	protected ImageLoader<? extends ImageAdapter> imageLoader;
	protected Point center;
	protected Velocity velocity;
	protected boolean isDestroyed = false;
	protected double facingDirection = 0.0;
	protected int width, height;

	protected Entity(Point center, Velocity velocity, double turningDegree,
					 ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		this.velocity = velocity;
		this.center = center;
		this.turningDegree = turningDegree;
	}

	protected abstract void loadImages(ImageLoader<? extends ImageAdapter> imageLoader);

	protected abstract void setWidth();

	public int getWidth() {
		return width;
	}

	protected abstract void setHeight();

	public int getHeight() {
		return height;
	}

	public final Point getCenter() {
		return new Point(center.getX(), center.getY());
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	// TODO: Replace this method with an injected DrawingStrategy. The DrawingStrategy can then be passed to the UI
	//  so that the UI has no knowledge of Entity objects
	public abstract void draw(GraphicsAdapter<? super ImageAdapter> graphics);

	protected void turnLeft() {
		setFacingDirection(facingDirection - turningDegree);
	}

	protected void turnRight() {
		setFacingDirection(facingDirection + turningDegree);
	}

	public double getFacingDirection() {
		return facingDirection;
	}

	protected void setFacingDirection(double direction) {
		facingDirection = Trigonometry.normalizeDegree(direction);
	}

	public final Velocity getVelocity() {
		return new Velocity(velocity.getSpeed(), velocity.getDirection());
	}

	public final boolean isDestroyed() {
		return isDestroyed;
	}

	public Optional<Explosion> collide() {
		isDestroyed = true;
		return Optional.empty();
	}

	public void updatePosition() {
		double diffX = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		double diffY = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		Point newPos = new Point(center.getX() + diffX, center.getY() + diffY);
		setCenter(newPos);
	}

	public boolean intersectsWith(Entity entity) {
		int otherWidth = entity.getWidth();
		int otherHeight = entity.getHeight();
		int thisX = (int) getCenter().getX() - width / 2;
		int thisY = (int) getCenter().getY() - height / 2;
		int otherX = (int) entity.getCenter().getX() - otherWidth / 2;
		int otherY = (int) entity.getCenter().getY() - otherHeight / 2;

		if (thisX < otherX + otherWidth &&
				thisX + width > otherX &&
				thisY < otherY + otherHeight &&
				thisY + height > otherY) {
			return true;
		}
		return false;
	}

}
