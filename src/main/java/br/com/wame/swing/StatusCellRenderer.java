package br.com.wame.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.wame.util.IconUtil;

import javax.swing.plaf.basic.BasicLabelUI;

public class StatusCellRenderer extends DefaultTableCellRenderer {

	private static boolean pulseToggle = false;
	private static final Timer pulseTimer = new Timer(600, e -> {
		pulseToggle = !pulseToggle;
	});
	static {
		pulseTimer.start();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		cell.setOpaque(true);
		cell.setBorder(noFocusBorder);
		cell.setHorizontalAlignment(SwingConstants.LEFT);
		cell.setHorizontalTextPosition(SwingConstants.RIGHT);
		cell.setIconTextGap(8);

		String status = table.getValueAt(row, 4).toString(); // coluna "Status"

		Color startColor;
		Color endColor;
		ImageIcon icon;

		switch (status.toUpperCase()) {
		case "FINALIZADO":
			startColor = new Color(200, 255, 200);
			endColor = new Color(150, 230, 150);
			icon = IconUtil.getIcon("check.png");
			break;
		case "PARCIAL":
			startColor = pulseToggle ? new Color(255, 255, 180) : new Color(255, 245, 160);
			endColor = pulseToggle ? new Color(255, 230, 120) : new Color(255, 215, 100);
			icon = IconUtil.getIcon("partial.png");
			break;
		default:
			startColor = new Color(255, 230, 180);
			endColor = new Color(255, 200, 120);
			icon = IconUtil.getIcon("pending.png");
			break;
		}

		switch (status.toUpperCase()) {
		case "FINALIZADO":
			cell.setToolTipText("Turno registrado com sucesso.");
			break;
		case "PARCIAL":
			cell.setToolTipText("Turno parcialmente registrado. Falta entrada ou saída.");
			break;
		default:
			cell.setToolTipText("Turno pendente. Nenhum ponto registrado.");
			break;
		}

		if (column == 0) {
		    cell.setIcon(icon);
		} else {
		    cell.setIcon(null);
		}
		cell.setBackground(new Color(0, 0, 0, 0)); // transparente para pintura personalizada

		cell.setUI(new BasicLabelUI() {
			@Override
			public void paint(Graphics g, JComponent c) {
				Graphics2D g2 = (Graphics2D) g.create();
				int w = c.getWidth();
				int h = c.getHeight();
				GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
				g2.setPaint(gp);
				g2.fillRect(0, 0, w, h);
				g2.dispose();
				super.paint(g, c);
			}
		});

		return cell;
	}
}