package br.com.wame.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ContentPanel() {
		setBackground(Color.decode("#F0E3CA"));
		setSize(804, 576);
		setOpaque(false);
	}
	
	public void setColor(Color color){
		setBackground(color);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		super.paintComponent(g);
	}

}
