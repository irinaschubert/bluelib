package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import domain.Schlagwort;

/**
 * Dient zur Darstellung und zum Updaten der Schalgwortliste
 * 
 * @version 1.0 15.11.2018
 * @author Mike
 *
 */
public class TableModelSchlagwort extends AbstractTableModel {

	// Definition der Spaltennamen, wie sie in der Tabelle dargestellt werden
	// sollen
	private static final String[] COLUMN_NAMES = { "Schlagwort", "LV" };
	private List<Schlagwort> schlagwortListe;

	public void setAndSortListe(List<Schlagwort> liste) {
		this.schlagwortListe = liste;
		// listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}

	public void schlagwortHinzufuegen(Schlagwort schlagwort) {
		schlagwortListe.add(schlagwort);
		// listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}
	/*
	 * private void listeSortieren() { Collections.sort(schlagwortListe);
	 * schlagwortListe.sort(Comparator.comparing(Schlagwort::getSchlagwort)
	 * .thenComparing(Comparator.comparing(Schlagwort::getVorname))); }
	 */

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return schlagwortListe.size();
	}

	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Schlagwort s = schlagwortListe.get(rowIndex);
		return s.getId();
	}

	// Rueckgabe des geklickten Objekts
	public Schlagwort getGeklicktesObjekt(int rowIndex) {
		return schlagwortListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Schlagwort s = schlagwortListe.get(rowIndex);
		Object returnWert = new Object();

		switch (columnIndex) {
		case 0:
			returnWert = s.getSchlagwort();
			break;
		case 1:
			returnWert = (s.getGeloescht() ? "x" : "");
			break;
		}

		return returnWert;
	}

}
