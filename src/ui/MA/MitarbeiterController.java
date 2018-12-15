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
import javax.swing.SwingUtilities;

import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import domain.Benutzer;
import domain.Mitarbeiter;
import hilfsklassen.ButtonNamen;
import models.TableModelMitarbeiter;
import services.BenutzerService;
import services.MitarbeiterService;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;
import ui.MA.MitarbeiterBenutzerDialog;
import ui.MA.MitarbeiterBenutzerDialogController;
import ui.MA.MitarbeiterController;

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
	private BenutzerService benutzerService;
	private MitarbeiterService mitarbeiterService;
	private List<Mitarbeiter> mitarbeiterL;
	private TableModelMitarbeiter tableModelMitarbeiter;
	private Mitarbeiter mitarbeiterSuchobjekt;
	private HauptController hauptController;
	private MitarbeiterController mitarbeiterController;

	public MitarbeiterController(MitarbeiterView view, HauptController hauptController) {
		mitarbeiterView = view;
		this.hauptController = hauptController;
		mitarbeiterController = this;
		normdatenService = new NormdatenService();
		mitarbeiterService = new MitarbeiterService();
		benutzerService = new BenutzerService();
		mitarbeiterL = new ArrayList<>();
		tableModelMitarbeiter = new TableModelMitarbeiter();
		tableModelMitarbeiter.setAndSortListe(mitarbeiterL);
		view.getMitarbeiterTabelle().setModel(tableModelMitarbeiter);
		view.spaltenBreiteSetzen();
		mitarbeiterSuchobjekt = new Mitarbeiter();
		mitarbeiterSuchobjekt.setAktiv(true);
		initialisieren();
		control();
	}

	// Button Klick ActionListener
	private void control() {

		// Suche Button
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
		mitarbeiterView.getSuchButton().addActionListener(suchenButtonActionListener);

		ActionListener neuButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						MitarbeiterBenutzerDialog mitarbeiterBenutzerDialog = new MitarbeiterBenutzerDialog(
								"Benutzer suchen");
						new MitarbeiterBenutzerDialogController(mitarbeiterController, mitarbeiterBenutzerDialog);
						mitarbeiterBenutzerDialog.setModal(true);
						mitarbeiterBenutzerDialog.setVisible(true);
					}
				});
			}

		};
		mitarbeiterView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mitarbeiter m = new Mitarbeiter();
				if (inputValidierungSpeichern()) {
					m = feldwertezuObjektSpeichern();
					if (mitarbeiterView.getNeuAendernL().getText().equals("Bearbeiten")) {
						nachAarbeitSpeichern(mitarbeiterService.aktualisierenMitarbeiter(m));
					} else if (mitarbeiterView.getNeuAendernL().getText().equals("Neuerfassung")) {
						nachAarbeitSpeichern(mitarbeiterService.speichernMitarbeiter(m));
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
		if (mitarbeiterView.getBenutzernameT().getText().isEmpty()
				|| mitarbeiterView.getPasswortT().getText().isEmpty()) {
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
		m.setName(mitarbeiterView.getNameT().getText());
		m.setVorname(mitarbeiterView.getVornameT().getText());
		m.setBenutzername(mitarbeiterView.getBenutzernameT().getText());
		m.setAktiv(mitarbeiterView.getAktivCbx().isSelected());
		m.setAdmin(mitarbeiterView.getAdminCbx().isSelected());
		//Muss noch gehased werden, dann kann Sysout auch gelöst werden
		m.setPasswort(mitarbeiterView.getPasswortT().getText());
		System.out.println("gePW Hashed: " + m.getPasswort());
		return m;
	}

	private Mitarbeiter feldwertezuObjektSuchen() {
		Mitarbeiter m = new Mitarbeiter();
		if (!mitarbeiterView.getMitarbeiterSucheT().getText().isEmpty()) {
			m.setBenutzername(mitarbeiterView.getMitarbeiterSucheT().getText());
		}
		if (!mitarbeiterView.getNameSucheT().getText().isEmpty()) {
			m.setName(mitarbeiterView.getNameSucheT().getText());
		}

		if (!mitarbeiterView.getVornameSucheT().getText().isEmpty()) {
			m.setVorname(mitarbeiterView.getVornameSucheT().getText());
		}
		m.setAktiv(mitarbeiterView.getAktivSucheCbx().isSelected());
		m.setAdmin(mitarbeiterView.getAdminSucheCbx().isSelected());
		return m;
	}

	private void uebernehmen() {
		Mitarbeiter m = new Mitarbeiter();
		m = tableModelMitarbeiter.getGeklicktesObjekt(mitarbeiterView.getMitarbeiterTabelle().getSelectedRow());
		mitarbeiterView.getPKT().setText(Integer.toString(m.getId()));
		mitarbeiterView.getBenutzernameT().setText(m.getBenutzername());
		mitarbeiterView.getNameT().setText(m.getName());
		mitarbeiterView.getVornameT().setText(m.getVorname());
		mitarbeiterView.getAktivCbx().setSelected(m.isAktiv());
		mitarbeiterView.getAdminCbx().setSelected(m.isAdmin());
		//löschen
		// mitarbeiterView.getMAIDT().setText(m.getMAIDT());
	}

	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelMitarbeiter.setAndSortListe(normdatenService.suchenMitarbeiter(mitarbeiterSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		neuBearbeitenFelderLeeren();
	}

	void pruefenUndUebernehmenBenutzerMitId(int id) {
		Benutzer benutzer = new Benutzer();
		Mitarbeiter ma = new Mitarbeiter();
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		MitarbeiterDAO maDAO = new MitarbeiterDAO();

		try {
			benutzer = benutzerDAO.findById(id);
			mitarbeiterView.getPKT().setText(Integer.toString(benutzer.getId()));
			mitarbeiterView.getNameT().setText(benutzer.getName());
			mitarbeiterView.getVornameT().setText(benutzer.getVorname());
			mitarbeiterView.getAktivCbx().setSelected(true);
			mitarbeiterView.getNeuAendernL().setText("Neuerfassung");

			if (benutzer.getId() <= 0) {
				mitarbeiterView.getPKT().setText("");
				JOptionPane.showMessageDialog(null, "Ungültige ID.");
			}

		} catch (NullPointerException npe) {
			mitarbeiterView.getPKT().setText("");
			JOptionPane.showMessageDialog(null, "Kein Benutzer mit dieser ID vorhanden.");
		} catch (NumberFormatException e) {
			mitarbeiterView.getPKT().setText("");
			JOptionPane.showMessageDialog(null, "Ungültige ID.");
		}

		try {
			ma = maDAO.findById(id);
			mitarbeiterView.getBenutzernameT().setText(ma.getBenutzername());
			mitarbeiterView.getMAIDT().setText(Integer.toString(ma.getMAId()));
			mitarbeiterView.getNeuAendernL().setText("Bearbeiten");
		} catch (NullPointerException npe) {
			mitarbeiterView.getBenutzernameT().setText("");
		} catch (NumberFormatException e) {
			mitarbeiterView.getBenutzernameT().setText("");
		}
	}

	// Bearbeiten Felder leeren
	private void neuBearbeitenFelderLeeren() {
		mitarbeiterView.getPKT().setText("");
		mitarbeiterView.getBenutzernameT().setText("");
		mitarbeiterView.getNameT().setText("");
		mitarbeiterView.getVornameT().setText("");
		mitarbeiterView.getPasswortT().setText("");
		mitarbeiterView.getAktivCbx().setSelected(true);
		mitarbeiterView.getAdminCbx().setSelected(false);
		mitarbeiterView.getNeuAendernL().setText("");
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
		mitarbeiterView.getAktivSucheL().setText("Aktiv:");
		//mitarbeiterView.getAdminSucheL().setText("AdministratorIn:");
		mitarbeiterView.getAktivSucheCbx().setSelected(true);
		mitarbeiterView.getSuchButton().setText("Suchen");
		mitarbeiterView.getPKT().setEditable(false);
		mitarbeiterView.getNameT().setEditable(false);
		mitarbeiterView.getVornameT().setEditable(false);
		mitarbeiterView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		mitarbeiterView.getButtonPanel().getButton2().setVisible(false);
		mitarbeiterView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		mitarbeiterView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
