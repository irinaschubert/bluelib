package ui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import domain.Anrede;
import domain.Ort;

/**
 * 
 * Mit dem Renderer k�nnen die ComboBox-Inhalte aus den Dom�nenobjekten flexibel zusammengestellt werden
 * 
 * @version 1.0 18.10.2018
 * @author Schmutz
 *
 */

public class AnredeRenderer extends BasicComboBoxRenderer{
	@Override
	  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//	    Definition der Variablen aus dem Objekt, welche in der ComboBox angezeigt werden sollen
//	    Der R�ckgabewert sollte vom Datentyp String sein. Der Cast des Objekts zu Anrede ist leider n�tig
	    if (value != null) {
	    	Anrede anrede = (Anrede) value;
		      setText(String.valueOf(anrede.getBezeichnung()));
		    }
		    if (index == -1) {
		    	Anrede anrede = (Anrede) value;
		    	setText("" + anrede.getBezeichnung());
		    }
	    return this;
	  }
}
