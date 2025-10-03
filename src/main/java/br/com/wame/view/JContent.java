package br.com.wame.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;

import br.com.wame.JMain;
import br.com.wame.component.Menu;
import br.com.wame.form.JHome;
import br.com.wame.manager.FormManager;
import br.com.wame.swing.ContentPanel;
import br.com.wame.swing.PanelBorder;

/**
 * Painel principal da aplicação após login.
 * Contém o menu lateral e a área de conteúdo dinâmico.
 */
public class JContent extends PanelBorder {

	private static final long serialVersionUID = 1L;

    private Menu menu;
    private ContentPanel contentPanel;
    private final int MENU_WIDTH = 221;
    private final int WINDOW_HEIGHT = 576;
    private JMain main;

    public JContent(JMain main) {
    	this.main = main;
        setupLayout();
        initComponents(main);
    }

    /**
     * Configura o layout base do painel.
     */
    private void setupLayout() {
        setLayout(null);
        setBackground(Color.decode("#1B1A17"));
    }

    /**
     * Inicializa os componentes visuais da tela.
     */
    private void initComponents(JMain main) {
        initContentPanel();
        initMenu(main);
        FormManager.getInstance().initApplication(this);

        // Tela inicial
        setForm(new JHome(contentPanel, this));
    }

    /**
     * Cria e posiciona o painel de conteúdo (dinâmico).
     */
    private void initContentPanel() {
        contentPanel = new ContentPanel();
        contentPanel.setBounds(MENU_WIDTH, 0, 1024 - MENU_WIDTH, WINDOW_HEIGHT);
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel);
    }

    /**
     * Cria e posiciona o menu lateral.
     */
    private void initMenu(JMain main) {
    	menu = new Menu(contentPanel, this);
        menu.setBounds(0, 0, MENU_WIDTH, WINDOW_HEIGHT);
        menu.initMoving(main);
        add(menu);
    }

    /**
     * Troca o conteúdo exibido na área principal.
     * @param component o novo componente a ser exibido
     */
    public void setForm(JComponent component) {
        contentPanel.removeAll();
        contentPanel.add(component);
        contentPanel.repaint();
        contentPanel.revalidate();
    }

	public ContentPanel content() {
		return contentPanel;
	}

	public ContentPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public JMain getMain() {
		return main;
	}

	public void setMain(JMain main) {
		this.main = main;
	}

}
