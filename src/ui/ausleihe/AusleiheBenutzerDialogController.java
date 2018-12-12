package ui.ausleihe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import models.TableModelBenutzer;
import ui.benutzer.BenutzerSuchController;
import ui.benutzer.BenutzerSuchView;

/**
 * Fügt das Suchpanel in den Dialog ein und steuert den Dialog für die Benutzer-Suche in der Ausleihe
 * 
 * @version 1.0 2018-12-08
 * @author Irina
 *
 */
public class AusleiheBenutzerDialogController {
	private BenutzerSuchController benutzerSuchController;
	private BenutzerSuchView benutzerSuchView;
	private AusleiheController ausleiheController;
	private AusleiheBenutzerDialog ausleiheBenutzerDialog;

	public AusleiheBenutzerDialogController(AusleiheController ausleiheController, AusleiheBenutzerDialog ausleiheBenutzerDialog) {
		this.ausleiheController = ausleiheController;
		this.ausleiheBenutzerDialog = ausleiheBenutzerDialog;
		initialisieren();
		control();
	}

	private void control() {
		ausleiheBenutzerDialog.getStandardButtonPanel().getButton1().addActionListener(benutzerUebernehmenButtonActionListener());
		ausleiheBenutzerDialog.getStandardButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		benutzerSuchView.getBenutzerTabelle().addMouseListener(doppelKlick());
	}

	private void initialisieren() {
		ausleiheBenutzerDialog.getStandardButtonPanel().getButton1().setText("Übernehmen");
		ausleiheBenutzerDialog.getStandardButtonPanel().getButton2().setVisible(false);
		ausleiheBenutzerDialog.getStandardButtonPanel().getButton3().setVisible(false);
		ausleiheBenutzerDialog.getStandardButtonPanel().getButton4().setText("Schliessen");

		benutzerSuchView = new BenutzerSuchView("Benutzer suchen");
		ausleiheBenutzerDialog.setSuchPanel(benutzerSuchView);
		benutzerSuchController = new BenutzerSuchController(benutzerSuchView);
	}

	private MouseListener doppelKlick() {
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					auswahlZurueckgeben();
					ausleiheBenutzerDialog.schliessen();
				}
			}
		};
		return doppelKlick;
	}

	private ActionListener benutzerUebernehmenButtonActionListener() {
		ActionListener benutzerUebernehmenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (benutzerSuchView.getBenutzerTabelle().getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Es ist kein Benutzer ausgewählt.");
				} else {
					auswahlZurueckgeben();
					ausleiheBenutzerDialog.schliessen();
				}
			}
		};
		return benutzerUebernehmenButtonActionListener;
	}

	private ActionListener schliessenButtonActionListener() {
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ausleiheBenutzerDialog.dispose();
			}
		};
		return schliessenButtonActionListener;
	}

	public void auswahlZurueckgeben() {
		TableModelBenutzer tableModelBenutzer = benutzerSuchController.getTableModelBenutzer();
		int id = tableModelBenutzer.getGeklicktesObjekt(benutzerSuchView.getBenutzerTabelle().getSelectedRow()).getId();
		ausleiheController.pruefenUndUebernehmenBenutzerMitId(id);
	}
}
