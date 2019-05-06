package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * Für Aufgabe 2
 */
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

		bufferedImage = editedImage;

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

		bufferedImage = editedImage;

	}
	
	@Test
	public void rotateImageTestMinus90() {

		BufferedImage editedImage = generator.rotateImage(bufferedImage, Math.toRadians(-90));
		BufferedImage comparisonImage = generator.rotateImage(bufferedImage, Math.toRadians(270));

		assertEquals("Überprüfe ob Höhe des Bildes mit Drehung -90° mit der von 270° übereinstimmen.", bufferedImage.getHeight(), editedImage.getWidth());
		assertEquals("Überprüfe ob Breite des Bildes mit Drehung -90° mit der von 270° übereinstimmen.", bufferedImage.getWidth(), editedImage.getHeight());

		for (int i = 0; i < editedImage.getWidth(); i++) {
			
			for (int j = 0; j < editedImage.getHeight(); j++) {
				
				int editedPixel = editedImage.getRGB(i, j);
				int comparisonPixel = comparisonImage.getRGB(i, j);
	
				assertEquals("Stimmen die individuellen Pixel überein?", editedPixel, comparisonPixel);
				
			}
			
		}

		bufferedImage = editedImage;

	}

	@Test
	public void rotateImageTestMinus270() {

		BufferedImage editedImage = generator.rotateImage(bufferedImage, Math.toRadians(-270));
		BufferedImage comparisonImage = generator.rotateImage(bufferedImage, Math.toRadians(90));
		
		assertEquals("Überprüfe ob Höhe des Bildes mit Drehung -270° mit der von 90° übereinstimmen.", bufferedImage.getHeight(), editedImage.getWidth());
		assertEquals("Überprüfe ob Höhe des Bildes mit Drehung -270° mit der von 90° übereinstimmen.", bufferedImage.getWidth(), editedImage.getHeight());
		
		for (int i = 0; i < editedImage.getWidth(); i++) {
			
			for (int j = 0; j < editedImage.getHeight(); j++) {
				
				int editedPixel = editedImage.getRGB(i, j);
				int comparisonPixel = comparisonImage.getRGB(i, j);
	
				assertEquals("Stimmen die individuellen Pixel überein?", editedPixel, comparisonPixel);
				
			}
			
		}

		bufferedImage = editedImage;

	}

	@After
	public void tearDown() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd_HH.mm.ss.SSS");
		String dateInfo = sdf.format(date);
		File product = new File("target/test/image" + "_rotated_" + sdf.format(date) + ".jpg");

		if (bufferedImage != null) {
			
			try {

				ImageIO.write(bufferedImage, "jpg", product);

			} catch (IOException e) {

				fail(e.getMessage());

			}
		
		}

	}
}
