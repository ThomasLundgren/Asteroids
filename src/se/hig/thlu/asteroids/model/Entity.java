package se.hig.thlu.asteroids.model;

public abstract class Entity {

    protected Point center;
    protected Velocity velocity;
    protected boolean isDestroyed;

    protected Entity(Point center, Velocity velocity) {
        this.velocity = velocity;
        this.center = center;
        isDestroyed = false;
    }

    protected Entity(Point center, Velocity velocity, boolean isDestroyed) {
        this.center = center;
        this.velocity = velocity;
        this.isDestroyed = isDestroyed;
    }

    public final Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public final Velocity getVelocity() {
        return velocity;
    }

    protected final void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public final boolean isDestroyed() {
        return isDestroyed;
    }

    public void collide() {
        isDestroyed = true;
    }

    public void updatePosition() {
        double diffX = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
        double diffY = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
        Point newPos = new Point(center.getX() + diffX, center.getY() + diffY);
        setCenter(newPos);
    }
}
