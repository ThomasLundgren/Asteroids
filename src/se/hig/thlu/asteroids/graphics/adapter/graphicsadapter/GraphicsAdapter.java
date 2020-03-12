package se.hig.thlu.asteroids.graphics.adapter.graphicsadapter;

import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;

public interface GraphicsAdapter<T extends ImageAdapter> {

	// TODO: Change parameter to object? "Drawable"?
	void drawImageWithRotation(T image, double angle, double anchorX, double anchorY, int x, int y);

	void drawImage(T image, int x, int y, int width, int height);

//	void drawRect(int x, int y, int width, int height);
//
//	void drawString(String text, int x, int y);
//
//	void setFont(S font);
//
//	void setColor(R color);

}
