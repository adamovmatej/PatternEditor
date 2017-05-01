package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.PlayController;

import javax.swing.JTable;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlayView extends JFrame{

	private static final long serialVersionUID = 1L;

	private PlayController controller;
	
	private JTable table;
	private JTextArea textArea;
	
	public PlayView() {
		setBounds(50, 50, 1000, 750);
		setAlwaysOnTop(true);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(12, 12, 976, 479);
		textArea.setFocusable(false);
		textArea.setBackground(new Color(0, 0, 0, 0));
		panel.add(textArea);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(12, 503, 912, 235);
		panel.add(panel1);
		panel1.setLayout(null);
		
		table = new JTable();
		table.setBackground(new Color(0, 0, 0, 0));
		table.setBounds(0, 0, 912, 235);
		panel1.add(table);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("resources/continueArrow.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image icon = image.getScaledInstance(image.getWidth()/10, image.getHeight()/10, java.awt.Image.SCALE_SMOOTH);		
		ImageIcon iconImage = new ImageIcon(icon);
		JButton continueButton = new JButton(iconImage);
		continueButton.setBounds(925, 572, 70, 70);
		continueButton.setBorder(BorderFactory.createEmptyBorder());
		continueButton.setContentAreaFilled(false);
		continueButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				controller.continueWith((String) table.getValueAt(table.getSelectedRow(), 0));
			}
		});
		panel.add(continueButton);
		panel.add(panel1);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public PlayController getController() {
		return controller;
	}

	public void setController(PlayController controller) {
		this.controller = controller;
	}
}
