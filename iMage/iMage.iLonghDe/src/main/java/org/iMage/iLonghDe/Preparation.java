package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;

public class Preparation implements State {

	public Preparation() {
		
		this.display("BrewCoffee");
		
	}
	
	public State powerButtonPressedState() {
		
		return this;
		
	}

	public State standbyButtonPressedState() {
		
		this.display("Done");
		return new Standby();
		
	}

	public State coffeeButtonPressedState() {
		
		this.display("Done");
		return new Waiting();
		
	}

	public State cleaningButtonPressedState() {
		
		return this;
		
	}
	
}
