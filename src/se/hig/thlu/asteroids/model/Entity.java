package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;
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
	protected double rotation = 0.0;
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
		setRotation(rotation - turningDegree);
	}

	protected void turnRight() {
		setRotation(rotation + turningDegree);
	}

	public double getRotation() {
		return rotation;
	}

	protected void setRotation(double rotation) {
		this.rotation = Trigonometry.normalizeDegree(rotation);
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
		center = newPos;
		handleOverflow();
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

	protected void handleOverflow() {
		int x = (int) center.getX();
		int y = (int) center.getY();
		int halfWidth = width / 2;
		int halfHeight = height / 2;

		if (x - halfWidth > GameConfig.WINDOW_WIDTH) {
			center = new Point(0.0, (double) y);
		}
		if (x + halfWidth < 0) {
			center = new Point((double) GameConfig.WINDOW_WIDTH, (double) y);
		}
		if (y - halfHeight > GameConfig.WINDOW_HEIGHT) {
			center = new Point((double) x, 0.0);
		}
		if (y + halfHeight < 0) {
			center = new Point((double) x, (double) GameConfig.WINDOW_HEIGHT);
		}
	}

}
