package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelVerlag;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die verlagView, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 26.10.2018
 * @author irina
 *
 */

public class VerlagController {
	private VerlagView verlagView;
	private NormdatenService normdatenService;
	private List<Verlag> verlagL;
	private TableModelVerlag tableModelVerlag;
	private Verlag verlagSuchobjekt;

	public VerlagController(VerlagView view) {
		verlagView = view;
		normdatenService = new NormdatenService();
		verlagL = new ArrayList<>();
		tableModelVerlag = new TableModelVerlag();
		//verlagL = normdatenService.alleVerlage();
		tableModelVerlag.setAndSortListe(verlagL);
		view.getVerlagTabelle().setModel(tableModelVerlag);
		view.spaltenBreiteSetzen();
		verlagSuchobjekt = new Verlag();
		initialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {
		
		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					verlagSuchobjekt = feldwertezuObjektSuchen();
					verlagL = normdatenService.sucheVerlag(verlagSuchobjekt);
					tableModelVerlag.setAndSortListe(verlagL);
				}

			}

		};

		// Zuweisen des Actionlisteners zum Suchen-Button
		verlagView.getSuchButton().addActionListener(suchenButtonActionListener);
		
		ActionListener sichernButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Verlag v = new Verlag();
				if (inputValidierungSpeichern()) {
					v = feldwertezuObjektSpeichern();
					if (verlagView.getPKT().getText().isEmpty()) {
						nachArbeitSpeichern(normdatenService.sichereVerlag(v));
					} else {
						nachArbeitSpeichern(normdatenService.aktualisiereVerlag(v));
					}
				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		verlagView.getButtonPanel().getButton1().addActionListener(sichernButtonActionListener);

		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (verlagView.getVerlagTabelle().getSelectedRow() != -1) {
					uebernehmen();
				}
			}

		};

		// Zuweisen des Actionlisteners zum Übernehmen-Button
		verlagView.getButtonPanel().getButton2().addActionListener(uebernehmenButtonActionListener);

		ActionListener abbrechenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				verlagView.schliessen();
			}

		};

		// Zuweisen des Actionlisteners zum Abbrechen-Button
		verlagView.getButtonPanel().getButton3().addActionListener(abbrechenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
				}
			}
		};

		// Zuweisen des Mouselisteners zur Tabelle
		verlagView.getVerlagTabelle().addMouseListener(doppelKlick);

	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!verlagView.getGruendungsDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getGruendungsDatumSucheT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Gründungsdatum");
				verlagView.getGruendungsDatumL().setText("");
				keinInputFehler = false;
			}
		}

		if (!verlagView.getEndDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getEndDatumSucheT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Enddatum");
				verlagView.getEndDatumL().setText("");
				keinInputFehler = false;
			}
		}

		return keinInputFehler;

	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (verlagView.getNameT().getText().isEmpty() || (verlagView.getNameT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!verlagView.getGruendungsDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getGruendungsDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
				verlagView.getGruendungsDatumL().setText("");
				keinInputFehler = false;
			}
		}

		if (!verlagView.getEndDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getEndDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Todesdatum");
				verlagView.getEndDatumL().setText("");
				keinInputFehler = false;
			}
		}

		return keinInputFehler;

	}

	private Verlag feldwertezuObjektSpeichern() {
		Verlag v = new Verlag();
		if (!verlagView.getPKT().getText().isEmpty()) {
			v.setId(Integer.parseInt(verlagView.getPKT().getText()));
		}
		v.setName(verlagView.getNameT().getText());
		
		if (!verlagView.getGruendungsDatumT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(verlagView.getGruendungsDatumT().getText())) {
				v.setGruendungsDatum(DateConverter.convertStringToJavaDate(verlagView.getGruendungsDatumT().getText()));
			}
		}
		if (!verlagView.getEndDatumT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(verlagView.getEndDatumT().getText())) {
				v.setEndDatum(DateConverter.convertStringToJavaDate(verlagView.getEndDatumT().getText()));
			}
		}
		v.setGeloescht(verlagView.getGeloeschtCbx().isSelected());
		return v;
	}

	private Verlag feldwertezuObjektSuchen() {
		Verlag v = new Verlag();
		if (!verlagView.getNameSucheT().getText().isEmpty()) {
			v.setName(verlagView.getNameSucheT().getText());
		}
		if (!verlagView.getGruendungsDatumT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(verlagView.getGruendungsDatumT().getText())) {
				v.setGruendungsDatum(DateConverter.convertStringToJavaDate(verlagView.getGruendungsDatumT().getText()));
			}
		}
		if (!verlagView.getEndDatumT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(verlagView.getEndDatumT().getText())) {
				v.setEndDatum(DateConverter.convertStringToJavaDate(verlagView.getEndDatumT().getText()));
			}
		}
		v.setGeloescht(verlagView.getGeloeschtSucheCbx().isSelected());
		return v;
	}

	private void uebernehmen() {
		Verlag Verlag = new Verlag();
		Verlag = tableModelVerlag.getGeklicktesObjekt(verlagView.getVerlagTabelle().getSelectedRow());

		verlagView.getPKT().setText(Integer.toString(Verlag.getId()));
		verlagView.getNameT().setText(Verlag.getName());

		if (Verlag.getGruendungsDatum() != null) {
			verlagView.getGruendungsDatumT().setText(DateConverter.convertJavaDateToString(Verlag.getGruendungsDatum()));
		}

		if (Verlag.getEndDatum() != null) {
			verlagView.getEndDatumT().setText(DateConverter.convertJavaDateToString(Verlag.getEndDatum()));
		}
		verlagView.getGeloeschtCbx().setSelected(Verlag.getGeloescht());
	}

	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelVerlag.setAndSortListe(normdatenService.sucheVerlag(verlagSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}

		// Felder leeren
		for (JComponent t : verlagView.getComponents().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}
		}

	}

	public void initialisieren() {

		verlagView.getPKL().setText("Nr:");
		verlagView.getNameLabel().setText("Name:*");
		verlagView.getGruendungsDatumL().setText("Gründungsdatum:");
		verlagView.getEndDatumL().setText("Enddatum:");
		verlagView.getGeloescht().setText("Löschvormerkung:");
		
		verlagView.getNameSucheL().setText("Name:*");
		verlagView.getGruendungsDatumSucheL().setText("Gründungsdatum:");
		verlagView.getEndDatumSucheL().setText("Enddatum:");
		verlagView.getGeloeschtSucheL().setText("Löschvormerkung:");
		verlagView.getSuchButton().setText("Suchen");
		verlagView.getPKT().setEditable(false);
		verlagView.getButtonPanel().getButton1().setText(ButtonNamen.SICHERN.getName());
		verlagView.getButtonPanel().getButton2().setText(ButtonNamen.UEBERNEHMEN.getName());
		verlagView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
	}
}
