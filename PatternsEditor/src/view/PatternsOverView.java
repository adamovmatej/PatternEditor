package view;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import controller.PatternOverviewController;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class PatternsOverView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private PatternOverviewController controller;
	
	private JTable table;
	private JSplitPane splitPane;	
	private JTextField textName;
	private JTextArea textDescription;
	private JTable adapterTable;
	private JButton btnDelete;
	private JButton btnPlayAdapters;
	
	public PatternsOverView() {
		setTitle("Pattern overview");
		setAlwaysOnTop(true);
		setBounds(200, 200, 750, 500);
		
		splitPane = new JSplitPane();
		splitPane.setDividerLocation(250);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		
		JPanel overview = new JPanel();
		tabbedPane.addTab("Overview", null, overview, null);
		overview.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(12, 12, 382, 15);
		overview.add(lblName);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(12, 56, 417, 15);
		overview.add(lblDescription);
		
		textName = new JTextField();
		textName.setBounds(12, 32, 459, 19);
		textName.setFocusable(false);
		overview.add(textName);
		textName.setColumns(10);
		
		textDescription = new JTextArea();
		textDescription.setBounds(12, 76, 459, 346);
		textDescription.setBorder(textName.getBorder());
		overview.add(textDescription);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(354, 434, 117, 25);
		btnOk.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveOverview(textDescription.getText());
				PatternsOverView.this.setVisible(false);
			}
		});
		overview.add(btnOk);
	
		
		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(225, 434, 117, 25);
		btnApply.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveOverview(textDescription.getText());
			}
		});
		overview.add(btnApply);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(96, 434, 117, 25);
		btnCancel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				PatternsOverView.this.setVisible(false);
			}
		});
		overview.add(btnCancel);
				
		JPanel adapters = new JPanel();
		adapters.setLayout(null);
		tabbedPane.addTab("Adapters", null, adapters, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 31, 459, 391);
		adapters.add(scrollPane_2);
		
		adapterTable = new JTable();
		adapterTable.setDefaultEditor(Object.class, null);
		scrollPane_2.setViewportView(adapterTable);
		adapterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnPlayAdapters = new JButton("Play");
		btnPlayAdapters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.playDiagram((String) adapterTable.getValueAt(adapterTable.getSelectedRow(), 0));
			}
		});
		btnPlayAdapters.setBounds(354, 434, 117, 25);
		adapters.add(btnPlayAdapters);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(12, 434, 117, 25);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteAdapter((String) adapterTable.getValueAt(adapterTable.getSelectedRow(), 0));
			}
		});
		adapters.add(btnDelete);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(250, 25));
		splitPane.setLeftComponent(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		scrollPane.setBounds(0, 0, 250, 475);
		scrollPane.setMinimumSize(new Dimension(250, 475));
		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnPatternDelete = new JButton("Delete Pattern");
		btnPatternDelete.setBounds(0, 475, 250, 25);
		btnPatternDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deletePattern();
			}
		});
		panel.add(btnPatternDelete);

	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public void showOverview(String name, String description){
		this.textName.setText(name);
		this.textDescription.setText(description);
	}

	public PatternOverviewController getController() {
		return controller;
	}

	public void setController(PatternOverviewController controller) {
		this.controller = controller;
	}

	public JTable getAdapterTable() {
		return adapterTable;
	}

	public void setAdapterTable(JTable adapterTable) {
		this.adapterTable = adapterTable;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}	
}
