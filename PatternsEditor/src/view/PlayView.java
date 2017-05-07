package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
	private JTextPane textPane;
	
	public PlayView() {
		setResizable(false);
		setTitle("Playthrough");
		setBounds(50, 50, 1000, 750);
		setAlwaysOnTop(true);
		
		JPanel panel = new BackgroundPane("resources/gameimage.png");
		panel.setBackground(Color.blue);
		getContentPane().add(panel);
		panel.setLayout(null);	
		
		StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.WHITE);
		
		textPane = new JTextPane();
		textPane.setBounds(12, 12, 976, 479);
		textPane.setFocusable(false);
		textPane.setBackground(new Color(0, 0, 0, 0));
		textPane.setCharacterAttributes(aset, false);
		textPane.setFont(new Font("Calibri", Font.BOLD, 20));
		panel.add(textPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 503, 912, 235);
		scrollPane.setLayout(null);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		panel.add(scrollPane);
		
		CustomCellRenderer rndr = new CustomCellRenderer();

		optionTable = new JTable();
		optionTable.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		optionTable.setFillsViewportHeight(true);
		optionTable.setDefaultEditor(Object.class, null);
		optionTable.setBounds(0, 0, 912, 235);
		optionTable.setOpaque(false);	
		optionTable.setFont(new Font("Calibri", Font.BOLD, 15));
		optionTable.setRowHeight(optionTable.getHeight()/4);
		optionTable.setDefaultRenderer(Object.class, rndr);
		optionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.add(optionTable);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("resources/continueArrow.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image icon = image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);		
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
		panel.add(scrollPane);
	}

	public JTable getTable() {
		return optionTable;
	}

	public void setTable(JTable table) {
		this.optionTable = table;
	}

	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}

	public PlayController getController() {
		return controller;
	}

	public void setController(PlayController controller) {
		this.controller = controller;
	}
}
