package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;

public final class Missile extends Entity {

    private final MissileSource missileSource;

    public enum MissileSource { PLAYER, ENEMY }

//    public Missile(MissileSource missileSource) {
//        super(getMissileSpeed(missileSource));
//        this.missileSource = missileSource;
//    }

    public Missile(Point position, double direction, MissileSource source) {
        super(position, new Velocity(getMissileSpeed(source), direction));
        missileSource = source;
    }

    private Missile(Point position, double direction, boolean isDestroyed, MissileSource source) {
        super(position, new Velocity(getMissileSpeed(source), direction), isDestroyed);
        missileSource = source;
    }

    @Override
    public Entity withPosition(Point position) {
        return new Missile(position, velocity.getDirection(), missileSource);
    }

    @Override
    protected Entity withVelocityImpl(Velocity velocity) {
        return this;
    }

    @Override
    public Entity destroy() {
        return new Missile(position, velocity.getDirection(), true, missileSource);
    }

    private static double getMissileSpeed(MissileSource missileSource) {
        switch (missileSource) {
            case ENEMY:
                return GameConfig.ENEMY_MISSILE_UPDATE_MS;
            case PLAYER:
                return GameConfig.PLAYER_SHOOTING_DELAY_MS;
            default:
                throw new IllegalArgumentException("Enum type not supported");
        }
    }

}