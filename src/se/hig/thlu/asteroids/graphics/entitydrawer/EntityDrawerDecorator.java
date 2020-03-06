package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingStrategy;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.observer.Event;

public class EntityDrawerDecorator extends EntityDrawer {

	private final EntityDrawer decoratee;

	public EntityDrawerDecorator(DrawingStrategy drawingStrategy, DrawingParameters initialParameters,
								 EntityDrawer decoratee) {
		super(drawingStrategy, initialParameters);
		this.decoratee = decoratee;
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		decoratee.draw(graphics);
		super.draw(graphics);
	}

	@Override
	public int getWidth() {
		return StrictMath.max(decoratee.getWidth(), super.getWidth());
	}

	@Override
	public int getHeight() {
		return StrictMath.max(decoratee.getHeight(), super.getHeight());
	}

	@Override
	public void onNotify(String propertyName, Event event) {
		decoratee.onNotify(propertyName, event);
		super.onNotify(propertyName, event);
	}
}
