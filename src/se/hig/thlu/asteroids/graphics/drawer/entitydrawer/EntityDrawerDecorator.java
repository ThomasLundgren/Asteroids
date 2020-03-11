package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingStrategy;
import se.hig.thlu.asteroids.graphics.font.FontAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
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
	public void draw(GraphicsAdapter<FontAdapter, ImageAdapter> graphics) {
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
	public void onNotify(String propertyName, Event event) {
		decoratee.onNotify(propertyName, event);
		super.onNotify(propertyName, event);
	}
}
