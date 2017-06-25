package core.data;

import java.util.Iterator;
import java.util.Vector;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServerData extends AData {

	private String user;
	private String ip;
	private String port;

	private Vector<ProfileData> profiles;

	public ServerData(String label) {
		super(label);
		this.user = "";
		this.ip = "";
		this.port = "";
		this.profiles = new Vector<ProfileData>();
	}

	public void read(JsonObject data) {
		this.setUser(data.getString("user", ""));
		this.setIP(data.getString("ip", ""));
		this.setPort(data.getString("port", ""));

		JsonArray profs = data.getJsonArray("profiles");
		for (int i = 0; i < profs.size(); i++) {
			this.addProfile(profs.getString(i), data.getJsonObject("data"));
		}
	}

	public JsonObject write() {
		JsonObjectBuilder serverJson = Json.createObjectBuilder();
		serverJson.add("user", this.getUser());
		serverJson.add("ip", this.getIP());
		serverJson.add("port", this.getPort());

		JsonArrayBuilder profileArray = Json.createArrayBuilder();
		for (int i = 0; i < profiles.size(); i++) {
			profileArray.add(profiles.elementAt(i).getLabel());
		}
		serverJson.add("profiles", profileArray.build());

		JsonObjectBuilder dataJson = Json.createObjectBuilder();
		for (int i = 0; i < profiles.size(); i++) {
			JsonObject profData = profiles.elementAt(i).write();
			Iterator<String> iter = profData.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				dataJson.add(key, profData.getString(key, ""));
			}
		}
		serverJson.add("data", dataJson.build());

		return serverJson.build();
	}

	public Vector<ProfileData> getProfiles() {
		return this.profiles;
	}

	public ProfileData addProfile(String profile) {
		ProfileData data = new ProfileData(profile);
		this.profiles.addElement(data);
		this.setChanged();
		this.notifyObservers();
		return data;
	}

	public void addProfile(String profile, JsonObject data) {
		ProfileData prof = this.addProfile(profile);
		prof.read(data);
	}

	public void removeProfile(ProfileData profile) {
		this.profiles.removeElement(profile);
		this.setChanged();
		this.notifyObservers();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
		this.setChanged();
		this.notifyObservers();
	}

	public String getIP() {
		return ip;
	}

	public void setIP(String ip) {
		this.ip = ip;
		this.setChanged();
		this.notifyObservers();
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
		this.setChanged();
		this.notifyObservers();
	}

}
