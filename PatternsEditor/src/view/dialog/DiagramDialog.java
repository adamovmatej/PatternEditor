package view.dialog;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import controller.MainScreenController;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DiagramDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JCheckBox mainCheckBox;
	private JComboBox<String>  patternComboBox;
	
	public DiagramDialog(MainScreenController controller, String type) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		setBounds(200, 200, 450, 250);
		
		patternComboBox = new JComboBox<String>();
		controller.initCombobox(patternComboBox);
		patternComboBox.setBounds(213, 40, 188, 20);
		getContentPane().add(patternComboBox);
		
		JLabel lvlVzor = new JLabel("Organizacny vzor:");
		lvlVzor.setBounds(33, 43, 134, 14);
		getContentPane().add(lvlVzor);
		
		mainCheckBox = new JCheckBox("");
		mainCheckBox.setSelected(true);
		mainCheckBox.setBounds(213, 67, 97, 23);
		getContentPane().add(mainCheckBox);
		
		JLabel lblHlavny = new JLabel("Hlavny:");
		lblHlavny.setBounds(33, 68, 46, 14);
		getContentPane().add(lblHlavny);
		
		JComboBox<String> versionComboBox = new JComboBox<String>();
		versionComboBox.setBounds(213, 97, 188, 20);
		versionComboBox.addItem("Default");
		controller.initCombobox(versionComboBox);
		getContentPane().add(versionComboBox);
		
		JLabel lblVerziaPre = new JLabel("Verzia pre:");
		lblVerziaPre.setBounds(33, 93, 134, 14);
		getContentPane().add(lblVerziaPre);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (type) {
				case "diagram":
					controller.createDiagram((String)patternComboBox.getSelectedItem(), (String)versionComboBox.getSelectedItem(), mainCheckBox.isSelected());					
					break;
				case "version":
					controller.createNewVersion((String)patternComboBox.getSelectedItem(), (String)versionComboBox.getSelectedItem(), mainCheckBox.isSelected());
					break;
				default:
					break;
				}
				
				DiagramDialog.this.dispose();
			}
		});
		btnNewButton.setBounds(336, 148, 75, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				DiagramDialog.this.dispose();
			}
		});
		btnCancel.setBounds(257, 148, 75, 23);
		getContentPane().add(btnCancel);
	}

	public JCheckBox getMainCheckBox() {
		return mainCheckBox;
	}

	public void setMainCheckBox(JCheckBox mainCheckBox) {
		this.mainCheckBox = mainCheckBox;
	}

	public JComboBox<String> getPatternComboBox() {
		return patternComboBox;
	}

	public void setPatternComboBox(JComboBox<String> patternComboBox) {
		this.patternComboBox = patternComboBox;
	}
}
