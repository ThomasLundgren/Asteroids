package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Entity.EntityProperty;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class AwtEntityDrawer implements PropertyChangeListener {

	protected final ImageLoaderAwt imageLoader;
	protected AwtImageAdapter sprite;
	protected double angle, x, y;

	protected AwtEntityDrawer(ImageLoaderAwt imageLoader) {
		this.imageLoader = imageLoader;
	}

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
