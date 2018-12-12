package ui.benutzer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.AnredeDAO;
import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import dao.OrtDAO;
import dao.StatusDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Benutzer;
import domain.EingeloggterMA;
import domain.Ort;
import domain.Status;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelBenutzer;
import services.BenutzerService;
import services.Verifikation;
import ui.HauptController;
import ui.renderer.AnredeRenderer;
import ui.renderer.PlzRenderer;
import ui.renderer.PlzSucheRenderer;
import ui.status.StatusRenderer;
import ui.status.StatusSucheRenderer;

/**
 * Controller für die Benutzer-View, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 06.11.2018
 * @author Irina
 * 
 */

public class BenutzerController {
	private BenutzerView benutzerView;
	private BenutzerService benutzerService;
	private List<Benutzer> benutzerL;
	private TableModelBenutzer tableModelBenutzer;
	private Benutzer benutzerSuchobjekt;
	private OrtDAO ortDao;
	private StatusDAO statusDao;
	private AnredeDAO anredeDao;
	private HauptController hauptController;

	public BenutzerController(BenutzerView view, HauptController hauptController) {
		benutzerView = view;
		this.hauptController = hauptController;
		benutzerService = new BenutzerService();
		benutzerL = new ArrayList<>();
		ortDao = new OrtDAO();
		statusDao = new StatusDAO();
		anredeDao = new AnredeDAO();
		tableModelBenutzer = new TableModelBenutzer();
		tableModelBenutzer.setAndSortListe(benutzerL);
		view.getBenutzerTabelle().setModel(tableModelBenutzer);
		view.spaltenBreiteSetzen();
		benutzerSuchobjekt = new Benutzer();
		initialisieren();
		control();
	}

	// Buttons
	private void control() {
		// Suchen
		ActionListener suchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerSuchobjekt = feldwertezuObjektSuchen();
				benutzerL = benutzerService.suchenBenutzer(benutzerSuchobjekt);
				tableModelBenutzer.setAndSortListe(benutzerL);
			}
		};
		benutzerView.getSuchButton().addActionListener(suchenButtonActionListener);

		// Neu
		ActionListener neuButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				felderLeeren();
				benutzerView.getNeuAendernL().setText("Neuerfassung");
			}
		};
		benutzerView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		// Speichern
		ActionListener speichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Benutzer b = new Benutzer();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					if (benutzerView.getPKT().getText().isEmpty()) {
						nachArbeitSpeichern(benutzerService.speichernBenutzer(b));
					} else {
						nachArbeitSpeichern(benutzerService.aktualisierenBenutzer(b));
					}
					felderLeeren();
				}
			}
		};
		benutzerView.getButtonPanel().getButton3().addActionListener(speichernButtonActionListener);

		// Schliessen
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		benutzerView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		// Doppelklick = Uebernehmen
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					benutzerView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};
		benutzerView.getBenutzerTabelle().addMouseListener(doppelKlick);

		// Dropdown PLZ Suche
		ActionListener plzCbxSucheListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<Ort> c = (JComboBox<Ort>) e.getSource();
				int ortId = c.getSelectedIndex();
				OrtDAO ortDAO = new OrtDAO();
				Ort ortFromDao = ortDAO.findById(ortId);
				Ort ort = new Ort();
				ort.setId(ortFromDao.getId());
				ort.setPlz(ortFromDao.getPlz());
				ort.setOrt(ortFromDao.getOrt());
			}
		};
		benutzerView.getPlzOrtSucheCbx().addActionListener(plzCbxSucheListener);

		// Dropdown PLZ Neu/Bearbeiten
		ActionListener plzCbxListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<Ort> c = (JComboBox<Ort>) e.getSource();
				int ortId = c.getSelectedIndex();
				OrtDAO ortDAO = new OrtDAO();
				Ort ortFromDao = ortDAO.findById(ortId);
				Ort ort = new Ort();
				ort.setId(ortFromDao.getId());
				ort.setPlz(ortFromDao.getPlz());
				ort.setOrt(ortFromDao.getOrt());
			}
		};
		benutzerView.getPlzOrtCbx().addActionListener(plzCbxListener);
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (benutzerView.getNachnameT().getText().isEmpty() || (benutzerView.getVornameT().getText().isEmpty())
				|| benutzerView.getStrasseNrT().getText().isEmpty()
				|| benutzerView.getPlzOrtCbx().getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			return keinInputFehler = false;
		}
		if (!benutzerView.getGeburtsdatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getGeburtsdatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
				benutzerView.getGeburtsdatumT().setText("");
				return keinInputFehler = false;
			}
		}
		if (benutzerView.getNachnameT().getText().length() > 30) {
			JOptionPane.showMessageDialog(null, "Der Nachname ist zu lang.");
			benutzerView.getNachnameT().setText(benutzerView.getNachnameT().getText().substring(0, 30));
			return keinInputFehler = false;
		}
		if (benutzerView.getVornameT().getText().length() > 30) {
			JOptionPane.showMessageDialog(null, "Der Vorname ist zu lang.");
			benutzerView.getVornameT().setText(benutzerView.getVornameT().getText().substring(0, 30));
			return keinInputFehler = false;
		}
		if (benutzerView.getStrasseNrT().getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "Die Strasse/Nr. ist zu lang.");
			benutzerView.getStrasseNrT().setText(benutzerView.getStrasseNrT().getText().substring(0, 50));
			return keinInputFehler = false;
		}
		if (benutzerView.getTelT().getText().length() > 30) {
			JOptionPane.showMessageDialog(null, "Die Telefonnummer ist zu lang.");
			benutzerView.getTelT().setText(benutzerView.getTelT().getText().substring(0, 30));
			return keinInputFehler = false;
		}
		if (benutzerView.getMailT().getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "Die E-Mailadresse ist zu lang.");
			benutzerView.getMailT().setText(benutzerView.getMailT().getText().substring(0, 50));
			return keinInputFehler = false;
		}
		if (benutzerView.getBemerkungT().getText().length() > 300) {
			JOptionPane.showMessageDialog(null, "Die Bemerkung ist zu lang.");
			benutzerView.getBemerkungT().setText(benutzerView.getBemerkungT().getText().substring(0, 300));
			return keinInputFehler = false;
		}
		return keinInputFehler;
	}

	private Benutzer feldwertezuObjektSpeichern() {
		Benutzer b = new Benutzer();
		if (!benutzerView.getPKT().getText().isEmpty()) {
			b.setId(Integer.parseInt(benutzerView.getPKT().getText()));
		} else {
			b.setId(0);
		}
		if (!benutzerView.getVornameT().getText().isEmpty()) {
			b.setVorname(benutzerView.getVornameT().getText());
		}
		if (!benutzerView.getNachnameT().getText().isEmpty()) {
			b.setName(benutzerView.getNachnameT().getText());
		}
		if (!benutzerView.getStrasseNrT().getText().isEmpty()) {
			String strasse = benutzerView.getStrasseNrT().getText();
			Ort plzSelected = (Ort) benutzerView.getPlzOrtCbx().getSelectedItem();
			int ortId = plzSelected.getId();
			int plz = plzSelected.getPlz();
			String ortString = plzSelected.getOrt();
			Ort ort = new Ort(ortId, plz, ortString);
			Adresse adresse = new Adresse(strasse, ort);
			b.setAdresse(adresse);
		}
		if (!benutzerView.getGeburtsdatumT().getText().isEmpty()) {
			b.setGeburtsdatum(DateConverter.convertStringToJavaDate(benutzerView.getGeburtsdatumT().getText()));
		}
		if (!benutzerView.getTelT().getText().isEmpty()) {
			b.setTelefon(benutzerView.getTelT().getText());
		}
		if (!benutzerView.getMailT().getText().isEmpty()) {
			b.setEmail(benutzerView.getMailT().getText());
		}
		if (!benutzerView.getBemerkungT().getText().isEmpty()) {
			b.setBemerkung(benutzerView.getBemerkungT().getText());
		}
		Status statusSelected = (Status) benutzerView.getStatusCbx().getSelectedItem();
		b.setBenutzerStatus(statusSelected);

		Anrede auswahlAnrede = (Anrede) benutzerView.getAnredeCbx().getSelectedItem();
		b.setAnrede(auswahlAnrede);

		if (benutzerView.getErfasstVonT().getText().isEmpty() || benutzerView.getErfasstVonT().getText().equals("")) {
			b.setErfassungMitarbeiterId(EingeloggterMA.getInstance().getMitarbeiter().getId());
			String nachname = EingeloggterMA.getInstance().getMitarbeiter().getName();
			String vorname = EingeloggterMA.getInstance().getMitarbeiter().getVorname();
			String name = nachname + " " + vorname;
			b.setErfassungMitarbeiterName(name);
		} else {
			MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
			b.setErfassungMitarbeiterName(benutzerView.getErfasstVonT().getText());
			String nameVorname = benutzerView.getErfasstVonT().getText();
			String[] splitName = nameVorname.split(" ");
			String name = splitName[0];
			String vorname = splitName[1];
			int erfassungMitarbeiterId = mitarbeiterDAO.findIdByName(name, vorname);
			b.setErfassungMitarbeiterId(erfassungMitarbeiterId);
		}
		if (!benutzerView.getErfasstAmT().getText().isEmpty() || !benutzerView.getErfasstAmT().getText().equals("")) {
			b.setErfassungDatum(DateConverter.convertStringToJavaDate(benutzerView.getErfasstAmT().getText()));
		} else {
			Date date = new Date();
			b.setErfassungDatum(date);
		}
		return b;
	}

	private Benutzer feldwertezuObjektSuchen() {
		Benutzer b = new Benutzer();
		if (!benutzerView.getPKSucheT().getText().isEmpty()) {
			b.setId(Integer.parseInt(benutzerView.getPKSucheT().getText()));
		}
		if (!benutzerView.getNachnameSucheT().getText().isEmpty()) {
			b.setName(benutzerView.getNachnameSucheT().getText());
		}
		if (!benutzerView.getVornameSucheT().getText().isEmpty()) {
			b.setVorname(benutzerView.getVornameSucheT().getText());
		}
		if (benutzerView.getPlzOrtSucheCbx().getSelectedIndex() != -1) {
			Ort plzSelected = (Ort) benutzerView.getPlzOrtSucheCbx().getSelectedItem();
			OrtDAO ortDAO = new OrtDAO();
			Ort ortFromDao = ortDAO.findById(plzSelected.getId());
			Ort ort = new Ort();
			ort.setId(ortFromDao.getId());
			ort.setPlz(ortFromDao.getPlz());
			ort.setOrt(ortFromDao.getOrt());
			Adresse adresse = new Adresse(ortFromDao);
			b.setAdresse(adresse);
		}
		if (!benutzerView.getStrasseNrSucheT().getText().isEmpty()) {
			String strasse = benutzerView.getStrasseNrSucheT().getText();
			Adresse adresse = new Adresse(strasse, null);
			b.setAdresse(adresse);
		}
		Status statusSelected = (Status) benutzerView.getStatusSucheCbx().getSelectedItem();
		b.setBenutzerStatus(statusSelected);
		return b;
	}

	private void uebernehmen() {
		felderLeeren();
		Benutzer benutzer = new Benutzer();
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		benutzer = tableModelBenutzer.getGeklicktesObjekt(benutzerView.getBenutzerTabelle().getSelectedRow());
		benutzer = benutzerDAO.findById(benutzer.getId());
		benutzerView.getPKT().setText(Integer.toString(benutzer.getId()));
		benutzerView.getNachnameT().setText(benutzer.getName());
		benutzerView.getVornameT().setText(benutzer.getVorname());
		if (benutzer.getAdresse() != null) {
			Adresse adresse = benutzer.getAdresse();
			String strasseNr = adresse.getStrasse();
			Ort ort = adresse.getOrt();
			int ortId = ort.getId();
			benutzerView.getStrasseNrT().setText(strasseNr);
			benutzerView.getPlzOrtCbx().setSelectedIndex(ortId);
		}
		if (benutzer.getGeburtsdatum() != null) {
			benutzerView.getGeburtsdatumT().setText(DateConverter.convertJavaDateToString(benutzer.getGeburtsdatum()));
		} else {
			benutzerView.getGeburtsdatumT().setText("");
		}
		if (benutzer.getTelefon() != null) {
			benutzerView.getTelT().setText(benutzer.getTelefon());
		} else {
			benutzerView.getTelT().setText("");
		}
		if (benutzer.getEmail() != null) {
			benutzerView.getMailT().setText(benutzer.getEmail());
		} else {
			benutzerView.getMailT().setText("");
		}
		if (benutzer.getBemerkung() != null) {
			benutzerView.getBemerkungT().setText(benutzer.getBemerkung());
		} else {
			benutzerView.getBemerkungT().setText("");
		}
		benutzerView.getStatusCbx().setSelectedIndex(benutzer.getBenutzerStatus().getId() - 1);
		benutzerView.getAnredeCbx().setSelectedIndex(benutzer.getAnrede().getId() - 1);
		if (benutzer.getErfassungMitarbeiterName() != null) {
			benutzerView.getErfasstVonT().setText(benutzer.getErfassungMitarbeiterName());
		} else {
			benutzerView.getErfasstVonT().setText("");
		}
		if (benutzer.getErfassungDatum() != null) {
			benutzerView.getErfasstAmT().setText(DateConverter.convertJavaDateToString(benutzer.getErfassungDatum()));
		} else {
			benutzerView.getErfasstAmT().setText("");
		}
	}

	private void nachArbeitSpeichern(Verifikation v) {
		felderLeeren();
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelBenutzer.setAndSortListe(benutzerService.suchenBenutzer(benutzerSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		benutzerView.getNeuAendernL().setText("");
	}

	private void felderLeeren() {
		benutzerView.getBemerkungT().setText("");
		for (Component t : benutzerView.getBenutzerNeuBearbeitenPanel().getComponents()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JComboBox) {
				((JComboBox) t).setSelectedIndex(0);
			}
		}
	}

	public void initialisieren() {
		benutzerView.getPKL().setText("Benutzer-ID:");
		benutzerView.getNachnameL().setText("Nachname:*");
		benutzerView.getVornameL().setText("Vorname:*");
		benutzerView.getStrasseNrL().setText("Strasse/Nr.:*");
		benutzerView.getPlzOrtL().setText("PLZ/Ort:*");
		benutzerView.getGeburtsdatumL().setText("Geburtsdatum:");
		benutzerView.getTelL().setText("Telefonnummer:");
		benutzerView.getMailL().setText("E-Mailadresse:");
		benutzerView.getBemerkungL().setText("Bemerkung: ");
		benutzerView.getStatusL().setText("Status:*");
		benutzerView.getAnredeL().setText("Anrede:*");
		benutzerView.getErfasstAmL().setText("Erfasst am:*");
		benutzerView.getErfasstVonL().setText("Erfasst von:*");

		benutzerView.getPKSucheL().setText("Benutzer-ID:");
		benutzerView.getNachnameSucheL().setText("Nachname:");
		benutzerView.getVornameSucheL().setText("Vorname:");
		benutzerView.getStrasseNrSucheL().setText("Strasse/Nr.:");
		benutzerView.getPlzOrtSucheL().setText("PLZ/Ort:");
		benutzerView.getStatusSucheL().setText("Status:");
		TextComponentLimit.addTo(benutzerView.getBemerkungT(), 300);
		TextComponentLimit.addTo(benutzerView.getMailT(), 50);
		TextComponentLimit.addTo(benutzerView.getTelT(), 30);
		TextComponentLimit.addTo(benutzerView.getGeburtsdatumT(), 10);
		TextComponentLimit.addTo(benutzerView.getStrasseNrT(), 50);
		TextComponentLimit.addTo(benutzerView.getPKT(), 15);
		TextComponentLimit.addTo(benutzerView.getVornameT(), 30);
		TextComponentLimit.addTo(benutzerView.getNachnameT(), 30);
		TextComponentLimit.addTo(benutzerView.getPKSucheT(), 15);
		TextComponentLimit.addTo(benutzerView.getNachnameSucheT(), 30);
		TextComponentLimit.addTo(benutzerView.getVornameSucheT(), 30);
		TextComponentLimit.addTo(benutzerView.getStrasseNrSucheT(), 50);
		StatusRenderer statusR = new StatusRenderer();
		benutzerView.getStatusCbx().setRenderer(statusR);
		for (Status s : statusDao.findAll()) {
			benutzerView.getStatusCbx().addItem(s);
		}
		benutzerView.getStatusCbx().setSelectedIndex(0);

		StatusSucheRenderer statusSucheR = new StatusSucheRenderer();
		benutzerView.getStatusSucheCbx().setRenderer(statusSucheR);
		benutzerView.getStatusSucheCbx().addItem(null);
		for (Status s : statusDao.findAll()) {
			benutzerView.getStatusSucheCbx().addItem(s);
		}
		benutzerView.getStatusSucheCbx().setSelectedIndex(0);

		PlzRenderer plzR = new PlzRenderer();
		benutzerView.getPlzOrtCbx().setRenderer(plzR);
		benutzerView.getPlzOrtCbx().addItem(null);
		for (Ort o : ortDao.findAll()) {
			benutzerView.getPlzOrtCbx().addItem(o);
		}
		benutzerView.getPlzOrtCbx().setMaximumRowCount(10);
		benutzerView.getPlzOrtCbx().setSelectedIndex(0);

		PlzSucheRenderer plzSucheR = new PlzSucheRenderer();
		benutzerView.getPlzOrtSucheCbx().setRenderer(plzSucheR);
		benutzerView.getPlzOrtSucheCbx().addItem(null);
		// benutzerView.getPlzOrtSucheCbx().setEditable(true);
		for (Ort o : ortDao.findAll()) {
			benutzerView.getPlzOrtSucheCbx().addItem(o);
		}
		benutzerView.getPlzOrtSucheCbx().setMaximumRowCount(10);
		benutzerView.getPlzOrtSucheCbx().setSelectedIndex(0);

		AnredeRenderer anredeR = new AnredeRenderer();
		benutzerView.getAnredeCbx().setRenderer(anredeR);
		for (Anrede a : anredeDao.findAll()) {
			benutzerView.getAnredeCbx().addItem(a);
		}
		benutzerView.getAnredeCbx().setSelectedIndex(0);
		benutzerView.getSuchButton().setText("Suchen");
		benutzerView.getPKT().setEditable(false);
		benutzerView.getErfasstVonT().setEditable(false);
		benutzerView.getErfasstAmT().setEditable(false);
		benutzerView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		benutzerView.getButtonPanel().getButton2().setVisible(false);
		benutzerView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		benutzerView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
