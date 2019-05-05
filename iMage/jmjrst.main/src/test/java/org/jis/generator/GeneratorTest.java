package org.jis.generator;

import static org.junit.Assert.*;

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
	public void test() {
		
	}

}
