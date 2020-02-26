package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.model.Missile.MissileSource;

public final class PlayerShip extends Entity implements Shooter {

    private final int lives;

    public PlayerShip() {
        lives = GameConfig.MAX_LIVES;
    }

    private PlayerShip(Point position, Velocity velocity, int lives) {
        super(position, velocity);
        lessThanTopSpeed(velocity.getSpeed());
        this.lives = validateLives(lives);
    }

    private PlayerShip(Point position, Velocity velocity, boolean isDestroyed, int lives) {
        super(position, velocity, isDestroyed);
        lessThanTopSpeed(velocity.getSpeed());
        this.lives = validateLives(lives);
    }

    public int getLives() {
        return lives;
    }

    private PlayerShip withLives(int lives) {
        int validatedLives = validateLives(lives);
        return new PlayerShip(position, velocity, validatedLives);
    }

    @Override
    public Entity withPosition(Point position) {
        return new PlayerShip(position, velocity, lives);
    }

    @Override
    public Entity destroy() {
        return lives == 1 ? new PlayerShip(position, new Velocity(0.0, 0.0), true, 0)
                : this.withLives(lives - 1);
    }

    @Override
    public Missile shoot(double direction) {
        return new Missile(position, direction, MissileSource.PLAYER);
    }

    @Override
    protected Entity withVelocityImpl(Velocity velocity) {
        lessThanTopSpeed(velocity.getSpeed());
        return new PlayerShip(position, velocity, lives);
    }

    private static int validateLives(int lives) {
        if (lives < 0) {
            throw new IllegalArgumentException("Lives must be between 0 and 3");
        }
        return lives;
    }

    private static double lessThanTopSpeed(double speed) {
        if (speed > GameConfig.PLAYER_SHIP_UPDATE_MS) {
            throw new IllegalArgumentException("PlayerShip cannot exceed GameConfig.PLAYER_MAX_SPEED");
        }
        return speed;
    }
}