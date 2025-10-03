package br.com.wame.swing;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.wame.model.MenuModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BorderLayout;
public class MenuItem extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean selected;
	private boolean over;

	/**
	 * Create the panel.
	 */
	public MenuItem(MenuModel data) {
		setOpaque(false);
		setBorder(new EmptyBorder(4,12,4,0));
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnlIcon = new JPanel();
		pnlIcon.setOpaque(false);
		add(pnlIcon, BorderLayout.LINE_START);
		
		JLabel lblIcon = new JLabel("");
		pnlIcon.add(lblIcon);
		
		JPanel pnlName = new JPanel();
		pnlName.setOpaque(false);
		add(pnlName, BorderLayout.CENTER);
		pnlName.setLayout(new BorderLayout(0, 0));
		pnlName.setBorder(new EmptyBorder(0,12,0,0));
		
		JLabel lblMenuName = new JLabel("MenuName");
		lblMenuName.setForeground(Color.decode("#1B1A17"));
		lblMenuName.setFont(new Font("Arial", Font.PLAIN, 12));
		pnlName.add(lblMenuName, BorderLayout.LINE_START);
		
		if(data.getType() == MenuModel.MenuType.MENU) {
			lblIcon.setIcon(data.toIcon());
			lblMenuName.setText(data.getName());
		} else if (data.getType() == MenuModel.MenuType.TITLE) {
			lblIcon.setText(data.getName());
			lblIcon.setForeground(Color.decode("#1B1A17"));
			lblIcon.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblMenuName.setVisible(false);
		} else {
			lblMenuName.setText(" ");
		}

	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}
	
	public void setOver(boolean over) {
		this.over = over;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(selected || over) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if(selected) {
				g2.setColor(new Color(255,255,255,80));
			}else {
				g2.setColor(new Color(255,255,255,30));
			}
			g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 5, 5);
		}
		super.paintComponent(g);
	}
	
}
