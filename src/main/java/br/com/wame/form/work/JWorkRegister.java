package br.com.wame.form.work;

import java.awt.Color;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import com.formdev.flatlaf.FlatClientProperties;

import br.com.wame.form.FormBase;
import br.com.wame.manager.FormManager;
import br.com.wame.model.Work;
import br.com.wame.model.dto.ApiResponse;
import br.com.wame.service.WorkService;
import br.com.wame.swing.ListMenu;
import br.com.wame.util.DateConverter;
import br.com.wame.view.JContent;
import net.miginfocom.swing.MigLayout;

public class JWorkRegister extends FormBase {
    private static final long serialVersionUID = 1L;
    private final WorkService workService;

    // Campos principais
    private JTextField txtName;
    private JFormattedTextField txtStartDate;
    private JComboBox<Integer> cmbWeeks;
    private JComboBox<Integer> cmbShift;
    private JFormattedTextField txtStartShift;
    private JFormattedTextField txtEndShift;
    private JTextArea txtDescription;
    private JLabel messageLabel;
    private JContent content;

    // Dias da semana
    private final Map<DayOfWeek, JCheckBox> dayCheckBoxes = new EnumMap<>(DayOfWeek.class);
    
    private static final Map<DayOfWeek, String> NOMES_PT = Map.of(
    	    DayOfWeek.SUNDAY, "Domingo",
    	    DayOfWeek.MONDAY, "Segunda",
    	    DayOfWeek.TUESDAY, "Terça",
    	    DayOfWeek.WEDNESDAY, "Quarta",
    	    DayOfWeek.THURSDAY, "Quinta",
    	    DayOfWeek.FRIDAY, "Sexta",
    	    DayOfWeek.SATURDAY, "Sábado"
    	);

    public JWorkRegister(JPanel contentPanel, JContent content) {
        this.content = content;
        this.workService = new WorkService();

        // Estilos de fundo
        contentPanel.setBackground(Color.decode("#F0E3CA"));
        content.setBackground(Color.decode("#F0E3CA"));

        setLayout(new MigLayout("wrap, fillx, insets 10 35 10 35", "[grow,fill]"));
        initComponents();
    }

    /** Inicializa todos os componentes da tela */
    private void initComponents() {
        // Título
        JLabel lblTitle = new JLabel("Adicionar Trabalho", SwingConstants.CENTER);
        lblTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        add(lblTitle, "gapy 10, span, center");

        // Nome
        txtName = new JTextField();
        txtName.setToolTipText("Digite o nome do trabalho");
        add(new JLabel("Nome do trabalho"), "gapy 10");
        add(txtName);

        // Data + duração
        txtStartDate = new JFormattedTextField(createDateMask());
        txtStartDate.setToolTipText("Formato: dd-MM-aaaa");
        cmbWeeks = createWeeksCombo();

        JPanel datePanel = new JPanel(new MigLayout("insets 0, fillx", "[fill][fill]"));
        datePanel.setOpaque(false);
        datePanel.add(txtStartDate, "gapright 10");
        datePanel.add(cmbWeeks);

        add(new JLabel("Data de início / duração(semanas)"), "gapy 10");
        add(datePanel);

        // Dias da semana
        add(new JLabel("Dias da semana"), "gapy 10");
        add(createWeekCheckBoxes());

        // Turnos
        cmbShift = new JComboBox<>(new Integer[]{1});
        txtStartShift = new JFormattedTextField(createHourMask());
        txtEndShift = new JFormattedTextField(createHourMask());

        add(new JLabel("Número de turnos"));
        add(cmbShift);
        add(createShiftPanel());

        // Descrição
        txtDescription = new JTextArea(4, 20);
        txtDescription.setToolTipText("Descreva suas motivações ou detalhes do trabalho");
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        add(new JLabel("Descrição"), "gapy 10");
        add(new JScrollPane(txtDescription), "height 80!");

        // Mensagens
        messageLabel = new JLabel();
        messageLabel.setVisible(false);
        add(messageLabel, "gapy 10");

        // Botões
        JButton btnAddWork = createButton("Adicionar \u2795", e -> handleSubmit());
        JButton btnToWorks = createButton("Ver Trabalhos \u2192", e -> navigateToWorks());

        JPanel buttonsPanel = new JPanel(new MigLayout("insets 0, center", "[grow]"));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnAddWork, "split 2, gapright 10");
        buttonsPanel.add(btnToWorks);

        add(buttonsPanel, "gapy 20");
    }

    /** Cria o painel de checkboxes dos dias da semana */
    private JPanel createWeekCheckBoxes() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setOpaque(false);

        // Ordem dos dias
        DayOfWeek[] weekDays = {
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
        };

        for (DayOfWeek day : weekDays) {
        	String nomePt = NOMES_PT.get(day);
        	JCheckBox checkBox = new JCheckBox(nomePt);
            checkBox.setSelected(true);
            dayCheckBoxes.put(day, checkBox);
            panel.add(checkBox);
        }
        return panel;
    }

    /** Cria painel para turno (início/fim) */
    private JPanel createShiftPanel() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setOpaque(false);
        panel.add(new JLabel("Início/Fim do turno:"));
        panel.add(txtStartShift);
        panel.add(txtEndShift);
        return panel;
    }

    /** Cria combo de semanas */
    private JComboBox<Integer> createWeeksCombo() {
        JComboBox<Integer> combo = new JComboBox<>();
        for (int i = 1; i <= 12; i++) combo.addItem(i);
        combo.setToolTipText("Quantas semanas o trabalho irá durar?");
        return combo;
    }

    /** Cria botões */
    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    /** Máscara de data */
    private MaskFormatter createDateMask() {
        return createMask("##-##-####", '_');
    }

    /** Máscara de hora */
    private MaskFormatter createHourMask() {
        return createMask("##:##", '0');
    }

    private MaskFormatter createMask(String pattern, char placeholder) {
        try {
            MaskFormatter mask = new MaskFormatter(pattern);
            mask.setPlaceholderCharacter(placeholder);
            return mask;
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao criar máscara: " + pattern, e);
        }
    }

    /** Submissão do formulário */
    private void handleSubmit() {
        if (!validateFields()) {
            showMessage("Preencha os campos corretamente.", Color.RED);
            return;
        }

        LocalDate startDate = DateConverter.stringToLocalDate(txtStartDate.getText());
        int semanas = (Integer) cmbWeeks.getSelectedItem();
        LocalDate endDate = startDate.plusWeeks(semanas);

        Work work = new Work(
                txtName.getText().trim(),
                txtDescription.getText().trim(),
                DateConverter.convertDateFormat(txtStartDate.getText()),
                endDate.toString(),
                txtStartShift.getText(),
                txtEndShift.getText()
        );

        work.setDaysOfWeek(getSelectedDays());
        work.setNumberOfShifts((Integer) cmbShift.getSelectedItem());

        ApiResponse<Void> response = workService.save(work);

        if (response.code() == 201) {
            showMessage(response.message(), new Color(0, 128, 0)); // verde
            navigateToWorks();
        } else {
            showMessage(response.message(), Color.RED);
        }
    }

    /** Validação dos campos */
    private boolean validateFields() {
        return validateField(txtName, !txtName.getText().isBlank()) &
               validateField(txtDescription, !txtDescription.getText().isBlank()) &
               validateField(txtStartDate, !txtStartDate.getText().equals("__-__-____")) &
               validateField(txtStartShift, !txtStartShift.getText().equals("00:00")) &
               validateField(txtEndShift, !txtEndShift.getText().equals("00:00"));
    }

    private boolean validateField(JComponent component, boolean isValid) {
        if (!isValid) {
            component.putClientProperty(FlatClientProperties.STYLE, "border: red; background: #FFF0F0");
        } else {
            component.putClientProperty(FlatClientProperties.STYLE, "border: null; background: #FFFFFF");
        }
        component.repaint();
        return isValid;
    }

    /** Retorna dias da semana selecionados */
    private Set<DayOfWeek> getSelectedDays() {
        Set<DayOfWeek> days = new HashSet<>();
        for (Map.Entry<DayOfWeek, JCheckBox> entry : dayCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                days.add(entry.getKey());
            }
        }
        return days;
    }

    /** Mensagens de feedback */
    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:" + toHex(color));
        messageLabel.setVisible(true);
        revalidate();
        repaint();
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

    /** Navegação para listagem de trabalhos */
    private void navigateToWorks() {
        FormManager.getInstance().showForm(new JWorks(content));
        ListMenu.setSelected(1);
    }
}
