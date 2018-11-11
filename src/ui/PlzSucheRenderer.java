package ui;

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
	      setText(String.valueOf(ort.getPlz()));
	    }
	    if (index == -1) {
	    	Ort ort = (Ort) value;
	    	setText("" + ort.getPlz());
	    }
	    return this;
	  }
	}