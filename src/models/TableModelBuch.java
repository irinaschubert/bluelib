package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Autor;
import domain.Buch;
import hilfsklassen.DateConverter;

/**
 * Dient zur Darstellung und zum Updaten der Autorenliste
 * 
 * @version 1.0 24.10.2018
 * @author Schmutz
 *
 */
public class TableModelBuch extends AbstractTableModel {

	// Definition der Spaltennamen, wie sie in der Tabelle dargestellt werden
	// sollen
	private static final String[] COLUMN_NAMES = { "Barcode", "Titel", "Signatur", "Autor", "Verlag"};
	private List<Buch> buchListe;
	
	public void setAndSortListe(List<Buch> liste) {
		this.buchListe = liste;
		listeSortieren();
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();

	}
	
	public void autorHinzufuegen(Buch buch) {
		buchListe.add(buch);
		listeSortieren();
		
		// Damit erhält die Liste in der View ein Update
		fireTableDataChanged();
	}
	
	private void listeSortieren() {
		buchListe.sort(Comparator.comparing(Buch::getTitel)
				.thenComparing(Comparator.comparing(Buch::getBarcode)));
	}
	
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return buchListe.size();
	}
	
	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}
	
	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Buch b = buchListe.get(rowIndex);
		return b.getId();
	}
	
	// Rueckgabe des gelickten Objekts
	public Buch getGeklicktesObjekt(int rowIndex) {
		return buchListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Buch b = buchListe.get(rowIndex);
		Object returnWert = new Object();

		switch (columnIndex) {
		case 0:
			returnWert = b.getBarcode();
			break;
		case 1:
			returnWert = b.getTitel();
			break;
		case 2:
			returnWert = b.getSignatur();
			break;
		case 3:
			String returnAutor = "";
			for (int i = 0; i < b.getAutoren().size(); i++) {
				returnAutor = returnAutor + (i+1==b.getAutoren().size()?b.getAutoren().get(i).getName():b.getAutoren().get(i).getName() + ", ");
			}
			returnWert = returnAutor;
			break;
		case 4:
			returnWert = b.getVerlag().getName();
			break;
		}
		

		return returnWert;
	}
	
	

}
