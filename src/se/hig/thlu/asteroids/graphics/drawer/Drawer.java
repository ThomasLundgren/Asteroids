package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.font.FontAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public interface Drawer {

	void draw(GraphicsAdapter<FontAdapter, ImageAdapter> graphics);

	boolean isFinished();

}
