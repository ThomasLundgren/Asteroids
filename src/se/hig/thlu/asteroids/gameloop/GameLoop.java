package se.hig.thlu.asteroids.gameloop;

import se.hig.thlu.asteroids.controller.GameController;

/**
 * Credit to http://www.java-gaming.org/index.php?topic=24220.0
 * and http://www.cokeandcode.com/info/showsrc/showsrc.php?src=../spaceinvaders102/org/newdawn/spaceinvaders/Game.java
 */
public class GameLoop {

	public static final int TARGET_FPS = 60;
	public static final long MILLION = 1000000L;
	public static final long BILLION = 1000000000L;
	public static final long OPTIMAL_TIME = BILLION / (long) TARGET_FPS;
	private boolean isRunning = true;
	private final GameController gameController;

	public GameLoop(GameController gameController) {
		this.gameController = gameController;
	}

	public void gameLoop() {
		long lastLoopTime = System.nanoTime();
		long lastFpsTime = 0L;
		int fps = 0;

		while (isRunning) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

//            lastFpsTime += updateLength;
//            fps++;
//
//            if (lastFpsTime >= BILLION) {
//                System.out.println("(FPS: " + fps + ")");
//                lastFpsTime = 0L;
//                fps = 0;
//            }

			gameController.update(delta);

			try {
				long millis = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / MILLION;
				if (millis > 0L) {
					Thread.sleep(millis);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
