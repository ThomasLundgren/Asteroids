package se.hig.thlu.asteroids.model;

public final class Explosion {

	// TODO: Make new supertype for Explosion? Smth like non-solid.
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
		ONE(6, 6),
		TWO(18, 16),
		THREE(34,36),
		FOUR(52,48);

		private final int width, height;

		ExplosionSize(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

	}

}
