package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

public abstract class ImageLoader<T extends ImageAdapter> {

	public static final String FS = File.separator;
	protected final EnumMap<ImageResource, T> imageCache =
			new EnumMap<>(ImageResource.class);
	protected final EnumMap<AnimationResource, List<T>> animationCache =
			new EnumMap<>(AnimationResource.class);

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

	public List<T> getAnimationResource(AnimationResource animationResource) {
		return animationCache.get(animationResource);
	}

	protected T loadImage(ImageResource image) throws IOException {
		return loadImageFromString(image.imagePath,
				image.getWidth(),
				image.getHeight());
	}

	protected List<T> loadAnimation(AnimationResource animation) throws IOException {
		T spriteSheet = loadImageFromString(animation.getImagePath(),
				animation.getWidth(),
				animation.getHeight());
		return spriteSheetToImageList(spriteSheet, animation);
	}

	protected abstract List<T> spriteSheetToImageList(T spriteSheet, AnimationResource animation);

	protected abstract T loadImageFromString(String path, int width, int height) throws IOException;

	protected final void loadAllImages() throws IOException {
		for (ImageResource imageResource : ImageResource.values()) {
			T image = loadImage(imageResource);
			imageCache.put(imageResource, image);
		}
		for (AnimationResource animationResource : AnimationResource.values()) {
			List<T> animation = loadAnimation(animationResource);
			animationCache.put(animationResource, animation);
		}
	}

	public enum AnimationResource {
		EXPLOSIONS_ALL(String.format("resources%simages%<sfinal%<sexplosions-all.png", FS),
				1024, 768, 6, 8);

		private final String imagePath;
		private final int width, height, rows, columns;

		// TODO: Move width and height out to Entity classes
		AnimationResource(String imagePath, int width, int height, int rows, int columns) {
			this.imagePath = imagePath;
			this.width = width;
			this.height = height;
			this.rows = rows;
			this.columns = columns;
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

		public int getRows() {
			return rows;
		}

		public int getColumns() {
			return columns;
		}
	}

	public enum ImageResource {
		BACKGROUND_PNG(String.format("resources%simages%<sfinal%<sbackground.png", FS),
				GameConfig.WINDOW_WIDTH,
				GameConfig.WINDOW_HEIGHT),
		PLAYER_SHIP_PNG(String.format("resources%simages%<sfinal%<splayer-ship.png", FS),
				35,
				26),
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
		MISSILE_ENEMY(String.format("resources%simages%<sfinal%<smissile-enemy-blue.png", FS),
				9,
				6);

		private final String imagePath;
		private final int width, height;

		// TODO: Move width and height out to Entity classes
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
