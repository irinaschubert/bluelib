package ui.MA;

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
import domain.Mitarbeiter;
import hilfsklassen.ButtonNamen;
import models.TableModelMitarbeiter;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;

/**
 * 
 * Controller für die Mitarbeiter-View, der die Logik und die Benutzeraktionen
 * der View steuert und der View die Models übergibt
 * 
 * @version 1.0 15.11.2018
 * @author Mike
 *
 */

public class MitarbeiterController {
	private MitarbeiterView mitarbeiterView;
	private NormdatenService normdatenService;
	private List<Mitarbeiter> mitarbeiterL;
	private TableModelMitarbeiter tableModelMitarbeiter;
	private Mitarbeiter mitarbeiterSuchobjekt;
	private HauptController hauptController;

	public MitarbeiterController(MitarbeiterView view, HauptController hauptController) {
		mitarbeiterView = view;
		this.hauptController = hauptController;
		normdatenService = new NormdatenService();
		mitarbeiterL = new ArrayList<>();
		tableModelMitarbeiter = new TableModelMitarbeiter();
		tableModelMitarbeiter.setAndSortListe(mitarbeiterL);
		view.getMitarbeiterTabelle().setModel(tableModelMitarbeiter);
		view.spaltenBreiteSetzen();
		mitarbeiterSuchobjekt = new Mitarbeiter();

		initialisieren();
		control();

	}

	// Definierten des Listeners für die Button-Klicks
	private void control() {

		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					mitarbeiterSuchobjekt = feldwertezuObjektSuchen();
					mitarbeiterL = normdatenService.suchenMitarbeiter(mitarbeiterSuchobjekt);
					tableModelMitarbeiter.setAndSortListe(mitarbeiterL);
				}

			}

		};

		// Zuweisen des Actionlisteners zum Suchen-Button
		mitarbeiterView.getSuchButton().addActionListener(suchenButtonActionListener);

		ActionListener neuButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				suchFelderLeeren();
				mitarbeiterView.getNeuAendernL().setText("Neuerfassung");
			}

		};

		// Zuweisen des Actionlisteners zum Neu-Button
		mitarbeiterView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Mitarbeiter m = new Mitarbeiter();
				if (inputValidierungSpeichern()) {
					m = feldwertezuObjektSpeichern();
					// Prüfung, ob ein neuer Mitarbeiter erfasst wurde oder ein Mitarbeiter
					// aktialisiert wird
					if (mitarbeiterView.getPKT().getText().isEmpty()) {
						nachAarbeitSpeichern(normdatenService.speichernMitarbeiter(m));
					} else {
						nachAarbeitSpeichern(normdatenService.aktualisierenMitarbeiter(m));
					}
				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		mitarbeiterView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		ActionListener schliessenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}

		};

		// Zuweisen des Actionlisteners zum Schliessen-Button
		mitarbeiterView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					mitarbeiterView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		mitarbeiterView.getMitarbeiterTabelle().addMouseListener(doppelKlick);

	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;
		// To do
		return keinInputFehler;
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (mitarbeiterView.getBenutzernameT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}
		return keinInputFehler;
	}

	private Mitarbeiter feldwertezuObjektSpeichern() {
		Mitarbeiter m = new Mitarbeiter();
		if (!mitarbeiterView.getPKT().getText().isEmpty()) {
			m.setId(Integer.parseInt(mitarbeiterView.getPKT().getText()));
		}
		m.setBenutzername(mitarbeiterView.getBenutzernameT().getText());
		m.setAktiv(mitarbeiterView.getAktivCbx().isSelected());
		return m;
	}

	private Mitarbeiter feldwertezuObjektSuchen() {
		Mitarbeiter m = new Mitarbeiter();
		if (!mitarbeiterView.getMitarbeiterSucheT().getText().isEmpty()) {
			m.setBenutzername(mitarbeiterView.getMitarbeiterSucheT().getText());
		}
		m.setAdmin(mitarbeiterView.getAktivSucheCbx().isSelected());
		return m;
	}

	private void uebernehmen() {
		Mitarbeiter m = new Mitarbeiter();
		m = tableModelMitarbeiter.getGeklicktesObjekt(mitarbeiterView.getMitarbeiterTabelle().getSelectedRow());

		mitarbeiterView.getPKT().setText(Integer.toString(m.getId()));
		mitarbeiterView.getBenutzernameT().setText(m.getBenutzername());
		mitarbeiterView.getAktivCbx().setSelected(m.isAktiv());
	}

	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelMitarbeiter.setAndSortListe(normdatenService.suchenMitarbeiter(mitarbeiterSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		suchFelderLeeren();
		mitarbeiterView.getNeuAendernL().setText("");

	}

	private void suchFelderLeeren() {
		// Felder leeren
		for (JComponent t : mitarbeiterView.getComponentsNeuBearbeiten().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}
		}
	}

	public void initialisieren() {
		mitarbeiterView.getPKL().setText("Nr:");
		mitarbeiterView.getNameL().setText("Name:");
		mitarbeiterView.getVornameL().setText("Vorname:");
		mitarbeiterView.getAktiv().setText("Aktive:");
		//
		mitarbeiterView.getBenutzernameL().setText("Benutzername:");
		mitarbeiterView.getPasswortL().setText("Passwort:");
		mitarbeiterView.getAdminL().setText("AdministratorIn:");
		//
		mitarbeiterView.getNameSucheL().setText("Name:");
		mitarbeiterView.getVornameSucheL().setText("Vorname:");
		mitarbeiterView.getBenutzernameSucheL().setText("Benutzername:");
		mitarbeiterView.getAktivSucheL().setText("inkl. inaktive:");
		mitarbeiterView.getSuchButton().setText("Suchen");
		mitarbeiterView.getPKT().setEditable(false);
		mitarbeiterView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		mitarbeiterView.getButtonPanel().getButton2().setVisible(false);
		mitarbeiterView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		mitarbeiterView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
