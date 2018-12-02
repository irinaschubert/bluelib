package ui.benutzer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import domain.Anrede;

/** 
 * @version 1.0 18.10.2018
 * @author Schmutz
 *
 */

@SuppressWarnings("serial")
public class AnredeRenderer extends BasicComboBoxRenderer{
	@Override
	  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
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
