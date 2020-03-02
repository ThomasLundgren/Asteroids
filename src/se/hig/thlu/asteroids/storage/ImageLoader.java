package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

public abstract class ImageLoader<T extends ImageAdapter> {

	public static final String FS = File.separator;
	protected final EnumMap<ImageResource, T> imageCache =
			new EnumMap<>(ImageResource.class);

	/*
	Not handling the Exception here since we NEED all images.
	If we cannot load all images, something has gone wrong and the program can't run.
	 */
	protected ImageLoader() throws IOException {
		loadAllImages();
	}

	public T getImageResource(ImageResource imageResource) {
		return imageCache.get(imageResource);
	}

	protected abstract T loadImage(ImageResource imageResource) throws IOException;

	protected final void loadAllImages() throws IOException {
		for (ImageResource imageResource : ImageResource.values()) {
			T image = loadImage(imageResource);
			imageCache.put(imageResource, image);
		}
	}

	public enum ImageResource {
		BACKGROUND_PNG(String.format("resources%simages%<sfinal%<sbackground.png", FS),
				GameConfig.WINDOW_WIDTH,
				GameConfig.WINDOW_HEIGHT),
		PLAYER_SHIP_ACCELERATION_PNG(String.format("resources%simages%<sfinal%<splayer-ship-acceleration.png", FS),
				17,
				18),
		PLAYER_SHIP_PNG(String.format("resources%simages%<sfinal%<splayer-ship.png", FS),
				29,
				22),
		ASTEROID_LARGE_PNG(String.format("resources%simages%<sfinal%<sasteroid-large.png", FS),
				61,
				58),
		ASTEROID_MEDIUM_PNG(String.format("resources%simages%<sfinal%<sasteroid-medium-1.png", FS),
				27,
				25),
		ASTEROID_SMALL_PNG(String.format("resources%simages%<sfinal%<sasteroid-small-1.png", FS),
				14,
				14),
		ENEMY_SHIP_SMALL(String.format("resources%simages%<sfinal%<senemy-ship-small.png", FS),
				23,
				16),
		MISSILE_PLAYER(String.format("resources%simages%<sfinal%<smissile-player.png", FS),
				11,
				6),
		MISSILE_ENEMY(String.format("resources%simages%<sfinal%<smissile-enemy.png", FS),
				6,
				4);;

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
