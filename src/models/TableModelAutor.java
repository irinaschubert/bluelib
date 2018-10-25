package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Autor;

/**
 * Dient zur Darstellung und zum Updaten der Autorenliste
 * 
 * @version 1.0 24.10.2018
 * @author Schmutz
 *
 */
public class TableModelAutor extends AbstractTableModel {

	// Definition der Spaltennamen, wie sie in der Tabelle dargestellt werden
	// sollen
	private static final String[] COLUMN_NAMES = { "Name", "Vorname", "Geburtsdatum", "Todesdatum"};
	private List<Autor> autorenListe;
	
	public void setAndSortListe(List<Autor> liste) {
		this.autorenListe = liste;
		listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();

	}
	
	public void autorHinzufuegen(Autor autor) {
		autorenListe.add(autor);
		listeSortieren();
		
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}
	
	private void listeSortieren() {
		autorenListe.sort(Comparator.comparing(Autor::getName)
				.thenComparing(Comparator.comparing(Autor::getVorname)));
	}
	
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return autorenListe.size();
	}
	
	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}
	
	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Autor a = autorenListe.get(rowIndex);
		return a.getId();
	}
	
	// Rueckgabe des gelickten Objekts
	public Autor getGeklicktesObjekt(int rowIndex) {
		return autorenListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Autor a = autorenListe.get(rowIndex);
		Object returnWert = new Object();

		switch (columnIndex) {
		case 0:
			returnWert = a.getName();
			break;
		case 1:
			returnWert = a.getVorname();
			break;
		case 2:
			returnWert = a.getGeburtsdatum();
			break;
		case 3:
			returnWert = a.getTodesdatum();
			break;
		}

		return returnWert;
	}
	
	

}
