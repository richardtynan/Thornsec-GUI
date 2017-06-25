package vc.data;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public abstract class ATabbedVC extends JPanel implements ActionListener, Observer {

	private static final long serialVersionUID = -1953023588048576490L;
	protected JButton newButton;
	protected JButton deleteButton;

	protected JTabbedPane tabs;

	public ATabbedVC(String multiple, String single) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;

		newButton = new JButton("New " + single);
		newButton.addActionListener(this);
		this.add(newButton, g);

		g.gridx = 1;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.HORIZONTAL;

		deleteButton = new JButton("Delete " + single);
		deleteButton.addActionListener(this);
		this.add(deleteButton, g);

		tabs = new JTabbedPane();
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 2;
		g.fill = GridBagConstraints.BOTH;
		this.add(tabs, g);
	}

}
