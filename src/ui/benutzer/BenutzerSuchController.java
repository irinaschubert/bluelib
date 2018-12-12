package ui.benutzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import domain.Benutzer;
import hilfsklassen.TextComponentLimit;
import models.TableModelBenutzer;
import services.BenutzerService;
import services.Verifikation;

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
		benutzerSuchobjekt = new Benutzer();
		initialisieren();
		control();
	}

	// Suchbutton
	private void control() {
		benutzerSuchView.getPKSucheT().addKeyListener(enterKeyAdapter());
		
		ActionListener suchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerSuchenUndResultatAnzeigen();
			}
		};
		benutzerSuchView.getSuchButton().addActionListener(suchenButtonActionListener);
	}
	
	private KeyAdapter enterKeyAdapter() {
		KeyAdapter enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					benutzerSuchenUndResultatAnzeigen();
				}
			}
		};
		return enterKeyListener;
	}
	
	private boolean inputValidierungSuche() {
		boolean keinInputFehler = true;

		if (!benutzerSuchView.getPKSucheT().getText().isEmpty()) {
			Verifikation v = benutzerService.istBenutzerID(benutzerSuchView.getPKSucheT().getText());
			if (!v.isAktionErfolgreich()) {
				JOptionPane.showMessageDialog(null, v.getNachricht());
				benutzerSuchView.getPKSucheT().setText("");
				keinInputFehler = false;
			}
		}
		return keinInputFehler;
	}
	
	/**
	 * Anzeigen der Suchresultate in der Tabelle
	 */
	public void benutzerSuchenUndResultatAnzeigen() {		
		if (inputValidierungSuche()) {
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
