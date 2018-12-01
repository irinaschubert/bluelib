package ui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import domain.Status;

public class StatusSucheRenderer extends BasicComboBoxRenderer {
	  @Override
	  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    if (value != null) {
	      Status status = (Status) value;
	      setText(String.valueOf(status.getBezeichnung()));
	    }
	    if (index == -1) {
	    	Status status = (Status) value;
	    	if(status == null) {
	    		setText("-- alle --");
	    	}else {
	    		setText("" + status.getBezeichnung());
	    	}
	    }
	    return this;
	  }
	}