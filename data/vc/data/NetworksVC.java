package vc.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;

import core.data.NetworkData;
import core.data.ThornsecData;

public class NetworksVC extends ATabbedVC implements ActionListener, Observer {

	private static final long serialVersionUID = 1545749264592473672L;
	
	private ThornsecData data;

	public NetworksVC() {
		super("networks", "Network");
	}

	public void setData(ThornsecData data) {
		this.data = data;
		data.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<NetworkData> netData = data.getNetworks();
		if (netData.size() > this.tabs.getTabCount()) {
			NetworkVC net = new NetworkVC();
			this.tabs.addTab(netData.lastElement().getLabel(), net);
			net.setData(netData.lastElement());
		} else if (netData.size() < this.tabs.getTabCount()) {
			for (int i = 0; i < netData.size(); i++) {
				if (netData.elementAt(i).getLabel() != this.tabs.getTitleAt(i)) {
					this.tabs.removeTabAt(i);
				}
			}
			if (netData.size() < this.tabs.getTabCount()) {
				this.tabs.removeTabAt(this.tabs.getComponentCount() - 1);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.newButton)) {
			String netName = JOptionPane.showInputDialog(this, "Network name", "New Network");
			this.data.newNetwork(netName);
		} else if (e.getSource().equals(this.deleteButton)) {
			NetworkVC vc = (NetworkVC) this.tabs.getSelectedComponent();
			this.data.deleteNetwork(vc.getData());
		}
	}

}
