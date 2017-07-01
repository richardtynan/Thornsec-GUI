package core.profile;

import java.util.Vector;

import core.iface.IProfile;
import core.iface.IUnit;
import core.model.NetworkModel;

public class Router implements IProfile {
	
	public String getLabel() {
		return "Router";
	}

	public String[] getStringProperties() {
		return new String[] {};
	}

	public void init(String server, NetworkModel model) {
		
	}

	public Vector<IUnit> getUnits(String server, NetworkModel model) {
		Vector<IUnit> units = new Vector<IUnit>();

		return units;
	}

}
