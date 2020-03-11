package se.hig.thlu.asteroids.graphics.drawer.entitydrawer;

import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.AbstractDrawingStrategy;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.font.FontAdapter;
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
	public void notify(String propertyName, Event event) {
		super.notify(propertyName, event);
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
		public void draw(GraphicsAdapter<FontAdapter, ImageAdapter> graphics, DrawingParameters drawingParameters) {
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
