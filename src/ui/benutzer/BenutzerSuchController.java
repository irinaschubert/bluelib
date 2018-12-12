package ui.benutzer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import dao.OrtDAO;
import dao.StatusDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Benutzer;
import domain.Buch;
import domain.EingeloggterMA;
import domain.Ort;
import domain.Status;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelBenutzer;
import models.TableModelBuch;
import services.BenutzerService;
import services.MedienhandlingService;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;
import ui.buch.BuchSuchView;

/**
 * Controller für die Benutzer-View der Ausleihe, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 06.11.2018
 * @author Irina
 * 
 */

public class BenutzerSuchController {	
	private BenutzerSuchView benutzerSuchView;
	private BenutzerService benutzerService;
	private List<Benutzer> benutzerL;
	private TableModelBenutzer tableModelBenutzer;
	private Benutzer benutzerSuchobjekt;

	public BenutzerSuchController(BenutzerSuchView view) {
		
		
		benutzerSuchView = view;
		benutzerService = new BenutzerService();
		//benutzerL = new ArrayList<>();
		benutzerSuchobjekt = new Benutzer();
		initialisieren();
		control();
		
		/*tableModelBenutzer = new TableModelBenutzer();
		tableModelBenutzer.setAndSortListe(benutzerL);
		view.getBenutzerTabelle().setModel(tableModelBenutzer);
		view.spaltenBreiteSetzen();
		
		initialisieren();
		control();*/
	}

	// Buttons
	private void control() {
		// Suchen
		ActionListener suchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerSuchenUndResultatAnzeigen();
				/*benutzerSuchobjekt = feldwertezuObjektSuchen();
				benutzerL = benutzerService.suchenBenutzer(benutzerSuchobjekt);
				tableModelBenutzer.setAndSortListe(benutzerL);*/
			}
		};
		benutzerSuchView.getSuchButton().addActionListener(suchenButtonActionListener);
	}
	
	private boolean inputValidierungBenutzer(boolean ruhig) {
		boolean keinInputFehler = true;
		if (benutzerSuchView.getPKSucheT().getText().isEmpty()) {
			if (ruhig != true) {
				JOptionPane.showMessageDialog(null, "Bitte einen Benutzer eingeben.");
			}
			return keinInputFehler = false;
		}
		try {
			Integer.parseInt(benutzerSuchView.getPKSucheT().getText());
		} catch (NumberFormatException e) {
			if (ruhig != true) {
				JOptionPane.showMessageDialog(null, "Ungültige ID");
			}
			keinInputFehler = false;
		}
		return keinInputFehler;
	}
	
	/**
	 * Anzeigen der Suchresultate in der Tabelle
	 */
	public void benutzerSuchenUndResultatAnzeigen() {
		if (inputValidierungBenutzer(false)) {
			benutzerSuchobjekt = feldwertezuObjektSuchen();
			benutzerL = benutzerService.suchenBenutzer(benutzerSuchobjekt);
			tableModelBenutzer.setAndSortListe(benutzerL);
			if (benutzerL.size() == 0) {
				JOptionPane.showMessageDialog(null, "Kein Treffer gefunden");
			}
		}
	}
	
	/**
	 * @return Benutzer mit den Feldwerten aus der Suche
	 */
	private Benutzer feldwertezuObjektSuchen() {
		Benutzer b = new Benutzer();
		if (!benutzerSuchView.getPKSucheT().getText().isEmpty()) {
			b.setId(Integer.parseInt(benutzerSuchView.getPKSucheT().getText()));
		}
		if (!benutzerSuchView.getNachnameSucheT().getText().isEmpty()) {
			b.setName(benutzerSuchView.getNachnameSucheT().getText());
		}
		if (!benutzerSuchView.getVornameSucheT().getText().isEmpty()) {
			b.setVorname(benutzerSuchView.getVornameSucheT().getText());
		}
		return b;
	}

	public void initialisieren() {
		// Suchfelder
		benutzerSuchView.getPKSucheL().setText("Benutzer-ID:");
		benutzerSuchView.getNachnameSucheL().setText("Nachname:");
		benutzerSuchView.getVornameSucheL().setText("Vorname:");
		TextComponentLimit.addTo(benutzerSuchView.getPKSucheT(), 15);
		TextComponentLimit.addTo(benutzerSuchView.getNachnameSucheT(), 30);
		TextComponentLimit.addTo(benutzerSuchView.getVornameSucheT(), 30);
		benutzerSuchView.getSuchButton().setText("Suchen");
		
		// Tabelle
		benutzerL = new ArrayList<>();
		tableModelBenutzer = new TableModelBenutzer();
		tableModelBenutzer.setAndSortListe(benutzerL);
		benutzerSuchView.getBenutzerTabelle().setModel(tableModelBenutzer);
		benutzerSuchView.spaltenBreiteSetzen();
	}
	
	public BenutzerSuchView getBenutzerSuchView() {
		return benutzerSuchView;
	}

	public TableModelBenutzer getTableModelBenutzer() {
		return tableModelBenutzer;
	}
}
