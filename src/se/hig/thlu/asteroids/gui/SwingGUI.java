package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.GameController;
import se.hig.thlu.asteroids.graphics.entitydrawer.AccelerationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.AnimationDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.Drawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.EntityDrawer;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.CenteredDrawingStrategy;
import se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy.DrawingParameters;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.AwtKeyboardAdapter;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.entity.*;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.storage.AbstractImageLoader;
import se.hig.thlu.asteroids.storage.AnimationResource;
import se.hig.thlu.asteroids.storage.ImageResource;

import javax.swing.*;
import java.awt.*;
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
		setSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
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
				int width = explosion.getSize().getWidth();
				int height = explosion.getSize().getHeight();
				se.hig.thlu.asteroids.model.Dimension dimension =
						new se.hig.thlu.asteroids.model.Dimension(width, height);
				List<? extends ImageAdapter> animation =
						imageLoader.getAnimation(AnimationResource.EXPLOSIONS_ALL, dimension);
				Drawer explAnimation = new AnimationDrawer(animation,
						explosion.getCenter(),
						2);
				backgroundPanel.addDrawer(explAnimation);
			} else if (newValue instanceof Entity) {
				Optional<Drawer> entityDrawer = getDrawerFromEntity(event.getValue());
				entityDrawer.ifPresent(drawer -> backgroundPanel.addDrawer(drawer));
			}
		}
	}

	// TODO: This is not clean... Store images inside of Entities? Or create a hashmap with mappings from properties
	//  to Images?
	private Optional<Drawer> getDrawerFromEntity(Object entity) {
		ImageAdapter image = null;
		if (entity instanceof Asteroid) {
			Asteroid asteroid = (Asteroid) entity;
			switch (asteroid.getAsteroidSize()) {
				case LARGE:
					image = imageLoader.getImage(ImageResource.ASTEROID_LARGE_PNG);
					break;
				case MEDIUM:
					image = imageLoader.getImage(ImageResource.ASTEROID_MEDIUM_PNG);
					break;
				case SMALL:
					image = imageLoader.getImage(ImageResource.ASTEROID_SMALL_PNG);
					break;
			}
		} else if (entity instanceof EnemyShip) {
			image = imageLoader.getImage(ImageResource.ENEMY_SHIP_SMALL);
		} else if (entity instanceof PlayerShip) {
			ImageAdapter accelerationImg =
					imageLoader.getImage(ImageResource.PLAYER_SHIP_ACCELERATION_PNG);
			image = imageLoader.getImage(ImageResource.PLAYER_SHIP_PNG);

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
				image = imageLoader.getImage(ImageResource.MISSILE_PLAYER);
			} else {
				image = imageLoader.getImage(ImageResource.MISSILE_ENEMY);
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
