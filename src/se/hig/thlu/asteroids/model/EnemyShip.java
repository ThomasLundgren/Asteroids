package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.model.Missile.MissileSource;

public final class EnemyShip extends Entity implements Shooter {

    protected EnemyShip(Point position, Velocity velocity) {
        super(position, velocity);
    }

    @Override
    public Missile shoot(double direction) {
        return new Missile(center, direction, MissileSource.ENEMY);
    }
}
