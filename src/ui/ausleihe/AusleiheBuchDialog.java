package ui.ausleihe;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import ui.buch.BuchSuchView;
import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * 
 * Dialog-View zur Suche eines Buches in der Ausleihe
 * Verwendet die BuchSuchView
 * 
 * @version 1.0 2018-12-08
 * @author Irina
 *
 */

public class AusleiheBuchDialog extends JDialog {
	StandardButtonPanel standardButtonPanel;
	StandardTitelPanel standardTitelPanel;

	private int HOEHE = 500;
	private int BREITE = 700;

	public AusleiheBuchDialog(String titel) {
		standardButtonPanel = new StandardButtonPanel();
		standardTitelPanel = new StandardTitelPanel(titel);

		this.setLayout(new BorderLayout());
		this.add(standardTitelPanel, BorderLayout.NORTH);
		this.add(standardButtonPanel, BorderLayout.SOUTH);
		this.setSize(BREITE, HOEHE);

	}

	public void schliessen() {
		this.dispose();
	}

	public void setSuchPanel(BuchSuchView buchSuchView) {
		this.add(buchSuchView, BorderLayout.CENTER);
	}

	public StandardButtonPanel getStandardButtonPanel() {
		return standardButtonPanel;
	}

	public void setStandardButtonPanel(StandardButtonPanel standardButtonPanel) {
		this.standardButtonPanel = standardButtonPanel;
	}

}
