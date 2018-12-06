package ui.rueckgabe;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ui.buch.BuchController;
import ui.buch.BuchSuchController;
import ui.buch.BuchSuchView;

/**
 * 
 * Konkrete ControllerKlasse für die BuchSuche aus der Rückgabe-View
 * 
 * @version 1.0 2018-11-18
 * @author Schmutz
 *
 */
public class BuchSuchControllerAusRueckgabe extends BuchSuchController {
	private RueckgabeController rueckgabeController;

	public BuchSuchControllerAusRueckgabe(BuchSuchView view, RueckgabeController rueckgabeController) {
		super(view);
		this.rueckgabeController = rueckgabeController;
		control();
	}

	private void control() {

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int id = tableModelBuch.getGeklicktesObjekt(buchSuchView.getBuchTabelle().getSelectedRow()).getBuchId();
					rueckgabeController.buchSuchenUndResultatAnzeigen(id);
	
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		buchSuchView.getBuchTabelle().addMouseListener(doppelKlick);
	}

}
