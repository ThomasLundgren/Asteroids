package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.mathutil.Trigonometry;
import se.hig.thlu.asteroids.storage.ImageLoader;

public abstract class Entity {

	protected final double turningDegree;
	protected ImageLoader<? extends ImageAdapter> imageLoader;
	protected Point center;
	protected Velocity velocity;
	protected boolean isDestroyed = false;
	protected double facingDirection = 0.0;
	protected int width, height;

	// TODO: subclasses have hard-coded turning degrees
	protected Entity(Point center, Velocity velocity, double turningDegree,
					 ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		this.velocity = velocity;
		this.center = center;
		this.turningDegree = turningDegree;
	}

	protected abstract void loadImages(ImageLoader<? extends ImageAdapter> imageLoader);

	protected abstract void setWidth();

	protected abstract void setHeight();

	public final Point getCenter() {
		return new Point(center.getX(), center.getY());
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public abstract void draw(GraphicsAdapter<? super ImageAdapter> graphics);

	protected void turnLeft() {
		setFacingDirection(facingDirection - turningDegree);
	}

	protected void turnRight() {
		setFacingDirection(facingDirection + turningDegree);
	}

	protected void setFacingDirection(double direction) {
		facingDirection = Trigonometry.normalizeDegree(direction);
	}

	public double getFacingDirection() {
		return facingDirection;
	}

	public final Velocity getVelocity() {
		return new Velocity(velocity.getSpeed(), velocity.getDirection());
	}

	public final boolean isDestroyed() {
		return isDestroyed;
	}

	public void collide() {
		isDestroyed = true;
	}

	public void updatePosition() {
		double diffX = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		double diffY = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		Point newPos = new Point(center.getX() + diffX, center.getY() + diffY);
		setCenter(newPos);
	}

}
