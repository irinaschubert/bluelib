package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import dao.BenutzerDAO;
import domain.Adresse;
import domain.Benutzer;
import domain.Ort;
import hilfsklassen.DateConverter;

/**
 * Dient zur Darstellung und zum Updaten der Benutzerliste
 * 
 * @version 1.0 06.11.2018
 * @author irina
 *
 */
public class TableModelBenutzer extends AbstractTableModel {

	// Definition der Spaltennamen
	private static final String[] COLUMN_NAMES = { "ID", "Name", "Vorname", "Strasse/Nr.", "PLZ/Ort", "Status"};
	private List<Benutzer> benutzerListe;
	
	public void setAndSortListe(List<Benutzer> liste) {
		this.benutzerListe = liste;
		listeSortieren();
		fireTableDataChanged();
	}
	
	public void benutzerHinzufuegen(Benutzer benutzer) {
		benutzerListe.add(benutzer);
		listeSortieren();
		fireTableDataChanged();
	}
	
	private void listeSortieren() {
		benutzerListe.sort(Comparator.comparing(Benutzer::getName)
				.thenComparing(Comparator.comparing(Benutzer::getVorname)));
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return benutzerListe.size();
	}
	
	// Setzen der Spaltennamen
	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}
	
	// Rueckgabe des PK
	public int getPK(int rowIndex) {
		Benutzer b = benutzerListe.get(rowIndex);
		return b.getId();
	}
	
	// Rueckgabe des angeglickten Objekts
	public Benutzer getGeklicktesObjekt(int rowIndex) {
		return benutzerListe.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		Benutzer a = benutzerListe.get(rowIndex);
		Benutzer b = benutzerDAO.findById(a.getId());
		Object returnWert = new Object();
		
		switch (columnIndex) {
		case 0:
			returnWert = b.getId();
			break;
		case 1:
			returnWert = b.getName();
			break;
		case 2:
			returnWert = b.getVorname();
			break;
		case 3:	
			//TODO
			if(b.getAdresse() != null) {
				Adresse adresse = b.getAdresse();
				String strasseNr = adresse.getStrasse();
				returnWert = strasseNr;
			}
			else {returnWert = "";}
			break;
		case 4:
			if(b.getAdresse() != null) {
				Adresse adresse = b.getAdresse();
				Ort ort = adresse.getOrt();	
				String ortString = ort.getOrt();
				int plz = ort.getPlz();
				returnWert = new String(plz + " " + ortString);
			}
			else {returnWert = "";}
		case 5:
			//TODO
			String s = Integer.toString(b.getBenutzerStatus());
			if(s.equals("1")) {
				returnWert = "Aktiv";
			}
			if(s.equals("2")) {
				returnWert = "Gesperrt";
			}
			if(s.equals("3")) {
				returnWert = "Gelöscht";
			}
			else {
				returnWert = "";
			}
			returnWert = s;
			break;
		}
		return returnWert;
	}
}
