package vc.data;

import javax.swing.JTabbedPane;

import core.data.NetworkData;

public class NetworkVC extends JTabbedPane {

	private static final long serialVersionUID = -5776633874585043469L;

	private NetworkData data;

	private ServersVC servers;
	private PeripheralsVC peripherals;
	private UsersVC users;

	public NetworkVC() {
		servers = new ServersVC();
		this.addTab("Servers", servers);

		peripherals = new PeripheralsVC();
		this.addTab("Peripherals", peripherals);

		users = new UsersVC();
		this.addTab("Users", users);
	}

	public NetworkData getData() {
		return this.data;
	}

	public void setData(NetworkData data) {
		this.data = data;
		this.servers.setData(data);
		this.peripherals.setData(data);
		this.users.setData(data);
	}

}
