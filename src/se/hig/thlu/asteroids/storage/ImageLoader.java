package se.hig.thlu.asteroids.storage;

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
		return loadImageFromString(image.getImagePath(),
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

}
