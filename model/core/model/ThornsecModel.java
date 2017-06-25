package core.model;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.data.NetworkData;
import core.data.ThornsecData;

public class ThornsecModel extends Observable implements Observer {

	private ThornsecData data;

	private Vector<NetworkModel> networks;

	public ThornsecModel() {
		this.networks = new Vector<NetworkModel>();
	}
	
	public Vector<NetworkModel> getNetworks() {
		return networks;
	}

	public void setData(ThornsecData data) {
		this.data = data;
		data.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<NetworkData> netData = data.getNetworks();
		if (netData.size() > networks.size()) {
			NetworkModel net = new NetworkModel(netData.lastElement().getLabel());
			this.networks.addElement(net);
			net.setData(netData.lastElement());
			this.setChanged();
			this.notifyObservers();
		} else if (netData.size() < networks.size()) {
			for (int i = 0; i < netData.size(); i++) {
				if (netData.elementAt(i).getLabel() != networks.elementAt(i).getData().getLabel()) {
					networks.removeElementAt(i);
					this.setChanged();
					this.notifyObservers();
				}
			}
			if (netData.size() < networks.size()) {
				networks.removeElementAt(this.networks.size() - 1);
				this.setChanged();
				this.notifyObservers();
			}
		}
	}

}
