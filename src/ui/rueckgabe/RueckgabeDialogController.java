package ui.rueckgabe;

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
 * Fügt das Suchpanel in den Dialog ein und steuert den Dialog
 * 
 * @version 1.0 2018-12-08
 * @author Schmutz
 *
 */
public class RueckgabeDialogController {
	private BuchSuchController buchSuchController;
	private BuchSuchView buchSuchView;
	private RueckgabeController rueckgabeController;
	private RueckgabeDialog rueckgabeDialog;

	public RueckgabeDialogController(RueckgabeController rueckgabeController, RueckgabeDialog rueckgabeDialog) {
		this.rueckgabeController = rueckgabeController;
		this.rueckgabeDialog = rueckgabeDialog;
		initialisieren();
		control();
	}

	private void control() {
		rueckgabeDialog.getStandardButtonPanel().getButton1().addActionListener(buchUebernehmenButtonActionListener());
		rueckgabeDialog.getStandardButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		buchSuchView.getBuchTabelle().addMouseListener(doppelKlick());

	}

	private void initialisieren() {

		rueckgabeDialog.getStandardButtonPanel().getButton1().setText("Übernehmen");
		rueckgabeDialog.getStandardButtonPanel().getButton2().setVisible(false);
		rueckgabeDialog.getStandardButtonPanel().getButton3().setVisible(false);
		rueckgabeDialog.getStandardButtonPanel().getButton4().setText("Schliessen");

		buchSuchView = new BuchSuchView();
		rueckgabeDialog.setSuchPanel(buchSuchView);
		buchSuchController = new BuchSuchController(buchSuchView);

	}

	private MouseListener doppelKlick() {
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					auswhalZurueckgeben();
					rueckgabeDialog.schliessen();

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
					JOptionPane.showMessageDialog(null, "Es ist kein Buch ausgewählt");
				} else {
					auswhalZurueckgeben();
					rueckgabeDialog.schliessen();
				}
			}
		};

		return buchUebernehmenButtonActionListener;
	}

	private ActionListener schliessenButtonActionListener() {

		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rueckgabeDialog.dispose();
			}
		};

		return schliessenButtonActionListener;
	}

	public void auswhalZurueckgeben() {
		TableModelBuch tableModelBuch = buchSuchController.getTableModelBuch();
		int id = tableModelBuch.getGeklicktesObjekt(buchSuchView.getBuchTabelle().getSelectedRow()).getBuchId();
		rueckgabeController.buchSuchenUndResultatAnzeigen(id);
	}

}
