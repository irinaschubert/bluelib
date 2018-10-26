package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import models.TableModelVerlag;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die VerlagView, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 26.10.2018
 * @author irina
 *
 */

public class VerlagController {
	private VerlagView verlagView;
	private NormdatenService normdatenService;
	private List<Verlag> verlagListe;
	private TableModelVerlag tableModelVerlag;

	public VerlagController(VerlagView view) {
		verlagView = view;
		normdatenService = new NormdatenService();
		verlagListe = new ArrayList<>();
		tableModelVerlag = new TableModelVerlag();
		verlagListe = normdatenService.alleVerlage();
		tableModelVerlag.setAndSortListe(verlagListe);
		view.getVerlagTabelle().setModel(tableModelVerlag);
		view.spaltenBreiteSetzen();

		initialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {
		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (verlagView.getNameT().getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
				} 
				else
					// Prüfung, ob ein neuer Verlag erfasst wurde
					if (verlagView.getPKT().getText().isEmpty()) {
						Verlag a = new Verlag();
						a.setName(verlagView.getNameT().getText());
						Verifikation v = normdatenService.sichereVerlag(a);
						if (v.isAktionErfolgreich()) {
							JOptionPane.showMessageDialog(null, v.getNachricht());
							tableModelVerlag.setAndSortListe(normdatenService.alleVerlage());
						}
						else {
							JOptionPane.showMessageDialog(null, v.getNachricht());
						}
						
						
						
						
						// Felder leeren
						for (JComponent t : verlagView.getComponents().values()) {
							((JTextField) t).setText("");
						}
					}
					else {
						// TODO
					}
				
			}

		};

//		Zuweisen des Actionlisteners zum Sichern-Button
		verlagView.getButtonPanel().getButton1().addActionListener(sichernButtonActionListener);

		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (verlagView.getVerlagTabelle().getSelectedRow() != -1) {
					JOptionPane.showMessageDialog(null, "Du hast den Verlag mit dem Primary Key "
							+ tableModelVerlag.getPK(verlagView.getVerlagTabelle().getSelectedRow()) + " gewählt.",
							"Test Titel", JOptionPane.OK_OPTION);
				}
			}

		};

//		Zuweisen des Actionlisteners zum Übernehmen-Button
		verlagView.getButtonPanel().getButton1().addActionListener(uebernehmenButtonActionListener);

		ActionListener abbrechenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				verlagView.schliessen();
			}

		};

//	Zuweisen des Actionlisteners zum Abbrechen-Button
		verlagView.getButtonPanel().getButton3().addActionListener(abbrechenButtonActionListener);

	}

	public void initialisieren() {

		verlagView.getPKL().setText("Nr:");
		verlagView.getNameLabel().setText("Name:*");
		verlagView.getGruendungsDatumLabel().setText("Gründungsdatum:");
		verlagView.getEndDatumLabel().setText("Enddatum:");
		verlagView.getPKT().setEditable(false);
		verlagView.getButtonPanel().getButton1().setText(ButtonNamen.SICHERN.getName());
		verlagView.getButtonPanel().getButton2().setText(ButtonNamen.UEBERNEHMEN.getName());
		verlagView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
	}
}
