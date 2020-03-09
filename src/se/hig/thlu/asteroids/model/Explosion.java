package se.hig.thlu.asteroids.model;

public final class Explosion {

	private final ExplosionSize size;
	private final Point center;

	public Explosion(Point center, ExplosionSize size) {
		this.size = size;
		this.center = center;
	}

	public Point getCenter() {
		return center;
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
