package core.data;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class UserData extends AData {

	private String ip;

	public UserData(String label) {
		super(label);
		this.ip = "";
	}

	public void read(JsonObject data) {
		this.setIP(data.getString("ip", ""));
	}

	public JsonObject write() {
		JsonObjectBuilder json = Json.createObjectBuilder();
		json.add("ip", this.getIP());
		return json.build();
	}

	public String getIP() {
		return ip;
	}

	public void setIP(String ip) {
		this.ip = ip;
		this.setChanged();
		this.notifyObservers();
	}

}
