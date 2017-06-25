package core.iface;

import java.util.Vector;

import core.model.NetworkModel;

public interface IProfile {
	
	public String getLabel();
	
	public String[] getStringProperties();
	
	public void init(String server, NetworkModel model);
	
	public Vector<IUnit> getUnits(String server, NetworkModel model);

}
