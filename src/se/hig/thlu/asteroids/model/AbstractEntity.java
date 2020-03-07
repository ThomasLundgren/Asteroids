package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.mathutil.Trigonometry;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractEntity implements Entity {

	protected final double turningDegree;
	protected Collection<IObserver> observers = new ArrayList<>(1);
	protected final UUID id = UUID.randomUUID();
	// TODO: Extract the three rows below into separate object Position or smth?
	protected Point center;
	protected double rotation = 0.0;
	protected int width, height;
	protected Velocity velocity;
	protected boolean isDestroyed = false;

	protected AbstractEntity(Point center, Velocity velocity, double turningDegree, int width, int height) {
		this.velocity = velocity;
		this.center = center;
		this.turningDegree = turningDegree;
		this.width = width;
		this.height = height;
	}

	public UUID getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public final Point getCenter() {
		return new Point(center.getX(), center.getY());
	}

	public void setCenter(Point center) {
		this.center = center;
		notifyObservers(EntityProperty.CENTER, center);
	}

	protected void notifyObservers(EntityProperty property, Object newValue) {
		observers.forEach(o -> {
			Event event = new Event(newValue);
			o.onNotify(property.toString(), event);
		});
	}

	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

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
		notifyObservers(EntityProperty.ROTATION, rotation);
	}

	public final boolean isDestroyed() {
		return isDestroyed;
	}

	public Optional<Explosion> destroy() {
		isDestroyed = true;
		observers = new ArrayList<>(1);
		return Optional.empty();
	}

	public void update() {
		double diffX = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		double diffY = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		Point newPos = new Point(center.getX() + diffX, center.getY() + diffY);
		setCenter(newPos);
		handleOverflow();
	}

	public boolean intersectsWith(Entity other) {
		int otherWidth = other.getWidth();
		int otherHeight = other.getHeight();
		int thisX = (int) getCenter().getX() - width / 2;
		int thisY = (int) getCenter().getY() - height / 2;
		int otherX = (int) other.getCenter().getX() - otherWidth / 2;
		int otherY = (int) other.getCenter().getY() - otherHeight / 2;

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

		Point newCenter = null;

		if (x - halfWidth > GameConfig.WINDOW_WIDTH) {
			newCenter = new Point(0.0, (double) y);
		}
		if (x + halfWidth < 0) {
			newCenter = new Point((double) GameConfig.WINDOW_WIDTH, (double) y);
		}
		if (y - halfHeight > GameConfig.WINDOW_HEIGHT) {
			newCenter = new Point((double) x, 0.0);
		}
		if (y + halfHeight < 0) {
			newCenter = new Point((double) x, (double) GameConfig.WINDOW_HEIGHT);
		}
		if (newCenter != null) {
			setCenter(newCenter);
		}
	}
}
