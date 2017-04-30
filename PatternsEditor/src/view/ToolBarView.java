package view;

import javax.swing.JPanel;

import controller.EditorController;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ToolBarView extends JFrame{

	private static final long serialVersionUID = 1L;

	EditorController controller;
	
	public ToolBarView() {
		BufferedImage image = null;
		Image icon ;
		ImageIcon iconImage;
		JButton defaultButton;
		JButton highlightButton;
		
		setAlwaysOnTop(true);
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		this.setBounds(100, 100, 200, 50);
		add(panel);
		
		try {
			image = ImageIO.read(new File("resources/defaultpointer.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/15, image.getHeight()/15, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		defaultButton = new JButton(iconImage);
		defaultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toggleHighlight(false);
			}
		});
		panel.add(defaultButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/highlight.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/15, image.getHeight()/15, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		highlightButton = new JButton(iconImage);
		highlightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toggleHighlight(true);
			}
		});
		panel.add(highlightButton);

		this.setContentPane(panel);
	}
	
	public EditorController getController() {
		return controller;
	}

	public void setController(EditorController controller) {
		this.controller = controller;
	}
	
}
