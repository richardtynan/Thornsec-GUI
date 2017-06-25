package vc.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import core.data.NetworkData;
import core.data.UserData;

public class UsersVC extends ATabbedVC implements ActionListener, Observer {

	private static final long serialVersionUID = 6365767402010906390L;

	private NetworkData data;

	public UsersVC() {
		super("users", "User");
	}

	public void setData(NetworkData data) {
		this.data = data;
		data.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<UserData> userData = data.getUsers();
		if (userData.size() > this.tabs.getTabCount()) {
			UserVC net = new UserVC();
			this.tabs.addTab(userData.lastElement().getLabel(), new JScrollPane(net,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
			net.setData(userData.lastElement());
		} else if (userData.size() < this.tabs.getTabCount()) {
			for (int i = 0; i < userData.size(); i++) {
				if (userData.elementAt(i).getLabel() != this.tabs.getTitleAt(i)) {
					this.tabs.removeTabAt(i);
				}
			}
			if (userData.size() < this.tabs.getTabCount()) {
				this.tabs.removeTabAt(this.tabs.getComponentCount() - 1);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.newButton)) {
			String netName = JOptionPane.showInputDialog(this, "User name", "New User");
			this.data.newUser(netName);
		} else if (e.getSource().equals(this.deleteButton)) {
			JScrollPane pane = (JScrollPane) this.tabs.getSelectedComponent();
			UserVC svc = (UserVC) pane.getViewport().getView();
			this.data.deleteUser(svc.getData());
		}
	}
}
