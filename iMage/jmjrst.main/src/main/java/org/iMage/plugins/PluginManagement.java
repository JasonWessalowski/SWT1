package org.iMage.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ServiceLoader;

/**
 * Knows all available plug-ins and is responsible for using the service loader API to detect them.
 *
 * @author Dominik Fuchss
 */
public final class PluginManagement {

  /**
   * No constructor for utility class.
   */
  private PluginManagement() {
    throw new IllegalAccessError();
  }

  /**
   * Return an {@link Iterable} Object with all available {@link PluginForJmjrst PluginForJmjrsts}
   * sorted according to the length of their class names (shortest first).
   *
   * @return an {@link Iterable} Object containing all available plug-ins alphabetically sorted
   * according to their class names.
   */
  public static Iterable<PluginForJmjrst> getPlugins() {
    
	  // Initialize new ServiceLoader.
	  ServiceLoader<PluginForJmjrst> loader = ServiceLoader.load(PluginForJmjrst.class);
	  // Prepare list.
	  List<PluginForJmjrst> plugins = new ArrayList<PluginForJmjrst>();
	  // Prepare iterator for list plugins.
	  ListIterator<PluginForJmjrst> iterator;
	  
	  // Insert every plugin at its appropriate spot on the list.
	  for (PluginForJmjrst plugin : loader) {
		  
		  // Start from the beginning of the list again.
		  iterator = plugins.listIterator();
		  // Save the current plugin for comparison.
		  PluginForJmjrst current;
		  
		  // Look for appropriate spot for given plugin of the for-each loop.
		  // Is there a next element?
		  while (iterator.hasNext()) {
			  
			  // If yes, go to the next element in the list.
			  current = iterator.next();
			  
			  // Does this current plugin have a longer name?
			  if (plugin.compareTo(current) <= 0) {
				  
				  // If yes, go to the previous element and break the while loop.
				  iterator.previous();
				  break;
				  
			  }
			  
		  }
		  
		  // Add plugin of the current for-each iterator prior to the plugin with the longer name.
		  iterator.add(plugin);
		  
	  }
	  
	  // Return finished list.
	  return plugins;
	  
  }

}
