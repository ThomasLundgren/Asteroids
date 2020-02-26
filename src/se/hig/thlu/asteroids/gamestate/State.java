package se.hig.thlu.asteroids.gamestate;

public abstract class State {

//	public enum PressedKey {
//		LEFT_ARROW(KeyEvent.VK_LEFT),
//		UP_ARROW(KeyEvent.VK_UP),
//		RIGHT_ARROW(KeyEvent.VK_DOWN),
//		SPACE_BAR(KeyEvent.VK_SPACE);
//
//		private int vkKey;
//
//		PressedKey(int vkKey) {
//			this.vkKey = vkKey;
//		}
//
//		public int getVkKey() {
//			return vkKey;
//		}
//	}
//
//	private static State currentState;
//	protected static long totalGameTime = 0;
//	protected static long nextSpawn = GameConfig.INITIAL_SPAWN_INTERVAL;
//	protected static double timeSinceLastShot = Long.MAX_VALUE;
//	protected static PlayerShip playerShip = new PlayerShip();
//	protected static List<Asteroid> asteroids;
//	protected static List<EnemyShip> enemyShips;
//	protected static List<Missile> missiles;
//
//	public static void reset(StateClient client) {
//		setState(new NormalState());
//	}
//
//	protected static void setState(State newState) { currentState = newState; }
//
//	public static State getState() { return currentState; }
//
//	public void update(double delta) {
//		totalGameTime = (long) ((double) totalGameTime + delta);
//		nextSpawn = (long) ((double) nextSpawn - delta);
//		timeSinceLastShot += delta;
//		checkCollisions();
//	}
//
//	/* TODO: We only need to check if outgoing missiles have hit
//	    an enemy or if PlayerShip has collided with anything.
//	*/
//	protected abstract void checkCollisions();
//
//	public void handleKeyPressed(PressedKey key) {
//		switch (key) {
//			case LEFT_ARROW:
//				turnLeft();
//				break;
//			case UP_ARROW:
//				accelerate();
//				break;
//			case RIGHT_ARROW:
//				turnRight();
//				break;
//			default:
//				shoot();
//				break;
//		}
//	}
//
//	protected void shoot() {
//		if (timeSinceLastShot > GameConfig.PLAYER_SHOOTING_DELAY_MS) {
//			System.out.println("shoot");
//			timeSinceLastShot = 0.0;
//		}
//	}
//
//	protected void turnRight() {
//		// TODO
//	}
//
//	protected void accelerate() {
//		// TODO
//	}
//
//	protected void turnLeft() {
//		// TODO
//	}

}
