package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Dimension;

import java.util.List;

public interface ImageLoader<T extends ImageAdapter> {

	T getImage(ImageResource imageResource);

	List<T> getAnimation(AnimationResource animationResource, Dimension dimension);

}
