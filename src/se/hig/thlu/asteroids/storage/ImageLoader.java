package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

import java.io.IOException;
import java.util.EnumMap;

public abstract class ImageLoader<T extends ImageAdapter> {

	protected final EnumMap<ImageResource, T> imageCache =
			new EnumMap<>(ImageResource.class);

	/*
	Not handling the Exception here since we NEED all images.
	If we cannot load all images, something has gone wrong and the program can't run.
	 */
	public ImageLoader() throws IOException {
		loadAllImages();
	}

	public T getImageResource(ImageResource imageResource) {
		return imageCache.get(imageResource);
	}

	protected abstract T loadImage(ImageResource imageResource) throws IOException;

	protected void loadAllImages() throws IOException {
		for (ImageResource imageResource : ImageResource.values()) {
			T image = loadImage(imageResource);
			imageCache.put(imageResource, image);
		}
	}

	public enum ImageResource {
		PLAYER_SHIP_ACCEL_PNG("resources/images/final/player-ship-accel.png", 45, 22),
		BACKGROUND_PNG("resources/images/final/background.png", GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT),
		PLAYER_SHIP_PNG("resources/images/final/player-ship.png", 29, 22);

		private final String imagePath;
		private final int width, height;

		ImageResource(String imagePath, int width, int height) {
			this.imagePath = imagePath;
			this.width = width;
			this.height = height;
		}

		protected String getImagePath() {
			return imagePath;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}

}
