package se.hig.thlu.asteroids.model;

public abstract class Entity {

    protected final Point position;
    protected final Velocity velocity;
    protected final boolean isDestroyed;

//    protected Entity() {
//        position = new Point(0.0, 0.0);
//        velocity = new Velocity(0.0, 0.0);
//        isDestroyed = false;
//    }
//
//    protected Entity(double speed) {
//        position = new Point(0.0, 0.0);
//        velocity = new Velocity(speed, 0.0);
//        isDestroyed = false;
//    }

    protected Entity(Point position, Velocity velocity) {
        this.velocity = velocity;
        this.position = position;
        isDestroyed = false;
    }

    protected Entity(Point position, Velocity velocity, boolean isDestroyed) {
        this.position = position;
        this.velocity = velocity;
        this.isDestroyed = isDestroyed;
    }

    public final Point getPosition() {
        return position;
    }

    protected abstract Entity withPosition(Point position);

    public final Velocity getVelocity() {
        return velocity;
    }

    protected final Entity withVelocity(Velocity velocity) {
        return withVelocityImpl(velocity);
    }

    protected abstract Entity withVelocityImpl(Velocity velocity);

    public final boolean isDestroyed() {
        return isDestroyed;
    }

    public abstract Entity destroy();

    public Entity withUpdatedPosition() {
        double diffX = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
        double diffY = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();

        Point newPos = new Point(position.getX() + diffX, position.getY() + diffY);
        return withPosition(newPos);
    }
}
