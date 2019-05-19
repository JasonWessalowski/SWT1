package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrix;
/**
 * Implementation of the IMatrix interface in this class matrix.
 * 
 * @author Jason Wessalowski
 *
 */
public final class Matrix implements IMatrix {

	private double[][] content;

  /**
   * Create a new matrix.
   *
   * @param mtx
   *          the original matrix
   */
  public Matrix(IMatrix mtx) {

	  this.content = mtx.copy();

  }

  /**
   * Create a new matrix.
   *
   * @param mtx
   *          the original matrix mtx[Rows][Cols]
   */
  public Matrix(double[][] mtx) {

	  this.content = mtx;

  }

  /**
   * Create a matrix (only zeros).
   *
   * @param rows
   *          the amount of rows
   * @param cols
   *          the amount of columns
   */
  public Matrix(int rows, int cols) {

	  this.content = new double[rows][cols];

  }

  @Override
  public double[][] copy() {

	  return this.content;

  }

  @Override
  public int rows() {

	  return this.content.length;

  }

  @Override
  public int cols() {

	  return this.content[0].length;

  }

  @Override
  public void set(int r, int c, double v) {

	  this.content[r][c] = v;

  }

  @Override
  public double get(int r, int c) {

	  return this.content[r][c];

  }

}
