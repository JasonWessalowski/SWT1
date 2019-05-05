package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneratorTest {

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

	@Test
	public void rotateImageTestRandom() {
		
		assertEquals("Überprüfe ob beliebiges Bild bei rotateImage() unverändert zurückgegeben wird, wenn angle=0.0 ist.", bufferedImage, generator.rotateImage(bufferedImage, 0.0));
		
	}
	
	@Test
	public void rotateImageTestNull() {
		
		assertNull("Überprüfe ob null zurückgegeben wird, wenn null in rotateImage() eingesetzt wird.", generator.rotateImage(null, 0.0));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rotateImageTestException() {

		generator.rotateImage(bufferedImage, 0.42);

	}

	@Test
	public void rotateImageTest90() {

		BufferedImage editedImage = generator.rotateImage(bufferedImage, Math.toRadians(90));

		assertEquals("Überprüfe ob Höhe des Bildes vor Drehng mit Breite des gedrehten Bildes übereinstimmt.", bufferedImage.getHeight(), editedImage.getWidth());
		assertEquals("Überprüfe ob Breite des Bildes vor Drehng mit Höhe des gedrehten Bildes übereinstimmt.", bufferedImage.getWidth(), editedImage.getHeight());
		
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				
				int originalPixel = bufferedImage.getRGB(i, j);
				int editedPixel = editedImage.getRGB(editedImage.getWidth() - 1 - j, i);
	
				assertEquals("Stimmen die individuellen Pixel überein?", originalPixel, editedPixel);
				
			}
			
		}

	}
	
	@Test
	public void rotateImageTest270() {

		BufferedImage editedImage = generator.rotateImage(bufferedImage, Math.toRadians(270));

		assertEquals("Überprüfe ob Höhe des Bildes vor Drehng mit Breite des gedrehten Bildes übereinstimmt.", bufferedImage.getHeight(), editedImage.getWidth());
		assertEquals("Überprüfe ob Breite des Bildes vor Drehng mit Höhe des gedrehten Bildes übereinstimmt.", bufferedImage.getWidth(), editedImage.getHeight());

		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				
				int originalPixel = bufferedImage.getRGB(i, j);
				int editedPixel = editedImage.getRGB(j, editedImage.getHeight() - 1 - i);
	
				assertEquals("Stimmen die individuellen Pixel überein?", originalPixel, editedPixel);
				
			}
			
		}

	}
}
