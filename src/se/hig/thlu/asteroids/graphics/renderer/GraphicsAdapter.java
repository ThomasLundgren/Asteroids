package se.hig.thlu.asteroids.graphics.renderer;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

@FunctionalInterface
public interface GraphicsAdapter<T extends ImageAdapter> {

	void drawImageWithRotation(T image, double angle, double anchorX, double anchorY, int x, int y);

}
