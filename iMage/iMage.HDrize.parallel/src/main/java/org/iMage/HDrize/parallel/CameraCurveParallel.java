package org.iMage.HDrize.parallel;

import java.util.Random;

import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.matrix.IMatrix;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;
import org.iMage.HDrize.matrix.Matrix;
import org.ojalgo.matrix.decomposition.SingularValue;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;

/**
 * Subclass of CameraCurve to parallelize the calculation of the response curves.
 * 
 * @author Jason Wessalowski
 */
public class CameraCurveParallel extends CameraCurve {

	private static final int CHANNELS = 3;
	private static final int SIZE = 256;
	// Maximum numbers of threads for the pool.
	private static final int MAX_THREADS = 3;

	private EnhancedImage[] images;
	private int samples;
	private IMatrix[] respCurves;

	private IMatrixCalculator<Matrix> matrixCalc;

	private final Random random = new Random(42);
	private double lambda;
	
	@Override
	public void calculate() {
		this.calculate(3);
	}
	
	private void calculate(int remainingTries) {
		  
		if (this.respCurves != null) {
			return;
		}

		if (remainingTries <= 0) {
			this.respCurves = null;
			return;
		}

		System.out.println("Try to calculate response curves .. remaining tries: " + remainingTries);

		int imageWidth = this.images[0].getWidth();
		int imageHeight = this.images[0].getHeight();

		int[] x = new int[this.samples];
		int[] y = new int[this.samples];

		boolean[][] taken = new boolean[imageHeight][imageWidth];

		for (int n = 0; n < this.samples; n++) {
			x[n] = this.random.nextInt(imageWidth);
			y[n] = this.random.nextInt(imageHeight);
			if (taken[y[n]][x[n]]) {
				n--;
				continue;
			}
			taken[y[n]][x[n]] = true;
		}

		this.respCurves = new IMatrix[CameraCurveParallel.CHANNELS];
		
		// Parallelization part.
		ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
		
		CameraCurveParallel ccp = this;

		for (int channel = 0; channel < CameraCurveParallel.CHANNELS; channel++) {
			
			final int ch = channel;
			
			pool.execute(new Runnable() {
				
				public void run() {
					
					ccp.calculateChannel(ch, x, y);
					
				}
				
			});
		}

		pool.shutdown(); 
		//
		
		for (var rc : this.respCurves) {
			if (rc == null) {
				this.respCurves = null;
				this.calculate(remainingTries - 1);
			}
		}

	}
	
	private boolean calculateChannel(int channel, final int[] x, final int[] y) {
		System.out.println("Calculation for channel " + channel);
	    IMatrix[] mtxAB = this.initAB(channel, x, y);

	    var decompositionVSU = this.calcSingularParallel(mtxAB[0]);

	    System.out.println("Starting inverse of M[" + decompositionVSU.mtxS.rows() + "x"
	        + decompositionVSU.mtxS.cols() + "]");
	    long t1 = System.currentTimeMillis();
	    Matrix sInverse = this.matrixCalc.inverse(decompositionVSU.mtxS);
	    long t2 = System.currentTimeMillis();
	    System.out.println("InverseOfMatrix has finished in " + ((t2 - t1) / 1000F) + " seconds");

	    if (sInverse == null) {
	      return false;
	    }

	    Matrix mtxVSInverse = this.matrixCalc.multiply(decompositionVSU.mtxV, sInverse);
	    Matrix mtxUTranspose = this.matrixCalc.transpose(decompositionVSU.mtxU);
	    Matrix result = this.matrixCalc.multiply(mtxVSInverse, mtxUTranspose);
	    result = this.matrixCalc.multiply(result, new Matrix(mtxAB[1]));

	    this.respCurves[channel] = result;
	    System.out.println("Finished channel " + channel);
	    return true;

	}
	
	  private IMatrix[] initAB(int channel, final int[] x, final int[] y) {
		  IMatrix mtxA = new Matrix(//
				  this.samples * this.images.length + CameraCurveParallel.SIZE + 1, //
				  CameraCurveParallel.SIZE + this.samples //
				  );
		  IMatrix b = new Matrix(mtxA.rows(), 1);

		  int k = 0;
		  for (int i = 0; i < this.samples; i++) {
			  for (int j = 0; j < this.images.length; j++) {
				  int[] clr = this.images[j].getRGB(x[i], y[i]);
				  int intensity = clr[channel];

				  double wij = this.getWeight(intensity);

				  mtxA.set(k, intensity, wij);
				  mtxA.set(k, CameraCurveParallel.SIZE + i, -wij);
				  b.set(k, 0, wij * Math.log(this.images[j].getExposureTime()));

				  k++;
			  }
		  }
		  
		  mtxA.set(k, CameraCurveParallel.SIZE / 2, 1.0);
		  k++;

		  for (int i = 0; i < CameraCurveParallel.SIZE - 2; i++) {
			  mtxA.set(k, i, this.lambda * this.getWeight(i + 1));
		      mtxA.set(k, i + 1, -2.f * this.lambda * this.getWeight(i + 1));
		      mtxA.set(k, i + 2, this.lambda * this.getWeight(i + 1));
		      k++;
		  }

		  return new IMatrix[] { mtxA, b };
	  }
	  
	  private double getWeight(int index) {
		  return (index <= 256 / 2 ? index : 256 - index) / 128.f;
	  }
	  
	  /**
	   * Calculate VSU decomposition.
	   *
	   * @param input
	   *          the matrix
	   * @return the decomposition
	   */
	  protected SingularDecompositionParallel calcSingularParallel(IMatrix input) {
	    var matrix = input.copy();
	    var mtxA = PrimitiveDenseStore.FACTORY.makeZero(matrix.length, matrix[0].length);
	    for (int r = 0; r < mtxA.countRows(); r++) {
	      for (int c = 0; c < mtxA.countColumns(); c++) {
	        mtxA.set(r, c, matrix[r][c]);
	      }
	    }

	    long t1 = System.currentTimeMillis();

	    SingularValue<Double> svd = SingularValue.PRIMITIVE.make(mtxA);
	    svd.compute(mtxA);
	    long t2 = System.currentTimeMillis();
	    System.out.println("SingularDecomposition has finished in " + ((t2 - t1) / 1000F) + " seconds");

	    var mtxV = new Matrix(svd.getQ2().toRawCopy2D());
	    var mtxS = new Matrix(svd.getD().toRawCopy2D());
	    var mtxU = new Matrix(svd.getQ1().toRawCopy2D());

	    return new SingularDecompositionParallel(mtxV, mtxS, mtxU);
	  }
	  
	  /**
	   * The Singular Decomposition.
	   *
	   * @see https://de.wikipedia.org/wiki/Singul%C3%A4rwertzerlegung
	   */
	  protected static final class SingularDecompositionParallel {

		  private final Matrix mtxV;
		  private final Matrix mtxS;
		  private final Matrix mtxU;

		  private SingularDecompositionParallel(Matrix v, Matrix s, Matrix u) {
			  this.mtxV = v;
		      this.mtxS = s;
		      this.mtxU = u;
		  }
		  
	  }
	  
}
