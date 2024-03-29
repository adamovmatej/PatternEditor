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
import javax.swing.JTextArea;
import javax.swing.JButton;

public class PatternsOverView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private PatternOverviewController controller;
	
	private JTable table;
	private JSplitPane splitPane;	
	private JTextField textName;
	private JTextArea textDescription;
	private JTable versionTable;
	private JTable adapterTable;
	
	public PatternsOverView() {
		setAlwaysOnTop(true);
		setBounds(200, 200, 750, 500);
		
		splitPane = new JSplitPane();
		splitPane.setDividerLocation(250);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(250, 500));
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		
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
				controller.saveOverview(textName.getText(), textDescription.getText());
				PatternsOverView.this.setVisible(false);
			}
		});
		overview.add(btnOk);
	
		
		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(225, 434, 117, 25);
		btnApply.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveOverview(textName.getText(), textDescription.getText());
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
		
		JPanel versions = new JPanel();
		tabbedPane.addTab("Versions", null, versions, null);
		versions.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 31, 459, 391);
		versions.add(scrollPane_1);
		
		versionTable = new JTable();
		scrollPane_1.setViewportView(versionTable);
		
		JButton btnRemoveVersions = new JButton("Remove");
		btnRemoveVersions.setBounds(96, 434, 117, 25);
		btnRemoveVersions.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeVersion();
			}
		});
		versions.add(btnRemoveVersions);
		
		JButton btnNewVersions = new JButton("New");
		btnNewVersions.setBounds(225, 434, 117, 25);
		btnNewVersions.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		versions.add(btnNewVersions);
		
		JButton btnPlayVersions = new JButton("Play");
		btnPlayVersions.setBounds(354, 434, 117, 25);
		btnPlayVersions.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		versions.add(btnPlayVersions);
		
		JPanel adapters = new JPanel();
		adapters.setLayout(null);
		tabbedPane.addTab("Adapters", null, adapters, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 31, 459, 391);
		adapters.add(scrollPane_2);
		
		adapterTable = new JTable();
		scrollPane_2.setViewportView(adapterTable);
		
		JButton btnRemovAdapters = new JButton("Remove");
		btnRemovAdapters.setBounds(96, 434, 117, 25);
		adapters.add(btnRemovAdapters);
		
		JButton btnNewAdapters = new JButton("New");
		btnNewAdapters.setBounds(225, 434, 117, 25);
		adapters.add(btnNewAdapters);
		
		JButton btnPlayAdapters = new JButton("Play");
		btnPlayAdapters.setBounds(354, 434, 117, 25);
		adapters.add(btnPlayAdapters);

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

	public JTable getVersionTable() {
		return versionTable;
	}

	public void setVersionTable(JTable versionTable) {
		this.versionTable = versionTable;
	}

	public JTable getAdapterTable() {
		return adapterTable;
	}

	public void setAdapterTable(JTable adapterTable) {
		this.adapterTable = adapterTable;
	}
}
