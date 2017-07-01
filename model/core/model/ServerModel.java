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

	private NetworkModel model;

	public ServerModel(String label, NetworkModel model) {
		this.label = label;
		this.model = model;
		this.profiles = new Vector<IProfile>();
	}

	public String getLabel() {
		return this.label;
	}

	public void init() {
		for (int i = 0; i < profiles.size(); i++) {
			profiles.elementAt(i).init(this.label, this.model);
		}
	}

	public void update(Observable o, Object arg) {
		Vector<ProfileData> netData = data.getProfiles();
		if (netData.size() > profiles.size()) {
			try {
				this.profiles.addElement(netData.lastElement().getProfile());
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

	public String getProperty(String key) {
		for (int i = 0; i < this.data.getProfiles().size(); i++) {
			if (this.data.getProfiles().elementAt(i).getProperties().containsKey(key)) {
				return this.data.getProfiles().elementAt(i).getProperties().getProperty(key);
			}
		}
		return null;
	}

	public void setData(ServerData data) {
		this.data = data;
		data.addObserver(this);
	}

	public ServerData getData() {
		return this.data;
	}

	public void auditBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("auditBlock");
	}

	public void auditNonBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("auditNonBlock");
	}

	public void dryrunBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("dryrunBlock");
	}

	public void dryrunNonBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("dryrunNonBlock");
	}

	public void configBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("configBlock");
	}

	public void configNonBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("configNonBlock");
	}

	public void isoBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("isoBlock");
	}

	public void isoNonBlock(OutputStream out, InputStream in, boolean quiet) {
		System.out.println("isoNonBlock");
	}

	public Vector<IProfile> getProfiles() {
		return this.profiles;
	}

}
