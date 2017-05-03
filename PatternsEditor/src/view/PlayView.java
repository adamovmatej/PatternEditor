package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.Document;

import controller.PlayController;

import javax.swing.JTable;

import java.awt.Color;
import java.awt.Font;
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
	
	private JTable optionTable;
	private JTextArea textArea;
	
	public PlayView() {
		setResizable(false);
		setTitle("Playthrough");
		setBounds(50, 50, 1000, 750);
		setAlwaysOnTop(true);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(12, 12, 976, 479);
		textArea.setFocusable(false);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setFont(new Font("Arial", Font.BOLD, 18));
		textArea.setLineWrap(true);
		panel.add(textArea);
		
		JScrollPane panel1 = new JScrollPane();
		panel1.setBounds(12, 503, 912, 235);
		panel.add(panel1);
		panel1.setLayout(null);
		
		optionTable = new JTable();
		optionTable.setFillsViewportHeight(true);
		optionTable.setDefaultEditor(Object.class, null);
		optionTable.setBounds(0, 0, 912, 235);
		optionTable.setBackground(new JPanel().getBackground());
		optionTable.setRowHeight(optionTable.getHeight()/4);
		optionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel1.add(optionTable);
		
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
				if (optionTable.getSelectedRow()==-1){
					PlayView.this.dispose();
				}else {
					controller.continueWith((String) optionTable.getValueAt(optionTable.getSelectedRow(), 0));
				}
			}
		});
		panel.add(continueButton);
		panel.add(panel1);
	}

	public JTable getTable() {
		return optionTable;
	}

	public void setTable(JTable table) {
		this.optionTable = table;
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
