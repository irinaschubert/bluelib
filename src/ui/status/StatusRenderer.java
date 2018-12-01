package ui.status;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import domain.Status;

public class StatusRenderer extends BasicComboBoxRenderer {
	  @Override
	  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    if (value != null) {
	      Status status = (Status) value;
	      setText(String.valueOf(status.getBezeichnung()));
	    }
	    if (index == -1) {
	    	Status status = (Status) value;
	    	setText("" + status.getBezeichnung());
	    }
	    return this;
	  }
	}