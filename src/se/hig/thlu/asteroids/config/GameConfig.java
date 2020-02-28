package se.hig.thlu.asteroids.config;

import se.hig.thlu.asteroids.model.Asteroid;

public enum GameConfig {
	;

	/**
	 * Player's starting lives.
	 */
	public static final int MAX_LIVES = 3;
	/**
	 * Initial time between {@link Asteroid} spawns.
	 */
	public static final int INITIAL_SPAWN_INTERVAL = 60;
	/**
	 * The time interval at which the spawn times decrease.
	 */
	public static final int LEVEL_INCREASE_TIME = 60;
	/**
	 * How much the spawn times should decrease at each level.
	 */
	public static final int SPAWN_TIME_DECREASE = 2;
	/**
	 * Minimum time between {@link Asteroid} spawns.
	 */
	public static final int MINIMUM_SPAWN_INTERVAL = 2;
	public static final double PLAYER_SHIP_UPDATE_MS = 5.0;
	public static final double ENEMY_SHIP_SPEED = 5.0;
	public static final double ENEMY_MISSILE_UPDATE_MS = 2.0;
	public static final double PLAYER_MISSILE_UPDATE_MS = 2.0;
	public static final double PLAYER_SHOOTING_DELAY_MS = 12.0;
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;


}
