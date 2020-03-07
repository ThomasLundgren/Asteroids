package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.graphics.entitydrawer.AccelerationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.AnimationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.EntityDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.CenteredDrawingStrategy;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.storage.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class SwingGUI extends JFrame implements GUI<AwtKeyboardAdapter> {

	// TODO: Use canvas instead because of BufferedStrategy?
	private final ImageLoader<? extends ImageAdapter> imageLoader;
	private BackgroundPanel backgroundPanel;

	public SwingGUI(ImageLoader<? extends ImageAdapter> imageLoader) {
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
		setSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void onNotify(String propertyName, Event event) {
		if (propertyName.equals(GameController.Action.REMOVE.toString())) {
			backgroundPanel.removeEntityDrawer(event.getId());
		} else if (propertyName.equals(GameController.Action.ADD.toString())) {
			Object newValue = event.getValue();
			if (newValue instanceof Explosion) {
				Explosion explosion = (Explosion) newValue;
				List<? extends ImageAdapter> images =
						imageLoader.getAnimationResource(ImageLoader.AnimationResource.EXPLOSIONS_ALL);
				AnimationDrawer explAnimation = new AnimationDrawer(images,
						explosion.getCenter(),
						1);
				backgroundPanel.addAnimationDrawer(explAnimation);
			} else if (newValue instanceof Entity) {
				Optional<EntityDrawer> entityDrawer = getDrawerFromEntity(event.getValue());
				entityDrawer.ifPresent(drawer -> backgroundPanel.addEntityDrawer(event.getId(), drawer));
			}
		}
	}

	// TODO: This is not clean... Store images inside of Entities? Or create a hashmap with mappings from properties
	//  to Images?
	private Optional<EntityDrawer> getDrawerFromEntity(Object entity) {
		ImageAdapter image = null;

		if (entity instanceof Asteroid) {
			Asteroid asteroid = (Asteroid) entity;
			switch (asteroid.getAsteroidSize()) {
				case LARGE:
					image = imageLoader.getImageResource(ImageLoader.ImageResource.ASTEROID_LARGE_PNG);
					break;
				case MEDIUM:
					image = imageLoader.getImageResource(ImageLoader.ImageResource.ASTEROID_MEDIUM_PNG);
					break;
				case SMALL:
					image = imageLoader.getImageResource(ImageLoader.ImageResource.ASTEROID_SMALL_PNG);
					break;
			}
		} else if (entity instanceof EnemyShip) {
			image = imageLoader.getImageResource(ImageLoader.ImageResource.ENEMY_SHIP_SMALL);
		} else if (entity instanceof PlayerShip) {
			ImageAdapter accelerationImg =
					imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_ACCELERATION_PNG);
			image = imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_PNG);

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
				image = imageLoader.getImageResource(ImageLoader.ImageResource.MISSILE_PLAYER);
			} else {
				image = imageLoader.getImageResource(ImageLoader.ImageResource.MISSILE_ENEMY);
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
