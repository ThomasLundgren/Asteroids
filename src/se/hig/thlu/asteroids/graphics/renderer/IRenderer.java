package se.hig.thlu.asteroids.graphics.renderer;

public interface IRenderer {

	void drawImageWithRotation(IImage image, double angle, double anchorX, double anchorY, int x, int y);

	void drawImage(IImage image, int x, int y);

	void drawImage(IImage img, int x, int y, int width, int height);

}
