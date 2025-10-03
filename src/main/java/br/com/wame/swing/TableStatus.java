package br.com.wame.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import br.com.wame.enums.Status;

public class TableStatus extends JLabel{

	private static final long serialVersionUID = 1L;
	
	Status status;
	
	public TableStatus() {
		setForeground(Color.WHITE);
	}

	public void setStatus(Status status) {
		this.status = status;
		setText(status.getText());
		repaint();
	}
	
	@Override
    protected void paintComponent(Graphics grphcs) {
		if(status != null) {
			Graphics2D g2 = (Graphics2D) grphcs;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        GradientPaint g;
	        if (status == Status.FINALIZED) {
	            g = new GradientPaint(0, 0, new Color(186, 123, 247), 0, getHeight(), new Color(167, 94, 236));
	        } else {
	            g = new GradientPaint(0, 0, new Color(241, 208, 62), 0, getHeight(), new Color(211, 184, 61));
	        }
	        g2.setPaint(g);
	        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 1, 1);
		}
        super.paintComponent(grphcs);
    }
}
