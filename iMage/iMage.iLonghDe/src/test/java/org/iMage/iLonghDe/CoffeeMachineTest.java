package org.iMage.iLonghDe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CoffeeMachineTest {

	private CoffeeMachine machine;
	
	@Before
	public void setUp() throws Exception {
		
		this.machine = new CoffeeMachine();
		
	}

	/**
	 * Test if a straight forward process of brewing coffee will end with an Off-state.
	 */
	@Test
	public void simpleRun() {
		
		machine.standbyButtonPressed();
		machine.coffeeButtonPressed();
		machine.coffeeButtonPressed();
		machine.cleaningButtonPressed();
		machine.standbyButtonPressed();
		machine.powerButtonPressed();
		
		assertTrue(machine.state instanceof Off);
		
	}
	
	/**
	 * Test if undefined transition will result in no changes.
	 */
	@Test
	public void undefinedTransition() {
		
		machine.coffeeButtonPressed();
		machine.cleaningButtonPressed();
		
		assertTrue(machine.state instanceof Standby);
		
	}
	
	/**
	 * Test if NullPointerException is thrown when a null-state is referenced.
	 */
	@Test(expected = NullPointerException.class) 
	public void nullEvent() {
		
		machine.state = null;
		machine.standbyButtonPressed();
		
	}
	
	/**
	 * Test if you can somehow get out of the Off-state again?
	 */
	@Test
	public void canYouEscape() {
		
		machine.standbyButtonPressed();
		machine.coffeeButtonPressed();
		machine.coffeeButtonPressed();
		machine.cleaningButtonPressed();
		machine.standbyButtonPressed();
		machine.powerButtonPressed();
		
		// Trying out all methods again.
		machine.standbyButtonPressed();
		machine.coffeeButtonPressed();
		machine.cleaningButtonPressed();
		machine.powerButtonPressed();
		
		assertTrue(machine.state instanceof Off);
		
	}
}
