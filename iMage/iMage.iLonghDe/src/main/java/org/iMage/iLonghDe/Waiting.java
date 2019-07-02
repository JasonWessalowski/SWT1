package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;

public class Waiting implements State {

	public Waiting() {
		
		this.display("Waiting");
		
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
		
		return new Preparation();
		
	}

	@Override
	public State cleaningButtonPressedState() {
		
		return new Cleansing();
		
	}
	
}
