package br.com.wame.form.work;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatClientProperties;

import br.com.wame.component.Card;
import br.com.wame.component.Header;
import br.com.wame.component.Table;
import br.com.wame.enums.Status;
import br.com.wame.form.FormBase;
import br.com.wame.model.CardModel;
import br.com.wame.model.Work;
import br.com.wame.model.dto.ApiResponse;
import br.com.wame.service.WorkService;
import br.com.wame.swing.PanelBorder;
import br.com.wame.view.JContent;

public class JWorks extends FormBase {

    private static final long serialVersionUID = 1L;

    private final WorkService workService = new WorkService();

    private Card card1;
    private Card card2;
    private Card card3;
    private Table table;
    private JContent content;

    public JWorks(JContent content) {
    	this.content = content;
        content.setBackground(Color.decode("#F0E3CA"));
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        addHeader();
        add(createCardsPanel());
        add(createTablePanel());
        loadTableData();
    }

    private void addHeader() {
        Header header = new Header();
        header.setBounds(0, 0, 804, 42);
        add(header);
    }

    private JPanel createCardsPanel() {
        JPanel pnlCards = new JPanel(new GridLayout(1, 0, 10, 0));
        pnlCards.setBounds(23, 63, 761, 163);
        pnlCards.setBackground(Color.decode("#F0E3CA"));

        card1 = new Card(Color.decode("#ED213A"), Color.decode("#93291E"));
        card2 = new Card(Color.decode("#C6426E"), Color.decode("#732B73"));
        card3 = new Card(Color.decode("#71B280"), Color.decode("#134E5E"));

        pnlCards.add(card1);
        pnlCards.add(card2);
        pnlCards.add(card3);

        return pnlCards;
    }

    private JPanel createTablePanel() {
        PanelBorder panelBorder = new PanelBorder();
        panelBorder.setBackground(Color.WHITE);
        panelBorder.setLayout(null);
        panelBorder.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelBorder.setBounds(23, 237, 761, 228);

        JLabel label = new JLabel("Tabela de Trabalhos");
        label.setBounds(10, 11, 150, 14);
        panelBorder.add(label);

        table = new Table(content,4);
        table.setModel(createTableModel());
        table.setShowVerticalLines(false);
        table.putClientProperty(FlatClientProperties.STYLE, "intercellSpacing:1,1;");
        table.getColumnModel().getColumn(0).setPreferredWidth(115);

        // Remove coluna de ID
        TableColumnModel tcm = table.getColumnModel();
        tcm.removeColumn(tcm.getColumn(5));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 36, 741, 281);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {
			private static final long serialVersionUID = 1L;
		{
            setBackground(Color.WHITE);
        }});

        panelBorder.add(scrollPane);
        return panelBorder;
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(new Object[][] {}, 
            new String[] { "Nome", "Gerenciador", "Início", "Fim", "Status", "ID" }) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void loadTableData() {
        ApiResponse<List<Work>> apiResponse = workService.findAll();

        if (apiResponse.code() != 0) {
            List<Work> works = apiResponse.data();
            works.forEach(work -> {
                Object[] row = {
                    work.getName(),
                    work.getUser().getUsername(),
                    formatDate(work.getStartDate()),
                    formatDate(work.getEndDate()),
                    work.isFinalized() ? Status.FINALIZED : Status.PENDING,
                    work.getID()
                };
                table.addRow(row);
            });

            updateCards(works);
        } else {
            table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Erro" }));
            table.addRow(new Object[] { apiResponse.message() });
        }
    }

    private void updateCards(List<Work> works) {
        int totalWorks = works.size();
        long finalizedCount = works.stream().filter(Work::isFinalized).count();
        // Aqui pode vir do backend no futuro
        String totalManagers = "2";

        card1.setData(new CardModel(
            new ImageIcon(getClass().getClassLoader().getResource("icon/24/03.png")),
            "Gerenciadores", totalManagers, "Número total"));

        card2.setData(new CardModel(
            new ImageIcon(getClass().getClassLoader().getResource("icon/24/04.png")),
            "Trabalhos", Integer.toString(totalWorks), "Número total"));

        card3.setData(new CardModel(
            new ImageIcon(getClass().getClassLoader().getResource("icon/24/02.png")),
            "Trabalhos Concluídos", Long.toString(finalizedCount), "Número total"));
    }

    private String formatDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
    }
}
