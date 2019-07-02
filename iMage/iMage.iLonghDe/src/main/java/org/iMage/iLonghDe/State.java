package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;

public interface State extends IState {

	  /**
	   * power Button pressed
	   */
	  State powerButtonPressedState();

	  /**
	   * standby Button pressed
	   */
	  State standbyButtonPressedState();

	  /**
	   * coffee Button pressed
	   */
	  State coffeeButtonPressedState();

	  /**
	   * cleaning Button pressed
	   */
	  State cleaningButtonPressedState ();
	  
	  /**
	   * display the specified message on standard out
	   *
	   * @param message
	   *          The message to display
	   */
	  default void display(String message) {
	    System.out.println(message);
	  }
	
}
