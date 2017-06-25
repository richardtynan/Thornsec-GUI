package core.data;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class ThornsecData extends Observable {

	private String file;
	private Vector<NetworkData> networks;

	public ThornsecData(String file) {
		this.file = file;
		networks = new Vector<>();
	}

	public void save() {
		try {
			JsonObject nets = this.write();
			Files.write(Paths.get(file), nets.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			String text = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
			JsonReader reader = Json.createReader(new StringReader(text));
			JsonObject nets = reader.readObject();
			this.read(nets);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// rw

	public JsonObject write() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		Iterator<NetworkData> iter = networks.iterator();
		while (iter.hasNext()) {
			NetworkData net = iter.next();
			obj.add(net.getLabel(), net.write());
		}
		return obj.build();
	}

	public void read(JsonObject data) {
		networks.removeAllElements();
		Iterator<?> iter = data.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			this.newNetwork(key, data.getJsonObject(key));
		}
	}
	
	// file

	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	// networks

	public Vector<NetworkData> getNetworks() {
		return networks;
	}
	
	public NetworkData newNetwork(String label) {
		NetworkData newNet = new NetworkData(label);
		this.networks.addElement(newNet);
		this.setChanged();
		this.notifyObservers();
		return newNet;
	}

	public void newNetwork(String label, JsonObject data) {
		NetworkData newNet = this.newNetwork(label);
		newNet.read(data);
	}
	
	public void deleteNetwork(NetworkData net) {
		this.networks.remove(net);
		this.setChanged();
		this.notifyObservers();
	}

}
