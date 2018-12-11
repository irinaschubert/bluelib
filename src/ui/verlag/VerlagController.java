package ui.verlag;

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

import domain.Autor;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelVerlag;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;

/**
 * Controller für die verlagView, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 26.10.2018
 * @author irina
 */

public class VerlagController {
	private VerlagView verlagView;
	private NormdatenService normdatenService;
	private List<Verlag> verlagL;
	private TableModelVerlag tableModelVerlag;
	private Verlag verlagSuchobjekt;
	private HauptController hauptController;

	public VerlagController(VerlagView view, HauptController hauptController) {
		verlagView = view;
		this.hauptController = hauptController;
		normdatenService = new NormdatenService();
		verlagL = new ArrayList<>();
		tableModelVerlag = new TableModelVerlag();
		tableModelVerlag.setAndSortListe(verlagL);
		view.getVerlagTabelle().setModel(tableModelVerlag);
		view.spaltenBreiteSetzen();
		verlagSuchobjekt = new Verlag();
		initialisieren();
		control();
	}

	// Buttons
	private void control() {
		// Suchen
		ActionListener suchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputValidierungSuchen()) {
					sucheAusfuehren();
				}
			}
		};
		verlagView.getSuchButton().addActionListener(suchenButtonActionListener);

		// Neu
		ActionListener neuButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				erfassungFelderLeeren();
				verlagView.getNeuAendernL().setText("Neuerfassung");
			}
		};
		verlagView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		// Speichern
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
		verlagView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		// Schliessen
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		verlagView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		// Doppelklick = Werte übernehmen
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					verlagView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};
		verlagView.getVerlagTabelle().addMouseListener(doppelKlick);
	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;
		if (!verlagView.getGruendungsDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getGruendungsDatumSucheT().getText())) {
				verlagView.getGruendungsDatumSucheT().setText("");
				keinInputFehler = false;
				JOptionPane.showMessageDialog(null, "Ungültiges Gründungsdatum");
			}
		}
		if (!verlagView.getEndDatumSucheT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getEndDatumSucheT().getText())) {
				verlagView.getEndDatumSucheT().setText("");
				keinInputFehler = false;
				JOptionPane.showMessageDialog(null, "Ungültiges Enddatum");
			}
		}
		return keinInputFehler;
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (verlagView.getNameT().getText().isEmpty() || (verlagView.getNameT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder (*) erfassen");
			keinInputFehler = false;
		}
		if (!verlagView.getGruendungsDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getGruendungsDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Gründungsdatum");
				verlagView.getGruendungsDatumT().setText("");
				keinInputFehler = false;
			}
		}
		if (!verlagView.getEndDatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(verlagView.getEndDatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Enddatum");
				verlagView.getEndDatumT().setText("");
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
		if (!verlagView.getGruendungsDatumSucheT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(verlagView.getGruendungsDatumSucheT().getText())) {
				v.setGruendungsDatum(
						DateConverter.convertStringToJavaDate(verlagView.getGruendungsDatumSucheT().getText()));
			}
		}
		if (!verlagView.getEndDatumSucheT().getText().isEmpty()) {
			if (DateConverter.datumIstGueltig(verlagView.getEndDatumSucheT().getText())) {
				v.setEndDatum(DateConverter.convertStringToJavaDate(verlagView.getEndDatumSucheT().getText()));
			}
		}
		return v;
	}
	
	/**
	 * Sucht die Verlage. Falls das Flag zur Inkludierung der gelöschten Verlage gesetzt ist, 
	 * muessen zwei Suchen ausgeführt werden: 1x geloescht = false und 1x geloescht = true. Die Resultate der 2. Suche
	 * muessen iterativ dem Tablemodel uebergeben wrden. 
	 */
	private void sucheAusfuehren() {
	
		verlagSuchobjekt = feldwertezuObjektSuchen();
		verlagL = normdatenService.sucheVerlag(verlagSuchobjekt);
		tableModelVerlag.setAndSortListe(verlagL);
		if (verlagView.getGeloeschtSucheCbx().isSelected()) {
			verlagSuchobjekt.setGeloescht(true);
			verlagL = normdatenService.sucheVerlag(verlagSuchobjekt);
			for (Verlag a: verlagL) {
				tableModelVerlag.verlagHinzufuegen(a);
			}
			verlagSuchobjekt.setGeloescht(false);
		}
		
	}

	private void uebernehmen() {
		Verlag verlag = new Verlag();
		verlag = tableModelVerlag.getGeklicktesObjekt(verlagView.getVerlagTabelle().getSelectedRow());
		erfassungFelderLeeren();
		verlagView.getPKT().setText(Integer.toString(verlag.getId()));
		verlagView.getNameT().setText(verlag.getName());
		if (verlag.getGruendungsDatum() != null) {
			verlagView.getGruendungsDatumT()
					.setText(DateConverter.convertJavaDateToString(verlag.getGruendungsDatum()));
		}

		if (verlag.getEndDatum() != null) {
			verlagView.getEndDatumT().setText(DateConverter.convertJavaDateToString(verlag.getEndDatum()));
		}
		verlagView.getGeloeschtCbx().setSelected(verlag.getGeloescht());
	}

	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			sucheAusfuehren();
//			tableModelVerlag.setAndSortListe(normdatenService.sucheVerlag(verlagSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		suchFelderLeeren();
		verlagView.getNeuAendernL().setText("");
	}

	private void suchFelderLeeren() {
		for (JComponent t : verlagView.getComponentsSuche().values()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}
		}
	}

	private void erfassungFelderLeeren() {
		for (JComponent t : verlagView.getComponentsNeuBearbeiten().values()) {
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
		verlagView.getNameL().setText("Name:*");
		verlagView.getGruendungsDatumL().setText("Gründungsdatum:");
		verlagView.getEndDatumL().setText("Enddatum:");
		verlagView.getGeloescht().setText("Löschvormerkung:");
		verlagView.getNameSucheL().setText("Name:");
		verlagView.getGruendungsDatumSucheL().setText("Gründungsdatum:");
		verlagView.getEndDatumSucheL().setText("Enddatum:");
		verlagView.getGeloeschtSucheL().setText("inkl. geloeschte:");
		verlagView.getSuchButton().setText("Suchen");
		verlagView.getPKT().setEditable(false);
		TextComponentLimit.addTo(verlagView.getNameT(), 50);
		TextComponentLimit.addTo(verlagView.getGruendungsDatumT(), 10);
		TextComponentLimit.addTo(verlagView.getEndDatumT(), 10);
		TextComponentLimit.addTo(verlagView.getNameSucheT(), 50);
		TextComponentLimit.addTo(verlagView.getGruendungsDatumSucheT(), 10);
		TextComponentLimit.addTo(verlagView.getEndDatumSucheT(), 10);
		verlagView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		verlagView.getButtonPanel().getButton2().setVisible(false);
		verlagView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		verlagView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
