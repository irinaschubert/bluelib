package ui.renderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import domain.Ort;

/**
 * Regelt die Darstellung der PLZ und Orte in der Dropdownliste im Suchbereich
 * 
 * @version 1.0 18.10.2018
 * @author Irina
 * 
 */
public class PlzSucheRenderer extends BasicComboBoxRenderer {
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value != null) {
			Ort ort = (Ort) value;
			setText(String.valueOf(ort.getPlz() + " " + ort.getOrt()));
		}
		if (index == -1) {
			Ort ort = (Ort) value;
			if (ort == null) {
				setText("-- Kein Ort ausgewählt --");
			} else {
				setText("" + ort.getPlz() + " " + ort.getOrt());
			}
		}
		return this;
	}
}