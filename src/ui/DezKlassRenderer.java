package ui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import domain.Autor;
import domain.DezKlassifikation;
import domain.DezKlassifikationGrpe;

/**
 * 
 * Stellt die Werte f�r die Comboboxen der Dezimalklassifikationsgruppen bereit
 * 
 * @version 1.0 18.10.2018
 * @author Schmutz
 *
 */

public class DezKlassRenderer extends BasicComboBoxRenderer{
	@Override
	  public Component getListCellRendererComponent(JList list, Object value,
	      int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected,
	        cellHasFocus);
//	    Definition der Variablen aus dem Objekt, welche in der ComboBox angezeigt werden sollen
//	    Der R�ckgabewert sollte vom Datentyp String sein. Der Cast des Objekts ist leidern�tig
	    if (value != null) {
	    	String dezKlassText = ((DezKlassifikation) value).getDezKlasse() + " " 
	    +  ((DezKlassifikation) value).getBezeichnung();
	      setText(dezKlassText);
	    }
	    return this;
	  }
}
