package br.com.wame.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.com.wame.enums.Status;
import br.com.wame.form.work.JWork;
import br.com.wame.manager.FormManager;
import br.com.wame.swing.CellStatus;
import br.com.wame.swing.TableHeader;
import br.com.wame.view.JContent;

public class Table extends JTable{
	
	private static final long serialVersionUID = 1L;
	//private final WorkService workService = new WorkService();
	
	public Table(JContent content, int specialColumnNumber) {
		setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				TableHeader header = new TableHeader(value+ "");
                if (column == 4) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
			}
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (column != specialColumnNumber) {
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if (isSelected) {
                        com.setForeground(new Color(15, 89, 140));
                    } else {
                        com.setForeground(new Color(102, 102, 102));
                    }
                    return com;
                } else {
                	if(value != "") {
                		CellStatus cell = new CellStatus(Status.valueOf(value.toString()));
                		return cell;
                	}else {
                		return new JLabel("");
                	}
                    
                }
			}
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Checks for double-click
                    int row = getSelectedRow();
                    if (row != -1) { // Ensures a row is selected
                        Long ID = (Long) getModel().getValueAt(row, 5);
                        FormManager.getInstance().showForm(new JWork(ID,content));
                    }
                }
            }
        });
	}
	
	public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }

}
