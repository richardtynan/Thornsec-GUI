package main;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import core.data.ThornsecData;
import core.model.ThornsecModel;
import vc.data.ThornsecDataVC;
import vc.model.ThornsecModelVC;

public class ThornsecMain5 {

	public static void main(String[] args) throws Exception {
		ThornsecData data = new ThornsecData(args[0]);

		ThornsecDataVC dataView = new ThornsecDataVC();
		dataView.setData(data);

		ThornsecModel model = new ThornsecModel();
		model.setData(data);

		ThornsecModelVC modelView = new ThornsecModelVC();
		modelView.setModel(model);

		JFrame frame = new JFrame("Thornsec");
		JTabbedPane tabs = new JTabbedPane();
		frame.getContentPane().add(tabs);

		tabs.add("Data", dataView);
		tabs.add("Model", modelView);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);

		data.load();
		model.init();
	}

}
