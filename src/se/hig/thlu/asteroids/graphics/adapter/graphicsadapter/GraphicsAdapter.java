package se.hig.thlu.asteroids.graphics.adapter.graphicsadapter;

import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;

public interface GraphicsAdapter<T extends ImageAdapter> {

	void drawImageWithRotation(T image, double angle, double anchorX, double anchorY, int x, int y);

	void drawImage(T image, int x, int y, int width, int height);


}
