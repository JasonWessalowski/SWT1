package org.iMage.plugins;

/**
 * Abstract parent class for plug-ins for JMJRST
 *
 * @author Dominik Fuchss
 */
public abstract class PluginForJmjrst implements Comparable<PluginForJmjrst> {

  /**
   * Returns the name of this plug-in
   *
   * @return the name of the plug-in
   */
  public abstract String getName();

  /**
   * JMJRST pushes the main application to every subclass - so plug-ins are allowed to look at Main
   * as well.
   *
   * @param main
   *     JMJRST main application
   */
  public abstract void init(org.jis.Main main);

  /**
   * Runs plug-in
   */
  public abstract void run();

  /**
   * Returns whether the plug-in can be configured or not
   *
   * @return true if the plug-in can be configured.
   */
  public abstract boolean isConfigurable();

  /**
   * Open a configuration dialogue.
   */
  public abstract void configure();

  @Override 
  public int compareTo(PluginForJmjrst otherPlugin) {
    
	  // Save the names of the two plug-ins
	  String thisString = this.getName();
	  String otherString = otherPlugin.getName();
	  
	  // Returns negative integer when this plug-in has a shorter name, positive when
	  // the opposite is true and 0 if they are equal
	  return thisString.length() - otherString.length();
	  
  }
}
