package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;

public final class Missile extends Entity {

    private final MissileSource missileSource;

    public enum MissileSource { PLAYER, ENEMY }

    public Missile(MissileSource missileSource) {
        super(getMissileSpeed(missileSource));
        this.missileSource = missileSource;
    }

    public Missile(Point position, double direction, MissileSource source) {
        super(position, getMissileSpeed(source), direction);
        missileSource = source;
    }

    private Missile(Point position, double direction, boolean isDestroyed, MissileSource source) {
        super(position, getMissileSpeed(source), direction, isDestroyed);
        missileSource = source;
    }

    @Override
    public Entity withPosition(Point position) {
        return new Missile(position, direction, missileSource);
    }

    @Override
    protected Entity withSpeedImpl(double speed) {
        return this;
    }

    @Override
    protected Entity withDirectionImpl(double direction) {
        return new Missile(position, direction, missileSource);
    }

    @Override
    public Entity destroy() {
        return new Missile(position, direction, true, missileSource);
    }

    private static double getMissileSpeed(MissileSource missileSource) {
        switch (missileSource) {
            case ENEMY:
                return GameConfig.ENEMY_MISSILE_DEFAULT_SPEED;
            case PLAYER:
                return GameConfig.PLAYER_MISSILE_DEFAULT_SPEED;
            default:
                throw new IllegalArgumentException("Enum type not supported");
        }
    }

}