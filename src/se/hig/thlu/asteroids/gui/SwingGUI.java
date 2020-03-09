package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.gamestate.GameController;
import se.hig.thlu.asteroids.graphics.entitydrawer.AccelerationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.AnimationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.Drawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.EntityDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.CenteredDrawingStrategy;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.entity.*;
import se.hig.thlu.asteroids.model.entity.Asteroid.AsteroidSize;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;
import se.hig.thlu.asteroids.storage.AnimationResource;
import se.hig.thlu.asteroids.storage.ImageResource;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class SwingGUI extends JFrame implements GUI<AwtKeyboardAdapter> {

	// TODO: Use canvas instead because of BufferedStrategy?
	private final AbstractImageLoader<? extends ImageAdapter> imageLoader;
	private BackgroundPanel backgroundPanel;

	public SwingGUI(AbstractImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		backgroundPanel = new BackgroundPanel(imageLoader);
		add(backgroundPanel);
		configureFrame();
	}

	@Override
	public void addEventListener(AwtKeyboardAdapter listener) {
		addKeyListener(listener);
	}

	private void configureFrame() {
		setResizable(false);
		setSize(new java.awt.Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void onNotify(String propertyName, Event event) {
		if (propertyName.equals(GameController.Action.ADD.toString())) {
			Object newValue = event.getValue();
			if (newValue instanceof Explosion) {
				Explosion explosion = (Explosion) newValue;
				List<? extends ImageAdapter> animation =
						imageLoader.getAnimation(AnimationResource.EXPLOSIONS_ALL,
								explosion.getSize().getDimensions());
				Drawer explAnimation = new AnimationDrawer(animation,
						explosion.getCenter(),
						5);
				backgroundPanel.addDrawer(explAnimation);
			} else if (newValue instanceof Entity) {
				Optional<Drawer> entityDrawer = getDrawerFromEntity((Entity) event.getValue());
				entityDrawer.ifPresent(drawer -> backgroundPanel.addDrawer(drawer));
			}
		}
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

			Entity ent = (Entity) entity;
			EntityDrawer shipDrawer = new EntityDrawer(new CenteredDrawingStrategy(),
					DrawingParameters.fromEntity(ent, image));
			EntityDrawer acceleration = new AccelerationDrawer(DrawingParameters.fromEntity(ent, accelerationImg),
					shipDrawer);

			ent.addObserver(acceleration);
			ent.addObserver(shipDrawer);
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
			Entity ent = (Entity) entity;
			EntityDrawer drawer = new EntityDrawer(new CenteredDrawingStrategy(),
					DrawingParameters.fromEntity(ent, image));
			ent.addObserver(drawer);
			return Optional.of(drawer);
		}
		return Optional.empty();
	}

}
