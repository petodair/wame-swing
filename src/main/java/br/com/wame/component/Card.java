package br.com.wame.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.wame.model.CardModel;
import java.awt.GridLayout;

public class Card extends JPanel {

	private static final long serialVersionUID = 1L;
	//DATA COMPONENTS
	private JLabel lblIcon;
	private JLabel lblTitle;
	private JLabel lblValues;
	private JLabel lblDescription;
	
	private Color color1;
	private Color color2;
	
	public Card() {
		this.color1 = Color.BLACK;
		this.color2 = Color.WHITE;
		initComponents();
	}
	
	public Card(Color color1, Color color2) {
		this.color1 = color1;
		this.color2 = color2;
		initComponents();
	}
	
	public void initComponents(){
		setOpaque(false);
		setBorder(new EmptyBorder(20, 18, 8, 0));
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(getClass().getResource("/icon/24/01.png")));
		
		lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitle.setBorder(new EmptyBorder(0,5,0,0));
		
		lblValues = new JLabel("Values");
		lblValues.setFont(new Font("Arial", Font.BOLD, 16));
		lblValues.setBorder(new EmptyBorder(0,5,0,0));
		
		lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescription.setBorder(new EmptyBorder(0,5,0,0));
		setLayout(new GridLayout(0, 1, 0, 0));
		add(lblIcon);
		add(lblTitle);
		add(lblValues);
		add(lblDescription);
	}
	
	public void setData(CardModel data) {
		lblIcon.setIcon(data.getIcon());
		lblTitle.setText(data.getTitle());
		lblValues.setText(data.getValues());
		lblDescription.setText(data.getDescription());
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
		g2.setPaint(gp);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		g2.setColor(new Color(255,255,255,50));
		g2.fillOval(getWidth() - (getHeight()/2), 10, getHeight(), getHeight());
		g2.fillOval(getWidth() - (getHeight()/2) - 20, getHeight()/2 + 20, getHeight(), getHeight());
		super.paintComponent(g);
	}

}
