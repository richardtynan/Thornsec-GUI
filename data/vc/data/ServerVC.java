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

import core.data.ServerData;

public class ServerVC extends JPanel implements FocusListener, Observer {

	private static final long serialVersionUID = -5755018011750456558L;

	private ServerData data;

	private JTextField user;
	private JTextField ip;
	private JTextField port;

	private ProfilesVC profiles;

	public ServerVC() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("user"), g);

		g.gridx = 1;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		user = new JTextField(20);
		user.addFocusListener(this);
		this.add(user, g);

		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("ip"), g);

		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		ip = new JTextField(20);
		ip.addFocusListener(this);
		this.add(ip, g);

		g.gridx = 0;
		g.gridy = 2;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("port"), g);

		g.gridx = 1;
		g.gridy = 2;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		port = new JTextField(20);
		port.addFocusListener(this);
		this.add(port, g);

		g.gridx = 0;
		g.gridy = 3;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 2;
		g.fill = GridBagConstraints.BOTH;

		this.profiles = new ProfilesVC();
		this.add(profiles, g);

	}

	public void setData(ServerData data) {
		this.data = data;
		profiles.setData(data);
		data.addObserver(this);
	}

	public ServerData getData() {
		return this.data;
	}

	public void update(Observable o, Object arg) {
		this.user.setText(this.data.getUser());
		this.ip.setText(this.data.getIP());
		this.port.setText(this.data.getPort());
	}

	public void focusLost(FocusEvent e) {
		if (e.getSource().equals(this.user)) {
			this.data.setUser(this.user.getText());
		} else if (e.getSource().equals(this.ip)) {
			this.data.setIP(this.ip.getText());
		} else if (e.getSource().equals(this.port)) {
			this.data.setPort(this.port.getText());
		}

	}

	public void focusGained(FocusEvent e) {

	}

}
