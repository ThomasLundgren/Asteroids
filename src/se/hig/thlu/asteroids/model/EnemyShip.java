package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.model.Missile.MissileSource;

public final class EnemyShip extends Entity implements Shooter {

    public EnemyShip(Point position, Velocity velocity) {
        super(position, velocity);
    }

    private EnemyShip(Point position, Velocity velocity, boolean isDestroyed) {
        super(position, velocity, isDestroyed);
    }

    @Override
    public Entity withPosition(Point position) {
        return new EnemyShip(position, velocity);
    }

    @Override
    protected Entity withVelocityImpl(Velocity velocity) {
        return new EnemyShip(position, velocity);
    }

    @Override
    public Entity destroy() {
        return new EnemyShip(position, velocity, true);
    }

    @Override
    public Missile shoot(double direction) {
        return new Missile(position, direction, MissileSource.ENEMY);
    }
}
