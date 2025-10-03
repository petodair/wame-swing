package br.com.wame;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

/**
 * Hello world!
 *
 */
public class WameApp 
{
    public static void main( String[] args )
    {
    	FlatLaf.registerCustomDefaultsSource("themes");
    	FlatRobotoFont.install();
    	UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY,Font.PLAIN,13));
    	FlatMacLightLaf.setup();
    	EventQueue.invokeLater(() -> new JMain().setVisible(true));
    }
}
