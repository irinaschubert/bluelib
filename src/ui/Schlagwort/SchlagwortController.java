package ui.Schlagwort;

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
import domain.Schlagwort;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import models.TableModelSchlagwort;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;

/**
 * 
 * Controller für die Schlagworten-View, der die Logik und die Benutzeraktionen
 * der View steuert und der View die Models übergibt
 * 
 * @version 1.0 15.11.2018
 * @author Mike
 *
 */

public class SchlagwortController {
	private SchlagwortView schlagwortView;
	private NormdatenService normdatenService;
	private List<Schlagwort> schlagwortL;
	private TableModelSchlagwort tableModelSchlagwort;
	private Schlagwort schlagwortSuchobjekt;
	private HauptController hauptController;

	public SchlagwortController(SchlagwortView view, HauptController hauptController) {
		schlagwortView = view;
		this.hauptController = hauptController;
		normdatenService = new NormdatenService();
		schlagwortL = new ArrayList<>();
		tableModelSchlagwort = new TableModelSchlagwort();
		tableModelSchlagwort.setAndSortListe(schlagwortL);
		view.getSchlagwortTabelle().setModel(tableModelSchlagwort);
		view.spaltenBreiteSetzen();
		schlagwortSuchobjekt = new Schlagwort();

		initialisieren();
		control();

	}

	// Definierten des Listeners für die Button-Klicks
	private void control() {

		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					sucheAusfuehren();
				}

			}

		};

		// Zuweisen des Actionlisteners zum Suchen-Button
		schlagwortView.getSuchButton().addActionListener(suchenButtonActionListener);

		ActionListener neuButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				suchFelderLeeren();
				schlagwortView.getNeuAendernL().setText("Neuerfassung");
			}

		};

		// Zuweisen des Actionlisteners zum Neu-Button
		schlagwortView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Schlagwort s = new Schlagwort();
				if (inputValidierungSpeichern()) {
					s = feldwertezuObjektSpeichern();
					// Prüfung, ob ein neuer Schlagwort erfasst wurde oder ein Schlagwort
					// aktialisiert wird
					if (schlagwortView.getPKT().getText().isEmpty()) {
						nachAarbeitSpeichern(normdatenService.speichernSchlagwort(s));
					} else {
						nachAarbeitSpeichern(normdatenService.aktualisierenSchlagwort(s));
					}
				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		schlagwortView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		ActionListener schliessenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}

		};

		// Zuweisen des Actionlisteners zum Schliessen-Button
		schlagwortView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					schlagwortView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		schlagwortView.getSchlagwortTabelle().addMouseListener(doppelKlick);

	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;
		// To do
		return keinInputFehler;
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (schlagwortView.getSchlagwortT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		return keinInputFehler;

	}

	private Schlagwort feldwertezuObjektSpeichern() {
		Schlagwort s = new Schlagwort();
		if (!schlagwortView.getPKT().getText().isEmpty()) {
			s.setId(Integer.parseInt(schlagwortView.getPKT().getText()));
		}
		s.setSchlagwort(schlagwortView.getSchlagwortT().getText());
		s.setGeloescht(schlagwortView.getGeloeschtCbx().isSelected());
		return s;
	}

	private Schlagwort feldwertezuObjektSuchen() {
		Schlagwort s = new Schlagwort();
		if (!schlagwortView.getSchlagwortSucheT().getText().isEmpty()) {
			s.setSchlagwort(schlagwortView.getSchlagwortSucheT().getText());
		}

		return s;
	}

	private void uebernehmen() {
		Schlagwort s = new Schlagwort();
		s = tableModelSchlagwort.getGeklicktesObjekt(schlagwortView.getSchlagwortTabelle().getSelectedRow());

		schlagwortView.getPKT().setText(Integer.toString(s.getId()));
		schlagwortView.getSchlagwortT().setText(s.getSchlagwort());

		schlagwortView.getGeloeschtCbx().setSelected(s.getGeloescht());
	}

	/**
	 * Sucht die Schlagworte. Falls das Flag zur Inkludierung der geloeschten Schlagworte gesetzt ist, 
	 * muessen zwei Suchen ausgeführt werden: 1x geloescht = false und 1x geloescht = true. Die Resultate der 2. Suche
	 * muessen iterativ dem Tablemodel uebergeben wrden. 
	 */
	private void sucheAusfuehren() {
	
		schlagwortSuchobjekt = feldwertezuObjektSuchen();
		schlagwortL = normdatenService.suchenSchlagwort(schlagwortSuchobjekt);
		tableModelSchlagwort.setAndSortListe(schlagwortL);
		if (schlagwortView.getGeloeschtSucheCbx().isSelected()) {
			schlagwortSuchobjekt.setGeloescht(true);
			schlagwortL = normdatenService.suchenSchlagwort(schlagwortSuchobjekt);
			for (Schlagwort a: schlagwortL) {
				tableModelSchlagwort.schlagwortHinzufuegen(a);
			}
			schlagwortSuchobjekt.setGeloescht(false);
		}
		
	}
	
	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			sucheAusfuehren();
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		suchFelderLeeren();
		schlagwortView.getNeuAendernL().setText("");

	}

	private void suchFelderLeeren() {
		// Felder leeren
		for (JComponent t : schlagwortView.getComponentsNeuBearbeiten().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}

		}
	}

	public void initialisieren() {

		schlagwortView.getPKL().setText("Nr:");
		schlagwortView.getSchlagwortL().setText("Schlagwort:*");
		schlagwortView.getGeloescht().setText("Löschvormerkung:");
		//
		schlagwortView.getSchlagwortSucheL().setText("Schlagwort:");
		schlagwortView.getGeloeschtSucheL().setText("inkl. gelöschte:");
		schlagwortView.getSuchButton().setText("Suchen");
		schlagwortView.getPKT().setEditable(false);
		schlagwortView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		schlagwortView.getButtonPanel().getButton2().setVisible(false);
		schlagwortView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		schlagwortView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
