package se.hig.thlu.asteroids.graphics.drawer.entitydrawer;

import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.event.entity.AccelerationEvent;
import se.hig.thlu.asteroids.event.entity.DestroyedEvent;
import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.AbstractDrawingStrategy;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingParameters;

public class AccelerationDrawer extends EntityDrawerDecorator {

	private boolean isAccelerating = false;

	public AccelerationDrawer(DrawingParameters initialParameters, EntityDrawer decoratee) {
		super(initialParameters, decoratee);
		setDrawingStrategy(new AccelerationDrawingStrategy(decoratee));
	}

	@Override
	public void notify(Event event) {
		if (!(event instanceof DestroyedEvent)) {
			super.notify(event);
		}
		if (event.toString().equals(AccelerationEvent.class.toString())) {
			isAccelerating = (boolean) event.getValue();
		}
	}

	private class AccelerationDrawingStrategy extends AbstractDrawingStrategy {
		private final EntityDrawer decoratee;

		private AccelerationDrawingStrategy(EntityDrawer decoratee) {
			this.decoratee = decoratee;
		}

		@Override
		public void draw(GraphicsAdapter<ImageAdapter> graphics,
						 DrawingParameters drawingParameters) {
			setParameters(drawingParameters);
			if (isAccelerating) {
				int xCorner = (int) x - decoratee.getDimensions().getWidth() / 2 - dimensions.getWidth() / 2 + 1;
				int yCorner = (int) y - dimensions.getHeight() / 2;
				graphics.drawImageWithRotation(image,
						rotation,
						(double) x,
						(double) y,
						xCorner,
						yCorner);
			}
		}
	}
}
