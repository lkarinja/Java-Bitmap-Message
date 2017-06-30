/*
Copyright © 2015-2017 Leejae Karinja

This file is part of Java Bitmap Message.

Java Bitmap Message is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Java Bitmap Message is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Java Bitmap Message.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageViewer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final short BOUND_X = 30;
	private static final short BOUND_Y = 30;

	private JFrame frame = null;

	private BufferedImage buffer = null;

	/**
	 * Default Constructor
	 */
	public ImageViewer() {
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setAlwaysOnTop(true);
		this.frame.add(this);
	}

	/**
	 * Constructor with specified BufferedImage to draw
	 * 
	 * @param b
	 *            BufferedImage to draw to JFrame
	 */
	public ImageViewer(BufferedImage b) {
		this.frame = new JFrame();
		this.buffer = b;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBounds(BOUND_X, BOUND_Y, this.buffer.getWidth() + BOUND_X, this.buffer.getHeight() + BOUND_Y);
		this.frame.setResizable(false);
		this.frame.setAlwaysOnTop(true);
		this.frame.add(this);
		this.frame.setVisible(true);
	}

	/**
	 * Sets BufferedImage to draw
	 * 
	 * @param b
	 *            BufferdImage to draw to JFrame
	 * @throws ObjectException
	 */
	public void setImage(BufferedImage b) throws ObjectException {
		if (this.frame == null)
			throw new ObjectException("Frame needs to be constructed first");
		this.buffer = b;
		this.frame.setBounds(BOUND_X, BOUND_Y, this.buffer.getWidth() + BOUND_X, this.buffer.getHeight() + BOUND_Y);
		this.frame.setVisible(true);
		return;
	}

	/**
	 * Draws the BufferedImage to the JFrame
	 * 
	 * @throws ObjectException
	 */
	public void drawImage() throws ObjectException {
		if (this.buffer == null)
			throw new ObjectException("Image needs to be set first");
		this.repaint();
		return;
	}

	/**
	 * Paints the actual Image to the JFrame
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.buffer, 0, 0, this);
		return;
	}
}
