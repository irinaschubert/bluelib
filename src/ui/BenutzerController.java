package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.Adresse;
import domain.Benutzer;
import domain.Ort;
import domain.Status;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelBenutzer;
import services.BenutzerService;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die Benutzer-View, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 06.11.2018
 * @author irina
 *
 */

public class BenutzerController {
	private BenutzerView benutzerView;
	private BenutzerService benutzerService;
	private List<Benutzer> benutzerL;
	private TableModelBenutzer tableModelBenutzer;
	private Benutzer benutzerSuchobjekt;

	public BenutzerController(BenutzerView view) {
		benutzerView = view;
		benutzerService = new BenutzerService();
		benutzerL = new ArrayList<>();
		tableModelBenutzer = new TableModelBenutzer();
//		benutzerL = benutzerService.allebenutzer();
		tableModelBenutzer.setAndSortListe(benutzerL);
		view.getBenutzerTabelle().setModel(tableModelBenutzer);
		view.spaltenBreiteSetzen();
		benutzerSuchobjekt = new Benutzer();

		initialisieren();
		control();
	}

	// Buttons
	private void control() {

		// Suchen
		ActionListener suchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerSuchobjekt = feldwertezuObjektSuchen();
				benutzerL = benutzerService.sucheBenutzer(benutzerSuchobjekt);
				tableModelBenutzer.setAndSortListe(benutzerL);
			}
		};
		benutzerView.getSuchButton().addActionListener(suchenButtonActionListener);

		// Speichern
		ActionListener sichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Benutzer b = new Benutzer();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					if (benutzerView.getPKT().getText().isEmpty()) {
						nachArbeitSpeichern(benutzerService.sichereBenutzer(b));
					} else {
						nachArbeitSpeichern(benutzerService.aktualisiereBenutzer(b));
					}
				}
			}
		};
		benutzerView.getButtonPanel().getButton1().addActionListener(sichernButtonActionListener);

		// Uebernehmen
		ActionListener uebernehmenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (benutzerView.getBenutzerTabelle().getSelectedRow() != -1) {
					uebernehmen();
				}
			}
		};
		benutzerView.getButtonPanel().getButton2().addActionListener(uebernehmenButtonActionListener);

		// Abbrechen
		ActionListener abbrechenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerView.schliessen();
			}
		};
		benutzerView.getButtonPanel().getButton3().addActionListener(abbrechenButtonActionListener);

		// Doppelklick = Uebernehmen
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
				}
			}
		};
		benutzerView.getBenutzerTabelle().addMouseListener(doppelKlick);
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (benutzerView.getNachnameT().getText().isEmpty() || (benutzerView.getVornameT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!benutzerView.getGeburtsdatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getGeburtsdatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
				benutzerView.getGeburtsdatumL().setText("");
				keinInputFehler = false;
			}
		}
		return keinInputFehler;
	}

	private Benutzer feldwertezuObjektSpeichern() {
		Benutzer b = new Benutzer();
		if (!benutzerView.getPKT().getText().isEmpty()) {
			b.setId(Integer.parseInt(benutzerView.getPKT().getText()));
		}
		b.setName(benutzerView.getNachnameT().getText());
		b.setVorname(benutzerView.getVornameT().getText());
		
		if (!benutzerView.getStrasseNrT().getText().isEmpty() && !benutzerView.getPlzT().getText().isEmpty() && !benutzerView.getOrtT().getText().isEmpty()) {
			String strasse = benutzerView.getStrasseNrT().getText();
			int plzInt = Integer.parseInt(benutzerView.getPlzT().getText());
			String ortString = benutzerView.getOrtT().getText();
			Ort ort = new Ort(plzInt, ortString);
			Adresse adresse = new Adresse(strasse, ort);
			b.setAdresse(adresse);
		}
		
		if (!benutzerView.getGeburtsdatumT().getText().isEmpty()) {
			b.setGeburtsdatum(DateConverter.convertStringToJavaDate(benutzerView.getGeburtsdatumT().getText()));
		}

		if (!benutzerView.getTelT().getText().isEmpty()) {
			b.setTelefon(benutzerView.getTelT().getText());
		}
		if (!benutzerView.getMailT().getText().isEmpty()) {
			b.setEmail(benutzerView.getMailT().getText());
		}
		if (!benutzerView.getBemerkungT().getText().isEmpty()) {
			b.setBemerkung(benutzerView.getBemerkungT().getText());
		}
		//b.setAnrede(benutzerView.getAnredeCbx().getSelectedItem().toString());
		//b.setStatus(benutzerView.getStatusCbx().getSelectedItem().toString());
		b.setMitarbeiter(benutzerView.getMitarbeiterCbx().isSelected());
		return b;
	}

	private Benutzer feldwertezuObjektSuchen() {
		Benutzer b = new Benutzer();
		if (!benutzerView.getNachnameSucheT().getText().isEmpty()) {
			b.setName(benutzerView.getNachnameSucheT().getText());
		}

		if (!benutzerView.getVornameSucheT().getText().isEmpty()) {
			b.setVorname(benutzerView.getVornameSucheT().getText());
		}
		
		b.setMitarbeiter(benutzerView.getMitarbeiterSucheCbx().isSelected());
		return b;
	}

	private void uebernehmen() {
		Benutzer benutzer = new Benutzer();
		benutzer = tableModelBenutzer.getGeklicktesObjekt(benutzerView.getBenutzerTabelle().getSelectedRow());

		benutzerView.getPKT().setText(Integer.toString(benutzer.getId()));
		benutzerView.getNachnameT().setText(benutzer.getName());
		benutzerView.getVornameT().setText(benutzer.getVorname());

		if (benutzer.getGeburtsdatum() != null) {
			benutzerView.getGeburtsdatumT().setText(DateConverter.convertJavaDateToString(benutzer.getGeburtsdatum()));
		}
		benutzerView.getMitarbeiterCbx().setSelected(benutzer.getMitarbeiter());
	}

	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelBenutzer.setAndSortListe(benutzerService.sucheBenutzer(benutzerSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}

		// Felder leeren
		for (JComponent t : benutzerView.getComponents().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}
		}

	}

	public void initialisieren() {

		benutzerView.getPKL().setText("Benutzer-ID:");
		benutzerView.getNachnameL().setText("Nachname:*");
		benutzerView.getVornameL().setText("Vorname:*");
		benutzerView.getStrasseNrL().setText("Strasse/Nr.:");
		benutzerView.getPlzL().setText("PLZ:");
		benutzerView.getOrtL().setText("Ort:");
		benutzerView.getGeburtsdatumL().setText("Geburtsdatum:");
		benutzerView.getTelL().setText("Telefonnummer:");
		benutzerView.getMailL().setText("E-Mailadresse:");
		benutzerView.getStatusL().setText("Status:");
		benutzerView.getMitarbeiterL().setText("MA:");
		benutzerView.getAnredeL().setText("Anrede:");
		benutzerView.getErfasstAmL().setText("Erfasst am:");
		benutzerView.getErfasstVonL().setText("Erfasst von:");
		
		benutzerView.getPKSucheL().setText("Benutzer-ID:");
		benutzerView.getNachnameSucheL().setText("Nachname:");
		benutzerView.getVornameSucheL().setText("Vorname:");
		benutzerView.getStrasseNrSucheL().setText("Strasse/Nr.:");
		benutzerView.getPlzSucheL().setText("PLZ:");
		benutzerView.getOrtSucheL().setText("Ort:");
		benutzerView.getMitarbeiterSucheL().setText("MA:");
		benutzerView.getStatusSucheL().setText("Status:");
		
		benutzerView.getSuchButton().setText("Suchen");
		benutzerView.getPKT().setEditable(false);
		benutzerView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		benutzerView.getButtonPanel().getButton2().setVisible(false);
		benutzerView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		benutzerView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
