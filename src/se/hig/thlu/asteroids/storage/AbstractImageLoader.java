package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractImageLoader<T extends ImageAdapter> implements ImageLoader<T> {

	public static final String FS = File.separator;
	protected final EnumMap<ImageResource, T> imageCache =
			new EnumMap<>(ImageResource.class);
	protected final EnumMap<ImageResource, Map<Dim, T>> sizedImageCache =
			new EnumMap<>(ImageResource.class);
	protected final EnumMap<AnimationResource, T> animationCache =
			new EnumMap<>(AnimationResource.class);
	protected final EnumMap<AnimationResource, Map<Dim, List<T>>> sizedAnimationCache =
			new EnumMap<>(AnimationResource.class);
	protected final Map<Class<?>, List<T>> classCache = new HashMap<>(15);

	/*
	Not handling the Exception here since we NEED all images.
	If we cannot load all images, something has gone wrong and the program can't run.
	 */
	protected AbstractImageLoader() throws IOException {
		loadAllImages();
	}

	@Override
	public T getImage(ImageResource imageResource, Dim dimensions) {
		Optional<T> resizedImage = getFromImageResizeCache(imageResource, dimensions);
		return resizedImage.orElseGet(() -> resizeAndCacheImage(imageResource, dimensions));
	}

	@Override
	public List<T> getAnimation(AnimationResource animationResource, Dim dimensions) {
		List<T> resizedAnimation = getFromAnimationResizeCache(animationResource, dimensions);
		if (!resizedAnimation.isEmpty()) {
			return resizedAnimation;
		}
		return resizeAndCacheAnimation(animationResource, dimensions);
	}

	protected abstract List<T> spriteSheetToImageList(T spriteSheet, AnimationResource animation);

	protected abstract T loadImageFromString(String path, int width, int height) throws IOException;

	protected final void loadAllImages() throws IOException {
		for (ImageResource imageResource : ImageResource.values()) {
			T image = loadImage(imageResource);
			imageCache.put(imageResource, image);
		}
		for (AnimationResource animationResource : AnimationResource.values()) {
			T animation = loadAnimation(animationResource);
			animationCache.put(animationResource, animation);
		}
	}

	protected T loadImage(ImageResource image) throws IOException {
		return loadImageFromString(image.getImagePath(),
				image.getWidth(),
				image.getHeight());
	}

	protected T loadAnimation(AnimationResource animation) throws IOException {
		T spriteSheet = loadImageFromString(animation.getImagePath(),
				animation.getWidth(),
				animation.getHeight());
		return spriteSheet;
	}

	protected Optional<T> getFromImageResizeCache(ImageResource imageResource, Dim dimensions) {
		Optional<T> cachedResized = Optional.empty();
		Map<Dim, T> imageMap = sizedImageCache.get(imageResource);
		if (imageMap != null) {
			cachedResized = Optional.ofNullable(imageMap.get(dimensions));
		}
		return cachedResized;
	}

	protected List<T> getFromAnimationResizeCache(AnimationResource animationResource, Dim dimension) {
		List<T> cachedImages = new ArrayList<>(50);
		Map<Dim, List<T>> animationList = sizedAnimationCache.get(animationResource);
		if (animationList != null) {
			List<T> cachedResized = animationList.get(dimension);
			if (cachedResized != null) {
				cachedImages = cachedResized;
			}
		}
		return cachedImages;
	}

	protected T resizeAndCacheImage(ImageResource imageResource, Dim dimensions) {
		T resizedImage = imageCache.get(imageResource)
				.resizeTo(dimensions);
		if (sizedImageCache.containsKey(imageResource)) {
			sizedImageCache.get(imageResource)
					.put(dimensions, resizedImage);
		} else {
			Map<Dim, T> newMapping = new HashMap<>(50);
			sizedImageCache.put(imageResource, newMapping);
		}
		return resizedImage;
	}

	protected List<T> resizeAndCacheAnimation(AnimationResource animationResource, Dim dimensions) {
		int columns = animationResource.getColumns();
		int rows = animationResource.getRows();
		int newWidth = dimensions.getWidth() * columns;
		int newHeight = dimensions.getHeight() * rows;
		T spriteSheet = animationCache.get(animationResource);
		T resizedSpriteSheet = spriteSheet.resizeTo(new Dim(newWidth, newHeight));
		List<T> resizedImages = spriteSheetToImageList(resizedSpriteSheet, animationResource);
		if (sizedAnimationCache.containsKey(animationResource)) {
			sizedAnimationCache.get(animationResource)
					.put(dimensions, resizedImages);
		} else {
			Map<Dim, List<T>> newMapping = new HashMap<>(10);
			newMapping.put(dimensions, resizedImages);
			sizedAnimationCache.put(animationResource, newMapping);
		}
		return resizedImages;
	}

}
