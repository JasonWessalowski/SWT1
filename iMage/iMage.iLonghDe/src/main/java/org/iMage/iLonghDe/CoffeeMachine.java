package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

public class CoffeeMachine implements IStateMachine {

	State state;
	
	public CoffeeMachine() {
		
		this.state = new Standby();
		
	}
	
	@Override
	public void powerButtonPressed() {

		this.state = state.powerButtonPressedState();
		
	}

	@Override
	public void standbyButtonPressed() {
		
		this.state = state.standbyButtonPressedState();
		
	}

	@Override
	public void coffeeButtonPressed() {
		
		this.state = state.coffeeButtonPressedState();
		
	}

	@Override
	public void cleaningButtonPressed() {
		
		this.state = state.cleaningButtonPressedState();
		
	}

	@Override
	public IState getCurrentState() {
			
		return this.state;

	}

}
