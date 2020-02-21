package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.model.Missile.MissileSource;

public final class EnemyShip extends Entity implements Shooter {

    public EnemyShip(Point position, double speed, double direction) {
        super(position, speed, direction);
    }

    private EnemyShip(Point position, double speed, double direction, boolean isDestroyed) {
        super(position, speed, direction);
    }

    @Override
    public Entity withPosition(Point position) {
        return new EnemyShip(position, speed, direction);
    }

    @Override
    protected Entity withSpeedImpl(double speed) {
        return new EnemyShip(position, speed, direction);
    }

    @Override
    protected Entity withDirectionImpl(double direction) {
        return new EnemyShip(position, speed, direction);
    }

    @Override
    public Entity destroy() {
        return new EnemyShip(position, speed, direction, true);
    }

    @Override
    public Missile shoot(double direction) {
        return new Missile(position, direction, MissileSource.ENEMY);
    }
}
