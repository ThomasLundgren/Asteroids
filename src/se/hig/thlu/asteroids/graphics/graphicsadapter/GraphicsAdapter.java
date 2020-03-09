package se.hig.thlu.asteroids.graphics.graphicsadapter;

import se.hig.thlu.asteroids.graphics.font.FontAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public interface GraphicsAdapter<S extends FontAdapter, T extends ImageAdapter> {

	// TODO: Change parameter to object? "Drawable"?
	void drawImageWithRotation(T image, double angle, double anchorX, double anchorY, int x, int y);

	void drawImage(T image, int x, int y, int width, int height);

	void drawRect(int x, int y, int width, int height);

	void drawString(String text, int x, int y);

	void setFont(S font);

}
