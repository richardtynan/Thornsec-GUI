package core.data;

import java.util.Observable;

import javax.json.JsonObject;

public abstract class AData extends Observable {

	private String label;

	public AData(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
	public abstract void read(JsonObject data);
	
	public abstract JsonObject write();

}
