package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;


import javax.swing.*;



public class LeaderBoardUI extends JFrame {
	
	private DefaultListModel<String> listModel;
	private JList<String> list;

	
	public LeaderBoardUI() {
		listModel =  new DefaultListModel<String>();
		list = new JList<String>(listModel);

	}
	/**
	 * add All of list 
	 * @param l
	 */
	public void addAll(List<String> l) {

		listModel.clear();
		for (String s : l) {
			listModel.addElement(s);
		}
		repaint();
		
	}
	
	public void init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200,550));
		list.setPreferredSize(new Dimension(200,500));
		
		JLabel label = new JLabel("Score Board");

		setTitle("Score Board");
		label.setPreferredSize(new Dimension(200,50));
		panel.add(label,BorderLayout.NORTH);
		panel.add(list,BorderLayout.SOUTH);
		add(panel);
//		setVisible(true);
		setResizable(false);
		pack();
	}
}
