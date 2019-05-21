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
  public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) throws UnsupportedOperationException {
	  // Check if the images are the same size.
	  if (imageList[0].getHeight() != imageList[1].getHeight()
	      || imageList[1].getHeight() != imageList[2].getHeight()
	      || imageList[0].getWidth() != imageList[1].getWidth()
	      || imageList[1].getWidth() != imageList[2].getWidth()) {
		  
		  throw new UnsupportedOperationException("Images were not the same size!");
		  
	  }
	  
	  // Save size.
	  int height = imageList[0].getHeight();
	  int width = imageList[0].getWidth();
	  
	  // Associated values of channel red, green and blue.
	  int channelR = 0;
	  int channelG = 1;
	  int channelB = 2;
	  
	  // Calculate matrices of the channels.
	  float[][] r = combineSeperately(channelR, width, height, curve, imageList);
	  float[][] g = combineSeperately(channelG, width, height, curve, imageList);
	  float[][] b = combineSeperately(channelB, width, height, curve, imageList);
	  
	  return new HDRImage(r, g, b);

  }
  
  /**
   * Handles the calculations for the HDR image's channels seperately.
   * 
   * @param channel Number association of the channel.
   * @param width Width of the image.
   * @param height Height of the image.
   * @param curve Array of the curve values.
   * @param imageList List of images.
   * @return Matrix of individual color channel.
   */
  private static float[][] combineSeperately(int channel, int width, int height,
		  									 ICameraCurve curve, EnhancedImage[] imageList) {
	  
	  // Preparing necessary matrices.
	  float[][] result = new float[width][height];
	  
	  // Bring array of weights.
	  HDRCombine hdrCombine = new HDRCombine();
	  float[] weights = hdrCombine.calculateWeights();
	  
	  // Put exposure time in appropriate array.
	  float[] exposureTime = new float[3];
	  
	  for (int i = 0; i <= 3; i++) {
		  
		  exposureTime[i] = imageList[i].getExposureTime();
		  
	  }
	  
	  // Local variable to help with summarization.
	  float sumNorm = 0.0f;
	  float summandNorm = 0.0f;
	  float curveValue = 0.0f;
	  float sumValue = 0.0f;
	  
	  // Inserting the appropriate values in all channels.
	  for (int x = 0; x < width; x++) {
		  
		  for (int y = 0; y < height; y++) {
			  
			  for (int n = 0; n <= 3; n++) {
				  
				  // norm(x, y, channel)
				  summandNorm = weights[imageList[n].getRGB(x, y)[channel]];
				  sumNorm += summandNorm;
				  
				  // Get curve value.
				  curveValue = curve.getResponse(imageList[n].getRGB(x, y))[channel];
				  
				  // value(x, y, channel)
				  sumValue += (summandNorm * curveValue) / exposureTime[n];
				  
				  // hdr(x, y, channel)
				  result[x][y] = sumValue / sumNorm;
				  
			  }
			  
		  }
		  
	  }
	  
	  return result;
	  
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
