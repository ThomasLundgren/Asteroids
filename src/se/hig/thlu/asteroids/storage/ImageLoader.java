package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;

import java.util.List;

public interface ImageLoader<T extends ImageAdapter> {

	T getImage(ImageResource imageResource, Dim dimension);

	List<T> getAnimation(AnimationResource animationResource, Dim dimension);

}
