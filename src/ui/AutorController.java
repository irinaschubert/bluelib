package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import domain.Autor;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
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
				if (autorView.getNachnameT().getText().isEmpty() || (autorView.getVornameT().getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
				} else

				{
					Autor a = new Autor();
					a.setId(Integer.parseInt(autorView.getPKT().getText()));
					a.setName(autorView.getNachnameT().getText());
					a.setVorname(autorView.getVornameT().getText());
					if (autorView.getGeburtsDatumT().getText() != null) {
						a.setGeburtsdatum(
								DateConverter.convertStringToJavaDate(autorView.getGeburtsDatumT().getText()));
					}

					if (autorView.getTodesDatumT().getText() != null) {
						a.setTodesdatum(DateConverter.convertStringToJavaDate(autorView.getTodesDatumT().getText()));
					}
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					if (autorView.getPKT().getText().isEmpty()) {

						nachAarbeitDBAktion(normdatenService.sichereAutor(a));

					} else {
						nachAarbeitDBAktion(normdatenService.aktualisiereAutor(a));
					}

				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		autorView.getButtonPanel().getButton1().addActionListener(sichernButtonActionListener);

		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (autorView.getAutorenTabelle().getSelectedRow() != -1) {
					uebernehmen();
				}
			}

		};

		// Zuweisen des Actionlisteners zum Übernehmen-Button
		autorView.getButtonPanel().getButton2().addActionListener(uebernehmenButtonActionListener);

		ActionListener abbrechenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				autorView.schliessen();
			}

		};

		// Zuweisen des Actionlisteners zum Abbrechen-Button
		autorView.getButtonPanel().getButton3().addActionListener(abbrechenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		autorView.getAutorenTabelle().addMouseListener(doppelKlick);

	}

	private void uebernehmen() {
		Autor autor = new Autor();
		autor = tableModelAutor.getGeklicktesObjekt(autorView.getAutorenTabelle().getSelectedRow());

		autorView.getPKT().setText(Integer.toString(autor.getId()));
		autorView.getNachnameT().setText(autor.getName());
		autorView.getVornameT().setText(autor.getVorname());

		if (autor.getGeburtsdatum() != null) {
			autorView.getGeburtsDatumT().setText(DateConverter.convertJavaDateToString(autor.getGeburtsdatum()));
		}

		if (autor.getTodesdatum() != null) {
			autorView.getTodesDatumT().setText(DateConverter.convertJavaDateToString(autor.getTodesdatum()));
		}

	}

	private void nachAarbeitDBAktion(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelAutor.setAndSortListe(normdatenService.alleautoren());
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}

		// Felder leeren
		for (JComponent t : autorView.getComponents().values()) {
			((JTextField) t).setText("");
		}

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
