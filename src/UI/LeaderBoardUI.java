package UI;

import java.awt.Dimension;
import java.util.List;


import javax.swing.*;

public class LeaderBoardUI extends JFrame {
	JList<String> list = new JList<String>();
//	String[] columnNames = {"Name","Score"};
//	
//	JTable table = new JTable()
	public void addAll(List<String> l) {
		DefaultListModel<String> listModel =  new DefaultListModel<String>();
		for (String s : l) {
			listModel.addElement(s);
		}
		list = new JList<String>(listModel);
	}
	
	public void init() {
		JPanel panel = new JPanel();
		list.setPreferredSize(new Dimension(200,600));
		panel.add(list);
		add(panel);
		setVisible(true);
		setResizable(false);
		pack();
	}
}
