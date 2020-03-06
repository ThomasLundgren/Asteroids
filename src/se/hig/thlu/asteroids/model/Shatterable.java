package se.hig.thlu.asteroids.model;

import java.util.List;

@FunctionalInterface
public interface Shatterable<T> {

	List<T> shatter();

}
