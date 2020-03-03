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
		PLAYER_SHIP_PNG(String.format("resources%simages%<sfinal%<splayer-ship.png", FS),
				35,
				26),
//		PLAYER_SHIP_PNG(String.format("resources%simages%<sfinal%<sblueship2.png", FS),
//				287,
//				151),
		PLAYER_SHIP_ACCELERATION_PNG(String.format("resources%simages%<sfinal%<splayer-ship-acceleration.png", FS),
				20,
				26),
		ASTEROID_LARGE_PNG(String.format("resources%simages%<sfinal%<sasteroid-large.png", FS),
				(int) (61.0 * 1.5),
				(int) (58.0 * 1.5)),
		ASTEROID_MEDIUM_PNG(String.format("resources%simages%<sfinal%<sasteroid-medium-1.png", FS),
				(int) (27.0 * 1.5),
				(int) (25.0 * 1.5)),
		ASTEROID_SMALL_PNG(String.format("resources%simages%<sfinal%<sasteroid-small-1.png", FS),
				(int) (14.0 * 1.5),
				(int) (14.0 * 1.5)),
		ENEMY_SHIP_SMALL(String.format("resources%simages%<sfinal%<senemy-ship-small.png", FS),
				23,
				16),
		MISSILE_PLAYER(String.format("resources%simages%<sfinal%<smissile-player.png", FS),
				(int) (11.0 * 1.5),
				(int) (6.0 * 1.5)),
		MISSILE_ENEMY(String.format("resources%simages%<sfinal%<smissile-enemy.png", FS),
				6,
				4),
		EXPLOSION_1(String.format("resources%simages%<sfinal%<sexplosion-1.png", FS),
				6,
				6),
		EXPLOSION_2(String.format("resources%simages%<sfinal%<sexplosion-2.png", FS),
				18,
				16),
		EXPLOSION_3(String.format("resources%simages%<sfinal%<sexplosion-3.png", FS),
				34,
				36),
		EXPLOSION_4(String.format("resources%simages%<sfinal%<sexplosion-4.png", FS),
				52,
				48),
		;

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
