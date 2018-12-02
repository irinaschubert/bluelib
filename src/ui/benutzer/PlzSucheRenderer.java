package ui.benutzer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import domain.Ort;

public class PlzSucheRenderer extends BasicComboBoxRenderer {
	  @Override
	  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    if (value != null) {
	      Ort ort = (Ort) value;
	      setText(String.valueOf(ort.getPlz() + " " + ort.getOrt()));
	    }
	    if (index == -1) {
	    	Ort ort = (Ort) value;
	    	if(ort == null) {
	    		setText("-- alle --");
	    	}else {
	    		setText("" + ort.getPlz() + " " + ort.getOrt());
	    	}
	    }
	    return this;
	  }
	}