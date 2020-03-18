package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.create.CreateEventHandler;
import se.hig.thlu.asteroids.event.create.ExplosionCreateEvent;
import se.hig.thlu.asteroids.event.create.MissileCreateEvent;
import se.hig.thlu.asteroids.event.entity.AccelerationEvent;
import se.hig.thlu.asteroids.event.entity.LoseLifeEvent;
import se.hig.thlu.asteroids.event.entity.PlayerMoveEvent;
import se.hig.thlu.asteroids.event.gamestate.GameOverEvent;
import se.hig.thlu.asteroids.event.gamestate.GameStateEventHandler;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.model.entity.Missile.MissileSource;
import se.hig.thlu.asteroids.util.Trigonometry;

import java.util.Optional;

public final class PlayerShip extends AbstractEntity implements Shooter {

	private static final double MAX_SPEED = 7.0;
	private static final double ACCELERATION = 0.04;
	private static final double DECELERATION = ACCELERATION / 2.5;
	private int lives = 3;
	private int ticksSinceLastShot = 1337;

	PlayerShip() {
		super(new Point(),
				new Velocity(),
				5.0,
				new Dim(35,
						26));
		center();
	}

	@Override
	public void setCenter(Point center) {
		super.setCenter(center);
		eventHandler.notify(new PlayerMoveEvent(center, id));
	}

	public void accelerate() {
		eventHandler.notify(new AccelerationEvent(true, id));
		Velocity acceleration = new Velocity(ACCELERATION, rotation);
		velocity.composeWith(acceleration);
		if (velocity.getSpeed() >= MAX_SPEED) {
			velocity = new Velocity(MAX_SPEED, velocity.getDirection());
		}
	}

	public void decelerate() {
		eventHandler.notify(new AccelerationEvent(false, id));
		if (velocity.getSpeed() < DECELERATION) {
			velocity = new Velocity(0.0, velocity.getDirection());
			return;
		}
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
	}

	@Override
	public void turnLeft() {
		super.turnLeft();
	}

	@Override
	public void turnRight() {
		super.turnRight();
	}

	@Override
	public void destroy() {
		if (lives == 1) {
			EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
					.notify(new GameOverEvent());
		} else {
			velocity = new Velocity();
			EventHandlerFactory.getEventHandler(CreateEventHandler.class)
					.notify(new LoseLifeEvent(lives));
		}
		lives--;
		Optional<Explosion> explosion = createDeathExplosion();
		EventHandlerFactory.getEventHandler(CreateEventHandler.class)
				.notify(new ExplosionCreateEvent(explosion.get()));
		center();
	}

	private void center() {
		setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
	}

	@Override
	protected Optional<Explosion> createDeathExplosion() {
		return Optional.of(new Explosion(center, Explosion.ExplosionSize.THREE));
	}

	public void shoot() {
		if (ticksSinceLastShot < MissileSource.PLAYER.getCoolDown()) {
			return;
		}
		ticksSinceLastShot = 0;
		double centerFrontDistance = ((double) getDimensions().getWidth() / 2.0);
		Point missileStart = Trigonometry.rotateAroundPoint(center,
				rotation,
				centerFrontDistance);
		Missile missile = new Missile(missileStart, rotation, MissileSource.PLAYER, 0.6);
		EventHandlerFactory.getEventHandler(CreateEventHandler.class)
				.notify(new MissileCreateEvent(missile));
	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public void update() {
		super.update();
		ticksSinceLastShot++;
	}

}