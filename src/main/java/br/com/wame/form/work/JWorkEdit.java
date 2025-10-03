package br.com.wame.form.work;

import java.awt.Color;

import javax.swing.JPanel;

public class JWorkEdit extends JPanel {

	private static final long serialVersionUID = 1L;
	//private final WorkService workService;
	
	public JWorkEdit(int ID) {
		//workService = new WorkService(new RestTemplate());
		setBackground(Color.decode("#F0E3CA"));
		setSize(804,576);
		setLayout(null);
		setOpaque(false);
		
		initComponents();
	}
	
	public void initComponents() {
		
	}
}
