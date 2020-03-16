package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;

import java.util.UUID;

public final class Explosion implements Entity {

	private final ExplosionSize size;
	private Point center;
	private final UUID id = UUID.randomUUID();

	public Explosion(Point center, ExplosionSize size) {
		this.size = size;
		this.center = center;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Dim getDimensions() {
		return size.getDimensions();
	}

	public Point getCenter() {
		return center;
	}

	@Override
	public void setCenter(Point center) {
		this.center = center;
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public void destroy() {
		//
	}

	@Override
	public void update() {
		//
	}

	@Override
	public boolean intersectsWith(Entity other) {
		return false;
	}

	@Override
	public int getScore() {
		return 0;
	}

	public ExplosionSize getSize() {
		return size;
	}

	public enum ExplosionSize {
		ONE(new Dim(23, 20)),
		TWO(new Dim(43,45)),
		THREE(new Dim(72,66));

		private final Dim dimensions;

		ExplosionSize(Dim dimensions) {
			this.dimensions = dimensions;
		}

		public Dim getDimensions() {
			return dimensions;
		}
	}

}
