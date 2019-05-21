package org.iMage.HDrize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.cli.*;
import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;
import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.iMage.HDrize.base.images.HDRImageIO;


/**
 * This class parses all command line parameters and creates an HDRImage.
 *
 */
public final class App {
  private App() {
    throw new IllegalAccessError();
  }

  private static final String CMD_OPTION_INPUT_IMAGES = "i";
  private static final String CMD_OPTION_OUTPUT_IMAGE = "o";
  private static final String CMD_OPTION_LAMBDA = "l";
  private static final String CMD_OPTION_SAMPLES = "s";
  
  // Default values for optional arguments.
  private static final double LAMBDA = 30;
  private static final int SAMPLESIZE = 142;

  public static void main(String[] args) {
    // Don't touch...
    CommandLine cmd = null;
    try {
      cmd = App.doCommandLineParsing(args);
    } catch (ParseException e) {
      System.err.println("Wrong command line arguments given: " + e.getMessage());
      System.exit(1);
    }
    // ...this!

    /**
     * Implement me! Remove exception when done!
     *
     * HINT: You have to convert the files from the image folder to InputStreams and then create
     * Objects of class org.iMage.HDrize.base.images.EnhancedImage before you can use HDrize.
     */
    // Save files in array.
    File[] files = new File(CMD_OPTION_INPUT_IMAGES).listFiles();
    // Prepare InputStream array
    InputStream[] inputstream = new InputStream[files.length];
    
    // Check how many images were in the input folder.
    if (files.length % 2 == 0) {
    	
    	System.err.println("Number of received images is not uneven!");
    	System.exit(1);
    	
    }
    
    // Save first three letters.
    String prefixCheck = files[0].getName().substring(0, 3);
    
    // Check if they have the same three letter prefix.
	for (int i = 0; i < files.length; i++) {
		
		if (!files[i].getName().substring(0, 3).matches(prefixCheck)) {
			
			System.err.println("Images don't have the same three letter prefix!");
			System.exit(1);
			
		}
		
		// And if passed, insert as another InputStream.
		try {
			
			inputstream[i] = new FileInputStream(files[i].getCanonicalPath());
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}

	}
    
    // Set local variables for optional arguments.
    double lambda = (cmd.hasOption(CMD_OPTION_LAMBDA))
    				? Integer.parseInt(cmd.getOptionValue(CMD_OPTION_LAMBDA)) : LAMBDA;
    int samplesize = (cmd.hasOption(CMD_OPTION_SAMPLES))
    				? Integer.parseInt(cmd.getOptionValue(CMD_OPTION_SAMPLES)) : SAMPLESIZE;
    
    // Check if lambda and samplesize have appropriate values.
    if (lambda <= 0 || lambda > 100) {
    	
    	System.err.println("Lambda is not a value between 1 and 100!");
    	System.exit(1);
    	
    }
    
    if (samplesize < 1 || samplesize > 1000) {
    	
    	System.err.println("Sample size is not a value between 1 and 1000!");
    	System.exit(1);
    	
    }
   
    // Turn InputStreams to EnhancedImages
    EnhancedImage[] enhancedImages = new EnhancedImage[inputstream.length];
    
    for (int i = 0; i < enhancedImages.length; i++) {
    	
    	try {
    		
			enhancedImages[i] = new EnhancedImage(inputstream[i]);
			
		} catch (ImageReadException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();

		}
    	
    }
    
    // Initialize necessary objects.
    HDrize hdRize = new HDrize();
    IMatrixCalculator<Matrix> mtxCalc = new MatrixCalculator();
    
    
    // Create HDR Image
    BufferedImage result = hdRize.createRGB(enhancedImages, samplesize, lambda, mtxCalc, ToneMapping.SRGBGamma);
	File output = new File(cmd.getOptionValue(CMD_OPTION_OUTPUT_IMAGE + "hdr_result.png"));
    
    // Save HDR image as PNG
    try {
    	
    	ImageIO.write(result, "png", output);
    	
    } catch (IOException e) {
    	
    	e.printStackTrace();
    	
    }
    
  }

  /**
   * Parse and check command line arguments
   *
   * @param args
   *          command line arguments given by the user
   * @return CommandLine object encapsulating all options
   * @throws ParseException
   *           if wrong command line parameters or arguments are given
   */
  private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
    Options options = new Options();
    Option opt;

    /*
     * Define command line options and arguments
     */
    opt = new Option(App.CMD_OPTION_INPUT_IMAGES, "input-images", true, "path to folder with input images");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_OUTPUT_IMAGE, "image-output", true, "path to output image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_LAMBDA, "lambda", true, "the lambda value of algorithm");
    opt.setRequired(false);
    opt.setType(Double.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_SAMPLES, "samples", true, "the number of samples");
    opt.setRequired(false);
    opt.setType(Integer.class);
    options.addOption(opt);

    CommandLineParser parser = new DefaultParser();
    return parser.parse(options, args);
  }
}