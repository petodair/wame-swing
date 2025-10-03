package br.com.wame.form.work;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatClientProperties;

import br.com.wame.component.Table;
import br.com.wame.enums.Status;
import br.com.wame.form.FormBase;
import br.com.wame.manager.FormManager;
import br.com.wame.model.TimeClock;
import br.com.wame.model.Work;
import br.com.wame.model.dto.ApiResponse;
import br.com.wame.service.TimeClockService;
import br.com.wame.service.WorkService;
import br.com.wame.swing.ListMenu;
import br.com.wame.swing.PanelBorder;
import br.com.wame.swing.StatusCellRenderer;
import br.com.wame.swing.TableHeader;
import br.com.wame.util.DateConverter;
import br.com.wame.view.JContent;
import net.miginfocom.swing.MigLayout;

public class JWork extends FormBase {

    private static final long serialVersionUID = 1L;
    private final WorkService workService = new WorkService();
    private final TimeClockService timeClockService = new TimeClockService();
    private final Long ID;
    
    private JLabel messageLabel;
    
    private Table table;

    public JWork(Long ID, JContent content) {
    	this.ID = ID;
        ApiResponse<Work> response = workService.findById(ID);
        Work work = response.data();

        if (work == null) {
            add(new JLabel("Erro ao carregar trabalho."));
            return;
        }

        JPanel contentPanel = new JPanel(new MigLayout("wrap, fillx, insets 30", "[center]"));
        contentPanel.setBackground(Color.decode("#F0E3CA"));

        JPanel widthLimiter = new JPanel(new MigLayout("wrap, fillx, gapy 15", "[fill, 704!]"));
        widthLimiter.setOpaque(false);

        // Cabeçalho estilo LinkedIn
        JPanel headerPanel = new JPanel(new MigLayout("insets 0, fillx", "[grow]push[right]"));
        headerPanel.setOpaque(false);

        JLabel lblUser = new JLabel("👤 " + (work.getUser().getUsername() != null ? work.getUser().getUsername() : "Desconhecido"));
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 15));

        headerPanel.add(lblUser);
        widthLimiter.add(headerPanel);

        // Título do trabalho
        JLabel lblTitle = new JLabel(work.getName());
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        widthLimiter.add(lblTitle);

        // Descrição estilo post
        JTextArea txtDescription = new JTextArea(work.getDescription());
        txtDescription.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        txtDescription.setOpaque(false);
        txtDescription.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        widthLimiter.add(txtDescription, "growx");

        // Informações adicionais
        //widthLimiter.add(createSectionLabel("Datas do Trabalho:"), "gapy 10");
        //widthLimiter.add(createInfoRow("Início:", DateConverter.brDateConverter(work.getStartDate())));
        //widthLimiter.add(createInfoRow("Término:", DateConverter.brDateConverter(work.getEndDate())));
        
        //Tabela de pontos/horários
        widthLimiter.add(createSectionLabel("Registro de pontos:"), "gapy 10");
        
        widthLimiter.add(createTablePanel(content), "gapy 10");
        loadTableData(ID);
        
        messageLabel = new JLabel();
        messageLabel.setVisible(false);
        widthLimiter.add(messageLabel);
        
        JButton btnClockNow = new JButton("Registrar início de turno");
        btnClockNow.addActionListener(e -> clockNow());
        widthLimiter.add(btnClockNow);

        widthLimiter.add(createSectionLabel("Número de avaliações:"), "gapy 10");
        widthLimiter.add(createInfoValue("0"));

        // Botões de interação
        JPanel interactionPanel = new JPanel(new MigLayout("insets 0", "[]10[]"));
        interactionPanel.setOpaque(false);

        JButton btnLike = new JButton("Curtir");
        JButton btnComment = new JButton("Comentar");
        JButton btnDelete = new JButton("Deletar");

        btnDelete.addActionListener((ActionEvent e) -> {
            workService.delete(ID);
            FormManager.getInstance().showForm(new JWorks(content));
            ListMenu.setSelected(1);
        });

        interactionPanel.add(btnLike);
        interactionPanel.add(btnComment);
        interactionPanel.add(btnDelete);
        widthLimiter.add(interactionPanel, "gapy 15");

        contentPanel.add(widthLimiter);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createTablePanel(JContent content) {
    	PanelBorder panelBorder = new PanelBorder();
        panelBorder.setBackground(Color.WHITE);
        panelBorder.setLayout(new MigLayout("wrap, fillx, height 328"));
        panelBorder.setBorder(new EmptyBorder(5, 5, 5, 5));
    	
    	table = new Table(content,4);
        table.setModel(createTableModel());
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.putClientProperty("Table.showCellSeparators", false);
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				TableHeader header = new TableHeader(value+ "");
                if (column == 4) {
                    header.setHorizontalAlignment(JLabel.LEFT);
                }
                return header;
			}
        });
        table.setDefaultRenderer(Object.class, new StatusCellRenderer());
        
        // Remove coluna de ID
        TableColumnModel tcm = table.getColumnModel();
        tcm.removeColumn(tcm.getColumn(0));
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(10, 36, 341, 81);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        tableScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {
			private static final long serialVersionUID = 1L;
		{
            setBackground(Color.WHITE);
        }});
        
        panelBorder.add(tableScrollPane, "growx, pushx");
        return panelBorder;
    }
    
    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(new Object[][] {}, 
            new String[] { "ID", "Data", "Início", "Fim","Dia da Semana", "Status"}) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    
    private void loadTableData(Long workId) {
    	//Clear table
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        ApiResponse<List<TimeClock>> apiResponse = timeClockService.getByWork(workId);

        if (apiResponse.code() != 0) {
            List<TimeClock> timeClocks = apiResponse.data();
            timeClocks.forEach(timeClock -> {
            	Status status = Status.valueOf(timeClock.getStatus().name());
            	Object[] row = {
            	    timeClock.getID(),
            	    DateConverter.brDateConverter(timeClock.getStartShift()),
            	    DateConverter.dateHourConverter(timeClock.getStartShift()),
            	    DateConverter.dateHourConverter(timeClock.getEndShift()),
            	    DateConverter.getDayOfTheWeek(timeClock.getStartShift()).toUpperCase(),
            	    status.getText()
            	};

                table.addRow(row);
            });

            //updateCards(works);
        } else {
            table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Erro" }));
            table.addRow(new Object[] { apiResponse.message() });
        }
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        return label;
    }

    private JLabel createInfoValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return label;
    }
    
    private void clockNow() {
    	ApiResponse<Void> response = timeClockService.clockNow();
    	if(response.code() != 200) {
    		showMessage(response.message(), Color.RED);
    	} else {
    		showMessage(response.message(), new Color(0, 128, 0)); // Verde
    	}
    	loadTableData(ID);
    }
    
    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:" + toHex(color));
        messageLabel.setVisible(true);
        revalidate();
        repaint();
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                color.getRed(), color.getGreen(), color.getBlue());
    }
    
}