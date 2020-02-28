package se.hig.thlu.asteroids.graphics.model;

import se.hig.thlu.asteroids.graphics.renderer.*;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.model.Entity.EntityProperty;
import se.hig.thlu.asteroids.storage.*;

import java.beans.*;

public abstract class EntityDrawer implements PropertyChangeListener {

	protected final ImageLoader imageLoader;
	protected final IRenderer renderer;
	protected IImage playerShipImage;
	protected double angle, anchorX, anchorY;
	protected int x, y;

	protected EntityDrawer(ImageLoader imageLoader, IRenderer renderer) {
		this.imageLoader = imageLoader;
		this.renderer = renderer;
	}

	abstract void draw(IRenderer renderer);

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EntityProperty.CENTER.getPropertyName())) {
			Point center = (Point) evt.getNewValue();
			x = (int) center.getX();
			y = (int) center.getY();
		}
		onPropertyChange(evt);
		draw(renderer);
	}

	protected abstract void onPropertyChange(PropertyChangeEvent evt);
}
