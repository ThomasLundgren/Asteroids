package se.hig.thlu.asteroids.storage;

import static se.hig.thlu.asteroids.storage.AbstractImageLoader.FS;

public enum AnimationResource {

	EXPLOSIONS_ALL(String.format("resources%simages%<sfinal%<sexplosions-all.png", FS),
			1024, 768, 6, 8),
	PLAYER_SHIP_ANI(String.format("resources%simages%<sfinal%<sship-animation.png", FS),
			1200, 1200, 3, 3);

	private final String imagePath;
	private final int width, height, rows, columns;

	AnimationResource(String imagePath, int width, int height, int rows, int columns) {
		this.imagePath = imagePath;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
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

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
}
