package ui;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * Stellt die Standardbuttons der Menüs zusammen. Muss noch angepasst werden.
 * 
 * @version 1.0 18.10.2018
 * @author BlueLib
 *
 */

public class StandardButtonPanel extends JPanel{
	private JButton sichernButton;
	private JButton abbrechenButton;
	private JButton uebernehmenButton;
	
	public StandardButtonPanel() {
		erstellenButtonPanel();
	}

	private void erstellenButtonPanel() {
		sichernButton = new JButton("Sichern");
		abbrechenButton = new JButton("Abbrechen");
		uebernehmenButton = new JButton("Übernehmen");
		
		this.add(sichernButton);
		this.add(uebernehmenButton);
		this.add(abbrechenButton);
				
	}
	
	public JButton getSichernButton() {
		return this.sichernButton;
	}
	
	public JButton getUebernehmenButton() {
		return this.uebernehmenButton;
	}
	
	public JButton getAbbrechenButton() {
		return this.abbrechenButton;
	}

}
