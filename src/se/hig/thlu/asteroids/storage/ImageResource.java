package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.config.GameConfig;

import static se.hig.thlu.asteroids.storage.AbstractImageLoader.FS;

public enum ImageResource {
	BACKGROUND_PNG(String.format("resources%simages%<sfinal%<sbackground.png", FS),
			GameConfig.WINDOW_WIDTH,
			GameConfig.WINDOW_HEIGHT),
	PLAYER_SHIP_PNG(String.format("resources%simages%<sfinal%<splayer-ship.png", FS),
			35,
			26),
	PLAYER_SHIP_ACCELERATION_PNG(String.format("resources%simages%<sfinal%<splayer-ship-acceleration.png", FS),
			20,
			26),
	ASTEROID_LARGE_PNG(String.format("resources%simages%<sfinal%<sasteroid-large.png", FS),
			(int) (61.0 * 1.5),
			(int) (58.0 * 1.5)),
	ASTEROID_MEDIUM_PNG(String.format("resources%simages%<sfinal%<sasteroid-medium-1.png", FS),
			(int) (27.0 * 1.5),
			(int) (25.0 * 1.5)),
	ASTEROID_SMALL_PNG(String.format("resources%simages%<sfinal%<sasteroid-small-1.png", FS),
			(int) (14.0 * 1.5),
			(int) (14.0 * 1.5)),
	ENEMY_SHIP_SMALL(String.format("resources%simages%<sfinal%<senemy-ship-small.png", FS),
			23,
			16),
	MISSILE_PLAYER(String.format("resources%simages%<sfinal%<smissile-player.png", FS),
			(int) (11.0 * 1.5),
			(int) (6.0 * 1.5)),
	MISSILE_ENEMY(String.format("resources%simages%<sfinal%<smissile-enemy-blue.png", FS),
			(int) (9.0 * 1.0),
			(int) (6.0 * 1.0));

	private final String imagePath;
	private final int width, height;

	// TODO: Move width and height out to Entity classes
	ImageResource(String imagePath, int width, int height) {
		this.imagePath = imagePath;
		this.width = width;
		this.height = height;
	}

	protected String getImagePath() {
		return imagePath;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
