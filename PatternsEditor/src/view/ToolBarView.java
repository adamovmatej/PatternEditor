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
		setResizable(false);
		setFocusableWindowState(false);
		setAutoRequestFocus(false);
		setFocusable(false);
		setTitle("Tools");
		BufferedImage image = null;
		Image icon ;
		ImageIcon iconImage;
		JButton defaultButton;
		JButton highlightButton;
		JButton stateButton;
		JButton startButton;
		JButton endButton;
		
		setAlwaysOnTop(true);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		setBounds(100, 100, 385, 75);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		try {
			image = ImageIO.read(new File("resources/defaultpointer.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/10, image.getHeight()/10, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		defaultButton = new JButton(iconImage);
		defaultButton.setBounds(5, 5, 90, 30);
		defaultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectTool(0);
			}
		});
		panel.setLayout(null);
		panel.add(defaultButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/highlight.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/10, image.getHeight()/10, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		highlightButton = new JButton(iconImage);
		highlightButton.setBounds(5, 40, 90, 30);
		highlightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectTool(1);
			}
		});
		panel.add(highlightButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/state.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/12, image.getHeight()/12, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		stateButton = new JButton(iconImage);
		stateButton.setBounds(100, 5, 90, 65);
		stateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectTool(2);
			}
		});
		panel.add(stateButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/start.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/12, image.getHeight()/12, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		startButton = new JButton(iconImage);
		startButton.setBounds(195, 5, 90, 65);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectTool(3);
			}
		});
		panel.add(startButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/end.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth()/12, image.getHeight()/12, java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		endButton = new JButton(iconImage);
		endButton.setBounds(290, 5, 90, 65);
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectTool(4);
			}
		});
		panel.add(endButton);
	}
	
	public EditorController getController() {
		return controller;
	}

	public void setController(EditorController controller) {
		this.controller = controller;
	}
	
}
