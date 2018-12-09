package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import domain.Verlag;
import hilfsklassen.DateConverter;

/**
 * Dient zur Darstellung und zum Updaten der Verlagsliste
 * 
 * @version 1.0 26.10.2018
 * @author irina
 */
@SuppressWarnings("serial")
public class TableModelVerlag extends AbstractTableModel {

	// Definition der Spaltennamen, wie sie in der Tabelle dargestellt werden sollen
	private static final String[] COLUMN_NAMES = { "Name", "Gründungsdatum", "Enddatum", "LV" };
	private List<Verlag> verlagListe;

	public void setAndSortListe(List<Verlag> liste) {
		this.verlagListe = liste;
		listeSortieren();
		fireTableDataChanged();
	}

	public void verlagHinzufuegen(Verlag verlag) {
		verlagListe.add(verlag);
		listeSortieren();
		fireTableDataChanged();
	}

	private void listeSortieren() {
		verlagListe.sort(Comparator.comparing(Verlag::getName));
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return verlagListe.size();
	}

	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Verlag v = verlagListe.get(rowIndex);
		return v.getId();
	}

	// Rueckgabe des angeklickten Objekts
	public Verlag getGeklicktesObjekt(int rowIndex) {
		return verlagListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Verlag v = verlagListe.get(rowIndex);
		Object returnWert = new Object();

		switch (columnIndex) {
		case 0:
			returnWert = v.getName();
			break;
		case 1:
			returnWert = DateConverter.convertJavaDateToString(v.getGruendungsDatum());
			break;
		case 2:
			returnWert = DateConverter.convertJavaDateToString(v.getEndDatum());
			break;
		case 3:
			returnWert = (v.getGeloescht() ? "x" : "");
			break;
		}

		return returnWert;
	}

}
