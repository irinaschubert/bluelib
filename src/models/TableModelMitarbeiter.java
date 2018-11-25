package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Mitarbeiter;
import hilfsklassen.DateConverter;

/**
 * Dient zur Darstellung und zum Updaten der Mitarbeiterenliste
 * 
 * @version 0.1 24.11.2018
 * @author Mike
 *
 */
public class TableModelMitarbeiter extends AbstractTableModel {

	// Definition der Spaltennamen, wie sie in der Tabelle dargestellt werden
	// sollen
	private static final String[] COLUMN_NAMES = { "Name", "Vorname", "Benutzername", "Status" };
	private List<Mitarbeiter> mitarbeiterenListe;

	public void setAndSortListe(List<Mitarbeiter> liste) {
		this.mitarbeiterenListe = liste;
		listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();

	}

	public void mitarbeiterHinzufuegen(Mitarbeiter mitarbeiter) {
		mitarbeiterenListe.add(mitarbeiter);
		listeSortieren();

		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}

	private void listeSortieren() {
		mitarbeiterenListe.sort(Comparator.comparing(Mitarbeiter::getName)
				.thenComparing(Comparator.comparing(Mitarbeiter::getVorname)));
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return mitarbeiterenListe.size();
	}

	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Mitarbeiter m = mitarbeiterenListe.get(rowIndex);
		return m.getId();
	}

	// Rueckgabe des gelickten Objekts
	public Mitarbeiter getGeklicktesObjekt(int rowIndex) {
		return mitarbeiterenListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mitarbeiter m = mitarbeiterenListe.get(rowIndex);
		Object returnWert = new Object();

		switch (columnIndex) {
		case 0:
			returnWert = m.getName();
			break;
		case 1:
			returnWert = m.getVorname();
			break;
		case 2:
			returnWert = m.getBenutzername();
			break;
		case 3:
			returnWert = (m.isAktiv() ? "x" : "");
			break;
		}
		return returnWert;
	}
}
