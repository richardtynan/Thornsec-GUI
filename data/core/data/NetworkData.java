package core.data;

import java.util.Iterator;
import java.util.Vector;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class NetworkData extends AData {

	private Vector<ServerData> servers;
	private Vector<PeripheralData> peripherals;
	private Vector<UserData> users;

	public NetworkData(String label) {
		super(label);
		this.servers = new Vector<ServerData>();
		this.peripherals = new Vector<PeripheralData>();
		this.users = new Vector<UserData>();
	}

	public void read(JsonObject data) {
		JsonObject serversData = data.getJsonObject("servers");
		if (serversData != null) {
			Iterator<?> iter = serversData.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				this.newServer(key, serversData.getJsonObject(key));
			}
		}

		JsonObject peripheralsData = data.getJsonObject("peripherals");
		if (peripheralsData != null) {
			Iterator<?> iter = peripheralsData.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				this.newPeripheral(key, peripheralsData.getJsonObject(key));
			}
		}

		JsonObject usersData = data.getJsonObject("users");
		if (usersData != null) {
			Iterator<?> iter = usersData.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				this.newUser(key, usersData.getJsonObject(key));
			}
		}
	}

	public JsonObject write() {
		JsonObjectBuilder obj = Json.createObjectBuilder();

		JsonObjectBuilder serversJson = Json.createObjectBuilder();
		for (int i = 0; i < this.servers.size(); i++) {
			serversJson.add(this.servers.elementAt(i).getLabel(), this.servers.elementAt(i).write());
		}
		obj.add("servers", serversJson.build());

		JsonObjectBuilder peripheralsJson = Json.createObjectBuilder();
		for (int i = 0; i < this.peripherals.size(); i++) {
			peripheralsJson.add(this.peripherals.elementAt(i).getLabel(), this.peripherals.elementAt(i).write());
		}
		obj.add("peripherals", peripheralsJson.build());

		JsonObjectBuilder usersJson = Json.createObjectBuilder();
		for (int i = 0; i < this.users.size(); i++) {
			usersJson.add(this.users.elementAt(i).getLabel(), this.users.elementAt(i).write());
		}
		obj.add("users", usersJson.build());

		return obj.build();
	}

	// servers

	public Vector<ServerData> getServers() {
		return servers;
	}

	public void deleteServer(ServerData server) {
		this.servers.remove(server);
		this.setChanged();
		this.notifyObservers();
	}

	public ServerData newServer(String label) {
		ServerData newNet = new ServerData(label);
		this.servers.addElement(newNet);
		this.setChanged();
		this.notifyObservers();
		return newNet;
	}

	public void newServer(String label, JsonObject data) {
		ServerData newNet = this.newServer(label);
		newNet.read(data);
	}

	// peripherals

	public Vector<PeripheralData> getPeripherals() {
		return peripherals;
	}

	public void deletePeripheral(PeripheralData server) {
		this.peripherals.remove(server);
		this.setChanged();
		this.notifyObservers();
	}

	public PeripheralData newPeripheral(String label) {
		PeripheralData newNet = new PeripheralData(label);
		this.peripherals.addElement(newNet);
		this.setChanged();
		this.notifyObservers();
		return newNet;
	}

	public void newPeripheral(String label, JsonObject data) {
		PeripheralData newNet = this.newPeripheral(label);
		newNet.read(data);
	}

	// users
	
	public Vector<UserData> getUsers() {
		return users;
	}

	public void deleteUser(UserData label) {
		this.users.remove(label);
		this.setChanged();
		this.notifyObservers();
	}

	public UserData newUser(String label) {
		UserData newNet = new UserData(label);
		this.users.addElement(newNet);
		this.setChanged();
		this.notifyObservers();
		return newNet;
	}

	public void newUser(String label, JsonObject data) {
		UserData newNet = this.newUser(label);
		newNet.read(data);
	}
}
