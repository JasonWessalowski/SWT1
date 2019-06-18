package org.iMage.iCatcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A subclass of JPanel, which serves as a container to display all the control settings.
 * 
 * @author Jason Wessalowski
 */
public class SettingsPanel extends JPanel {

	private JPanel areaOne;
	private JPanel areaTwo;
	
	private File[] files;
	
	/**
	 * Constructor to generate the SettingsPanel.
	 */
	public SettingsPanel() {
		
		// Set up size and layout.
		this.setPreferredSize(new Dimension(800, 400));
		this.setBackground(Color.WHITE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		// Define the two separate areas.
		this.setUpAreaOne();
		this.setUpAreaTwo();
		
	}
	
	/**
	 * Set up area one in the settings panel.
	 */
	private void setUpAreaOne() {
		
		// Set up first area and its components.
		areaOne = new JPanel();
		areaOne.setPreferredSize(new Dimension(400, 400));
		areaOne.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		this.add(areaOne);
		
		// Divide area in three panels.
		addThreePanels(areaOne);
		
		// Set layout of first panel.
		getSubJPanel(areaOne, 0).setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		// Component 00: Scroll bar.
		JScrollBar sbar = new JScrollBar(JScrollBar.HORIZONTAL, 175, 5, 0, 350);
		sbar.setPreferredSize(new Dimension(300, 20));
		getSubJPanel(areaOne, 0).add(sbar);
		
	    // Set layout of second panel.
		getSubJPanel(areaOne, 1).setLayout(null);
		// Component 10: Label "Camera Curve".
		JLabel cbOneL = new JLabel("Camera Curve");
		cbOneL.setBounds(50, 0, cbOneL.getPreferredSize().width, cbOneL.getPreferredSize().height);
		getSubJPanel(areaOne, 1).add(cbOneL);
		
		// Component 11: Combobox for camera curve.
		String[] curves = {"Standard Curve", "Calculate Curve"};
	    JComboBox<String> cbOne = new JComboBox<String>(curves);
	    cbOne.setBounds(46, 15, 250, 35);
	    getSubJPanel(areaOne, 1).add(cbOne);
	    
	    // Set layout of third panel.
		getSubJPanel(areaOne, 2).setLayout(null);
	    // Component 20: Label "Tone Mapping".
		JLabel cbTwoL = new JLabel("Tone Mapping");
		cbTwoL.setBounds(50, 0, cbTwoL.getPreferredSize().width, cbTwoL.getPreferredSize().height);
		getSubJPanel(areaOne, 2).add(cbTwoL);
	    
		// Component 21: Combobox for tone mapping.
		String[] maps = {"Simple Map", "Standard Gamma", "SRGB Gamma"};
	    JComboBox<String> cbTwo = new JComboBox<String>(maps);    
	    cbTwo.setBounds(46, 15, 250, 35);
	    getSubJPanel(areaOne, 2).add(cbTwo);

	}
	
	/**
	 * Set up area two in the settings panel.
	 */
	private void setUpAreaTwo() {
		
		// Set up second area an its components.
		areaTwo = new JPanel();
		areaTwo.setPreferredSize(new Dimension(400, 400));
		areaTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		this.add(areaTwo);

		// Divide area in three panels.
		addThreePanels(areaTwo);
		
		// Set layout of first panel.
		getSubJPanel(areaTwo, 0).setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		// Component 00: Show Curve button.
		JButton showCurve = new JButton("SHOW CURVE");
		getSubJPanel(areaTwo, 0).add(showCurve);
		
		// Component 01: Save Curve button.
		JButton saveCurve = new JButton("SAVE CURVE");
		getSubJPanel(areaTwo, 0).add(saveCurve);
		
		// Component 02: Save HDR button.
		JButton saveHDR = new JButton("SAVE HDR");
		getSubJPanel(areaTwo, 0).add(saveHDR);
		// Set FlowLayout.
		
		// Set layout of the second panel.
		getSubJPanel(areaTwo, 1).setLayout(null);
		// Component 10: Label "Samples [value]".
		JLabel sliderL = new JLabel("Samples 500");
		sliderL.setBounds(30, 0, sliderL.getPreferredSize().width, sliderL.getPreferredSize().height);
		getSubJPanel(areaTwo, 1).add(sliderL);
		
		// Component 12: Label "Lambda".
		JLabel tFieldL = new JLabel("Lambda");
		tFieldL.setBounds(300, 0, tFieldL.getPreferredSize().width, tFieldL.getPreferredSize().height);
		getSubJPanel(areaTwo, 1).add(tFieldL);
		
		// Component 11: Slider for sample size.
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 1000, 500);  
		slider.setMajorTickSpacing(999);
		slider.setPaintLabels(true);
		slider.setBounds(15, 10, slider.getPreferredSize().width, slider.getPreferredSize().height);
		getSubJPanel(areaTwo, 1).add(slider);
		// Change label of slider, when slider value is changed.
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {

				sliderL.setText("Samples " + slider.getValue());
				
			}
			
		});
		
		// Component 13: Text field for lambda value.
		JTextField tField = new JTextField("20.0");
		tField.setHorizontalAlignment(SwingConstants.RIGHT);
		tField.setBounds(296, 20, 60, tField.getPreferredSize().height);
		getSubJPanel(areaTwo, 1).add(tField);
		// Change color based on whether or not the entered text is appropriate.
		tField.getDocument().addDocumentListener(new DocumentListener() {
	     
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	
	            changeLambdaField(isLambdaValid(tField.getText()));
	            
	        }
	     
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	
	        	changeLambdaField(isLambdaValid(tField.getText()));
	            
	        }

			@Override
			public void changedUpdate(DocumentEvent e) {
				
				changeLambdaField(isLambdaValid(tField.getText()));
				
			}
			
		});
		
		// Set layout of third panel.
		getSubJPanel(areaTwo, 2).setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		// Component 20: Load Dir button.
		JButton loadDir = new JButton("LOAD DIR");
		getSubJPanel(areaTwo, 2).add(loadDir);
		// Open file chooser window with proper constraints.
		loadDir.addActionListener(new ActionListener() {

			  public void actionPerformed(ActionEvent e) {
				  
				  JFileChooser chooser = new JFileChooser();
				  // Only allow the choice of folders, not files.
				  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				  int state = chooser.showDialog(App.getFrame(), "Select Folder");

				  if (state == JFileChooser.APPROVE_OPTION) {

					  File dir = chooser.getSelectedFile();
					  
					  // Check if folder meets criteria.
					  if (!(checkNumber(dir) && checkPrefix(dir))) {
						  
						  JOptionPane.showMessageDialog(App.getFrame(), "This folder either has an even number of JPEG "
						  		+ "files or their common prefix\n"
						  		+ "isn't at least three figures long!", "Faulty Input", JOptionPane.WARNING_MESSAGE);
						  
						  return;
						  
					  }
					  
					  setFiles(dir);
					  
				  }

			  }
			  
		});
		
		// Component 21: Load Curve button.
		JButton loadCurve = new JButton("LOAD CURVE");
		getSubJPanel(areaTwo, 2).add(loadCurve);
		
		// Component 22: Run HDrize button.
		JButton run = new JButton("RUN HDRIZE");
		getSubJPanel(areaTwo, 2).add(run);

	}	
	
	/**
	 * Add three panels to a given area.
	 * 
	 * @param comp The selected area.
	 */
	private static void addThreePanels(JPanel comp) {

		JPanel panel;
		
		for (int i = 1; i <= 3; i++) {
			
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(400, 50));
			comp.add(panel);
			
		}
		
	}
	
	/**
	 * Retrieves one of the sub-JPanels of the areas.
	 * 
	 * @param area Which area.
	 * @param n Which sub-component.
	 * @return Child JPanel that was selected.
	 */
	private static JPanel getSubJPanel(JPanel area, int n) {
		
		return (JPanel) area.getComponent(n);
		
	}
	
	/**
	 * Checks if text field has proper value for lambda.
	 * 
	 * @param content String content of presumably a JTextField.
	 * @return Boolean that represents validity of text field content.
	 */
	private static boolean isLambdaValid(String content) {
		
		boolean validity = false;
		
		// Input must be value in (0, 100].
		if ((content.matches("[0-9]{0,2}\\.[0-9]+") || content.matches("100\\.0")) && !content.matches("0\\.0")) {
			
			App.setLambdaIsValid(true);
			validity = true;
			
		} else {
			
			App.setLambdaIsValid(false);
			validity = false;
			
		}
		
		return validity;
		
	}
	
	/**
	 * Getter to retrieve the lambda text field component.
	 * 
	 * @return Lambda JTextField of area two.
	 */
	private JTextField getLambdaField() {
		
		JPanel subpanel = (JPanel) areaTwo.getComponent(1);
		JTextField field = (JTextField) subpanel.getComponent(3);
		
		return field;
		
	}
	
	/**
	 * Changes color of the text fields content depending on whether or not the input is proper.
	 * 
	 * @param validity Boolean regarding the appropriateness of the input as a lambda value.
	 */
	private void changeLambdaField(boolean validity) {
		
		if (validity) {
			
			this.getLambdaField().setForeground(Color.BLACK);
			
		} else {
			
			this.getLambdaField().setForeground(Color.RED);
			
		}
		
	}
	
	/**
	 * Check if the folder meets the criteria of having an uneven number of JPEG files.
	 * 
	 * @param dir Path of folder.
	 * @return Boolean on whether or not the folder meets the criteria.
	 */
	private static boolean checkNumber(File dir) {
		
		boolean result = false;
		int count = 0;
		
		String[] files = dir.list();
		
		for (String file : files) {
			
			// Count how many JPEG files.
			if (file.endsWith(".jpg")) {
				
				count++;
				
			}
			
		}
		
		if (count % 2 == 1) {
			
			result = true;
			
		}
		
		return result;
		
	}
	
	/**
	 * Check if the folder meets the criteria of having JPEG files with a common prefix that
	 * is at least three figures long.
	 * 
	 * @param dir Path of folder.
	 * @return Boolean on whether or not the folder meets the criteria.
	 */
	public static boolean checkPrefix(File dir) {
		
		boolean result = false;
		String prefix = null;
		
		String[] files = dir.list();
		
		for (int i = 0; i < files.length; i++) {
			
			// REMOVE System.out.println(files[i]);
			
			// Count how many JPEG files.
			if (files[i].endsWith(".jpg")) {
				
				if (prefix == null) {
					
					prefix = files[i];
					continue;
					
				}
				
				prefix = greatestCommonPrefix(prefix, files[i]);
				
			}
			
		}
		
		if (prefix.length() >= 3) {
			
			result = true;
			// Save relevant prefix for further use.
			App.setPrefix(prefix);
			
		}
		
		return result;
		
	}
	
	/**
	 * Figure out longest common prefix of two strings.
	 * 
	 * @param a First string.
	 * @param b Second string.
	 * @return Longest common prefix.
	 */
	private static String greatestCommonPrefix(String a, String b) {
		
		// Identify shortest string of the two to figure out bounds of iteration.
	    int minLength = Math.min(a.length(), b.length());
	    
	    // Return number of the first chronological unequivalence.
	    for (int i = 0; i < minLength; i++) {
	    	
	        if (a.charAt(i) != b.charAt(i)) {
	        	
	            return a.substring(0, i);
	            
	        }
	        
	    }
	    
	    return a.substring(0, minLength);
	    
	}
	
	/**
	 * Setter for the file array.
	 * 
	 * @param dir Given folder with desired content.
	 */
	private void setFiles(File dir) {
		
		FilenameFilter filter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				
				String lowercaseName = name.toLowerCase();
				
				if (lowercaseName.endsWith(".jpg")) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			}
			
		};
		
		this.files = dir.listFiles(filter);
		
	}

}
