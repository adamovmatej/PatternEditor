package view;

import java.awt.Component;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.EditorController;

public class CustomTabPane extends JTabbedPane{

	private static final long serialVersionUID = 1L;
	private EditorController controller;
	
	public CustomTabPane() {
		super();
	}

	private JPanel generateTabTitle(String title, Component tab){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setOpaque(false);
		
		JLabel lblTitle = new JLabel(title);
		lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		panel.add(lblTitle);
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("resources/closeBtnIcon.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image icon = image.getScaledInstance(image.getWidth()/6, image.getHeight()/6, java.awt.Image.SCALE_SMOOTH);		
		JButton closeButton = new JButton(new ImageIcon(icon));
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setContentAreaFilled(false);
		closeButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				CustomTabPane.this.remove(tab);
				controller.removeTab(tab);
			}
		});
		panel.add(closeButton);
		
		return panel;
	}
	
	public Component add(String title, Component component){
		Component temp = super.add(component);
		this.setTabComponentAt(this.indexOfComponent(temp), generateTabTitle(title, temp));
		return temp;
	}

	public EditorController getController() {
		return controller;
	}

	public void setController(EditorController controller) {
		this.controller = controller;
	}
}
