package ui.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import domain.Autor;

/**
 * Regelt die Darstellung der Werte der Autoren in der JList
 * 
 * @version 1.0 2018-11-18
 * @author Ueli
 */

public class AutorListCellRenderer extends JLabel implements ListCellRenderer<Autor> {
	
	public AutorListCellRenderer() {
         setOpaque(true);
     }
	@Override
	public Component getListCellRendererComponent(JList<? extends Autor> list, Autor value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value.getName() + " " + value.getVorname());
		
		Color background;
        Color foreground;

         // check if this cell is selected
        if (isSelected) {
            background = Color.LIGHT_GRAY;

        // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;

        };

        setBackground(background);
		return this;
	}

}
