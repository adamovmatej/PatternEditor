package view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.EditorController;
import controller.MainScreenController;

public class PatternDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private MainScreenController controller;
	private JTextField nameField;
	private JTextArea descText;
	
	public PatternDialog(MainScreenController controller, String type, Locale locale) {
		setResizable(false);
		if (type.equals("new")){
			setTitle("New pattern");
		} else {
			setTitle("Pattern properties");
		}
		ResourceBundle bundle = ResourceBundle.getBundle("TextBundle", locale);	
		
		setBounds(200, 200, 450, 300);
		setAlwaysOnTop(true);
		
		this.controller = controller;
		
		getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel(bundle.getString("label.name")+":");
		lblName.setBounds(10, 11, 46, 14);
		getContentPane().add(lblName);
		
		nameField = new JTextField();
		nameField.setBounds(140, 8, 284, 20);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblDesc = new JLabel(bundle.getString("label.description")+":");
		lblDesc.setBounds(10, 36, 112, 14);
		getContentPane().add(lblDesc);
		
		descText = new JTextArea();
		descText.setBounds(140, 39, 284, 177);
		descText.setBorder(nameField.getBorder());
		getContentPane().add(descText);
		
		JButton btnNewButton = new JButton(bundle.getString("button.ok"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (type.equals("new")){
					controller.createNewPattern(nameField.getText(), descText.getText());
				} else {
					controller.updatePattern(nameField.getText(), descText.getText());
				}
				PatternDialog.this.dispose();
			}
		});
		btnNewButton.setBounds(335, 227, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton(bundle.getString("button.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PatternDialog.this.dispose();
			}
		});
		btnCancel.setBounds(236, 227, 89, 23);
		getContentPane().add(btnCancel);
	}
}
