package core.unit.fs;

import core.unit.SimpleUnit;

public class FileUnit extends SimpleUnit {

	private String text;
	private String path;

	public FileUnit(String name, String precondition, String text, String path) {
		super(name, precondition, "", "cat " + path + ";", text, "pass");
		this.text = text;
		this.path = path;
	}

	public String getConfig() {
		return "echo \"" + getText() + "\" | sudo tee " + path + " > /dev/null;";
	}

	public String getText() {
		return this.text;
	}

}