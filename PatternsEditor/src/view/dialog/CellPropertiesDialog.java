package view.dialog;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.EditorController;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class CellPropertiesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private EditorController controller;
	private JTextField nameField;
	private JTextArea sceneText;
	
	public CellPropertiesDialog(EditorController controller, String name, String scene, Boolean disable, Boolean newState, Boolean isState, MouseEvent me) {
		setResizable(false);
		if (isState){
			if (newState){
				setTitle("New state");				
			} else {
				setTitle("State properties");	
			}
		} else {
			setTitle("Edge properties");	
		}
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
		sceneText.setLineWrap(true);
		sceneText.setWrapStyleWord(true);
		getContentPane().add(sceneText);
		
		JCheckBox disableCheckBox = new JCheckBox("Disabled:");
		disableCheckBox.setSelected(disable);
		disableCheckBox.setBounds(3, 58, 129, 23);
		getContentPane().add(disableCheckBox);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (newState){
					controller.createState(nameField.getText(), sceneText.getText(), disableCheckBox.isSelected(), me);
				} else{
					if (!nameField.getText().equals(null) || !scene.equals(sceneText.getText())){
						if (isState){
							controller.updateState(nameField.getText(), sceneText.getText(), disableCheckBox.isSelected(), me);							
						} else {
							controller.updateEdge(nameField.getText(), sceneText.getText(), disableCheckBox.isSelected(), me);
						}
					}					
				}
				CellPropertiesDialog.this.dispose();
			}
		});
		btnNewButton.setBounds(335, 227, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CellPropertiesDialog.this.dispose();
			}
		});
		btnCancel.setBounds(236, 227, 89, 23);
		getContentPane().add(btnCancel);
	}
}
