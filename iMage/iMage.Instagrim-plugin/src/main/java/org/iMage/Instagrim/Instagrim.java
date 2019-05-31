package org.iMage.Instagrim;

import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;

public class Instagrim extends PluginForJmjrst {

	private Main jmjrst;
	
	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		
		return "Instagrim";
		
	}

	@Override
	public void init(Main arg0) {
		
		// Assign argument to the class attribute jmjrst.
		this.jmjrst = arg0;
		// Retrieve name of the user.
		String name = System.getProperty("user.name");
		// Give out message.
		System.err.println("iMage: Der Bildverschönerer, dem Influencer vertrauen!"
							+ "Jetzt bist auch Du Teil unseres Teams, " + name + ".");

	}

	@Override
	public boolean isConfigurable() {
		
		return true;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	

}
