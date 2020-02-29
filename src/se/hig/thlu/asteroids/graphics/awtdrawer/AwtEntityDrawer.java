package se.hig.thlu.asteroids.graphics.awtdrawer;

import se.hig.thlu.asteroids.model.Entity.*;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.storage.*;

import java.awt.*;
import java.beans.*;

public abstract class AwtEntityDrawer implements PropertyChangeListener {

	protected final ImageLoader imageLoader;
	protected Image playerShipImage;
	protected double angle, x, y;

	protected AwtEntityDrawer(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	abstract void draw(Graphics2D g2d);

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EntityProperty.CENTER.getPropertyName())) {
			Point center = (Point) evt.getNewValue();
			x = center.getX();
			y = center.getY();
		}
	}

}
