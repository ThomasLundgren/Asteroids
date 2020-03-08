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
		ONE(18, 16),
		TWO(34,36),
		THREE(52,48);

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
