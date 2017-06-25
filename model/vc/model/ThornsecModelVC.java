package vc.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import core.model.NetworkModel;
import core.model.ThornsecModel;

public class ThornsecModelVC extends JPanel implements Observer {

	private static final long serialVersionUID = 2725105276536657607L;

	private ThornsecModel model;

	protected JTabbedPane tabs;

	public ThornsecModelVC() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 1;
		g.fill = GridBagConstraints.BOTH;

		tabs = new JTabbedPane();
		this.add(tabs, g);
	}

	public void setModel(ThornsecModel model) {
		this.model = model;
		model.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<NetworkModel> netData = model.getNetworks();
		if (netData.size() > this.tabs.getTabCount()) {
			NetworkModelVC net = new NetworkModelVC();
			this.tabs.addTab(netData.lastElement().getData().getLabel(), net);
			net.setModel(netData.lastElement());
		} else if (netData.size() < this.tabs.getTabCount()) {
			for (int i = 0; i < netData.size(); i++) {
				if (netData.elementAt(i).getData().getLabel() != this.tabs.getTitleAt(i)) {
					this.tabs.removeTabAt(i);
				}
			}
			if (netData.size() < this.tabs.getTabCount()) {
				this.tabs.removeTabAt(this.tabs.getComponentCount() - 1);
			}
		}
	}

}
