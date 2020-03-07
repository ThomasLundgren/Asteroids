package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public class NullDrawingStrategy implements DrawingStrategy {


	private NullDrawingStrategy() {
	}

	public static NullDrawingStrategy createNullDrawingStrategy() {
		return new NullDrawingStrategy();
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics, DrawingParameters drawingParameters) {
		// Do nothing
	}
}
