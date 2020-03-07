package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingStrategy;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.EntityProperty;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObserver;

public class EntityDrawer implements Drawer, IObserver {

	protected DrawingStrategy drawingStrategy;
	protected final DrawingParameters drawingParameters;
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

	public int getWidth() {
		return drawingParameters.getWidth();
	}

	public int getHeight() {
		return drawingParameters.getHeight();
	}

	@Override
	public void onNotify(String propertyName, Event event) {
		Object value = event.getValue();
		if (propertyName.equals(EntityProperty.CENTER.toString())) {
			drawingParameters.setCenter((Point) value);
		} else if (propertyName.equals(EntityProperty.ROTATION.toString())) {
			drawingParameters.setRotation((double) value);
		} else if (propertyName.equals(EntityProperty.WIDTH.toString())) {
			drawingParameters.setWidth((int) value);
		} else if (propertyName.equals(EntityProperty.HEIGHT.toString())) {
			drawingParameters.setHeight((int) value);
		} else if (propertyName.equals(EntityProperty.IS_DESTROYED.toString())) {
			isFinished = (boolean) value;
		}
	}
}
