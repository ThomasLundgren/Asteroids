package se.hig.thlu.asteroids.event.entity;

public class LoseLifeEvent extends EntityEvent<Integer> {

	public LoseLifeEvent(Integer value) {
		super(value);
	}
}
