package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 
 * Konkrete ControllerKlasse für die BuchSuche aus der BuchView heraus
 * 
 * @version 1.0 2018-11-18
 * @author Schmutz
 *
 */
public class BuchSuchControllerAusBuch extends BuchSuchController {
	private BuchController buchController;

	public BuchSuchControllerAusBuch(BuchSuchView view, BuchController buchController) {
		super(view);
		this.buchController = buchController;
		control();
	}

	private void control() {

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					buchController.uebernehmen(
							tableModelBuch.getGeklicktesObjekt(buchSuchView.getBuchTabelle().getSelectedRow()));
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		buchSuchView.getBuchTabelle().addMouseListener(doppelKlick);
	}

}
