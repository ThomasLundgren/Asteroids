package se.hig.thlu.asteroids.model.drawingstrategy;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;

public interface DrawingStrategy {

	void draw(GraphicsAdapter<? super ImageAdapter> graphics);

}
