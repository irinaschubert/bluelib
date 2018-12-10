package ui.Autor;

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
import domain.Autor;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelAutor;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;

/**
 * 
 * Controller für die Autoren-View, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 25.10.2018
 * @author Ueli
 *
 */

public class AutorController {
	private AutorView autorView;
	private NormdatenService normdatenService;
	private List<Autor> autorL;
	private TableModelAutor tableModelAutor;
	private Autor autorSuchobjekt;
	private HauptController hauptController;

	public AutorController(AutorView view, HauptController hauptController) {
		autorView = view;
		this.hauptController = hauptController;
		normdatenService = new NormdatenService();
		autorL = new ArrayList<>();
		tableModelAutor = new TableModelAutor();
		tableModelAutor.setAndSortListe(autorL);
		view.getAutorenTabelle().setModel(tableModelAutor);
		view.spaltenBreiteSetzen();
		autorSuchobjekt = new Autor();

		initialisieren();
		control();
	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {

		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					autorSuchobjekt = feldwertezuObjektSuchen();
					autorL = normdatenService.sucheAutor(autorSuchobjekt);
					tableModelAutor.setAndSortListe(autorL);
				}

			}

		};

		// Zuweisen des Actionlisteners zum Suchen-Button
		autorView.getSuchButton().addActionListener(suchenButtonActionListener);

		ActionListener neuButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				neuBearbeitenFelderLeeren();
				autorView.getNeuAendernL().setText("Neuerfassung");
			}

		};

		// Zuweisen des Actionlisteners zum Neu-Button
		autorView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Autor a = new Autor();
				if (inputValidierungSpeichern()) {
					a = feldwertezuObjektSpeichern();
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					if (autorView.getPKT().getText().isEmpty()) {

						nachAarbeitSpeichern(normdatenService.speichereAutor(a));

					} else {
						nachAarbeitSpeichern(normdatenService.aktualisiereAutor(a));
					}
				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		autorView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		ActionListener schliessenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};

		// Zuweisen des Actionlisteners zum Schliessen-Button
		autorView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					autorView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		autorView.getAutorenTabelle().addMouseListener(doppelKlick);

	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!autorView.getGeburtsDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(autorView.getGeburtsDatumSucheT().getText())) {
				autorView.getGeburtsDatumSucheT().setText("");
				keinInputFehler = false;
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
			}
		}

		return keinInputFehler;

	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (autorView.getNachnameT().getText().isEmpty() || (autorView.getVornameT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!autorView.getGeburtsDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(autorView.getGeburtsDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
				autorView.getGeburtsDatumT().setText("");
				keinInputFehler = false;
			}
		}

		if (!autorView.getTodesDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(autorView.getTodesDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Todesdatum");
				autorView.getTodesDatumT().setText("");
				keinInputFehler = false;
			}
		}

		return keinInputFehler;

	}

	private Autor feldwertezuObjektSpeichern() {
		Autor a = new Autor();
		if (!autorView.getPKT().getText().isEmpty()) {
			a.setId(Integer.parseInt(autorView.getPKT().getText()));
		}
		a.setName(autorView.getNachnameT().getText());
		a.setVorname(autorView.getVornameT().getText());
		if (!autorView.getGeburtsDatumT().getText().isEmpty()) {
			a.setGeburtsdatum(DateConverter.convertStringToJavaDate(autorView.getGeburtsDatumT().getText()));
		}

		if (!autorView.getTodesDatumT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(autorView.getTodesDatumT().getText())) {
				a.setTodesdatum(DateConverter.convertStringToJavaDate(autorView.getTodesDatumT().getText()));
			}
		}
		a.setGeloescht(autorView.getGeloeschtCbx().isSelected());
		return a;
	}

	private Autor feldwertezuObjektSuchen() {
		Autor a = new Autor();
		if (!autorView.getNachnameSucheT().getText().isEmpty()) {
			a.setName(autorView.getNachnameSucheT().getText());
		}

		if (!autorView.getVornameSucheT().getText().isEmpty()) {
			a.setVorname(autorView.getVornameSucheT().getText());
		}
		if (!autorView.getGeburtsDatumSucheT().getText().isEmpty()) {
			a.setGeburtsdatum(DateConverter.convertStringToJavaDate(autorView.getGeburtsDatumSucheT().getText()));
		}

		a.setGeloescht(autorView.getGeloeschtSucheCbx().isSelected());
		return a;
	}

	private void uebernehmen() {
		Autor autor = new Autor();
		neuBearbeitenFelderLeeren();
		autor = tableModelAutor.getGeklicktesObjekt(autorView.getAutorenTabelle().getSelectedRow());

		autorView.getPKT().setText(Integer.toString(autor.getId()));
		autorView.getNachnameT().setText(autor.getName());
		autorView.getVornameT().setText(autor.getVorname());

		if (autor.getGeburtsdatum() != null) {
			autorView.getGeburtsDatumT().setText(DateConverter.convertJavaDateToString(autor.getGeburtsdatum()));
		}

		if (autor.getTodesdatum() != null) {
			autorView.getTodesDatumT().setText(DateConverter.convertJavaDateToString(autor.getTodesdatum()));
		}
		autorView.getGeloeschtCbx().setSelected(autor.getGeloescht());
	}

	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelAutor.setAndSortListe(normdatenService.sucheAutor(autorSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		neuBearbeitenFelderLeeren();
		autorView.getNeuAendernL().setText("");

	}

	private void neuBearbeitenFelderLeeren() {

		// Felder leeren
		for (JComponent t : autorView.getComponentsNeuBearbeiten().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}

		}
	}
	
	private void suchFelderLeeren() {

		// Felder leeren
		for (JComponent t : autorView.getComponentsSuche().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}

		}
	}

	public void initialisieren() {

		autorView.getPKL().setText("Nr:");
		autorView.getNachnameL().setText("Name:*");
		autorView.getVornameL().setText("Vorname:*");
		autorView.getGeburtsDatumL().setText("Geburtsdatum:");
		autorView.getTodesDatumL().setText("Todesdatum:");
		autorView.getGeloescht().setText("Löschvormerkung:");
		autorView.getNachnameSucheL().setText("Name:");
		autorView.getVornameSucheL().setText("Vorname:");
		autorView.getGeburtsDatumSucheL().setText("Geburtsdatum:");
		autorView.getGeloeschtSucheL().setText("Gelöschte Autoren:");
		
		TextComponentLimit.addTo(autorView.getNachnameT(), 50);
		TextComponentLimit.addTo(autorView.getVornameT(), 50);
		TextComponentLimit.addTo(autorView.getGeburtsDatumT(), 10);
		TextComponentLimit.addTo(autorView.getTodesDatumT(), 10);
		TextComponentLimit.addTo(autorView.getNachnameSucheT(), 50);
		TextComponentLimit.addTo(autorView.getVornameSucheT(), 50);
		TextComponentLimit.addTo(autorView.getGeburtsDatumSucheT(), 10);
		
		autorView.getSuchButton().setText("Suchen");
		autorView.getPKT().setEditable(false);
		autorView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		autorView.getButtonPanel().getButton2().setVisible(false);
		autorView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		autorView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
