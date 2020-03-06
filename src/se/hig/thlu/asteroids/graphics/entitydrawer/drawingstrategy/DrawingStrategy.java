package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

@FunctionalInterface
public interface DrawingStrategy {

	void draw(GraphicsAdapter<? super ImageAdapter> graphics, DrawingParameters drawingParameters);

}
