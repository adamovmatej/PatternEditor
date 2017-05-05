package view;

import javax.swing.JToggleButton;

import controller.EditorController;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.FlowLayout;

public class ToolBarView extends JPanel{

	private static final long serialVersionUID = 1L;

	private EditorController controller;
	
	private JToggleButton defaultButton;
	private JToggleButton highlightButton;
	private JToggleButton stateButton;
	private JToggleButton startButton;
	private JToggleButton endButton;
	
	public ToolBarView() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		BufferedImage image = null;
		Image icon;
		ImageIcon iconImage;
		
				
		try {
			image = ImageIO.read(new File("resources/defaultpointer.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		defaultButton = new JToggleButton(iconImage);
		defaultButton.setSelected(true);
		defaultButton.setMargin(new Insets(0, 0, 0, 0));
		defaultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkToolBarButtons(0);
				controller.selectTool(0);
			}
		});
		this.add(defaultButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/highlight.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		highlightButton = new JToggleButton(iconImage);
		highlightButton.setMargin(new Insets(0, 0, 0, 0));
		highlightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectTool(1);
				checkToolBarButtons(1);
			}
		});
		this.add(highlightButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/state.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		stateButton = new JToggleButton(iconImage);
		stateButton.setMargin(new Insets(0, 0, 0, 0));
		stateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkToolBarButtons(2);
				controller.selectTool(2);
			}
		});
		this.add(stateButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/start.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		startButton = new JToggleButton(iconImage);
		startButton.setMargin(new Insets(0, 0, 0, 0));
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkToolBarButtons(3);
				controller.selectTool(3);
			}
		});
		this.add(startButton);
		
		image = null;
		try {
			image = ImageIO.read(new File("resources/end.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		icon = image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);		
		iconImage = new ImageIcon(icon);
		endButton = new JToggleButton(iconImage);
		endButton.setMargin(new Insets(0, 0, 0, 0));
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkToolBarButtons(4);
				controller.selectTool(4);
			}
		});
		this.add(endButton);
		
	}
	
	public void checkToolBarButtons(int tool){
		switch (tool) {
		case 0:
			defaultButton.setSelected(true);
			highlightButton.setSelected(false);
			startButton.setSelected(false);
			stateButton.setSelected(false);
			endButton.setSelected(false);
			break;
		case 1:
			defaultButton.setSelected(false);
			startButton.setSelected(false);
			stateButton.setSelected(false);
			endButton.setSelected(false);
			break;
		case 2:
			highlightButton.setSelected(false);
			startButton.setSelected(false);
			defaultButton.setSelected(false);
			endButton.setSelected(false);
			break;
		case 3:
			highlightButton.setSelected(false);
			defaultButton.setSelected(false);
			stateButton.setSelected(false);
			endButton.setSelected(false);
			break;
		case 4:
			highlightButton.setSelected(false);
			startButton.setSelected(false);
			stateButton.setSelected(false);
			defaultButton.setSelected(false);
			break;
		default:
			break;
		}
	}
	
	
	
	public EditorController getController() {
		return controller;
	}

	public void setController(EditorController controller) {
		this.controller = controller;
	}
	
}
