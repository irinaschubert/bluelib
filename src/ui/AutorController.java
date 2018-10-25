package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import domain.Autor;
import hilfsklassen.ButtonNamen;
import models.TableModelAutor;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die Autoren-View, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 25.10.2018
 * @author Schmutz
 *
 */

public class AutorController {
	private AutorView autorView;
	private NormdatenService normdatenService;
	private List<Autor> autorL;
	private TableModelAutor tableModelAutor;

	public AutorController(AutorView view) {
		autorView = view;
		normdatenService = new NormdatenService();
		autorL = new ArrayList<>();
		tableModelAutor = new TableModelAutor();
		autorL = normdatenService.alleautoren();
		tableModelAutor.setAndSortListe(autorL);
		view.getAutorenTabelle().setModel(tableModelAutor);
		view.spaltenBreiteSetzen();

		initialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {
		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (	autorView.getNachnameT().getText().isEmpty()
						|| (autorView.getVornameT().getText().isEmpty())){
					JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
				} 
				else
					// Prüfung, ob ein neuer Autor erfasst wurde
					if (autorView.getPKT().getText().isEmpty()) {
						Autor a = new Autor();
						a.setName(autorView.getNachnameT().getText());
						a.setVorname(autorView.getVornameT().getText());
						Verifikation v = normdatenService.sichereAutor(a);
						if (v.isAktionErfolgreich()) {
							JOptionPane.showMessageDialog(null, v.getNachricht());
							tableModelAutor.setAndSortListe(normdatenService.alleautoren());
						}
						else {
							JOptionPane.showMessageDialog(null, v.getNachricht());
						}
						
						
						
						
						// Felder leeren
						for (JComponent t : autorView.getComponents().values()) {
							((JTextField) t).setText("");
						}
					}
					else {
						// TODO
					}
				
			}

		};

//		Zuweisen des Actionlisteners zum Sichern-Button
		autorView.getButtonPanel().getButton1().addActionListener(sichernButtonActionListener);

		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (autorView.getAutorenTabelle().getSelectedRow() != -1) {
					JOptionPane.showMessageDialog(null, "Du hast den Autoren mit dem Primary Key "
							+ tableModelAutor.getPK(autorView.getAutorenTabelle().getSelectedRow()) + " gewählt.",
							"Test Titel", JOptionPane.OK_OPTION);
				}
			}

		};

//		Zuweisen des Actionlisteners zum Übernehmen-Button
		autorView.getButtonPanel().getButton1().addActionListener(uebernehmenButtonActionListener);

		ActionListener abbrechenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				autorView.schliessen();
			}

		};

//	Zuweisen des Actionlisteners zum Abbrechen-Button
		autorView.getButtonPanel().getButton3().addActionListener(abbrechenButtonActionListener);

	}

	public void initialisieren() {

		autorView.getPKL().setText("Nr:");
		autorView.getNachnameL().setText("Nachname:*");
		autorView.getVornameL().setText("Vorname:*");
		autorView.getGeburtsDatumL().setText("Geburtsdatum:");
		autorView.getTodesDatumL().setText("Todesdatum:");
		autorView.getPKT().setEditable(false);
		autorView.getButtonPanel().getButton1().setText(ButtonNamen.SICHERN.getName());
		autorView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
		autorView.getButtonPanel().getButton2().setText(ButtonNamen.UEBERNEHMEN.getName());

	}
}
