package se.hig.thlu.asteroids.model;

import java.beans.*;

public abstract class Entity {

	public enum EntityProperty {

		CENTER("CENTER"), IS_DESTROYED("IS_DESTROYED");

		private String propertyName;

		EntityProperty(String propertyName) {
			this.propertyName = propertyName;
		}

		public String getPropertyName() {
			return propertyName;
		}
	}

	protected Point center;
	protected Velocity velocity;
	protected boolean isDestroyed = false;
	protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	protected Entity(Point center, Velocity velocity) {
		this.velocity = velocity;
		this.center = center;
	}

	protected Entity(Point center, Velocity velocity, boolean isDestroyed) {
		this.center = center;
		this.velocity = velocity;
		this.isDestroyed = isDestroyed;
	}

	public final Point getCenter() {
		return center;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void setCenter(Point center) {
		this.center = center;
		notifyListeners(EntityProperty.CENTER.getPropertyName(), center);
	}

	protected void notifyListeners(String propertyName, Object newValue) {
		changeSupport.firePropertyChange(propertyName, null, newValue);
	}

	public final Velocity getVelocity() {
		return velocity;
	}

	protected final void setVelocity(Velocity velocity) {
		this.velocity = velocity;
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
}
