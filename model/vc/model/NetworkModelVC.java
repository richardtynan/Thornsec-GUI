package vc.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import core.model.NetworkModel;
import core.model.ServerModel;

public class NetworkModelVC extends JPanel implements Observer, ActionListener {

	private static final long serialVersionUID = 912200431295223243L;

	private NetworkModel model;

	protected JTabbedPane tabs;

	public NetworkModelVC() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;

		JButton init = new JButton("initialise");
		init.addActionListener(this);
		this.add(init, g);
		
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.fill = GridBagConstraints.BOTH;

		tabs = new JTabbedPane();
		this.add(tabs, g);
	}

	public void setModel(NetworkModel model) {
		this.model = model;
		model.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<ServerModel> netData = model.getServers();
		if (netData.size() > this.tabs.getTabCount()) {
			ServerModelVC net = new ServerModelVC();
			this.tabs.addTab(netData.lastElement().getLabel(), net);
			net.setModel(netData.lastElement().getLabel(), netData.lastElement());
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
		this.model.init();
	}

}
