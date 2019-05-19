package org.iMage.HDrize;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDRCombine;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;

/**
 * Implementation of {@link IHDRCombine}.
 */
public class HDRCombine implements IHDRCombine {
  @Override
  public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) {
    throw new UnsupportedOperationException("TODO Implement me!");
  }

  @Override
  public float[] calculateWeights() {

	  float[] result = new float[256];
	  
	  // Set first and last value.
	  result[0] = 1.0f;
	  result[255] = 1.0f;
	  
	  for (int n = 1; n <= 235; n++) {
		  
		  // Set value of first and last 19 values based on recurrent equation. Rest is equal to 2.
		  if (n < 20) {
			  
			  result[n] = result[n - 1] + (1 / 20);
			  result[255 - n] = result[n];
			  
			  
		  } else {
			  
			  result[n] = 2;
			  
		  }
		  
	  }
	  
	  return result;
	  
  }

}
