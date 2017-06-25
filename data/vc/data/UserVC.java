package vc.data;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.data.UserData;

public class UserVC extends JPanel implements FocusListener, Observer {

	private static final long serialVersionUID = -5755018011750456558L;

	private UserData data;

	private JTextField ip;

	public UserVC() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("ip"), g);

		g.gridx = 1;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		ip = new JTextField(20);
		ip.addFocusListener(this);
		this.add(ip, g);

	}

	public void setData(UserData data) {
		this.data = data;
		data.addObserver(this);
	}

	public UserData getData() {
		return this.data;
	}

	public void update(Observable o, Object arg) {
		this.ip.setText(this.data.getIP());
	}

	public void focusLost(FocusEvent e) {
		if (e.getSource().equals(this.ip)) {
			this.data.setIP(this.ip.getText());
		}
	}

	public void focusGained(FocusEvent e) {

	}

}
