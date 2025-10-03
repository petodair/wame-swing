package br.com.wame.swing;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.wame.enums.Status;

public class CellStatus extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private TableStatus tableStatus;
	
	 public CellStatus(Status status) {
		 	initComponents();
		 	tableStatus.setStatus(status);
	    }
	 
	 private void initComponents() {

		 tableStatus = new TableStatus();

	        setBackground(new java.awt.Color(255, 255, 255));

	        tableStatus.setHorizontalAlignment(JLabel.CENTER);
	        tableStatus.setText("tableStatus1");

	        GroupLayout layout = new GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(Alignment.LEADING)
	            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(10, Short.MAX_VALUE)
	                .addComponent(tableStatus, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(10, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(8, 8, 8)
	                .addComponent(tableStatus, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
	                .addGap(8, 8, 8))
	        );
	    }

}
