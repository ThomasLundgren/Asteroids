package se.hig.thlu.asteroids.graphics.image;

public interface ImageAdapter {

	int getWidth();

	int getHeight();

	<T extends ImageAdapter> T resizeTo(int width, int height);
}
