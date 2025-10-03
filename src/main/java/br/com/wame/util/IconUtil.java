package br.com.wame.util;

import javax.swing.ImageIcon;

public class IconUtil {
    public static ImageIcon getIcon(String name) {
        return new ImageIcon(IconUtil.class.getResource("/icon/24/status/" + name));
    }
}
