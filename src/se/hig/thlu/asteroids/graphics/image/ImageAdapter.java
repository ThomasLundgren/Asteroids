package se.hig.thlu.asteroids.graphics.image;

import se.hig.thlu.asteroids.model.Dim;

public interface ImageAdapter {

	int getWidth();

	int getHeight();

	<T extends ImageAdapter> T resizeTo(Dim dimensions);
}
