package br.com.wame.component;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.wame.swing.SearchText;
import java.awt.Color;

public class Header extends JPanel {

	private static final long serialVersionUID = 1L;
	private SearchText searchText;

	public Header() {
		setBackground(Color.WHITE);
		setOpaque(false);
		setLayout(new BorderLayout(0, 0)); //Layout de Borda
		
		JPanel pnlIcon = new JPanel(); //Painel do ícone
		pnlIcon.setOpaque(false);	
		add(pnlIcon, BorderLayout.WEST);
		pnlIcon.setLayout(new BorderLayout(0, 0)); 
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(getClass().getResource("/icon/24/search.png"))); //Setando o ícone
		lblIcon.setBorder(new EmptyBorder(0,4,0,10)); //Bordas/margens
		pnlIcon.add(lblIcon);
		
		searchText = new SearchText(); //Instancia do campo de pesquisa de texto
		add(searchText, BorderLayout.CENTER);
		JPanel pnlMenu = new JPanel(); //Painel para o menu(ícone)
		pnlMenu.setOpaque(false);
		add(pnlMenu, BorderLayout.EAST);
		
		JLabel lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(getClass().getResource("/icon/24/menu.png")));
		lblMenu.setBorder(new EmptyBorder(2,0,0,0));
		pnlMenu.add(lblMenu, BorderLayout.CENTER);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		g2.fillRect(0, 0, 25, getHeight());
		g2.fillRect(getWidth() - 25, getHeight() - 25, getWidth(), getHeight());
		super.paintComponent(g);
	}

}
