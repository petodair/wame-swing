package br.com.wame.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.FlatClientProperties;

import br.com.wame.JMain;
import br.com.wame.controller.LoginController;
import br.com.wame.manager.ContentManager;
import br.com.wame.manager.SessionManager;
import br.com.wame.model.dto.ApiResponse;
import br.com.wame.service.AuthService;
import br.com.wame.swing.PanelBorder;
import net.miginfocom.swing.MigLayout;

/**
 * Tela de login da aplicação. Exibe campos de autenticação e envia os dados à
 * API.
 */
public class JLogin extends PanelBorder {

	private static final long serialVersionUID = 1L;

	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnExit;
	private JLabel lblMessage;
	private JPanel panel;

	private final LoginController loginController;
	private final JMain main;

	public JLogin(JMain main) {
		this.main = main;
		this.loginController = new LoginController(new AuthService());
		setupLayout();
		initComponents();
	}

	/**
	 * Configura layout principal da tela de login.
	 */
	private void setupLayout() {
		setSize(804, 576);
		setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
		setOpaque(false);
	}

	/**
	 * Inicializa os componentes visuais.
	 */
	private void initComponents() {
	    panel = new JPanel(new MigLayout("wrap 1, align center, insets 30 50 30 50", "[300!]"));
	    panel.setBackground(Color.decode("#F0E3CA"));
	    this.setBackground(Color.decode("#F0E3CA"));
	    panel.setOpaque(false);

	    JLabel lblTitle = new JLabel("Wame");
	    ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("icon/wame-0.png"));
	    
	    Image originalImage = imageIcon.getImage();
	    Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	    ImageIcon expandedIcon = new ImageIcon(scaledImage);
	    
	    lblTitle.setIcon(expandedIcon);
	    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 44));
	    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

	    JLabel lblSubtitle = new JLabel("Faça login para continuar");
	    lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    lblSubtitle.setForeground(Color.DARK_GRAY);
	    lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

	    txtUsername = new JTextField();
	    txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
	    txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));

	    txtPassword = new JPasswordField();
	    txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
	    txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));

	    btnLogin = new JButton("Entrar");
	    btnLogin.setBackground(new Color(255, 140, 0));
	    btnLogin.setForeground(Color.WHITE);
	    btnLogin.setFocusPainted(false);
	    btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
	    btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    
	    btnExit = new JButton("Sair");
	    btnExit.setBackground(new Color(255, 140, 0));
	    btnExit.setForeground(Color.WHITE);
	    btnExit.setFocusPainted(false);
	    btnExit.setFont(new Font("SansSerif", Font.BOLD, 14));
	    btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    lblMessage = new JLabel();
	    lblMessage.setFont(new Font("SansSerif", Font.PLAIN, 13));
	    lblMessage.setForeground(Color.RED);
	    lblMessage.setVisible(false);
	    lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

	    btnLogin.addActionListener(e -> submitLogin());
	    btnExit.addActionListener(e -> System.exit(0));

	    panel.add(lblTitle, "align center");
	    //panel.add(lblSubtitle, "gapbottom 15, align center");
	    panel.add(txtUsername, "growx");
	    panel.add(txtPassword, "growx, gapy 10");
	    panel.add(btnLogin, "gapy 15, growx");
	    panel.add(btnExit, "gapy 5, growx");
	    panel.add(lblMessage, "align center");

	    add(panel);
	}

	/**
	 * Envia os dados de login à API e trata a resposta.
	 */
	private void submitLogin() {
		String username = txtUsername.getText();
		String password = new String(txtPassword.getPassword());

		ApiResponse<Void> response = loginController.autenticar(username, password);

		//200 = OK STATUS
		if (response.code() != 200) {
			showError(response.message());
		} else {
			ContentManager.getInstance().setContentPane(new JContent(main));
		}

		panel.repaint();
		panel.revalidate();

		System.out.println("Token: " + SessionManager.getToken());
	}

	/**
	 * Exibe uma mensagem de erro no painel.
	 */
	private void showError(String message) {
		lblMessage.setText(message);
		lblMessage.putClientProperty(FlatClientProperties.STYLE, "foreground:#FF0000");
		lblMessage.setVisible(true);
	}

}
