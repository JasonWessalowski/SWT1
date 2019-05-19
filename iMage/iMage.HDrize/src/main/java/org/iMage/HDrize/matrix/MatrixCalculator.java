package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrixCalculator;

/**
 * Implementation of inverting, multiplying and transposition as methods for matrices.
 * 
 * @author Jason Wessalowski
 *
 */
public class MatrixCalculator implements IMatrixCalculator<Matrix> {

  @Override
  public Matrix inverse(Matrix mtx) {

	  // Create new matrix of the same size as input matrix.
	  Matrix inverse = new Matrix(mtx.rows(), mtx.cols());
	  // Calculate the determinant of the input matrix.
	  double determinant = determinant(mtx);
	  
	  // Get inverse of determinant, if possible.
	  double factor = 1.0;
	  
	  try {
		  
		  factor = 1 / determinant;
		  
	  } catch (ArithmeticException e) {
		  
		  System.out.println(e.getMessage());
		  
	  }
	  
	  // Calculate the inverse matrix by multiplying the adjugate matrix with the inverse of the determinant.
	  for (int row = 0; row < mtx.rows(); row++) {
		  
		  for (int col = 0; col < mtx.cols(); col++) {
			  
			  inverse.set(row, col, factor * Math.pow(-1, row + col) * determinant(incision(mtx, row, col)));
			  
		  }
		  
	  }
	  
	  return inverse;

  }
  
  /**
   * Calculates the determinant of a matrix.
   * 
   * @param matrix matrix Relevant matrix.
   * @return Determinant of given matrix.
   * @throws UnsupportedOperationException Exception in case the matrix is not quadratic.
   */
  public static double determinant(Matrix matrix) throws UnsupportedOperationException {
	  
	  // Don't proceed if matrix isn't quadratic.
	  if (matrix.rows() != matrix.cols()) {

		  throw new UnsupportedOperationException("Given matrix was not quadratic!");
		  
	  }
	  
	  double result = 0.0;
	  
	  // Return only value when matrix is size 1x1.
	  if (matrix.rows() == 1) {
		  
		  return matrix.get(0, 0);
		  
	  }
	  
	  // Calculate determinant via recursion and the Laplace equation.
	  for (int col = 0; col < matrix.cols(); col++) {
		  
		  result += Math.pow(-1, col) * matrix.get(0, col) * determinant(incision(matrix, 0, col));
		  
	  }
	  
	  return result;
	  
  }
  
  /**
   * Removes selected row and column from matrix.
   * 
   * @param matrix Given matrix to edit.
   * @param rowRmv Selected row.
   * @param colRmv Selected column.
   * @return Same matrix just without the selected row and column.
   */
  public static Matrix incision(Matrix matrix, int rowRmv, int colRmv) {
	  
	  // Create empty matrix with one row and column less.
	  Matrix result = new Matrix(matrix.rows() - 1, matrix.cols() - 1);
	  
	  // Insert the value of input matrix to the same spot of the new matrix until you reach the cross section.
	  // In that case insert the value of the following spot, as to skip the cross section.
	  for (int row = 0; row < result.rows(); row++) {
		  
		  for (int col = 0; col < result.cols(); col++) {
			  
			  if (col >= colRmv) {
				  
				  result.set(row, col, matrix.get(row, col + 1));
				  
			  } else if (row >= rowRmv) {
				  
				  result.set(row, col, matrix.get(row + 1, col));
				  
			  }
			  
			  result.set(row, col, matrix.get(row, col));
			  
		  }
		  
	  }
	  
	  return result;
	  
  }

  @Override
  public Matrix multiply(Matrix a, Matrix b) throws UnsupportedOperationException {

	  // Check if the number of columns in a are identical to the number of rows in b.
	  if (a.cols() != b.rows()) {
		  
		  throw new UnsupportedOperationException("Number of columns of first matrix doesn't"
				  								+ "match number of rows of second matrix!");
		  
	  }
	  
	  // Create new empty matrix to save the results.
	  int rows = a.rows();
	  int cols = b.cols();
	  Matrix product = new Matrix(rows, cols);
	  
	  // Multiply products to corresponding position and summarize all of them.
	  // Then insert them to their appropriate spot.
	  for (int row = 0; row < product.rows(); row++) {
		  
		  for (int col = 0; col < product.cols(); col++) {
			  
			  product.set(row, col, summationSeries(a, b, row, col));
			  
		  }
		  
	  }
	  
	  return product;

  }
  
  /**
   * Method to help with matrix multiplication by multiplying and adding components.
   * 
   * @param row Row of the currently handled matrix value.
   * @param col Column of the currently handled matrix value.
   * @return Product and sum of relevant components for the current spot in the product matrix.
   */
  private static double summationSeries(Matrix a, Matrix b, int row, int col) {
	  
	  double sum = 0;
	  
	  // Multiply products to corresponding position and summarize all of them.
	  for (int i = 0; i < a.cols(); i++) {
		  
		  sum += a.get(row, i) * b.get(i, col);
		  
	  }
	  
	  return sum;
	  
  }

  @Override
  public Matrix transpose(Matrix mtx) {

	  // Create new empty matrix with possibly new size to save results.
	  Matrix result = new Matrix(mtx.cols(), mtx.rows());
	  
	  // Swap values with their counterpart for transposition.
	  for (int row = 0; row < mtx.rows(); row++) {
		  
		  for (int col = 0; col < mtx.cols(); col++) {
			  
			  result.set(row, col, mtx.get(col, row));
			  
		  }
		  
	  }
	  
	  return result;

  }

}
