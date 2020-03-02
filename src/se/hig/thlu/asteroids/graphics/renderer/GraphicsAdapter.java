package se.hig.thlu.asteroids.graphics.renderer;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public interface GraphicsAdapter<T extends ImageAdapter> {

	// TODO: Change parameter to object? "Drawable"?
	void drawImageWithRotation(T image, double angle, double anchorX, double anchorY, int x, int y);

	void drawImage(T image, int x, int y, int width, int height);

}
