package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import dao.AusleiheDAO;
import domain.Ausleihe;
import domain.Buch;

/**
 * Dient zur Darstellung und zum Updaten der Ausleiheliste.
 * 
 * @version 1.0 22.11.2018
 * @author Irina
 * 
 */
@SuppressWarnings("serial")
public class TableModelAusleihe extends AbstractTableModel {

	// Definition der Spaltennamen
	private static final String[] COLUMN_NAMES = { "Barcode Buch", "Buchtitel", "Datum Ausleihe", "Notiz" };
	private List<Ausleihe> ausleihliste;

	public void setAndSortListe(List<Ausleihe> liste) {
		this.ausleihliste = liste;
		fireTableDataChanged();
	}

	public void ausleiheHinzufuegen(Ausleihe ausleihe) {
		ausleihliste.add(ausleihe);
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		if (ausleihliste.size() != 0) {
			return ausleihliste.size();
		} else {
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
		Ausleihe a = ausleihDAO.findById(ausleihliste.get(rowIndex).getId());
		Buch buch = (Buch) a.getMedium();
		Object returnWert = new Object();
		switch (columnIndex) {
		case 0:
			if (a.getMedium() != null) {
				int barcode = buch.getBarcodeNr();
				returnWert = barcode;
			} else {
				returnWert = "";
			}
			break;
		case 1:
			if (a.getMedium() != null) {
				String titel = buch.getTitel();
				returnWert = titel;
			} else {
				returnWert = "";
			}
			break;
		case 2:
			returnWert = a.getAusleiheDatum();
			break;
		case 3:
			if (buch.getBemerkung() != null) {
				returnWert = buch.getBemerkung();
			} else {
				returnWert = "";
			}
			break;
		}
		return returnWert;
	}
}
