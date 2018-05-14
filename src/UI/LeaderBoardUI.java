package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;


import javax.swing.*;



public class LeaderBoardUI extends JFrame {
	DefaultListModel<String> listModel =  new DefaultListModel<String>();
	JList<String> list = new JList<String>(listModel);
//	String[] columnNames = {"Name","Score"};
//	
//	JTable table = new JTable()
	/**
	 * add All of list 
	 * @param l
	 */
	public void addAll(List<String> l) {
//		DefaultListModel<String> listModel =  new DefaultListModel<String>();
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
//		JButton button = new JButton("refresh");
//		button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Controller.getGC().sendMessage("reqscorelist");
//				List<String> list = Controller.getGC().getScoreList();	
//				addAll(list);
//				
//			}
//		});
//		JPanel toppanel = new JPanel();
		
		
		
		JLabel label = new JLabel("Score Board");
//		toppanel.add(label,BorderLayout.EAST);
//		toppanel.add(button,BorderLayout.WEST);
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
