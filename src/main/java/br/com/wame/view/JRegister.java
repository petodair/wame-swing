package br.com.wame.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import br.com.wame.JMain;
import br.com.wame.manager.ContentManager;
import br.com.wame.swing.PanelBorder;
import net.miginfocom.swing.MigLayout;

public class JRegister extends PanelBorder{
	
	private static final long serialVersionUID = 1L;
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	private JButton btnRegister;

	public JRegister(JMain main) {
		//setBackground(Color.decode("#F0E3CA"));
		setSize(804,576);
		setLayout(new MigLayout("fill, insets 20","[center]","[center]"));
		setOpaque(false);
		initComponents(main);
	}
	
	public void initComponents(JMain main) {
		txtUsername = new JTextField();
		txtEmail = new JTextField();
		txtPassword = new JPasswordField();
		txtConfirmPassword = new JPasswordField();
		btnRegister = new JButton("Registrar");
		
		JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45","[fill,360]"));
		panel.putClientProperty(FlatClientProperties.STYLE, "background: #F0F8FF");
		JLabel lblTitle = new JLabel("Bem Vindo!");
		lblTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
		JLabel lblDescription = new JLabel("Dê um UP na sua evolução pessoal, cadastre-se agora.");
		
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ContentManager.getInstance().setContentPane(new JContent(main));
			}
		});
		
		panel.add(lblTitle);
		panel.add(lblDescription);
		panel.add(new JLabel("Nome de usuário"), "gapy 10");
		panel.add(txtUsername);
		panel.add(new JLabel("Email"));
		panel.add(txtEmail);
		panel.add(new JLabel("Senha"));
		panel.add(txtPassword);
		panel.add(new JLabel("Confirmação da senha"));
		panel.add(txtConfirmPassword);
		panel.add(btnRegister, "gapy 20");
		add(panel);
	}
}
