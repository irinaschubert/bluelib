package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import dao.AusleiheDAO;
import dao.BuchDAO;
import domain.Ausleihe;
import domain.Buch;

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
	private List<Ausleihe> ausleihliste;
	
	public void setAndSortListe(List<Ausleihe> liste) {
		this.ausleihliste = liste;
		listeSortieren();
		fireTableDataChanged();
	}
	
	public void ausleiheHinzufuegen(Ausleihe ausleihe) {
		ausleihliste.add(ausleihe);
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
		if(ausleihliste.size() != 0) {
			return ausleihliste.size();
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
		Ausleihe a = ausleihliste.get(rowIndex);
		return a.getId();
	}
	
	// Rueckgabe des angeklickten Objekts
	public Ausleihe getGeklicktesObjekt(int rowIndex) {
		return ausleihliste.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AusleiheDAO ausleihDAO = new AusleiheDAO();
		BuchDAO buchDAO = new BuchDAO();
		Ausleihe a = ausleihDAO.findById(ausleihliste.get(rowIndex).getId());
		Buch buch = buchDAO.findById(ausleihliste.get(rowIndex).getMediumID());
		
		Object returnWert = new Object();
		
		switch (columnIndex) {
		case 0:
			if(a.getMediumID() != 0) {
				//String barcode = buch.getBarcode();
				returnWert = "1000002";
			}
			else {returnWert = "";}
			break;
		case 1:
			if(a.getMediumID() != 0) {
				String titel = buch.getTitel();
				returnWert = titel;
			}
			else {returnWert = "";}
			break;
		case 2:
			returnWert = a.getAusleiheDatum();
			break;
		case 3:
			if(a.getNotizAusleihe() != null) {
				returnWert = a.getNotizAusleihe();
			}
			else {returnWert = "";}
			break;
		}
		return returnWert;
	}
}
