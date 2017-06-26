package vc.data;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.data.ProfileData;
import core.iface.IProfile;

public class ProfileVC extends JPanel implements FocusListener, Observer {

	private static final long serialVersionUID = -5755018011750456558L;

	private ProfileData data;
	
	private Hashtable<String, JTextField> fields;

	public ProfileVC(String profile) {
		fields = new Hashtable<>();
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		try {
			IProfile prof = (IProfile) Class.forName("core.profile." + profile).newInstance();
			
			String[] props = prof.getStringProperties();
			for (int i = 0; i < props.length; i++) {
				g.gridx = 0;
				g.gridy = i;
				g.weightx = 0;
				g.weighty = 0;
				g.fill = GridBagConstraints.HORIZONTAL;
				this.add(new JLabel(props[i]), g);

				g.gridx = 1;
				g.gridy = i;
				g.weightx = 1;
				g.weighty = 0;
				g.fill = GridBagConstraints.HORIZONTAL;
				JTextField user = new JTextField(20);
				user.setName(props[i]);
				user.addFocusListener(this);
				this.add(user, g);
				this.fields.put(props[i], user);
				
			}
			
			g.gridx = 0;
			g.gridy = props.length;
			g.weightx = 1;
			g.weighty = 1;
			g.gridwidth = 2;
			g.fill = GridBagConstraints.BOTH;
			this.add(new JPanel(), g);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void setData(ProfileData data) {
		this.data = data;
		data.addObserver(this);
	}

	public ProfileData getData() {
		return this.data;
	}

	public void update(Observable o, Object arg) {
		Properties props = this.data.getProperties();
		Iterator<Object> iter = props.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			this.fields.get(key).setText(props.getProperty(key));
		}
	}

	public void focusLost(FocusEvent e) {
		JTextField field = (JTextField) e.getComponent();
		this.data.setProperty(field.getName(), field.getText());
	}

	public void focusGained(FocusEvent e) {

	}

}
