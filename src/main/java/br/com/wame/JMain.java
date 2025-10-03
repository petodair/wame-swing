package br.com.wame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import br.com.wame.manager.ContentManager;
import br.com.wame.swing.PanelBorder;
import br.com.wame.view.JLogin;
import br.com.wame.view.JSplash;

/**
 * Classe principal da aplicação Swing.
 * Inicializa o frame e carrega o primeiro conteúdo (login ou painel principal).
 */
public class JMain extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Painel de conteúdo principal com borda personalizada
    private PanelBorder mainPanel;

	public JMain() {
		configureFrame();
		initComponents();
	}
	
	/**
     * Configurações básicas da janela principal.
     */
    private void configureFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 576);
        setUndecorated(true); // Janela sem bordas
        setBackground(new Color(0, 0, 0, 0)); // Fundo transparente
        setLocationRelativeTo(null); // Centraliza na tela
    }

    private void initComponents() {
    	// Inicializa o gerenciamento de conteúdo da aplicação
        ContentManager.getInstance().initApplication(this);
        ContentManager.getInstance().setContentPane(new JSplash());

    }
	
}
