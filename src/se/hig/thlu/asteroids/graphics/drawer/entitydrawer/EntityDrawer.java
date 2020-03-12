package se.hig.thlu.asteroids.graphics.drawer.entitydrawer;

import se.hig.thlu.asteroids.graphics.drawer.Drawer;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingStrategy;
import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.entity.EntityProperty;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObserver;

public class EntityDrawer implements Drawer, IObserver {

	protected final DrawingParameters drawingParameters;
	protected DrawingStrategy drawingStrategy;
	protected boolean isFinished = false;

	public EntityDrawer(DrawingStrategy drawingStrategy, DrawingParameters initialParameters) {
		this.drawingStrategy = drawingStrategy;
		drawingParameters = initialParameters;
	}

	protected EntityDrawer(DrawingParameters drawingParameters) {
		this.drawingParameters = drawingParameters;
	}

	protected final void setDrawingStrategy(DrawingStrategy drawingStrategy) {
		this.drawingStrategy = drawingStrategy;
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		if (isFinished) {
			return;
		}
		drawingStrategy.draw(graphics, drawingParameters);
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	public Dim getDimensions() {
		return drawingParameters.getDimensions();
	}

	@Override
	public void notify(String propertyName, Event event) {
		Object value = event.getValue();
		int width = drawingParameters.getDimensions().getWidth();
		int height = drawingParameters.getDimensions().getHeight();
		if (propertyName.equals(EntityProperty.CENTER.toString())) {
			drawingParameters.setCenter((Point) value);
		} else if (propertyName.equals(EntityProperty.ROTATION.toString())) {
			drawingParameters.setRotation((double) value);
		} else if (propertyName.equals(EntityProperty.WIDTH.toString())) {
			drawingParameters.setDimensions(new Dim((int) value, height));
		} else if (propertyName.equals(EntityProperty.HEIGHT.toString())) {
			drawingParameters.setDimensions(new Dim(width, (int) value));
		} else if (propertyName.equals(EntityProperty.IS_DESTROYED.toString())) {
			isFinished = (boolean) value;
		}
	}
}
