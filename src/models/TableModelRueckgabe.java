package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import dao.AusleiheDAO;
import dao.BuchDAO;
import domain.Ausleihe;
import domain.Buch;
import hilfsklassen.DateConverter;

/**
 * Dient zur Darstellung und zum Updaten der Rückgabe
 * 
 * @version 1.0 2018-12-09
 * @author Schmutz
 *
 */
@SuppressWarnings("serial")
public class TableModelRueckgabe extends AbstractTableModel {

	// Definition der Spaltennamen
	private static final String[] COLUMN_NAMES = { "Barcode Buch", "Buchtitel", "Datum Rückgabe", "Notiz"};
	private List<Ausleihe> rueckgabeListe;
	
	public void setAndSortListe(List<Ausleihe> liste) {
		this.rueckgabeListe = liste;
		listeSortieren();
		fireTableDataChanged();
	}
	
	public void ausleiheHinzufuegen(Ausleihe ausleihe) {
		rueckgabeListe.add(ausleihe);
		listeSortieren();
		fireTableDataChanged();
	}
	
	private void listeSortieren() {
		// TODO Sortierung nach Implementierung Buch in der Ausleihe
		//ausleihliste.sort(Comparator.comparing(Buch::getTitel));
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		if(rueckgabeListe.size() != 0) {
			return rueckgabeListe.size();
		}
		else {
			return 0;
		}
	}
	
	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}
	
	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Ausleihe a = rueckgabeListe.get(rowIndex);
		return a.getId();
	}
	
	// Rueckgabe des angeklickten Objekts
	public Ausleihe getGeklicktesObjekt(int rowIndex) {
		return rueckgabeListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	
		Ausleihe a = rueckgabeListe.get(rowIndex);
		Object returnWert = new Object();
		
		switch (columnIndex) {
		case 0:
			returnWert = a.getMedium().getBarcodeNr();
			break;
		case 1:
			returnWert = a.getMedium().getTitel();
			break;
		case 2:
			returnWert = DateConverter.convertJavaDateToString(a.getRueckgabeDatum());
			break;
		case 3:
			returnWert = a.getMedium().getBemerkung();
			break;
		}
		return returnWert;
	}
}
