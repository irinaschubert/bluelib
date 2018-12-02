package ui.renderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import domain.Autor;
import domain.DezKlassifikationGrpe;

/**
 * 
 * Stellt die Werte für die Comboboxen der Dezimalklassifikationsgruppen bereit
 * 
 * @version 1.0 18.10.2018
 * @author Schmutz
 *
 */

public class DezKlassGrpeRenderer extends BasicComboBoxRenderer{
	@Override
	  public Component getListCellRendererComponent(JList list, Object value,
	      int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected,
	        cellHasFocus);
//	    Definition der Variablen aus dem Objekt, welche in der ComboBox angezeigt werden sollen
//	    Der Rückgabewert sollte vom Datentyp String sein. Der Cast des Objekts ist leidernötig
	    if (value != null) {
	    	String dezKlassGrpeText = ((DezKlassifikationGrpe) value).getGruppe() + " " 
	    +  ((DezKlassifikationGrpe) value).getBezeichnung();
	      setText(dezKlassGrpeText);
	    }
	    return this;
	  }
}
