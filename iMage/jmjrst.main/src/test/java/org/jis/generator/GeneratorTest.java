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
	public void rotateImageTestOne() {
		
		assertEquals("Überprüfe ob beliebiges Bild bei rotateImage() unverändert zurückgegeben wird, wenn angle=0.0 ist.", bufferedImage, generator.rotateImage(bufferedImage, 0.0));
		
	}
	
	@Test
	public void totateImageTestTwo() {
		
		assertNull("Überprüfe ob null zurückgegeben wird, wenn null in rotateImage() eingesetzt wird.", generator.rotateImage(null, 0.0));
		
	}

}
