package iMage.filter;

import org.iMage.HDrize.base.images.EnhancedImage;

/**
 * Superclass to specified image editing filters.
 * 
 * @author Jason Wessalowski
 */
public abstract class Filter {

	/*

	Specific parameters for subclasses.

	 */
	
	/**
	 * This method is used to apply this filter to a given image.
	 * 
	 * @param image Desired image to be altered.
	 * @return Resulting image.
	 */
	public abstract EnhancedImage execute(EnhancedImage image);
	
	/*

	Getter and setter methods for parameters in subclasses.

	 */
}
