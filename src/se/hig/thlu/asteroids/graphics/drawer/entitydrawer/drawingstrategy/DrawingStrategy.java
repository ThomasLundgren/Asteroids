package se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.font.FontAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

@FunctionalInterface
public interface DrawingStrategy {

	void draw(GraphicsAdapter<FontAdapter, ImageAdapter> graphics, DrawingParameters drawingParameters);

}
