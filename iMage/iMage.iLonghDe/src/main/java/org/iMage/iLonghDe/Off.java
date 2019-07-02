package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;

public class Off implements State {

	@Override
	public State powerButtonPressedState() {
		
		return this;
		
	}

	@Override
	public State standbyButtonPressedState() {
		
		return this;
		
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
