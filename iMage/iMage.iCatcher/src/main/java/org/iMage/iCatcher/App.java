package org.iMage.iCatcher;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

/**
 * Graphical user interface to present the functionalities of HDrize.
 * 
 * @author Jason Wessalowski
 */
public final class App {

	private static JFrame frame;
	
	private static ImagePanel imagePanel;
	private static SettingsPanel settingsPanel;
	
	private static boolean lambdaIsValid = true;
	private static boolean calculatedHDR;
	private static boolean calculatedCurve;
	
	private static String prefix;
	
	private App() {
		
		throw new IllegalAccessError();
		
	}
	
    /**
	 * The programs entry point.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		
		// Schedule a job for the event-dispatching thread, creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
            public void run() {
            	
                launchDisplay();
                
            }
            
        });
		
	}
	
	/**
	 * Establish and display the entire content of iCatcher as a GUI.
	 */
    private static void launchDisplay() {
    	
        // Create and set up the window.
        frame = new JFrame("iCatcher");
        
        // Set attributes of frame.
		frame.setSize(800, 700);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Allocate the two main panels.
        Container container = frame.getContentPane();
        
		// Set the layout to a vertical FlowLayout.
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 0));
		
		imagePanel = new ImagePanel();
		settingsPanel = new SettingsPanel();

		container.add(imagePanel);
		container.add(settingsPanel);
        
    }
    
    /**
     * Getter for static frame JFrame.
     * 
     * @return JFrame of this application.
     */
    public static JFrame getFrame() {
    	
    	return App.frame;
    	
    }
    
    /**
     * Getter for static lambdaIsValid boolean.
     * 
     * @return Boolean if lambda value is valid.
     */
    public static boolean getLambdaIsValid() {
    	
    	return App.lambdaIsValid;
    	
    }
    
    /**
     * Setter for static lambdaIsValid boolean.
     * 
     * @param isValid Boolean for if Lambda has a proper value.
     */
    public static void setLambdaIsValid(boolean isValid) {
    	
    	App.lambdaIsValid = isValid;
    	
    }
    
    /**
     * Getter for static calculatedHDR boolean.
     * 
     * @return Boolean if HDR was calculated.
     */
    public static boolean getCalculatedHDR() {
    	
    	return App.calculatedHDR;
    	
    }
    
    /**
     * Setter for static calculatedHDR boolean.
     * 
     * @param isCalculated Boolean for if HDR has been calculated.
     */
    public static void setCalculatedHDR(boolean isCalculated) {
    	
    	App.calculatedHDR = isCalculated;
    	
    }
    
    /**
     * Getter for static calculatedCurve boolean.
     * 
     * @return Boolean if curve was calculated.
     */
    public static boolean getCalculatedCurve() {
    	
    	return App.calculatedCurve;
    	
    }
    
    /**
     * Setter for static calculatedCurve boolean.
     * 
     * @param isCalculated Boolean for if curve has been calculated.
     */
    public static void setCalculatedCurve(boolean isCalculated) {
    	
    	App.calculatedCurve = isCalculated;
    	
    }
    
    /**
     * Getter for currently relevant prefix.
     * 
     * @return String of the relevant prefix.
     */
    public static String getPrefix() {
    	
    	return App.prefix;
    	
    }
    
    /**
     * Setter for currently relevant prefix.
     * 
     * @param prefix String of the relevant prefix.
     */
    public static void setPrefix(String prefix) {
    	
    	App.prefix = prefix;
    	
    }
    
    /**
     * Getter to retrieve App's image panel.
     * 
     * @return Image panel of App class.
     */
    public static ImagePanel getImagePanel() {
    	
    	return App.imagePanel;
    	
    }
	
}