package vc.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.model.ServerModel;

public class ServerModelVC extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6848864259576909519L;

	private ServerModel model;

	private String name;

	private JButton audit;
	private JButton dryRun;
	private JButton config;
	private JButton iso;

	private JTextArea output;

	public ServerModelVC() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		audit = new JButton("Audit");
		audit.addActionListener(this);
		this.add(audit, g);

		g.gridx = 1;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		dryRun = new JButton("Dry Run");
		dryRun.addActionListener(this);
		this.add(dryRun, g);

		g.gridx = 2;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		config = new JButton("Config");
		config.addActionListener(this);
		this.add(config, g);

		g.gridx = 3;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;
		iso = new JButton("ISO");
		iso.addActionListener(this);
		this.add(iso, g);

		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 4;
		g.fill = GridBagConstraints.BOTH;
		output = new JTextArea();
		this.add(new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), g);
	}

	public void setModel(String name, ServerModel model) {
		this.name = name;
		this.model = model;
	}

	public String getServerName() {
		return this.name;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.audit)) {
			this.model.auditNonBlock(this.getName(), new TextAreaOutputStream(this.output), System.in, false);
		} else if (e.getSource().equals(this.config)) {
			this.model.configNonBlock(this.getName(), new TextAreaOutputStream(this.output), System.in);
		} else if (e.getSource().equals(this.dryRun)) {
			this.model.dryrunNonBlock(this.getName(), new TextAreaOutputStream(this.output), System.in);
		} else if (e.getSource().equals(this.iso)) {
			this.model.isoNonBlock(this.getName(), new TextAreaOutputStream(this.output), System.in);
		}
	}

}
