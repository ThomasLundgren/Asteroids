package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.mathutil.Trigonometry;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Entity {

	protected final double turningDegree;
	protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	protected Point center;
	protected Velocity velocity;
	protected boolean isDestroyed = false;
	protected double facingDirection = 0.0;
	// TODO: Store size information in here?

	// TODO: subclasses have hard-coded turning degrees
	protected Entity(Point center, Velocity velocity, double turningDegree) {
		this.velocity = velocity;
		this.center = center;
		this.turningDegree = turningDegree;
	}

	public final Point getCenter() {
		return new Point(center.getX(), center.getY());
	}

	public void setCenter(Point center) {
		this.center = center;
		notifyListeners(EntityProperty.CENTER.getPropertyName(), center);
	}

	protected void turnLeft() {
		facingDirection -= turningDegree;
		facingDirection = Trigonometry.normalizeDegree(facingDirection);
		notifyListeners(EntityProperty.FACING_DIRECTION.getPropertyName(), facingDirection);
	}

	protected void turnRight() {
		facingDirection += turningDegree;
		facingDirection = Trigonometry.normalizeDegree(facingDirection);
		notifyListeners(EntityProperty.FACING_DIRECTION.getPropertyName(), facingDirection);
	}

	public double getFacingDirection() {
		return facingDirection;
	}


	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	protected void notifyListeners(String propertyName, Object newValue) {
		changeSupport.firePropertyChange(propertyName, null, newValue);
	}

	public final Velocity getVelocity() {
		return new Velocity(velocity.getSpeed(), velocity.getDirection());
	}

	public final boolean isDestroyed() {
		return isDestroyed;
	}

	public void collide() {
		isDestroyed = true;
		notifyListeners(EntityProperty.IS_DESTROYED.getPropertyName(), true);
	}

	public void updatePosition() {
		double diffX = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		double diffY = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		Point newPos = new Point(center.getX() + diffX, center.getY() + diffY);
		setCenter(newPos);
		notifyListeners(EntityProperty.CENTER.getPropertyName(), center);
	}

	public enum EntityProperty {

		CENTER("CENTER"),
		FACING_DIRECTION("FACING_DIRECTION"),
		IS_DESTROYED("IS_DESTROYED");

		private String propertyName;

		EntityProperty(String propertyName) {
			this.propertyName = propertyName;
		}

		public String getPropertyName() {
			return propertyName;
		}
	}
}
