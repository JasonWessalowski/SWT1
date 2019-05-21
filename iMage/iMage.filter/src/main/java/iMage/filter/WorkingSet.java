package iMage.filter;

import java.util.List;

import org.iMage.HDrize.base.images.EnhancedImage;

/**
 * Class that acts to bundle selected images and desired filters to be applied.
 * 
 * @author Jason Wessalowski
 */
public class WorkingSet {

	// List of selected filters to apply or alter before application
	List<Filter> fList;
	// List of selected images to apply a filter to
	List<EnhancedImage> iList;
	
	/**
	 *  Add another image to the end of the list
	 *  
	 * @param image Image to add to the list.
	 */
	public void addImage(EnhancedImage image) {
		
	}
	
	/**
	 *  Look for given image in the list and have it removed.
	 *  
	 * @param image Image to look for in the list.
	 */
	public void removeImage(EnhancedImage image) {
		
	}
	
	/**
	 *  Empty the entire list of images
	 */
	public void emptyImageList() {
		
	}
	
	/**
	 *  Add another filter to the end of the list.
	 *  
	 * @param filter Filter to add to the list.
	 */
	public void addFilter(Filter filter) {
		
	}
	
	/**
	 *  Look for given filter in the list and have it removed.
	 *  
	 * @param filter Filter to look for in the list.
	 */
	public void removeFilter(Filter filter) {
		
	}
	
	/**
	 *  Empty the entire list of filters.
	 */
	public void emptyFilterList() {
		
	}
	
	/**
	 *  Apply every listed filter on every listed image
	 */
	public void applyFilters() {
		
	}
	
	/**
	 * Have all of the listed images saved at a given spot.
	 * 
	 * @param directory Location to use for writing the files
	 */
	public void saveImages(String directory) {
		
	}
}
