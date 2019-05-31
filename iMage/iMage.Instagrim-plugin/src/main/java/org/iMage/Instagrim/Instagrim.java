package org.iMage.Instagrim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

/**
 * Plug-In to generate positive comments about iMage.
 * 
 * @author Jason Wessalowski
 *
 */
@MetaInfServices
public class Instagrim extends PluginForJmjrst {

	private Main jmjrst;
	
	@Override
	public void configure() {
		
		// Get all pre-written comments.
		String[] comments = getComments();
		// Prepare message.
		String message = "";
		
		for (String string : comments) {
			
			message += string + "\n";
			
		}
		
		JOptionPane.showMessageDialog(this.jmjrst, message, "Kommentarliste", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private static String[] getComments() {
		
		// Count number of comments.
		int number = 0;
		
		// Prepare array for comments.
		String[] comments = new String[0];
		
		// The name of the file to open.
		String fileName = "src/main/resources/kommentare.txt";

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

		    // Always wrap FileReader in BufferedReader.
		    BufferedReader bufferedReader = new BufferedReader(fileReader);

		    // Count number of comments.
		    while (bufferedReader.readLine() != null) {
            	
		    	number++;
		                
		    }  
		    
		    // Update size of comments array.
		    comments = new String[number];
		    
		    int i = 0;
		    
		    // Assign comments to array.
		    while ((line = bufferedReader.readLine()) != null) {
		            	
		    	comments[i] += "\"" + line + "\"";
		    	i++;
		                
		    }   

		// Always close files.
		bufferedReader.close();         
		    
		} catch (FileNotFoundException e) {
		        	
			System.out.println("Unable to open file '" + fileName + "'");  
		            
		} catch (IOException e) {
		        	
		    System.out.println("Error reading file '" + fileName + "'");                  

		}
		
		return comments;
		
	}

	@Override
	public String getName() {
		
		return "Instagrim";
		
	}

	@Override
	public void init(Main arg0) {
		
		// Assign argument to the class attribute jmjrst.
		this.jmjrst = arg0;
		// Retrieve name of the user.
		String name = System.getProperty("user.name");
		// Give out message.
		System.err.println("iMage: Der Bildverschönerer, dem Influencer vertrauen!"
							+ "Jetzt bist auch Du Teil unseres Teams, " + name + ".");

	}

	@Override
	public boolean isConfigurable() {
		
		return true;
		
	}

	@Override
	public void run() {
		
		// Get all the pre-written comments.
		String[] comments = getComments();
		
		// Select random number for comment.
		Random random = new Random();
		int number = random.nextInt(comments.length);
		
		System.out.println(comments[number]);
		
	}

	

}
