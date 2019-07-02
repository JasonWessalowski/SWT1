package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;

public class Cleansing implements State {

	public Cleansing() {
		
		this.display("Cleaning");
		
	}
	
	@Override
	public State powerButtonPressedState() {
		
		return this;
		
	}

	@Override
	public State standbyButtonPressedState() {
		
		return new Standby();
		
	}

	@Override
	public State coffeeButtonPressedState() {
		
		return this;
		
	}

	@Override
	public State cleaningButtonPressedState() {
		
		return this;
		
	}
	
}
