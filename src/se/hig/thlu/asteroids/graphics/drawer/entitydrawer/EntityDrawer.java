package se.hig.thlu.asteroids.graphics.drawer.entitydrawer;

import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.event.IObserver;
import se.hig.thlu.asteroids.event.entity.DestroyedEvent;
import se.hig.thlu.asteroids.event.entity.MoveEvent;
import se.hig.thlu.asteroids.event.entity.RotationEvent;
import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.graphics.drawer.Drawer;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingStrategy;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;

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
	public void notify(Event event) {
		Object value = event.getValue();
		if (event.toString().equals(MoveEvent.class.toString())) {
			drawingParameters.setCenter((Point) value);
		} else if (event.toString().equals(RotationEvent.class.toString())) {
			drawingParameters.setRotation((double) value);
		} else if (event.toString().equals(DestroyedEvent.class.toString())) {
			isFinished = true;
		}
	}
}
