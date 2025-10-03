package br.com.wame.manager;

import java.awt.EventQueue;
import javax.swing.JComponent;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import br.com.wame.view.JContent;

/**
 * Gerencia a exibição de formulários (telas) com transições animadas.
 */
public class FormManager {

    private static FormManager instance;
    private JContent content;

    private FormManager() {}

    public static FormManager getInstance() {
        if (instance == null) {
            instance = new FormManager();
        }
        return instance;
    }

    public void initApplication(JContent content) {
        this.content = content;
    }

    /**
     * Troca o conteúdo da tela principal com animação.
     * 
     * @param form Componente a ser exibido
     */
    public void showForm(JComponent form) {
        if (content == null) {
            throw new IllegalStateException("FormManager não foi inicializado com JContent.");
        }

        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot(); // tira um "print" da tela atual
            content.setForm(form);                // troca a tela
            content.revalidate();
            content.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation(); // faz transição suave
        });
    }
}
