package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.AbstractDrawingStrategy;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.entity.EntityProperty;
import se.hig.thlu.asteroids.observer.Event;

public class AccelerationDrawer extends EntityDrawerDecorator {

	private boolean isAccelerating = false;

	public AccelerationDrawer(DrawingParameters initialParameters, EntityDrawer decoratee) {
		super(initialParameters, decoratee);
		setDrawingStrategy(new AccelerationDrawingStrategy(decoratee));
	}

	@Override
	public void onNotify(String propertyName, Event event) {
		super.onNotify(propertyName, event);
		if (propertyName.equals(EntityProperty.IS_ACCELERATING.toString())) {
			isAccelerating = (boolean) event.getValue();
		}
	}

	private class AccelerationDrawingStrategy extends AbstractDrawingStrategy {
		private final EntityDrawer decoratee;

		private AccelerationDrawingStrategy(EntityDrawer decoratee) {
			this.decoratee = decoratee;
		}

		@Override
		public void draw(GraphicsAdapter<? super ImageAdapter> graphics, DrawingParameters drawingParameters) {
			setParameters(drawingParameters);
			if (isAccelerating) {
				int xCorner = (int) x - decoratee.getWidth() / 2 - width / 2 - 1;
				int yCorner = (int) y - height / 2;
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
