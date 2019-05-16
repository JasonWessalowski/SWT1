package org.jis.generator;

import static org.junit.Assert.*;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/*
 * Für Aufgabe 3
 */
public class GeneratorTestTwo {

	Generator generator;
	BufferedImage bufferedImage;
	
	@Before
	public void setUp() {
		
		generator = new Generator(null, 0);
		
		try {
			
			bufferedImage = ImageIO.read(getClass().getResource("/image.jpg"));
			
		} catch (IOException e) {
			
			fail(e.getMessage());
			
		}

	}

  // Liefert eine 360° Drehung das selbe, wie das unveränderte Original?
  @Test
  public void rotateImage360Test() {
	  
	  BufferedImage editedImage = generator.rotateImage(bufferedImage, Math.toRadians(90));
	  editedImage = generator.rotateImage(editedImage, Math.toRadians(270));
	  
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				
				int originalPixel = bufferedImage.getRGB(i, j);
				int editedPixel = editedImage.getRGB(i, j);
	
				assertEquals("Stimmen die individuellen Pixel überein?", originalPixel, editedPixel);
				
			}
			
		}
	  
  }

}
