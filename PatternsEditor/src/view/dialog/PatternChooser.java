package view.dialog;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.MainScreenController;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PatternChooser extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	public PatternChooser(MainScreenController controller, String type, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("TextBundle", locale);	
		
		if (type.equals("version")){
			setTitle(bundle.getString("title.addVersion"));
		} else if (type.equals("diagram")){
			setTitle(bundle.getString("title.newDiagram"));
		} else if (type.equals("adapter")){
			setTitle(bundle.getString("title.addAdapter"));
		} else {
			setTitle(bundle.getString("title.openDiagram"));
		}
		
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		setBounds(200, 200, 250, 250);
		
		JButton btnNewButton = new JButton(bundle.getString("button.ok"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow()>-1){
					if (type.equals("diagram")){
						controller.createDiagram((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
					} else if (type.equals("adapter")){
						List<String> list = new ArrayList<>();
						for (int i : table.getSelectedRows()) {
							list.add((String) table.getModel().getValueAt(i, 0));
						}
						controller.createAdapter(list);
					} else {		
						controller.openDiagram((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
					}
					PatternChooser.this.dispose();
				} else {
					JOptionPane.showMessageDialog(PatternChooser.this, bundle.getString("warning.patternNotSelected"));
				}				
			}
		});
		btnNewButton.setBounds(133, 215, 105, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton(bundle.getString("button.cancel"));
		btnCancel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				PatternChooser.this.dispose();
			}
		});
		btnCancel.setBounds(12, 215, 105, 23);
		getContentPane().add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 35, 226, 172);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		controller.populateTable(table);
		table.setTableHeader(null);
		scrollPane.setViewportView(table);
		
		JLabel lblHeader = new JLabel();
		if (type.equals("version")){
			lblHeader.setText(bundle.getString("label.verForPat")+":");
		} else if (type.equals("diagram")){
			lblHeader.setText(bundle.getString("label.diaForPat")+":");
		} else if (type.equals("adapter")){
			lblHeader.setText(bundle.getString("label.adaForPat")+":");
		} else {
			lblHeader.setText(bundle.getString("label.chosePattern")+":");
		}
		lblHeader.setBounds(12, 12, 207, 15);
		getContentPane().add(lblHeader);
	}
}
