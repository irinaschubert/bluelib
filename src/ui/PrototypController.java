package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import domain.Anrede;
import hilfsklassen.ButtonNamen;
import services.NormdatenService;

/**
 * 
 * Prototyp eines Controllers, der die Logik und die Benutzeraktionen der View steuert und der 
 * View die Models übergibt
 * 
 * @version 1.0 18.10.2018
 * @author Schmutz
 *
 */

public class PrototypController {
	private PrototypView prototypView;
	private ActionListener sichernButonActionListener;
	private NormdatenService normdatenService;
	private List<Anrede> anredeL;
	
	public PrototypController(PrototypView view) {
		this.prototypView = view;
		this.normdatenService = new NormdatenService();
		this.anredeL = normdatenService.alleAnreden();
		
//		toArray konvertiert die ArrayList in ein Array, das als Model-Datentyp aktzeptiert wird
		view.getAnredeComboBox().setModel(new DefaultComboBoxModel(anredeL.toArray()));
		initialisieren();
		control();

	}
	
//	Definierten des Listeners für den Klick auf den Sichern-Button
	private void control() {
		sichernButonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Du hast die Anrede mit dem Primary Key " 
						+ ((Anrede) prototypView.getAnredeComboBox().getSelectedItem()).getId()
						+ " gewählt.", "Test Titel", JOptionPane.OK_OPTION);
				
			}
		};
		
//		Zuweisen des Actionlisteners zum Sichern-Button
		prototypView.getButtonPanel().getButton1().addActionListener(sichernButonActionListener);
	}
	
	public void initialisieren() {
		prototypView.getLabel().setText("Anrede");
		prototypView.getButtonPanel().getButton1().setText(ButtonNamen.SICHERN.getName());
		prototypView.getButtonPanel().getButton2().setText(ButtonNamen.UEBERNEHMEN.getName());
		prototypView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
	}
}
