package se.hig.thlu.asteroids.graphics.drawer;

import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;

public interface Drawer {

	void draw(GraphicsAdapter<ImageAdapter> graphics);

	boolean isFinished();

}
