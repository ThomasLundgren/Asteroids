package se.hig.thlu.asteroids.event.entity;

import java.util.UUID;

public class CollisionEvent extends EntityEvent<CollisionEvent.Collision> {

	public CollisionEvent(Collision value) {
		super(value);
	}

	public static class Collision {

		private final UUID first, second;

		public Collision(UUID first, UUID second) {
			this.first = first;
			this.second = second;
		}

		public UUID getFirst() {
			return first;
		}

		public UUID getSecond() {
			return second;
		}
	}

}
