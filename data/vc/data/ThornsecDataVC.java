package vc.data;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.data.ThornsecData;

public class ThornsecDataVC extends JPanel implements ActionListener {

	private static final long serialVersionUID = 7528351319287921440L;

	private ThornsecData data;

	private NetworksVC networks;

	public ThornsecDataVC() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		this.add(saveButton, g);

		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.fill = GridBagConstraints.BOTH;

		this.networks = new NetworksVC();
		this.add(networks, g);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			this.data.save();
		}
	}

	public void setData(ThornsecData data) {
		this.data = data;
		this.networks.setData(data);
	}

}
