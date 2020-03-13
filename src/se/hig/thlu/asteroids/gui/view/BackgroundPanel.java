package se.hig.thlu.asteroids.gui.view;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.IObserver;
import se.hig.thlu.asteroids.event.create.CreateEventHandler;
import se.hig.thlu.asteroids.event.create.ExplosionCreateEvent;
import se.hig.thlu.asteroids.event.entity.EntityEventHandler;
import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.AwtGraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;
import se.hig.thlu.asteroids.graphics.drawer.Drawer;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.AccelerationDrawer;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.AnimationDrawer;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.EntityDrawer;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.CenteredDrawingStrategy;
import se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.entity.*;
import se.hig.thlu.asteroids.model.entity.Asteroid.AsteroidSize;
import se.hig.thlu.asteroids.storage.AnimationResource;
import se.hig.thlu.asteroids.storage.ImageLoader;
import se.hig.thlu.asteroids.storage.ImageResource;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BackgroundPanel extends JPanel implements IObserver {

	private final Collection<Drawer> drawers = new CopyOnWriteArrayList<>();
	private final ImageLoader<? extends ImageAdapter> imageLoader;
	private ImageAdapter image;

	public BackgroundPanel(ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		setImage(imageLoader.getImage(ImageResource.BACKGROUND_PNG,
				new Dim(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT)));
		setLayout(new BorderLayout());
		setDoubleBuffered(true);
		EventHandlerFactory.getEventHandler(CreateEventHandler.class)
				.addObserver(this);
	}

	public void setImage(ImageAdapter image) {
		this.image = image;
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(), image.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		GraphicsAdapter graphics = new AwtGraphicsAdapter(g2d);

		Dimension dim = getSize();
		graphics.drawImage(image, 0, 0, dim.width, dim.height);

		drawers.forEach(drawer -> {
			drawer.draw(graphics);
			if (drawer.isFinished()) {
				drawers.remove(drawer);
			}
		});
		repaint();
	}

	@Override
	public void notify(Event event) {
//			if (propertyName.equals(ScoreKeeper.Score.SCORE_UPDATED.toString())) {
//				updateScore(score);
//			}
		if (event.getTypeString().equals(ExplosionCreateEvent.class.toString())) {
			Explosion explosion = (Explosion) event.getValue();
			Drawer explAnimation = createExplosionDrawer(explosion);
			drawers.add(explAnimation);
		} else if (event.getValue() instanceof Entity) {
			Optional<Drawer> entityDrawer = getDrawerFromEntity((Entity) event.getValue());
			entityDrawer.ifPresent(drawers::add);
		}
	}

	private Drawer createExplosionDrawer(Explosion explosion) {
		List<? extends ImageAdapter> animation =
				imageLoader.getAnimation(AnimationResource.EXPLOSIONS_ALL,
						explosion.getSize().getDimensions());
		return new AnimationDrawer(animation,
				explosion.getCenter(),
				5);
	}

	// TODO: Create map with Entity->Drawer mappings, load it at startup so that caching can occur in advance
	private Optional<Drawer> getDrawerFromEntity(Entity entity) {
		ImageAdapter image = null;
		if (entity instanceof Asteroid) {
			Asteroid asteroid = (Asteroid) entity;
			switch (asteroid.getAsteroidSize()) {
				case LARGE:
					image = imageLoader.getImage(ImageResource.ASTEROID_LARGE_PNG,
							new Dim(AsteroidSize.LARGE.getWidth(),
									AsteroidSize.LARGE.getHeight()));
					break;
				case MEDIUM:
					image = imageLoader.getImage(ImageResource.ASTEROID_MEDIUM_PNG,
							new Dim(AsteroidSize.MEDIUM.getWidth(),
									AsteroidSize.MEDIUM.getHeight()));
					break;
				case SMALL:
					image = imageLoader.getImage(ImageResource.ASTEROID_SMALL_PNG,
							new Dim(AsteroidSize.SMALL.getWidth(),
									AsteroidSize.SMALL.getHeight()));
					break;
			}
		} else if (entity instanceof EnemyShip) {
			image = imageLoader.getImage(ImageResource.ENEMY_SHIP_SMALL, entity.getDimensions());
		} else if (entity instanceof PlayerShip) {
			Dim accelerationDim = new Dim(entity.getDimensions().getWidth() / 2,
					entity.getDimensions().getHeight());
			ImageAdapter accelerationImg =
					imageLoader.getImage(ImageResource.PLAYER_SHIP_ACCELERATION_PNG, accelerationDim);
			image = imageLoader.getImage(ImageResource.PLAYER_SHIP_PNG, entity.getDimensions());

			EntityDrawer shipDrawer = new EntityDrawer(new CenteredDrawingStrategy(),
					DrawingParameters.fromEntity(entity, image));
			EntityDrawer acceleration = new AccelerationDrawer(DrawingParameters.fromEntity(entity, accelerationImg),
					shipDrawer);

			EventHandlerFactory.getEventHandler(EntityEventHandler.class)
					.addObserverMapping(entity.getId(), acceleration);
			EventHandlerFactory.getEventHandler(EntityEventHandler.class)
					.addObserverMapping(entity.getId(), shipDrawer);
			return Optional.of(acceleration);
		} else if (entity instanceof Missile) {
			Missile missile = (Missile) entity;
			if (missile.getMissileSource() == Missile.MissileSource.PLAYER) {
				image = imageLoader.getImage(ImageResource.MISSILE_PLAYER, entity.getDimensions());
			} else {
				image = imageLoader.getImage(ImageResource.MISSILE_ENEMY, entity.getDimensions());
			}
		}

		if (image != null) {
			EntityDrawer drawer = new EntityDrawer(new CenteredDrawingStrategy(),
					DrawingParameters.fromEntity(entity, image));
			EventHandlerFactory.getEventHandler(EntityEventHandler.class)
					.addObserverMapping(entity.getId(), drawer);
			return Optional.of(drawer);
		}
		return Optional.empty();
	}

}