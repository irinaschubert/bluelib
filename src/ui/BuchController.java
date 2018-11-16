package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import domain.Autor;
import domain.Buch;
import domain.Status;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.IntHelfer;
import models.ComboBoxModelAutor;
import models.ComboBoxModelVerlag;
import models.TableModelAutor;
import models.TableModelBuch;
import services.MedienhandlingService;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die Buch-View, der die Logik und die Benutzeraktionen der View
 * steuert und der View die Models übergibt
 * 
 * @version 1.0 2018-11-13
 * @author Schmutz
 *
 */

public class BuchController {
	private BuchView buchView;
	private List<Buch> buchL;
	private MedienhandlingService medienHandlingService;
	private NormdatenService normdatenService;
	private TableModelBuch tableModelBuch;
	private Buch buchSuchobjekt;
	private HauptController hauptController;

	public BuchController(BuchView view, HauptController hauptController) {
		buchView = view;
		this.hauptController = hauptController;
		medienHandlingService = new MedienhandlingService();
		normdatenService = new NormdatenService();
//		autorL = normdatenService.alleautoren();
//		tableModelAutor.setAndSortListe(buchL);
//		view.getAutorenTabelle().setModel(tableModelAutor);
//		view.spaltenBreiteSetzen();
//		autorSuchobjekt = new Autor();

		suchPanelInitialisieren();
		tabellenPanelInitialisieren();
		neuBearbeitenPanelInitialisieren();
		ButtonPanelInitialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {

		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					buchSuchobjekt = feldwertezuObjektSuchen();
					buchL = medienHandlingService.BuchSuchen(buchSuchobjekt);
					tableModelBuch.setAndSortListe(buchL);
				}

			}

		};

		// Zuweisen des Actionlisteners zum Suchen-Button
		buchView.getSuchButton().addActionListener(suchenButtonActionListener);

		ActionListener neuButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				suchFelderLeeren();
				buchView.getNeuAendernL().setText("Neuerfassung");
			}

		};

		// Zuweisen des Actionlisteners zum Neu-Button
		buchView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Autor a = new Autor();
				if (inputValidierungSpeichern()) {
					a = feldwertezuObjektSpeichern();
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
//					if (autorView.getPKT().getText().isEmpty()) {
//
//						nachAarbeitSpeichern(normdatenService.sichereAutor(a));
//
//					} else {
//						nachAarbeitSpeichern(normdatenService.aktualisiereAutor(a));
//					}
				}

			}

		};

		// Zuweisen des Actionlisteners zum Sichern-Button
		buchView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		ActionListener schliessenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}

		};

		// Zuweisen des Actionlisteners zum Schliessen-Button
		buchView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					buchView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};

//		// Zuweisen des Mouselisteners zur Tabelle
//		buchView.getAutorenTabelle().addMouseListener(doppelKlick);

	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!buchView.getBarcodeSucheT().getText().isEmpty()) {
			if (IntHelfer.istInteger(buchView.getBarcodeSucheL().getText())) {
				JOptionPane.showMessageDialog(null, "Üngültiges Barcodeformat");
				buchView.getBarcodeSucheT().setText("");
				keinInputFehler = false;
			}

		}

		return keinInputFehler;

	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
//		if (autorView.getNachnameT().getText().isEmpty() || (autorView.getVornameT().getText().isEmpty())) {
//			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
//			keinInputFehler = false;
//		}
//
//		if (!autorView.getGeburtsDatumT().getText().isEmpty()) {
//			if (!DateConverter.datumIstGueltig(autorView.getGeburtsDatumT().getText())) {
//				JOptionPane.showMessageDialog(null, "Üngültiges Geburtsdatum");
//				autorView.getGeburtsDatumL().setText("");
//				keinInputFehler = false;
//			}
//		}
//
//		if (!autorView.getTodesDatumT().getText().isEmpty()) {
//			if (!DateConverter.datumIstGueltig(autorView.getTodesDatumT().getText())) {
//				JOptionPane.showMessageDialog(null, "Üngültiges Todesdatum");
//				autorView.getTodesDatumL().setText("");
//				keinInputFehler = false;
//			}
//		}

		return keinInputFehler;

	}

	private Autor feldwertezuObjektSpeichern() {
		Autor a = new Autor();
//		if (!autorView.getPKT().getText().isEmpty()) {
//			a.setId(Integer.parseInt(autorView.getPKT().getText()));
//		}
//		a.setName(autorView.getNachnameT().getText());
//		a.setVorname(autorView.getVornameT().getText());
//		if (!autorView.getGeburtsDatumT().getText().isEmpty()) {
//			a.setGeburtsdatum(DateConverter.convertStringToJavaDate(autorView.getGeburtsDatumT().getText()));
//		}
//
//		if (!autorView.getTodesDatumT().getText().isEmpty()) {
//			if (DateConverter.datumIstGueltig(autorView.getTodesDatumT().getText())) {
//				a.setTodesdatum(DateConverter.convertStringToJavaDate(autorView.getTodesDatumT().getText()));
//			}
//		}
//		a.setGeloescht(autorView.getGeloeschtCbx().isSelected());
		return a;
	}

	private Buch feldwertezuObjektSuchen() {
		Buch b = new Buch();
		if (!buchView.getBarcodeSucheT().getText().isEmpty()) {
			int barCode = IntHelfer.stringZuInt(buchView.getBarcodeSucheT().getText());
			b.setBarcodeNr(barCode);
		}
		if (!buchView.getTitelSucheT().getText().isEmpty()) {
			b.setTitel(buchView.getTitelSucheT().getText());
		}

		if (buchView.getAutorSucheCbx().getSelectedIndex() > 0) { // 0 = kein Autor ausgewählt
			Autor a = new Autor();
			a = (Autor) buchView.getAutorSucheCbx().getModel().getSelectedItem();
			b.setAutor(a);
		}

		if (buchView.getVerlagSucheCbx().getSelectedIndex() > 0) { // 0 = kein Verlag ausgewählt
			b.setVerlag((Verlag) buchView.getVerlagSucheCbx().getModel().getSelectedItem());
		}

		if (!buchView.getSignaturSucheT().getText().isEmpty()) {
			b.setSignatur(buchView.getSignaturSucheT().getText());
		}

		b.setStatus((Status) buchView.getStatusSucheCbx().getModel().getSelectedItem());

		return b;
	}

	private void uebernehmen() {
//		Autor autor = new Autor();
//		autor = tableModelAutor.getGeklicktesObjekt(autorView.getAutorenTabelle().getSelectedRow());
//
//		autorView.getPKT().setText(Integer.toString(autor.getId()));
//		autorView.getNachnameT().setText(autor.getName());
//		autorView.getVornameT().setText(autor.getVorname());
//
//		if (autor.getGeburtsdatum() != null) {
//			autorView.getGeburtsDatumT().setText(DateConverter.convertJavaDateToString(autor.getGeburtsdatum()));
//		}
//
//		if (autor.getTodesdatum() != null) {
//			autorView.getTodesDatumT().setText(DateConverter.convertJavaDateToString(autor.getTodesdatum()));
//		}
//		autorView.getGeloeschtCbx().setSelected(autor.getGeloescht());
	}

	private void nachAarbeitSpeichern(Verifikation v) {
//		if (v.isAktionErfolgreich()) {
//			JOptionPane.showMessageDialog(null, v.getNachricht());
//			tableModelAutor.setAndSortListe(normdatenService.sucheAutor(autorSuchobjekt));
//		} else {
//			JOptionPane.showMessageDialog(null, v.getNachricht());
//		}
//		suchFelderLeeren();
//		autorView.getNeuAendernL().setText("");

	}

	private void suchFelderLeeren() {

		// Felder leeren
		for (JComponent t : buchView.getComponentsNeuBearbeiten()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JCheckBox) {
				((JCheckBox) t).setSelected(false);
			}

		}
	}

	public void suchPanelInitialisieren() {

//		buchView.getPKL().setText("Nr:");
		buchView.getBarcodeSucheL().setText("Barcode:");
		buchView.getTitelSucheL().setText("Titel:");
		buchView.getAutorSucheL().setText("Autor:");
		buchView.getVerlagSucheL().setText("Verlag:");
		buchView.getSignaturSucheL().setText("Signatur:");
		buchView.getStatusSucheL().setText("Status:");
		buchView.getSuchButton().setText("Suchen");

		buchView.getVerlagSucheCbx().setModel(new ComboBoxModelVerlag(normdatenService.alleVerlage()));
		buchView.getVerlagSucheCbx().setSelectedIndex(0);
		buchView.getAutorSucheCbx().setModel(new ComboBoxModelAutor(normdatenService.alleautoren()));
		buchView.getAutorSucheCbx().setSelectedIndex(0);
		buchView.getStatusSucheCbx()
				.setModel(new DefaultComboBoxModel(medienHandlingService.alleMedienStati().toArray()));

	}

	private void tabellenPanelInitialisieren() {
		buchL = new ArrayList<>();
		tableModelBuch = new TableModelBuch();
		tableModelBuch.setAndSortListe(buchL);
		buchView.getBuchTabelle().setModel(tableModelBuch);
		buchView.spaltenBreiteSetzen();
//		autorSuchobjekt = new Autor();
	}

	private void neuBearbeitenPanelInitialisieren() {
		buchView.getPKL().setText("Nr.:");
		buchView.getBarcodeL().setText("Barcode*:");
		buchView.getErfassenBarcodeB().setText("Erfassen");
		buchView.getTitelL().setText("Titel*:");
		buchView.getVerlagL().setText("Verlag*:");
		buchView.getAuflageL().setText("Auflage*:");
		buchView.getReiheL().setText("Reihe:");
		buchView.getAnzahlSeitenL().setText("Anz. Seiten:");
		buchView.getPreisL().setText("Preis:");
		buchView.getJahrL().setText("Jahr*:");
		buchView.getOrtL().setText("Ort*:");
		buchView.getIsbnL().setText("ISBN:");
		buchView.getStatusL().setText("Status:");
		buchView.getAutoren().setText("Autor(en)*:");
		buchView.getZuweisenAutorB().setText("Zuweisen");
		buchView.getEntfernenAutorB().setText("Entfernen");
		buchView.getBelletristikR().setText("Belletristik");
		buchView.getSachbuchR().setText("Sachbuch");
		buchView.getBelletristikR().setSelected(true);
		buchView.getSchlagwortL().setText("Schlagwort");
		buchView.getZuweisenSchlagwortB().setText("zuweisen");
		buchView.getEntferntenSchlagwortB().setText("entfernen");
		buchView.getNotizL().setText("Notiz");
		buchView.getErfassungsDatumL().setText("Erfassungsdatum:");
		buchView.getErfassungsUserL().setText("Erfasst durch:");
		Autor a = new Autor();
		a.setName("Testname");
		a.setVorname("TestVorname");
		Verlag v = new Verlag();
		v.setName("test");
		DefaultListModel model = new DefaultListModel();
		model.addElement(v);

		buchView.getAutorList().setModel(model);

	}

	private void ButtonPanelInitialisieren() {
		buchView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		buchView.getButtonPanel().getButton2().setVisible(false);
		buchView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		buchView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
