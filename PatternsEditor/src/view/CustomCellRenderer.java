package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	public CustomCellRenderer() {
		super();
		this.setOpaque(false);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		this.setForeground(Color.WHITE);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
		
		if (isSelected){
			this.setOpaque(true);		
			this.setBackground(new Color(138, 194, 243, 100));
		} else {
			this.setOpaque(false);			
		}
		
		return this;
	}
}
