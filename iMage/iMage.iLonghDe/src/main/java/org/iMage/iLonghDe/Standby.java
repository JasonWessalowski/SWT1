package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;

public class Standby implements State {

	public Standby() {
		
		this.display("");
		
	}
	
	@Override
	public State powerButtonPressedState() {
		
		return new Off();
		
	}

	@Override
	public State standbyButtonPressedState() {
		
		return new Waiting();
		
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
