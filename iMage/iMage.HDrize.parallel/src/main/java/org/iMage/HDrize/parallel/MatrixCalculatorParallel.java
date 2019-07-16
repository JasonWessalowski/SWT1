package org.iMage.HDrize.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculator;

/**
 * Subclass of MatrixCalculator to parallelize the calculation of matrices.
 * 
 * @author Jason Wessalowski
 */
public class MatrixCalculatorParallel extends MatrixCalculator {

	private int numThreads;
	
	/**
	 * Constructor for the class MatrixCalculatorParallel.
	 * 
	 * @param numThreads Number of threads for parallelized processed.
	 */
	public MatrixCalculatorParallel(int numThreads) {
		
		this.numThreads = numThreads;
		
	}
	
	@Override
	public Matrix multiply(Matrix a, Matrix b) {
		
		if (a.cols() != b.rows()) {
			
	        return null;
	        
		}

		Matrix res = new Matrix(a.rows(), b.cols());
		ExecutorService pool = Executors.newFixedThreadPool(this.numThreads);
		
		for (int c = 0; c < a.rows(); c++) {
			
			final int cFinal = c;
			
			pool.execute(new Runnable() {
				
				public void run() {
					
					for (int d = 0; d < b.cols(); d++) {
						
						double sum = 0;
						
						for (int k = 0; k < b.rows(); k++) {
							
							sum = sum + a.get(cFinal, k) * b.get(k, d);
							
						}

						res.set(cFinal, d, sum);
						
					}
					
				}
				
			});
			
		}
		
		pool.shutdown(); 
		return res;
		
	}
	
}
