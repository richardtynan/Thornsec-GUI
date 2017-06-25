package vc.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import core.data.NetworkData;
import core.data.ServerData;

public class ServersVC extends ATabbedVC implements ActionListener, Observer {

	private static final long serialVersionUID = 6365767402010906390L;

	private NetworkData data;

	public ServersVC() {
		super("servers", "Server");
	}

	public void setData(NetworkData data) {
		this.data = data;
		data.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<ServerData> serverData = data.getServers();
		if (serverData.size() > this.tabs.getTabCount()) {
			ServerVC net = new ServerVC();
			this.tabs.addTab(serverData.lastElement().getLabel(), new JScrollPane(net,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
			net.setData(serverData.lastElement());
		} else if (serverData.size() < this.tabs.getTabCount()) {
			for (int i = 0; i < serverData.size(); i++) {
				if (serverData.elementAt(i).getLabel() != this.tabs.getTitleAt(i)) {
					this.tabs.removeTabAt(i);
				}
			}
			if (serverData.size() < this.tabs.getTabCount()) {
				this.tabs.removeTabAt(this.tabs.getComponentCount() - 1);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.newButton)) {
			String netName = JOptionPane.showInputDialog(this, "Server name", "New Server");
			this.data.newServer(netName);
		} else if (e.getSource().equals(this.deleteButton)) {
			JScrollPane pane = (JScrollPane) this.tabs.getSelectedComponent();
			ServerVC svc = (ServerVC) pane.getViewport().getView();
			this.data.deleteServer(svc.getData());
		}
	}
}
