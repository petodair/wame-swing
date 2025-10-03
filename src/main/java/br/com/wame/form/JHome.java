package br.com.wame.form;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.FlatClientProperties;

import br.com.wame.swing.ListMenu;
import br.com.wame.util.Constants;
import br.com.wame.view.JContent;
import net.miginfocom.swing.MigLayout;

public class JHome extends FormBase {

	private static final long serialVersionUID = 1L;
	private Image backgroundImage;

	public JHome(JPanel contentPanel, JContent content) {
		ListMenu.setSelected(0);
		setLayout(new MigLayout("wrap, fillx, insets 280 0 0 0", "[grow,fill]"));
		setBackground(Color.decode("#1b1a17"));
		setOpaque(false);
		content.setBackground(Color.decode("#1b1a17"));
		contentPanel.setBackground(getBackground());
		contentPanel.repaint();
		try {
			backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("bg/home-bg.png")).getImage();
		} catch (Exception e) {
			e.printStackTrace();
			// Handle image loading errors
		}

		JLabel lblWorks = new JLabel("Trabalhos", SwingConstants.CENTER);
		lblWorks.setForeground(Color.decode(Constants.color1));
		lblWorks.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
		lblWorks.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblWorks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblWorks.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblWorks.setForeground(Color.decode(Constants.color1));
			}
		});
		add(lblWorks, "gapy 10");

		JLabel lblWorkRegister = new JLabel("Adicionar novo trabalho", SwingConstants.CENTER);
		lblWorkRegister.setForeground(Color.decode(Constants.color1));
		lblWorkRegister.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
		lblWorkRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblWorkRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblWorkRegister.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblWorkRegister.setForeground(Color.decode(Constants.color1));
			}
		});
		add(lblWorkRegister, "gapy 10");

		JLabel lblAccount = new JLabel("Conta", SwingConstants.CENTER);
		lblAccount.setForeground(Color.decode(Constants.color1));
		lblAccount.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
		lblAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAccount.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAccount.setForeground(Color.decode(Constants.color1));
			}
		});
		add(lblAccount, "gapy 10");

		JLabel lblAbout = new JLabel("Sobre", SwingConstants.CENTER);
		lblAbout.setForeground(Color.decode(Constants.color1));
		lblAbout.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
		lblAbout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAbout.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAbout.setForeground(Color.decode(Constants.color1));
			}
		});
		add(lblAbout, "gapy 10");
		
		JLabel lblExit = new JLabel("Sair", SwingConstants.CENTER);
		lblExit.setForeground(Color.decode(Constants.color1));
		lblExit.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
		lblExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblExit.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblExit.setForeground(Color.decode(Constants.color1));
			}
		});
		add(lblExit, "gapy 10");

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		if (backgroundImage != null) {
			// Draw the image to fill the panel
			g2.drawImage(getRoundedImage(backgroundImage, 15), 0, 0, this);
		}
		super.paintComponent(g2); // Call superclass method to ensure proper rendering

	}

	public Image getRoundedImage(Image image, int cornerRadius) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = outputImage.createGraphics();

		// Habilita o anti-aliasing para suavizar as bordas
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Cria uma área com cantos arredondados
		Area area = new Area(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));

		// Define a área como a área de corte
		g2.setClip(area);

		// Desenha a imagem dentro da área
		g2.drawImage(image, 0, 0, null);

		g2.dispose();
		return outputImage;
	}

}
