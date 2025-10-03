package br.com.wame.component;

import javax.swing.*;
import java.awt.*;

public class WidthLimiter extends JPanel {
    
	private static final long serialVersionUID = 1L;
	private final JPanel inner;

    public WidthLimiter(JPanel content, int maxWidth) {
        setLayout(new GridBagLayout());
        setOpaque(false); // Torna o painel transparente para preservar o fundo do JScrollPane

        inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.add(content, BorderLayout.CENTER);
        inner.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));
        inner.setPreferredSize(new Dimension(maxWidth, content.getPreferredSize().height));

        add(inner);
    }
}
