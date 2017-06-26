package core.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.data.ProfileData;
import core.data.ServerData;
import core.iface.IProfile;

public class ServerModel extends Observable implements Observer {

	private String label;

	private ServerData data;

	private Vector<IProfile> profiles;

	public ServerModel(String label) {
		this.label = label;
		this.profiles = new Vector<IProfile>();
	}

	public String getLabel() {
		return this.label;
	}

	public void init(NetworkModel model) {
		for (int i = 0; i < profiles.size(); i++) {
			profiles.elementAt(i).init(this.label, model);
		}
	}

	public void update(Observable o, Object arg) {
		Vector<ProfileData> netData = data.getProfiles();
		if (netData.size() > profiles.size()) {
			try {
				IProfile prof = (IProfile) Class.forName("core.profile." + netData.lastElement().getLabel()).newInstance();
				this.profiles.addElement(prof);
				this.setChanged();
				this.notifyObservers();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (netData.size() < profiles.size()) {
			for (int i = 0; i < netData.size(); i++) {
				if (netData.elementAt(i).getLabel() != profiles.elementAt(i).getLabel()) {
					profiles.removeElementAt(i);
					this.setChanged();
					this.notifyObservers();
				}
			}
			if (netData.size() < profiles.size()) {
				profiles.removeElementAt(this.profiles.size() - 1);
				this.setChanged();
				this.notifyObservers();
			}
		}
	}

	public void setData(ServerData data) {
		this.data = data;
		data.addObserver(this);
	}

	public ServerData getData() {
		return this.data;
	}

	public void auditBlock(String server, OutputStream out, InputStream in, boolean quiet) {
		System.out.println("auditBlock");
	}

	public void auditNonBlock(String server, OutputStream out, InputStream in, boolean quiet) {
		System.out.println("auditNonBlock");
	}

	public void dryrunBlock(String server, OutputStream out, InputStream in) {
		System.out.println("dryrunBlock");
	}

	public void dryrunNonBlock(String server, OutputStream out, InputStream in) {
		System.out.println("dryrunNonBlock");
	}

	public void configBlock(String server, OutputStream out, InputStream in) {
		System.out.println("configBlock");
	}

	public void configNonBlock(String server, OutputStream out, InputStream in) {
		System.out.println("configNonBlock");
	}

	public void isoBlock(String server, OutputStream out, InputStream in) {
		System.out.println("isoBlock");
	}

	public void isoNonBlock(String server, OutputStream out, InputStream in) {
		System.out.println("isoNonBlock");
	}
	
	public Vector<IProfile> getProfiles() {
		return this.profiles;
	}

}
