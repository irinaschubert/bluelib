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
import domain.Benutzer;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelBenutzer;
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
	private List<Benutzer> benutzerL;
	private TableModelBenutzer tableModelBenutzer;
	private Benutzer benutzerSuchobjekt;

	public BenutzerController(BenutzerView view) {
		benutzerView = view;
		benutzerL = new ArrayList<>();
		tableModelBenutzer = new TableModelBenutzer();
		tableModelBenutzer.setAndSortListe(benutzerL);
		view.getBenutzerTabelle().setModel(tableModelBenutzer);
		view.spaltenBreiteSetzen();
		benutzerSuchobjekt = new Benutzer();

		initialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {

		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					benutzerSuchobjekt = feldwertezuObjektSuchen();
					benutzerL = normdatenService.sucheBenutzer(benutzerSuchobjekt);
					tableModelBenutzer.setAndSortListe(benutzerL);
				}

			}

		};

		// Zuweisen des Actionlisteners zum Suchen-Button
		benutzerView.getSuchButton().addActionListener(suchenButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Benutzer a = new Benutzer();
				if (inputValidierungSpeichern()) {
					a = feldwertezuObjektSpeichern();
					// Prüfung, ob ein neuer Benutzer erfasst wurde oder ein Benutzer aktialisiert wird
					if (benutzerView.getPKT().getText().isEmpty()) {

						nachAarbeitSpeichern(normdatenService.sichereBenutzer(a));

					} else {
						nachAarbeitSpeichern(normdatenService.aktualisiereBenutzer(a));
					}
				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		benutzerView.getButtonPanel().getButton1().addActionListener(sichernButtonActionListener);

		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (benutzerView.getBenutzerTabelle().getSelectedRow() != -1) {
					uebernehmen();
				}
			}

		};

		// Zuweisen des Actionlisteners zum Übernehmen-Button
		benutzerView.getButtonPanel().getButton2().addActionListener(uebernehmenButtonActionListener);

		ActionListener abbrechenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerView.schliessen();
			}

		};

		// Zuweisen des Actionlisteners zum Abbrechen-Button
		benutzerView.getButtonPanel().getButton3().addActionListener(abbrechenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		benutzerView.getBenutzerTabelle().addMouseListener(doppelKlick);

	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!benutzerView.getGeburtsDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getGeburtsDatumSucheT().getText())) {
				JOptionPane.showMessageDialog(null, "Üngültiges Geburtsdatum");
				benutzerView.getGeburtsDatumL().setText("");
				keinInputFehler = false;
			}
		}

		if (!benutzerView.getTodesDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getTodesDatumSucheT().getText())) {
				JOptionPane.showMessageDialog(null, "Üngültiges Todesdatum");
				benutzerView.getTodesDatumL().setText("");
				keinInputFehler = false;
			}
		}

		return keinInputFehler;

	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (benutzerView.getNachnameT().getText().isEmpty() || (benutzerView.getVornameT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!benutzerView.getGeburtsDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getGeburtsDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Üngültiges Geburtsdatum");
				benutzerView.getGeburtsDatumL().setText("");
				keinInputFehler = false;
			}
		}

		if (!benutzerView.getTodesDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getTodesDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Üngültiges Todesdatum");
				benutzerView.getTodesDatumL().setText("");
				keinInputFehler = false;
			}
		}

		return keinInputFehler;

	}

	private Benutzer feldwertezuObjektSpeichern() {
		Benutzer a = new Benutzer();
		if (!benutzerView.getPKT().getText().isEmpty()) {
			a.setId(Integer.parseInt(benutzerView.getPKT().getText()));
		}
		a.setName(benutzerView.getNachnameT().getText());
		a.setVorname(benutzerView.getVornameT().getText());
		if (!benutzerView.getGeburtsDatumT().getText().isEmpty()) {
			a.setGeburtsdatum(DateConverter.convertStringToJavaDate(benutzerView.getGeburtsDatumT().getText()));
		}

		if (!benutzerView.getTodesDatumT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(benutzerView.getTodesDatumT().getText())) {
				a.setTodesdatum(DateConverter.convertStringToJavaDate(benutzerView.getTodesDatumT().getText()));
			}
		}
		a.setGeloescht(benutzerView.getGeloeschtCbx().isSelected());
		return a;
	}

	private Benutzer feldwertezuObjektSuchen() {
		Benutzer a = new Benutzer();
		if (!benutzerView.getNachnameSucheT().getText().isEmpty()) {
			a.setName(benutzerView.getNachnameSucheT().getText());
		}

		if (!benutzerView.getVornameSucheT().getText().isEmpty()) {
			a.setVorname(benutzerView.getVornameSucheT().getText());
		}
		if (!benutzerView.getGeburtsDatumSucheT().getText().isEmpty()) {
			a.setGeburtsdatum(DateConverter.convertStringToJavaDate(benutzerView.getGeburtsDatumSucheT().getText()));
		}

		if (!benutzerView.getTodesDatumSucheT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(benutzerView.getTodesDatumSucheT().getText())) {
				a.setTodesdatum(DateConverter.convertStringToJavaDate(benutzerView.getTodesDatumSucheT().getText()));
			}
		}
		a.setGeloescht(benutzerView.getGeloeschtSucheCbx().isSelected());
		return a;
	}

	private void uebernehmen() {
		Benutzer benutzer = new Benutzer();
		benutzer = tableModelBenutzer.getGeklicktesObjekt(benutzerView.getBenutzerTabelle().getSelectedRow());

		benutzerView.getPKT().setText(Integer.toString(benutzer.getId()));
		benutzerView.getNachnameT().setText(benutzer.getName());
		benutzerView.getVornameT().setText(benutzer.getVorname());

		if (benutzer.getGeburtsdatum() != null) {
			benutzerView.getGeburtsDatumT().setText(DateConverter.convertJavaDateToString(benutzer.getGeburtsdatum()));
		}

		if (benutzer.getTodesdatum() != null) {
			benutzerView.getTodesDatumT().setText(DateConverter.convertJavaDateToString(benutzer.getTodesdatum()));
		}
		benutzerView.getGeloeschtCbx().setSelected(benutzer.getGeloescht());
	}

	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelBenutzer.setAndSortListe(normdatenService.sucheBenutzer(benutzerSuchobjekt));
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

		benutzerView.getPKL().setText("Nr:");
		benutzerView.getNachnameL().setText("Nachname:*");
		benutzerView.getVornameL().setText("Vorname:*");
		benutzerView.getGeburtsDatumL().setText("Geburtsdatum:");
		benutzerView.getTodesDatumL().setText("Todesdatum:");
		benutzerView.getGeloescht().setText("Löschvormerkung:");
		benutzerView.getNachnameSucheL().setText("Nachname:");
		benutzerView.getVornameSucheL().setText("Vorname:");
		benutzerView.getGeburtsDatumSucheL().setText("Geburtsdatum:");
		benutzerView.getTodesDatumSucheL().setText("Todesdatum:");
		benutzerView.getGeloeschtSucheL().setText("Löschvormerkung:");
		benutzerView.getSuchButton().setText("Suchen");
		benutzerView.getPKT().setEditable(false);
		benutzerView.getButtonPanel().getButton1().setText(ButtonNamen.SICHERN.getName());
		benutzerView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
		benutzerView.getButtonPanel().getButton2().setText(ButtonNamen.UEBERNEHMEN.getName());

	}
}
