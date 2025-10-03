package br.com.wame.manager;

import java.awt.EventQueue;

import javax.swing.JComponent;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import br.com.wame.JMain;

public class ContentManager {
	private JMain application;
	private static ContentManager instance;
	
	public static ContentManager getInstance() {
		if(instance == null) {
			instance = new ContentManager();
		}
		return instance;
	}

	private ContentManager() {
		
	}
	
	public void initApplication(JMain application) {
		this.application = application;
	}
	
	public JMain getApplication() {
		return this.application;
	}
	
	public void setContentPane(JComponent form) {
		EventQueue.invokeLater(() -> {
			FlatAnimatedLafChange.showSnapshot();
			application.setContentPane(form);
			application.revalidate();
			application.repaint();
			FlatAnimatedLafChange.hideSnapshotWithAnimation();
		});
	}
}
