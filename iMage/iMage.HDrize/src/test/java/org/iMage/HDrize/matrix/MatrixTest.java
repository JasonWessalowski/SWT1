package org.iMage.HDrize.matrix;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for the class Matrix.
 * 
 * @author Jason Wessalowski
 *
 */
public class MatrixTest {

	Matrix matrix;

	/**
	 * Initialize a new matrix object to for testing, prior to every test.
	 */
	@Before
	public void setUp() {
		
		matrix = new Matrix(3, 3);
		
	}

	/**
	 * Will the method get() throw an OutOfBounds exception when unsuitable coordinates are given?
	 */
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getTest() {

		matrix.get(0, matrix.cols());
		
	}

}