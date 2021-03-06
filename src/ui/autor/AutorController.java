package ui.autor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
 * Controller f�r die Autoren-View, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models �bergibt
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

	private void control() {

		autorView.getSuchButton().addActionListener(suchenButtonActionListener());
		autorView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener());
		autorView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener());
		autorView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		autorView.getAutorenTabelle().addMouseListener(doppelKlick());

	}

	private ActionListener suchenButtonActionListener() {

		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					sucheAusfuehren();
				}

			}

		};

		return suchenButtonActionListener;
	}

	private ActionListener neuButtonActionListener() {

		ActionListener neuButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				neuBearbeitenFelderLeeren();
				autorView.getNeuAendernL().setText("Neuerfassung");
			}

		};
		return neuButtonActionListener;
	}

	private ActionListener sichernButtonActionListener() {

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Autor a = new Autor();
				if (inputValidierungSpeichern()) {
					a = feldwertezuObjektSpeichern();
					// Pr�fung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					if (autorView.getPKT().getText().isEmpty()) {

						nachAarbeitSpeichern(normdatenService.speichernAutor(a));

					} else {
						nachAarbeitSpeichern(normdatenService.aktualisierenAutor(a));
					}
				}

			}

		};
		return sichernButtonActionListener;
	}

	private ActionListener schliessenButtonActionListener() {
		ActionListener schliessenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};

		return schliessenButtonActionListener;
	}

	private MouseListener doppelKlick() {
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					autorView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};

		return doppelKlick;

	}

	/**
	 * 
	 * @return Input der Suchfelder valide: true, Input der Suchfelder nicht valide
	 *         = false
	 */
	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!autorView.getGeburtsDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(autorView.getGeburtsDatumSucheT().getText())) {
				autorView.getGeburtsDatumSucheT().setText("");
				keinInputFehler = false;
				JOptionPane.showMessageDialog(null, "Ung�ltiges Geburtsdatum");
			}
		}

		return keinInputFehler;

	}

	/**
	 * 
	 * @return Input der Neu/Bearbeiten-Felder valide = true, nicht valide = false
	 */
	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (autorView.getNachnameT().getText().isEmpty() || (autorView.getVornameT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!autorView.getGeburtsDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(autorView.getGeburtsDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ung�ltiges Geburtsdatum");
				autorView.getGeburtsDatumT().setText("");
				keinInputFehler = false;
			}
		}

		if (!autorView.getTodesDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(autorView.getTodesDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ung�ltiges Todesdatum");
				autorView.getTodesDatumT().setText("");
				keinInputFehler = false;
			}
		}

		return keinInputFehler;

	}

	/**
	 * 
	 * @return Autor-Objekt, gefuellt mit den Werten aus den Feldwerten in
	 *         Neu/Bearbeiten
	 */
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

	/**
	 * 
	 * @return Autor-Objekt, gefuellt mit den Feldwerten der Suche
	 */
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

//		a.setGeloescht(autorView.getGeloeschtSucheCbx().isSelected());
		return a;
	}

	/**
	 * Uebernahme der Werte des selektierten Objektes in der Tabelle zu den Feldern
	 * in Neu/Bearbeiten
	 */
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

	/**
	 * Sucht die Autoren. Falls das Flag zur Inkludierung der gel�schten Autoren
	 * gesetzt ist, muessen zwei Suchen ausgef�hrt werden: 1x geloescht = false und
	 * 1x geloescht = true. Die Resultate der 2. Suche muessen iterativ dem
	 * Tablemodel uebergeben wrden.
	 */
	private void sucheAusfuehren() {

		autorSuchobjekt = feldwertezuObjektSuchen();
		autorL = normdatenService.suchenAutor(autorSuchobjekt);
		tableModelAutor.setAndSortListe(autorL);
		if (autorView.getGeloeschtSucheCbx().isSelected()) {
			autorSuchobjekt.setGeloescht(true);
			autorL = normdatenService.suchenAutor(autorSuchobjekt);
			for (Autor a : autorL) {
				tableModelAutor.autorHinzufuegen(a);
			}
			autorSuchobjekt.setGeloescht(false);
		}

	}

	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());

			sucheAusfuehren();

		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		neuBearbeitenFelderLeeren();
		suchFelderLeeren();
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
		autorView.getGeloescht().setText("L�schvormerkung:");
		autorView.getNachnameSucheL().setText("Name:");
		autorView.getVornameSucheL().setText("Vorname:");
		autorView.getGeburtsDatumSucheL().setText("Geburtsdatum:");
		autorView.getGeloeschtSucheL().setText("inkl. gel�schte:");

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
