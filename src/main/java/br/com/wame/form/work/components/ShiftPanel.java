package br.com.wame.form.work.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import br.com.wame.util.DateConverter;

public class ShiftPanel extends JPanel {
    private final JFormattedTextField txtInicio;
    private final JFormattedTextField txtFim;
    private final JButton btnRemover;

    public ShiftPanel(List<ShiftPanel> turnos, JPanel container) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setOpaque(false);

        txtInicio = new JFormattedTextField(createHourMask());
        txtFim = new JFormattedTextField(createHourMask());
        
        btnRemover = new JButton("Remover");
        btnRemover.setToolTipText("Remover este turno");
        btnRemover.addActionListener(e -> {
            turnos.remove(this);
            container.remove(this);
            container.revalidate();
            container.repaint();
        });


        add(new JLabel("Início:"));
        add(txtInicio);
        add(new JLabel("Fim:"));
        add(txtFim);
        add(btnRemover);

    }
    
    public void destacarConflito() {
        txtInicio.setBackground(new Color(255, 200, 200));
        txtFim.setBackground(new Color(255, 200, 200));
    }
    
    public void limparConflito() {
        txtInicio.setBackground(Color.WHITE);
        txtFim.setBackground(Color.WHITE);
    }

    public String getInicio() {
        return DateConverter.dateHourConverter(txtInicio.getText());
    }

    public String getFim() {
        return DateConverter.dateHourConverter(txtFim.getText());
    }
    
    private MaskFormatter createHourMask() {
        try {
            MaskFormatter mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter('0');
            return mask;
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao criar máscara de data", e);
        }
    }
}
