package vc.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import core.data.NetworkData;
import core.data.PeripheralData;

public class PeripheralsVC extends ATabbedVC implements ActionListener, Observer {

	private static final long serialVersionUID = 6365767402010906390L;

	private NetworkData data;

	public PeripheralsVC() {
		super("peripherals", "Peripheral");
	}

	public void setData(NetworkData data) {
		this.data = data;
		data.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Vector<PeripheralData> peripheralData = data.getPeripherals();
		if (peripheralData.size() > this.tabs.getTabCount()) {
			PeripheralVC net = new PeripheralVC();
			this.tabs.addTab(peripheralData.lastElement().getLabel(), new JScrollPane(net,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
			net.setData(peripheralData.lastElement());
		} else if (peripheralData.size() < this.tabs.getTabCount()) {
			for (int i = 0; i < peripheralData.size(); i++) {
				if (peripheralData.elementAt(i).getLabel() != this.tabs.getTitleAt(i)) {
					this.tabs.removeTabAt(i);
				}
			}
			if (peripheralData.size() < this.tabs.getTabCount()) {
				this.tabs.removeTabAt(this.tabs.getComponentCount() - 1);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.newButton)) {
			String netName = JOptionPane.showInputDialog(this, "Peripheral name", "New Peripheral");
			this.data.newPeripheral(netName);
		} else if (e.getSource().equals(this.deleteButton)) {
			JScrollPane pane = (JScrollPane) this.tabs.getSelectedComponent();
			PeripheralVC svc = (PeripheralVC) pane.getViewport().getView();
			this.data.deletePeripheral(svc.getData());
		}
	}
}
