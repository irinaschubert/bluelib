package ui.MA;

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
 * Fügt das Suchpanel in den Dialog ein und steuert den Dialog für die Benutzer-Suche bei dem Mitarbeiter
 * 
 * @version 2.0 2018-12.12
 * @author Mike
 *
 */
public class MitarbeiterBenutzerDialogController {
	private BenutzerSuchController benutzerSuchController;
	private BenutzerSuchView benutzerSuchView;
	private MitarbeiterController mitarbeiterController;
	private MitarbeiterBenutzerDialog mitarbeiterBenutzerDialog;

	public MitarbeiterBenutzerDialogController(MitarbeiterController mitarbeiterController, MitarbeiterBenutzerDialog mitarbeiterBenutzerDialog) {
		this.mitarbeiterController = mitarbeiterController;
		this.mitarbeiterBenutzerDialog = mitarbeiterBenutzerDialog;
		initialisieren();
		control();
	}

	private void control() {
		mitarbeiterBenutzerDialog.getStandardButtonPanel().getButton1().addActionListener(benutzerUebernehmenButtonActionListener());
		mitarbeiterBenutzerDialog.getStandardButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		benutzerSuchView.getBenutzerTabelle().addMouseListener(doppelKlick());
	}

	private void initialisieren() {
		mitarbeiterBenutzerDialog.getStandardButtonPanel().getButton1().setText("Übernehmen");
		mitarbeiterBenutzerDialog.getStandardButtonPanel().getButton2().setVisible(false);
		mitarbeiterBenutzerDialog.getStandardButtonPanel().getButton3().setVisible(false);
		mitarbeiterBenutzerDialog.getStandardButtonPanel().getButton4().setText("Schliessen");

		benutzerSuchView = new BenutzerSuchView("Benutzer suchen");
		mitarbeiterBenutzerDialog.setSuchPanel(benutzerSuchView);
		benutzerSuchController = new BenutzerSuchController(benutzerSuchView);
	}

	private MouseListener doppelKlick() {
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					auswahlZurueckgeben();
					mitarbeiterBenutzerDialog.schliessen();
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
					mitarbeiterBenutzerDialog.schliessen();
				}
			}
		};
		return benutzerUebernehmenButtonActionListener;
	}

	private ActionListener schliessenButtonActionListener() {
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mitarbeiterBenutzerDialog.dispose();
			}
		};
		return schliessenButtonActionListener;
	}

	public void auswahlZurueckgeben() {
		TableModelBenutzer tableModelBenutzer = benutzerSuchController.getTableModelBenutzer();
		int id = tableModelBenutzer.getGeklicktesObjekt(benutzerSuchView.getBenutzerTabelle().getSelectedRow()).getId();
		System.out.println("doit 1");
		mitarbeiterController.pruefenUndUebernehmenBenutzerMitId(id);
	}
}
