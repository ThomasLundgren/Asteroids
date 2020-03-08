package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractImageLoader<T extends ImageAdapter> implements ImageLoader<T> {

	public static final String FS = File.separator;
	protected final EnumMap<ImageResource, T> imageCache =
			new EnumMap<>(ImageResource.class);
	protected final EnumMap<ImageResource, Map<Dim, T>> sizedImageCache =
			new EnumMap<>(ImageResource.class);
	protected final EnumMap<AnimationResource, List<T>> animationCache =
			new EnumMap<>(AnimationResource.class);
	protected final EnumMap<AnimationResource, Map<Dim, List<T>>> sizedAnimationCache =
			new EnumMap<>(AnimationResource.class);

	/*
	Not handling the Exception here since we NEED all images.
	If we cannot load all images, something has gone wrong and the program can't run.
	 */
	protected AbstractImageLoader() throws IOException {
		loadAllImages();
	}

	@Override
	public T getImage(ImageResource imageResource, Dim dimension) {
		Optional<T> resizedImage = getFromImageResizeCache(imageResource, dimension);
		System.out.println("ResizedImage present? " + resizedImage.isPresent());
		return resizedImage.orElseGet(() -> resizeAndCacheImage(imageResource, dimension));
	}

	@Override
	public List<T> getAnimation(AnimationResource animationResource, Dim dimension) {
		List<T> resizedAnimation = getFromAnimationResizeCache(animationResource, dimension);
		if (!resizedAnimation.isEmpty()) {
			return resizedAnimation;
		}
		return resizeAndCacheAnimation(animationResource, dimension);
	}

	protected abstract List<T> spriteSheetToImageList(T spriteSheet, AnimationResource animation);

	protected abstract T loadImageFromString(String path, int width, int height) throws IOException;

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

	protected Optional<T> getFromImageResizeCache(ImageResource imageResource, Dim dimension) {
		Optional<T> cachedResized = Optional.empty();
		Map<Dim, T> imageMap = sizedImageCache.get(imageResource);
		if (imageMap != null) {
			cachedResized = Optional.ofNullable(imageMap.get(dimension));
		}
		return cachedResized;
	}

	protected List<T> getFromAnimationResizeCache(AnimationResource animationResource, Dim dimension) {
		List<T> cachedImages = new ArrayList<>(50);
		Map<Dim, List<T>> animationList = sizedAnimationCache.get(animationResource);
		if (animationList != null) {
			// Animation was cached
			List<T> cachedResized = animationList.get(dimension);
			if (cachedResized != null) {
				cachedImages = cachedResized;
			}
		}
		return cachedImages;
	}

	protected T resizeAndCacheImage(ImageResource imageResource, Dim dimension) {
		T resizedImage = imageCache.get(imageResource)
				.resizeTo(dimension);
		if (sizedImageCache.containsKey(imageResource)) {
			sizedImageCache.get(imageResource)
					.put(dimension, resizedImage);
		} else {
			Map<Dim, T> newMapping = new HashMap<>(50);
			sizedImageCache.put(imageResource, newMapping);
		}
		return resizedImage;
	}

	protected List<T> resizeAndCacheAnimation(AnimationResource animationResource, Dim dimension) {
		List<T> animation = animationCache.get(animationResource);
		List<T> resizedImages = animation.parallelStream()
				.map(image -> image.<T>resizeTo(dimension))
				.collect(Collectors.toList());
		if (sizedAnimationCache.containsKey(animationResource)) {
			sizedAnimationCache.get(animationResource)
					.put(dimension, resizedImages);
		} else {
			Map<Dim, List<T>> newMapping = new HashMap<>(10);
			newMapping.put(dimension, resizedImages);
			sizedAnimationCache.put(animationResource, newMapping);
		}
		return resizedImages;
	}

}
