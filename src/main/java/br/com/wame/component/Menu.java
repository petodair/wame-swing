package br.com.wame.component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import br.com.wame.controller.ContentController;
import br.com.wame.model.MenuModel;
import br.com.wame.swing.ListMenu;
import br.com.wame.view.JContent;

public class Menu extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel panelMoving;
    private ListMenu listMenu;
    private int x, y;

    private final ContentController contentController;

    public Menu(JPanel contentPanel, JContent content) {
    	this.contentController = new ContentController(content);
        setOpaque(false);
        setLayout(null);
        initComponents();
        setupMenuEvents();
    }

    private void initComponents() {
        initHeader();
        initMenuList();
    }

    private void initHeader() {
        panelMoving = new JPanel(null);
        panelMoving.setBounds(0, 0, 219, 69);
        panelMoving.setOpaque(false);

        JLabel titleLabel = new JLabel("Wame");
        titleLabel.setIcon(new ImageIcon(getClass()
        		.getClassLoader()
        		.getResource("icon/64/wame.png")));
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(Color.decode("#443627"));
        titleLabel.setBounds(10, 11, 199, 55);

        panelMoving.add(titleLabel);
        add(panelMoving);
    }

    private void initMenuList() {
        listMenu = new ListMenu();
        listMenu.setOpaque(false);
        listMenu.setBounds(0, 69, 219, 437);

        listMenu.addItem(new MenuModel("home", "Home", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("work", "Trabalhos e Atividades", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("person_search", "Pesquisar Usuário", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("info", "Sobre", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("", " ", MenuModel.MenuType.EMPTY));
        listMenu.addItem(new MenuModel("", " Meus Dados", MenuModel.MenuType.TITLE));
        listMenu.addItem(new MenuModel("", " ", MenuModel.MenuType.EMPTY));
        listMenu.addItem(new MenuModel("account", "Conta", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("contacts", "Contatos Salvos", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("settings", "Configurações", MenuModel.MenuType.MENU));
        listMenu.addItem(new MenuModel("logout", "Sair", MenuModel.MenuType.MENU));

        add(listMenu);
    }

    private void setupMenuEvents() {
        listMenu.addEventMenuSelected(index -> {
            switch (index) {
                case 0 -> contentController.openHomeScreen();
                case 1 -> contentController.openWorksScreen();
                case 2 -> contentController.abrirTelaCadastroTrabalho();
                case 10 -> contentController.realizarLogout();
                default -> {} // Futuras implementações
            }
        });
    }

    public void initMoving(JFrame frame) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });

        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
        });
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#F09819"), 0, getHeight(), Color.decode("#FF512F"));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(g);
    }
}
