package vc.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import core.data.ProfileData;
import core.data.ServerData;

public class ProfilesVC extends ATabbedVC implements ActionListener, Observer {

	private static final long serialVersionUID = 6365767402010906390L;

	private ServerData data;

	public ProfilesVC() {
		super("profiles", "Profile");
	}

	public void setData(ServerData data) {
		this.data = data;
		data.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<ProfileData> serverData = data.getProfiles();
		if (serverData.size() > this.tabs.getTabCount()) {
			ProfileVC pvc = new ProfileVC(serverData.lastElement().getLabel());
			this.tabs.addTab(serverData.lastElement().getLabel(), new JScrollPane(pvc,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
			pvc.setData(serverData.lastElement());
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
			String netName = JOptionPane.showInputDialog(this, "Profile Name", "New Profile");
			this.data.addProfile(netName);
		} else if (e.getSource().equals(this.deleteButton)) {
			JScrollPane pane = (JScrollPane) this.tabs.getSelectedComponent();
			ProfileVC svc = (ProfileVC) pane.getViewport().getView();
			this.data.removeProfile(svc.getData());
		}
	}
}
