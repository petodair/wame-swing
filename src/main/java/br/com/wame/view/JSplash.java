package br.com.wame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import br.com.wame.JMain;
import br.com.wame.manager.ContentManager;
import br.com.wame.swing.PanelBorder;

public class JSplash extends PanelBorder {
	
	private static final long serialVersionUID = 1L;

	public JSplash() {
		setLayout(new BorderLayout());
        setBackground(Color.decode("#F0E3CA"));

        ImageIcon logoImg = new ImageIcon(getClass()
        		.getClassLoader()
        		.getResource("bg/splash-wame.png"));

        JLabel logo = new JLabel(logoImg);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        add(logo, BorderLayout.CENTER);

        showSplash(2000, ()-> {
            ContentManager.getInstance().setContentPane(
                    new JLogin(ContentManager
                    .getInstance()
                    .getApplication()));
        });
       
    }

    public void showSplash(int durationMillis, Runnable onFinish) {
        Timer timer = new Timer(durationMillis, e -> {
            setVisible(false);
            onFinish.run(); // chama a próxima tela
        });
        timer.setRepeats(false);
        timer.start();
    }
}
