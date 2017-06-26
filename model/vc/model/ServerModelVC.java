package vc.model;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import core.iface.IProfile;
import core.model.ServerModel;

public class ServerModelVC extends JPanel implements Observer, ActionListener {

	private static final long serialVersionUID = 6848864259576909519L;

	private ServerModel model;

	private String name;
	
	private JPanel profiles;

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
		g.gridwidth = 4;
		g.fill = GridBagConstraints.HORIZONTAL;
		profiles = new JPanel();
		profiles.setLayout(new BoxLayout(profiles, BoxLayout.X_AXIS));
		this.add(new JScrollPane(profiles, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), g);
		
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		audit = new JButton("Audit");
		audit.addActionListener(this);
		this.add(audit, g);

		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		dryRun = new JButton("Dry Run");
		dryRun.addActionListener(this);
		this.add(dryRun, g);

		g.gridx = 2;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		config = new JButton("Config");
		config.addActionListener(this);
		this.add(config, g);

		g.gridx = 3;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		iso = new JButton("ISO");
		iso.addActionListener(this);
		this.add(iso, g);

		g.gridx = 0;
		g.gridy = 2;
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
		model.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		Vector<IProfile> netData = model.getProfiles();
		if (netData.size() > this.profiles.getComponentCount()) {
			JLabel newprof = new JLabel(netData.lastElement().getLabel());
			newprof.setName(netData.lastElement().getLabel());
			newprof.setBorder(new LineBorder(Color.black));
			this.profiles.add(newprof);
		} else if (netData.size() < this.profiles.getComponentCount()) {
			for (int i = 0; i < netData.size(); i++) {
				if (netData.elementAt(i).getLabel() != this.profiles.getComponent(i).getName()) {
					this.profiles.remove(i);
				}
			}
			if (netData.size() < this.profiles.getComponentCount()) {
				this.profiles.remove(this.profiles.getComponentCount() - 1);
			}
		}
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
