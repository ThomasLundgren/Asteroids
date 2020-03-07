package se.hig.thlu.asteroids.graphics.graphicsadapter;

public interface GraphicsAdapter<ImageAdapter> {

	// TODO: Change parameter to object? "Drawable"?
	void drawImageWithRotation(ImageAdapter image, double angle, double anchorX, double anchorY, int x, int y);

	void drawImage(ImageAdapter image, int x, int y, int width, int height);

}
