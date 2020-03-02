package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Entity.EntityProperty;
import se.hig.thlu.asteroids.model.Point;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class AwtEntityDrawer implements PropertyChangeListener {

	// TODO: Shouldn't have a reference to ImageLoader? Should just have an ImageAdapter?
	protected double angle = 0.0, x = 0.0, y = 0.0;

	// TODO: Understand generics.......?
	abstract void draw(GraphicsAdapter<ImageAdapter> graphics);

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EntityProperty.CENTER.getPropertyName())) {
			Point center = (Point) evt.getNewValue();
			x = center.getX();
			y = center.getY();
		}
	}

}
