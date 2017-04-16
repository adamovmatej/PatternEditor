package view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.EditorController;

public class EdgePropertiesDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private EditorController controller;
	private JTextField nameField;
	private JTextArea sceneText;
	
	public EdgePropertiesDialog(EditorController controller, String name, String scene, MouseEvent me) {
		setBounds(200, 200, 450, 300);
		setAlwaysOnTop(true);
		
		this.controller = controller;
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel);
		
		nameField = new JTextField(name);
		nameField.setBounds(140, 8, 284, 20);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblScene = new JLabel("Scene:");
		lblScene.setBounds(10, 36, 46, 14);
		getContentPane().add(lblScene);
		
		sceneText = new JTextArea(scene);
		sceneText.setBounds(140, 39, 284, 177);
		sceneText.setBorder(nameField.getBorder());
		getContentPane().add(sceneText);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!nameField.getText().equals(null) || !scene.equals(sceneText.getText())){
					controller.updateEdge(nameField.getText(), sceneText.getText(), me);
				}			
				EdgePropertiesDialog.this.dispose();
			}
		});
		btnNewButton.setBounds(335, 227, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EdgePropertiesDialog.this.dispose();
			}
		});
		btnCancel.setBounds(236, 227, 89, 23);
		getContentPane().add(btnCancel);
	}

}
