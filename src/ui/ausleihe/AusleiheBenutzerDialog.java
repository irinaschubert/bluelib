package ui.ausleihe;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import ui.benutzer.BenutzerSuchView;
import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * Dialog-View zur Suche eines Benutzers in der Ausleihe
 * Verwendet die BenutzerSuchView
 * 
 * @version 1.0 2018-12-08
 * @author Irina
 *
 */

@SuppressWarnings("serial")
public class AusleiheBenutzerDialog extends JDialog {
	StandardButtonPanel standardButtonPanel;
	StandardTitelPanel standardTitelPanel;
	private int HOEHE = 500;
	private int BREITE = 700;
	public AusleiheBenutzerDialog(String titel) {
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

	public void setSuchPanel(BenutzerSuchView benutzerSuchView) {
		this.add(benutzerSuchView, BorderLayout.CENTER);
	}

	public StandardButtonPanel getStandardButtonPanel() {
		return standardButtonPanel;
	}

	public void setStandardButtonPanel(StandardButtonPanel standardButtonPanel) {
		this.standardButtonPanel = standardButtonPanel;
	}
}
