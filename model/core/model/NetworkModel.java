package core.model;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.data.NetworkData;
import core.data.ServerData;

public class NetworkModel extends Observable implements Observer {

	private String label;

	private NetworkData data;

	private Vector<ServerModel> servers;

	public NetworkModel(String label) {
		this.label = label;
		this.servers = new Vector<ServerModel>();
	}

	public String getLabel() {
		return this.label;
	}
	
	public void init() {
		for (int i = 0; i < servers.size(); i++) {
			servers.elementAt(i).init(this);
		}
	}

	public void update(Observable o, Object arg) {
		Vector<ServerData> netData = data.getServers();
		if (netData.size() > servers.size()) {
			ServerModel net = new ServerModel(netData.lastElement().getLabel());
			this.servers.addElement(net);
			net.setData(netData.lastElement());
			net.addObserver(this);
			this.setChanged();
			this.notifyObservers();
		} else if (netData.size() < servers.size()) {
			for (int i = 0; i < netData.size(); i++) {
				if (netData.elementAt(i).getLabel() != servers.elementAt(i).getLabel()) {
					servers.removeElementAt(i);
					this.setChanged();
					this.notifyObservers();
				}
			}
			if (netData.size() < servers.size()) {
				servers.removeElementAt(this.servers.size() - 1);
				this.setChanged();
				this.notifyObservers();
			}
		} else {
			this.init();
		}
	}

	public void setData(NetworkData data) {
		this.data = data;
		data.addObserver(this);
	}

	public NetworkData getData() {
		return this.data;
	}

	public Vector<ServerModel> getServers() {
		return servers;
	}

}
