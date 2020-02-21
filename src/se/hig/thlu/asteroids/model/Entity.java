package se.hig.thlu.asteroids.model;

public abstract class Entity {

    protected final Point position;
    protected final double speed;
    protected final double direction;
    protected final boolean isDestroyed;

    protected Entity() {
        position = new Point(0.0, 0.0);
        speed = validateSpeed(0.0);
        direction = validateDirection(0.0);
        isDestroyed = false;
    }

    protected Entity(double speed) {
        position = new Point(0.0, 0.0);
        this.speed = validateSpeed(speed);
        direction = validateDirection(0.0);
        isDestroyed = false;
    }

    protected Entity(Point position, double speed, double direction) {
        this.speed = validateSpeed(speed);
        this.direction = validateDirection(direction);
        this.position = position;
        isDestroyed = false;
    }

    protected Entity(Point position, double speed, double direction, boolean isDestroyed) {
        this.position = position;
        this.speed = validateSpeed(speed);
        this.direction = validateDirection(direction);
        this.isDestroyed = isDestroyed;
    }

    public final Point getPosition() {
        return position;
    }

    public abstract Entity withPosition(Point position);

    public final double getSpeed() {
        return speed;
    }

    public final Entity withSpeed(double speed) {
        double spd = validateSpeed(speed);
        return withSpeedImpl(spd);
    }

    protected abstract Entity withSpeedImpl(double speed);

    public final double getDirection() {
        return direction;
    }

    public final Entity withDirection(double direction) {
        double dir = validateDirection(direction);
        return withDirectionImpl(dir);
    }

    protected abstract Entity withDirectionImpl(double direction);

    public final boolean isDestroyed() {
        return isDestroyed;
    }

    public abstract Entity destroy();

    private static double validateDirection(double direction) {
        final double lap = 360.0;
        double temp = direction % lap;
        if (temp < 0.0) {
            temp = lap - temp;
        }
        return temp % lap;
    }

    private static double validateSpeed(double speed) {
        if (speed < 0.0) {
            throw new IllegalArgumentException("Speed cannot be less than zero");
        }
        return speed;
    }
}
