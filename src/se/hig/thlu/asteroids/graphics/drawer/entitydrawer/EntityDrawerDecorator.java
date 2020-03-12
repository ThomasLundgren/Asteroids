package se.hig.thlu.asteroids.graphics.drawer.entitydrawer;

import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingStrategy;
import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.observer.Event;

public class EntityDrawerDecorator extends EntityDrawer {

	private final EntityDrawer decoratee;

	public EntityDrawerDecorator(DrawingStrategy drawingStrategy, DrawingParameters initialParameters,
								 EntityDrawer decoratee) {
		super(drawingStrategy, initialParameters);
		this.decoratee = decoratee;
	}

	protected EntityDrawerDecorator (DrawingParameters drawingParameters, EntityDrawer decoratee) {
		super(drawingParameters);
		this.decoratee = decoratee;
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		decoratee.draw(graphics);
		super.draw(graphics);
	}

	@Override
	public Dim getDimensions() {
		Dim myDim = super.getDimensions();
		Dim decorateeDim = decoratee.getDimensions();
		int width = myDim.getWidth() + decorateeDim.getWidth();
		int height = myDim.getHeight() + decorateeDim.getHeight();
		return new Dim(width, height);
	}

	@Override
	public void notify(String propertyName, Event event) {
		decoratee.notify(propertyName, event);
		super.notify(propertyName, event);
	}
}
