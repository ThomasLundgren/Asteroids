package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public interface Drawer {

	void draw(GraphicsAdapter<ImageAdapter> graphics);

	boolean isFinished();

}
