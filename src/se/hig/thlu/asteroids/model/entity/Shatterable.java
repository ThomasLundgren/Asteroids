package se.hig.thlu.asteroids.model.entity;

import java.util.List;

@FunctionalInterface
public interface Shatterable {

	List<Entity> shatter();

}
