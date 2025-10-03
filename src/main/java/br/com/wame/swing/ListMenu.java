package br.com.wame.swing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import br.com.wame.event.EventMenuSelected;
import br.com.wame.model.MenuModel;

public class ListMenu extends JList<MenuModel> {

	private static final long serialVersionUID = 1L;

	private final DefaultListModel<MenuModel> model;
	private static int selectedIndex = -1;
	private int overIndex = -1;

	private EventMenuSelected event;

	public ListMenu() {
		model = new DefaultListModel<MenuModel>();
		setModel(model);
		// Ouvinte do mouse(captura os comandos executados no Mouse)
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int index = locationToIndex(e.getPoint()); // index = item clicado pelo mouse
					MenuModel m = model.getElementAt(index);
					if (m.getType() == MenuModel.MenuType.MENU) {
						selectedIndex = index;
						if (event != null) {
							event.selected(index);
						}
					}
					repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				overIndex = -1;
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index != overIndex) {
					MenuModel m = model.getElementAt(index);
					if (m instanceof MenuModel) {
						if (m.getType() == MenuModel.MenuType.MENU) {
							overIndex = index;
						} else {
							overIndex = -1;
						}
						repaint();
					}
				}
			}
		});
	}

	@Override
	public ListCellRenderer<? super MenuModel> getCellRenderer() {
		return new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected,
					boolean focus) {
				MenuModel data;
				if (value instanceof MenuModel) {
					data = (MenuModel) value;
				} else {
					data = new MenuModel("", value + "", MenuModel.MenuType.EMPTY);
				}
				MenuItem item = new MenuItem(data);
				item.setSelected(selectedIndex == index);
				item.setOver(overIndex == index);
				return item;
			}
		};
	}

	public void addEventMenuSelected(EventMenuSelected event) {
		this.event = event;
	}

	public void addItem(MenuModel data) {
		model.addElement(data);
	}
	
	public static void setSelected(int index){
		selectedIndex = index;
	}
}
