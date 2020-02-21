package se.hig.thlu.asteroids.factory;

import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.Asteroid.AsteroidSize;
import se.hig.thlu.asteroids.model.EnemyShip;
import se.hig.thlu.asteroids.model.Point;

enum EntityFactory {
    ;

    static Asteroid[] createAsteroids(int largeAsteroids) {
        Asteroid[] asteroids = new Asteroid[largeAsteroids];
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid(new Point(0.0, 0.0), 1.0, 0.0, AsteroidSize.LARGE);
        }
        return asteroids;
    }

    static EnemyShip[] createEnemyShips(int enemyShips) {
        EnemyShip[] ships = new EnemyShip[enemyShips];
        for (int i = 0; i < ships.length; i++) {
            ships[i] = new EnemyShip(new Point(0.0, 0.0), 1.0, 0.0);
        }
        return ships;
    }

}
