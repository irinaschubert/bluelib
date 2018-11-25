package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import domain.Mitarbeiter;

/**
 * Dient zur Darstellung und zum Updaten der Schalgwortliste
 * 
 * @version 1.0 15.11.2018
 * @author Mike
 *
 */
public class TableModelMitarbeiter extends AbstractTableModel {

	// Definition der Spaltennamen, wie sie in der Tabelle dargestellt werden
	// sollen
	private static final String[] COLUMN_NAMES = { "Mitarbeiter", "LV" };
	private List<Mitarbeiter> mitarbeiterListe;

	public void setAndSortListe(List<Mitarbeiter> liste) {
		this.mitarbeiterListe = liste;
		// listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}

	public void mitarbeiterHinzufuegen(Mitarbeiter mitarbeiter) {
		mitarbeiterListe.add(mitarbeiter);
		// listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}
	/*
	 * private void listeSortieren() { Collections.sort(mitarbeiterListe);
	 * mitarbeiterListe.sort(Comparator.comparing(Mitarbeiter::getMitarbeiter)
	 * .thenComparing(Comparator.comparing(Mitarbeiter::getVorname))); }
	 */

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return mitarbeiterListe.size();
	}

	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Mitarbeiter s = mitarbeiterListe.get(rowIndex);
		return s.getId();
	}

	// Rueckgabe des geklickten Objekts
	public Mitarbeiter getGeklicktesObjekt(int rowIndex) {
		return mitarbeiterListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mitarbeiter m = mitarbeiterListe.get(rowIndex);
		Object returnWert = new Object();

		switch (columnIndex) {
		case 0:
			returnWert = m.getBenutzername();
			break;
		case 1:
			returnWert = (m.isAktiv() ? "x" : "");
			break;
		}
		return returnWert;
	}
}
