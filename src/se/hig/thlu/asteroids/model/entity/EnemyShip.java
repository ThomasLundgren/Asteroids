package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.IObserver;
import se.hig.thlu.asteroids.event.create.CreateEventHandler;
import se.hig.thlu.asteroids.event.create.MissileCreateEvent;
import se.hig.thlu.asteroids.event.entity.PlayerMoveEvent;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.model.entity.Missile.MissileSource;
import se.hig.thlu.asteroids.util.Trigonometry;

import java.util.Optional;

public final class EnemyShip extends AbstractEntity implements Shooter, IObserver {

	private static final double ENEMY_SHIP_SPEED = 2.0;
	private int ticksSinceLastShot = 1337;
	private Point playerPosition = new Point();

	EnemyShip(double direction) {
		super(new Point(),
				new Velocity(ENEMY_SHIP_SPEED, direction),
				0.0,
				new Dim(23,
						16));
		setRotation(direction);
	}

	public void shoot() {
		ticksSinceLastShot = 0;
		double centerFrontDistance = ((double) getDimensions().getWidth() / 2.0);
		Point missileStart = Trigonometry.rotateAroundPoint(center,
				rotation,
				centerFrontDistance);
		double direction = Trigonometry.getAngle(center, playerPosition);
		double distance = center.distanceTo(playerPosition) + 150.0;
		distance /= (double) GameConfig.WINDOW_WIDTH;
		distance = StrictMath.max(distance, 0.6);
		Missile missile = new Missile(missileStart, direction, MissileSource.ENEMY, distance);
		CreateEventHandler createHandler = EventHandlerFactory.getEventHandler(CreateEventHandler.class);
		createHandler.notify(new MissileCreateEvent(missile));
	}

	@Override
	public void update() {
		super.update();
		ticksSinceLastShot++;
		if (ticksSinceLastShot > MissileSource.ENEMY.getCoolDown()) {
			shoot();
			ticksSinceLastShot = 0;
		}
	}

	@Override
	protected Optional<Explosion> createDeathExplosion() {
		return Optional.of(new Explosion(center, Explosion.ExplosionSize.TWO));
	}

	@Override
	public int getScore() {
		return 5;
	}

	@Override
	public void notify(Event event) {
		if (event.toString().equals(PlayerMoveEvent.class.toString())) {
			playerPosition = (Point) event.getValue();
		}
	}
}
