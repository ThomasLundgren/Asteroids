package se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;

@FunctionalInterface
public interface DrawingStrategy {

	void draw(GraphicsAdapter<ImageAdapter> graphics, DrawingParameters drawingParameters);

}
