package core.data;

import java.util.Iterator;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import core.iface.IProfile;

public class ProfileData extends AData {

	private Properties props;
	private IProfile prof;

	public ProfileData(String label) {
		super(label);
		try {
			this.prof = (IProfile) Class.forName("core.profile." + this.getLabel()).newInstance();
			this.props = new Properties();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public IProfile getProfile() {
		return this.prof;
	}

	public Properties getProperties() {
		return this.props;
	}

	public void setProperty(String key, String value) {
		props.setProperty(key, value);
		this.setChanged();
		this.notifyObservers();
	}

	public void read(JsonObject data) {
		String[] profProps = prof.getStringProperties();
		for (int i = 0; i < profProps.length; i++) {
			this.setProperty(profProps[i], data.getString(profProps[i], ""));
		}
	}

	public JsonObject write() {
		JsonObjectBuilder serverJson = Json.createObjectBuilder();
		Iterator<Object> iter = props.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			serverJson.add(key, props.getProperty(key, props.getProperty(key)));
		}
		return serverJson.build();
	}

}
