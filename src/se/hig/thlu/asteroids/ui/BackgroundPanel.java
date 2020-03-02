package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.graphics.entitydrawer.PlayerShipDrawerAwt;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.AwtGraphicsAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;

import javax.swing.*;
import java.awt.*;

/*
 *  Support custom painting on a panel in the form of
 *	http://www.camick.com/java/source/BackgroundPanel.java
 *
 *  a) images - that can be scaled, tiled or painted at original size
 *  b) non solid painting - that can be done by using a Paint object
 *
 *  Also, any component added directly to this panel will be made
 *  non-opaque so that the custom painting can show through.
 */
public class BackgroundPanel extends JPanel {

	private final PlayerShipDrawerAwt playerShipDrawer;
	private Paint painter;
	private ImageAdapter image;
	private double alignmentX = 0.5;
	private double alignmentY = 0.5;
	private boolean isTransparentAdd = true;

	/*
	 *  Set image as the background with the specified style
	 */
	public BackgroundPanel(PlayerShipDrawerAwt playerShipDrawer, ImageAdapter image) {
		this.playerShipDrawer = playerShipDrawer;
		setImage(image);
		setLayout(new BorderLayout());
	}

	/*
	 *	Set the image used as the background
	 */
	public void setImage(ImageAdapter image) {
		this.image = image;
		repaint();
	}


	/*
	 *  Override to provide a preferred size equal to the image size
	 */
	@Override
	public Dimension getPreferredSize() {
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(), image.getHeight());
	}


	/*
	 *	Try to make the component transparent.
	 *  For components that use renderers, like JTable, you will also need to
	 *  change the renderer to be transparent. An easy way to do this it to
	 *  set the background of the table to a Color using an alpha value of 0.
	 */
	private void makeComponentTransparent(JComponent component) {
		component.setOpaque(false);
	}

	/*
	 *  Add custom painting
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//  Invoke the painter for the background
		if (painter != null) {
			Dimension d = getSize();
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(painter);
			g2.fill(new Rectangle(0, 0, d.width, d.height));

		}

		if (image == null) return;
		//  Draw the image
		drawScaled(g);
	}

	/*
	 *  Custom painting code for drawing a SCALED image as the background
	 */
	private void drawScaled(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GraphicsAdapter graphics = new AwtGraphicsAdapter(g2d);

		Dimension dim = getSize();
		graphics.drawImage(image, 0, 0, dim.width, dim.height);

		playerShipDrawer.draw(graphics);

		repaint();
	}

}