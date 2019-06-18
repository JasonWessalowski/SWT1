package org.iMage.iCatcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.iMage.HDrize.base.images.HDRImage;

/**
 * A subclass of JPanel, which serves as a container to display a selected set of images.
 * 
 * @author Jason Wessalowski
 */
public class ImagePanel extends JPanel {

	private JPanel windowOne;
	private JPanel windowTwo;
	private HDRImage hdrImage;
	
	/**
	 * Constructor to generate the ImagePanel.
	 */
	public ImagePanel() {
		
		// Set up size and layout.
		this.setPreferredSize(new Dimension(800, 300));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 33, 25));
		
		// Set up place holders.
		this.placeDummy();
		
	}
	
	/**
	 * Generate place holders until a set of images has been loaded.
	 */
	private void placeDummy() {
		
		this.windowOne = new JPanel();
		this.windowOne.setPreferredSize(new Dimension(350, 250));
		this.windowOne.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.windowOne.setLayout(null);
		
		this.windowTwo = new JPanel();
		this.windowTwo.setPreferredSize(new Dimension(350, 250));
		this.windowTwo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.windowOne.setLayout(null);
		
		this.add(windowOne);
		this.add(windowTwo);
		
	}
	
	/**
	 * Will remove all the place holder dummies.
	 */
	public void removeDummy() {
		
		this.removeAll();
		
	}
	
	/**
	 * Resize given image so it fits the window of 350x250.
	 * 
	 * @param dir Path to image.
	 * @return Resized image icon.
	 */
	private static ImageIcon resize(String dir) {
		
		// Load image icon and transform it.
		ImageIcon imgIcon = new ImageIcon(dir);
		Image img = imgIcon.getImage();
		// Instantiate new image as placeholder.
		Image newImg;
		
		// What is bigger, it's height or width? Change size based on that.
		if (imgIcon.getIconHeight() >= imgIcon.getIconWidth()) {
			// Calculate new width.
			int width = (int) Math.round(imgIcon.getIconWidth() * (250.0 / imgIcon.getIconHeight()));
			
			newImg = img.getScaledInstance(width, 250, java.awt.Image.SCALE_SMOOTH);
			
		} else {
			// Calculate new height.
			int height = (int) Math.round(imgIcon.getIconHeight() * (350.0 / imgIcon.getIconWidth()));

			newImg = img.getScaledInstance(350, height, java.awt.Image.SCALE_SMOOTH);
			
		}
		
		imgIcon = new ImageIcon(newImg);
		
		return imgIcon;
		
	}
	
	/**
	 * Draw preview image in the first window.
	 * 
	 * @param dirOne Path to desired image.
	 */
	public void drawPreview(String dirOne) {
		
		ImageIcon icon = resize(dirOne);
		JLabel image = new JLabel(icon);
		image.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		this.windowOne.add(image);
		
	}
	
}
