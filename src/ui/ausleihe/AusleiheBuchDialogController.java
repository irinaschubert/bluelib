package ui.ausleihe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import models.TableModelBuch;
import ui.buch.BuchSuchController;
import ui.buch.BuchSuchView;

/**
 * 
 * Fügt das Suchpanel in den Dialog ein und steuert den Dialog für die Buch-Suche
 * 
 * @version 1.0 2018-12-08
 * @author Irina
 *
 */
public class AusleiheBuchDialogController {
	private BuchSuchController buchSuchController;
	private BuchSuchView buchSuchView;
	private AusleiheController ausleiheController;
	private AusleiheBuchDialog ausleiheBuchDialog;

	public AusleiheBuchDialogController(AusleiheController ausleiheController, AusleiheBuchDialog ausleiheBuchDialog) {
		this.ausleiheController = ausleiheController;
		this.ausleiheBuchDialog = ausleiheBuchDialog;
		initialisieren();
		control();
	}

	private void control() {
		ausleiheBuchDialog.getStandardButtonPanel().getButton1().addActionListener(buchUebernehmenButtonActionListener());
		ausleiheBuchDialog.getStandardButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		buchSuchView.getBuchTabelle().addMouseListener(doppelKlick());
	}

	private void initialisieren() {
		ausleiheBuchDialog.getStandardButtonPanel().getButton1().setText("Übernehmen");
		ausleiheBuchDialog.getStandardButtonPanel().getButton2().setVisible(false);
		ausleiheBuchDialog.getStandardButtonPanel().getButton3().setVisible(false);
		ausleiheBuchDialog.getStandardButtonPanel().getButton4().setText("Schliessen");

		buchSuchView = new BuchSuchView();
		ausleiheBuchDialog.setSuchPanel(buchSuchView);
		buchSuchController = new BuchSuchController(buchSuchView);
	}

	private MouseListener doppelKlick() {
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					auswahlZurueckgeben();
					ausleiheBuchDialog.schliessen();
				}
			}
		};
		return doppelKlick;
	}

	private ActionListener buchUebernehmenButtonActionListener() {
		ActionListener buchUebernehmenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (buchSuchView.getBuchTabelle().getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Es ist kein Buch ausgewählt.");
				} else {
					auswahlZurueckgeben();
					ausleiheBuchDialog.schliessen();
				}
			}
		};
		return buchUebernehmenButtonActionListener;
	}

	private ActionListener schliessenButtonActionListener() {
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ausleiheBuchDialog.dispose();
			}
		};
		return schliessenButtonActionListener;
	}

	public void auswahlZurueckgeben() {
		TableModelBuch tableModelBuch = buchSuchController.getTableModelBuch();
		int id = tableModelBuch.getGeklicktesObjekt(buchSuchView.getBuchTabelle().getSelectedRow()).getBuchId();
		ausleiheController.suchenBuchMitId(id);
	}
}
