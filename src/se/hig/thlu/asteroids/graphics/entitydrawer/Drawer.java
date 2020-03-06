package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

@FunctionalInterface
public interface Drawer {

	public abstract void draw(GraphicsAdapter<ImageAdapter> graphics);

}
