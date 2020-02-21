package se.hig.thlu.asteroids.factory;

import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.EnemyShip;

public class LevelFactory {

    public enum LevelConfig {

        ONE(4, 3),
        TWO(5, 4),
        THREE(6, 5);

        private final Asteroid[] asteroids;
        private final EnemyShip[] enemyShips;

        LevelConfig(int asteroids, int enemyShips) {
            this.asteroids = EntityFactory.createAsteroids(asteroids);
            this.enemyShips = EntityFactory.createEnemyShips(enemyShips);
        }

        public Asteroid[] getAsteroids() {
            return asteroids.clone();
        }

        public EnemyShip[] getEnemyShips() {
            return enemyShips.clone();
        }
    }

    private final LevelConfig level;

    public LevelFactory(LevelConfig level) {
        this.level = level;
    }

}
