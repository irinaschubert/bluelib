package ui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import domain.Schlagwort;

/** 
 * @version 1.0 2018-11-22
 * @author Schmutz
 *
 */

public class SchlagwortRenderer extends BasicComboBoxRenderer{
	@Override
	  public Component getListCellRendererComponent(JList list, Object value,
	      int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected,
	        cellHasFocus);
//	    Definition der Variablen aus dem Objekt, welche in der ComboBox angezeigt werden sollen
//	    Der Rückgabewert sollte vom Datentyp String sein. Der Cast des Objekts ist leidernötig
	    if (value != null) {
	    	String schlagwortText = ((Schlagwort) value).getSchlagwort();
	      setText(schlagwortText);
	    }
	    return this;
	  }
}
