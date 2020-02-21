package se.hig.thlu.asteroids.gamestate;

/**
 * Credit to http://www.java-gaming.org/index.php?topic=24220.0
 * and http://www.cokeandcode.com/info/showsrc/showsrc.php?src=../spaceinvaders102/org/newdawn/spaceinvaders/Game.java
 *
 */
public class GameLoop {

    public static final int TARGET_FPS = 60;
    public static final long MILLION = 1000000L;
    public static final long BILLION = 1000000000L;
    public static final long OPTIMAL_TIME = BILLION / (long) TARGET_FPS;
    private boolean isRunning = true;

    public void gameLoop() {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0L;
        int fps = 0;

        while (isRunning) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            // update the frame counter
            lastFpsTime += updateLength;
            fps++;

            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= BILLION) {
                System.out.println("(FPS: " + fps + ")");
                lastFpsTime = 0L;
                fps = 0;
            }

            // update the game logic
            State.getState().update(delta);

            // draw everyting
//            ui.render();

            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give
            // us our final value to wait for
            // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / MILLION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
