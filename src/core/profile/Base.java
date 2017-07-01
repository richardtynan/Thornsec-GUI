package core.profile;

import java.util.Vector;

import core.iface.IProfile;
import core.iface.IUnit;
import core.model.NetworkModel;
import core.unit.SimpleUnit;

public class Base implements IProfile {

	public String getLabel() {
		return "Base";
	}

	public String[] getStringProperties() {
		return new String[] { "hostname", "update" };
	}

	public void init(String server, NetworkModel model) {

	}

	public Vector<IUnit> getUnits(String server, NetworkModel model) {
		Vector<IUnit> units = new Vector<IUnit>();
		units.addElement(new SimpleUnit("host", "sudo", "echo \"ERROR: Configuring with hostname mismatch\";",
				"hostname;", model.getServer(server).getProperty("hostname"), "pass"));
		// units.addElement(new SimpleUnit("sudo", "proceed", "echo \"ERROR:
		// sudo\";",
		// "sudo --stdin hostname &>/dev/null;", "", "pass"));
		// String configcmd = "";
		// if (networkData.getUpdate(this.getLabel()).equals("true")) {
		// configcmd = "sudo apt-get --assume-yes upgrade;";
		// } else {
		// configcmd = "echo \"$out\";";
		// }
		// units.insertElementAt(new SimpleUnit("update", "proceed", configcmd,
		// "sudo apt-get update > /dev/null; sudo apt-get --assume-no upgrade |
		// grep \"0 upgraded, 0 newly installed, 0 to remove and 0 not
		// upgraded.\";",
		// "0 upgraded, 0 newly installed, 0 to remove and 0 not upgraded.",
		// "pass"), 2);
		return units;
	}

}
