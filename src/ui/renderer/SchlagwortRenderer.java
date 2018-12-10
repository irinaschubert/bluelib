package ui.renderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import domain.Schlagwort;

/** 
 * 
 * Regelt die Werte und deren Darstellung der Schlagworte in der Dropdownliste
 * 
 * @version 1.0 2018-11-22
 * @author Ueli
 *
 */

public class SchlagwortRenderer extends BasicComboBoxRenderer{
	@Override
	  public Component getListCellRendererComponent(JList list, Object value,
	      int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected,
	        cellHasFocus);
//	    Definition der Variablen aus dem Objekt, welche in der ComboBox angezeigt werden sollen
//	    Der R�ckgabewert sollte vom Datentyp String sein. Der Cast des Objekts ist leidern�tig
	    if (value != null) {
	    	String schlagwortText = ((Schlagwort) value).getSchlagwort();
	      setText(schlagwortText);
	    }
	    return this;
	  }
}
