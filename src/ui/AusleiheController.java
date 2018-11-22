package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.AnredeDAO;
import dao.AusleiheDAO;
import dao.MitarbeiterDAO;
import dao.OrtDAO;
import dao.StatusDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Ausleihe;
import domain.EingeloggterMA;
import domain.Mitarbeiter;
import domain.Ort;
import domain.Status;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelAusleihe;
import services.AusleiheService;
import services.Verifikation;

/**
 * 
 * Controller für die AusleiheView, der die Logik und die Ausleihaktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 22.11.2018
 * @author irina
 *
 */

public class AusleiheController {
	private AusleiheView ausleiheView;
	private AusleiheService ausleiheService;
	private List<Ausleihe> ausleiheL;
	private TableModelAusleihe tableModelAusleihe;
	private Ausleihe ausleiheSuchobjekt;
	private OrtDAO ortDao;
	private StatusDAO statusDao;
	private AnredeDAO anredeDao;
	private HauptController hauptController;

	public AusleiheController(AusleiheView view, HauptController hauptController) {
		ausleiheView = view;
		this.hauptController = hauptController;
		ausleiheService = new AusleiheService();
		ausleiheL = new ArrayList<>();
		ortDao = new OrtDAO();
		statusDao = new StatusDAO();
		anredeDao = new AnredeDAO();
		tableModelAusleihe = new TableModelAusleihe();
		tableModelAusleihe.setAndSortListe(ausleiheL);
		view.getAusleiheTabelle().setModel(tableModelAusleihe);
		view.spaltenBreiteSetzen();
		ausleiheSuchobjekt = new Ausleihe();

		initialisieren();
		control();
	}

	// Buttons
	private void control() {
		// Suchen
		ActionListener suchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ausleiheSuchobjekt = feldwertezuObjektSuchen();
				ausleiheL = ausleiheService.sucheAusleihe(ausleiheSuchobjekt);
				tableModelAusleihe.setAndSortListe(ausleiheL);
			}
		};
		ausleiheView.getSuchButton().addActionListener(suchenButtonActionListener);
		
		// Neu
		ActionListener neuButtonActionListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				felderLeeren();
				ausleiheView.getNeuAendernL().setText("Neuerfassung");
			}
		};
		ausleiheView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		// Speichern
		ActionListener sichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ausleihe b = new Ausleihe();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					if (ausleiheView.getPKT().getText().isEmpty()) {
						nachArbeitSpeichern(ausleiheService.sichereAusleihe(b));
					} else {
						nachArbeitSpeichern(ausleiheService.aktualisiereAusleihe(b));
					}
				}
			}
		};
		ausleiheView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		
		// Schliessen
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		ausleiheView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);
		
		// Doppelklick = Uebernehmen
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
					ausleiheView.getNeuAendernL().setText("Bearbeiten");
				}
			}
		};
		ausleiheView.getAusleiheTabelle().addMouseListener(doppelKlick);
		
		//Dropdown PLZ Suche
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
				ausleiheView.getOrtSucheT().setText(ort.getOrt());
		    }
		};
		ausleiheView.getPlzSucheCbx().addActionListener(plzCbxSucheListener);
		
		//Dropdown PLZ Neu/Bearbeiten
		ActionListener plzCbxListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<Ort> c = (JComboBox) e.getSource();
				int ortId = c.getSelectedIndex();
				OrtDAO ortDAO = new OrtDAO();
				Ort ortFromDao = ortDAO.findById(ortId);
				Ort ort = new Ort();
				ort.setId(ortFromDao.getId());
				ort.setPlz(ortFromDao.getPlz());
				ort.setOrt(ortFromDao.getOrt());
				ausleiheView.getOrtT().setText(ort.getOrt());
		    }
		};
		ausleiheView.getPlzCbx().addActionListener(plzCbxListener);
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (ausleiheView.getNachnameT().getText().isEmpty() || (ausleiheView.getVornameT().getText().isEmpty())
				|| ausleiheView.getStrasseNrT().getText().isEmpty() || ausleiheView.getPlzCbx().getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!ausleiheView.getGeburtsdatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(ausleiheView.getGeburtsdatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
				ausleiheView.getGeburtsdatumL().setText("");
				keinInputFehler = false;
			}
		}
		return keinInputFehler;
	}

	private Ausleihe feldwertezuObjektSpeichern() {
		Ausleihe b = new Ausleihe();
		if (!ausleiheView.getPKT().getText().isEmpty()) {
			b.setId(Integer.parseInt(ausleiheView.getPKT().getText()));
		}else {
			b.setId(0);
		}
		if (!ausleiheView.getVornameT().getText().isEmpty()) {
			b.setVorname(ausleiheView.getVornameT().getText());
		}		
		if (!ausleiheView.getNachnameT().getText().isEmpty()) {
			b.setName(ausleiheView.getNachnameT().getText());
		}
		if (!ausleiheView.getStrasseNrT().getText().isEmpty()) {
			String strasse = ausleiheView.getStrasseNrT().getText();
			Ort plzSelected = (Ort) ausleiheView.getPlzCbx().getSelectedItem();
			int ortId = plzSelected.getId();
			int plz = plzSelected.getPlz();
			String ortString = ausleiheView.getOrtT().getText();
			Ort ort = new Ort(ortId, plz, ortString);
			Adresse adresse = new Adresse(strasse, ort);
			b.setAdresse(adresse);
		}
		if (!ausleiheView.getGeburtsdatumT().getText().isEmpty()) {
			b.setGeburtsdatum(DateConverter.convertStringToJavaDate(ausleiheView.getGeburtsdatumT().getText()));
		}
		if (!ausleiheView.getTelT().getText().isEmpty()) {
			b.setTelefon(ausleiheView.getTelT().getText());
		}
		if (!ausleiheView.getMailT().getText().isEmpty()) {
			b.setEmail(ausleiheView.getMailT().getText());
		}
		if (!ausleiheView.getBemerkungT().getText().isEmpty()) {
			b.setBemerkung(ausleiheView.getBemerkungT().getText());
		}
		Status statusSelected = (Status)ausleiheView.getStatusCbx().getSelectedItem();
        b.setAusleiheStatus(statusSelected);

        Anrede auswahlAnrede = (Anrede)ausleiheView.getAnredeCbx().getSelectedItem();
        b.setAnrede(auswahlAnrede);
        
        if (!ausleiheView.getErfasstVonT().getText().isEmpty() || !ausleiheView.getErfasstVonT().getText().equals("")) {
        	MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
        	b.setErfassungMitarbeiter(mitarbeiterDAO.findByAusleihename(ausleiheView.getErfasstVonT().getText()));
		}
        else {
        	b.setErfassungMitarbeiter(EingeloggterMA.getInstance().getMitarbeiter());
        }
        if (!ausleiheView.getErfasstAmT().getText().isEmpty() || !ausleiheView.getErfasstAmT().getText().equals("")) {
        	b.setErfassungDatum(DateConverter.convertStringToJavaDate(ausleiheView.getErfasstAmT().getText()));
		}else {
			Date date = new Date();
        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			b.setErfassungDatum(date);
		}
		return b;
	}

	private Ausleihe feldwertezuObjektSuchen() {
		Ausleihe b = new Ausleihe();
		if (!ausleiheView.getPKSucheT().getText().isEmpty()) {
			b.setId(Integer.parseInt(ausleiheView.getPKSucheT().getText()));
		}
		if (!ausleiheView.getNachnameSucheT().getText().isEmpty()) {
			b.setName(ausleiheView.getNachnameSucheT().getText());
		}
		if (!ausleiheView.getVornameSucheT().getText().isEmpty()) {
			b.setVorname(ausleiheView.getVornameSucheT().getText());
		}
		if (ausleiheView.getPlzSucheCbx().getSelectedIndex() != -1) {
			Ort plzSelected = (Ort) ausleiheView.getPlzSucheCbx().getSelectedItem();
			OrtDAO ortDAO = new OrtDAO();
			Ort ortFromDao = ortDAO.findById(plzSelected.getId());
			Ort ort = new Ort();
			ort.setId(ortFromDao.getId());
			ort.setPlz(ortFromDao.getPlz());
			ort.setOrt(ortFromDao.getOrt());
			Adresse adresse = new Adresse(ortFromDao);
			b.setAdresse(adresse);
		}
		if (!ausleiheView.getStrasseNrSucheT().getText().isEmpty()) {
			String strasse = ausleiheView.getStrasseNrSucheT().getText();
			Adresse adresse = new Adresse(strasse, null);
			b.setAdresse(adresse);
		}
		Status statusSelected = (Status)ausleiheView.getStatusSucheCbx().getSelectedItem();
        b.setAusleiheStatus(statusSelected);
		return b;
	}

	private void uebernehmen() {
		felderLeeren();
		Ausleihe ausleihe = new Ausleihe();
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		ausleihe = tableModelAusleihe.getGeklicktesObjekt(ausleiheView.getAusleiheTabelle().getSelectedRow());
		ausleihe = ausleiheDAO.findById(ausleihe.getId());
		ausleiheView.getPKT().setText(Integer.toString(ausleihe.getId()));
		ausleiheView.getNachnameT().setText(ausleihe.getName());
		ausleiheView.getVornameT().setText(ausleihe.getVorname());
		if(ausleihe.getAdresse() != null) {
			Adresse adresse = ausleihe.getAdresse();
			String strasseNr = adresse.getStrasse();
			Ort ort = adresse.getOrt();
			int ortId = ort.getId();
			ausleiheView.getStrasseNrT().setText(strasseNr);
			ausleiheView.getPlzCbx().setSelectedIndex(ortId);
		}
		if (ausleihe.getGeburtsdatum() != null) {
			ausleiheView.getGeburtsdatumT().setText(DateConverter.convertJavaDateToString(ausleihe.getGeburtsdatum()));
		}else {
			ausleiheView.getGeburtsdatumT().setText("");
		}
		if (ausleihe.getTelefon() != null) {
			ausleiheView.getTelT().setText(ausleihe.getTelefon());
		}else {
			ausleiheView.getTelT().setText("");
		}
		if (ausleihe.getEmail() != null) {
			ausleiheView.getMailT().setText(ausleihe.getEmail());
		}else {
			ausleiheView.getMailT().setText("");
		}
		if (ausleihe.getBemerkung() != null) {
			ausleiheView.getBemerkungT().setText(ausleihe.getBemerkung());
		}else {
			ausleiheView.getBemerkungT().setText("");
		}
		ausleiheView.getStatusCbx().setSelectedIndex(ausleihe.getAusleiheStatus().getId() -1 );
		ausleiheView.getAnredeCbx().setSelectedIndex(ausleihe.getAnrede().getId() - 1);
		if (ausleihe.getErfassungMitarbeiter() != null) {
        	ausleiheView.getErfasstVonT().setText(ausleihe.getErfassungMitarbeiter().getAusleihename());
		}else {
			ausleiheView.getErfasstVonT().setText("");
		}
        if (ausleihe.getErfassungDatum() != null) {
			ausleiheView.getErfasstAmT().setText(DateConverter.convertJavaDateToString(ausleihe.getErfassungDatum()));
		}else {
			ausleiheView.getErfasstAmT().setText("");
		}
	}

	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelAusleihe.setAndSortListe(ausleiheService.sucheAusleihe(ausleiheSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		felderLeeren();
		ausleiheView.getNeuAendernL().setText("");
	}

	// Felder leeren
	private void felderLeeren() {
		for (Component t : ausleiheView.getAusleiheNeuBearbeitenPanel().getComponents()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JTextArea) {
				((JTextArea) t).setText("");
			}
			if (t instanceof JComboBox) {
				((JComboBox) t).setSelectedIndex(0);
			}

		}
		
		Component[] components = ausleiheView.getAusleiheNeuBearbeitenPanel().getComponents();
		
		for (int i = 0; i < components.length; ++i) {
			
		   if (components[i] instanceof Container) {
			   
		       Container subContainer = (Container)components[i];
		       Component[] containers = subContainer.getComponents();
		       
		       for(Component containerComponent : containers)
				{
				    if(containerComponent instanceof JTextField)
				    {
				        JTextField compo = (JTextField) containerComponent;
				        compo.setText("");
				    }
				    else if(containerComponent instanceof JTextArea)
				    {
				    	JTextArea compo = (JTextArea) containerComponent;
				        compo.setText("");
				    }
				    else if (containerComponent instanceof JComboBox)
				    {
				        JComboBox<Object> compo = (JComboBox) containerComponent;
				        compo.setSelectedIndex(0);
				    }
				}
		   }
		}
	}

	public void initialisieren() {

		ausleiheView.getPKL().setText("Ausleihe-ID:");
		ausleiheView.getNachnameL().setText("Nachname:*");
		ausleiheView.getVornameL().setText("Vorname:*");
		ausleiheView.getStrasseNrL().setText("Strasse/Nr.:");
		ausleiheView.getPlzL().setText("PLZ:");
		ausleiheView.getOrtL().setText("Ort:");
		ausleiheView.getGeburtsdatumL().setText("Geburtsdatum:");
		ausleiheView.getTelL().setText("Telefonnummer:");
		ausleiheView.getMailL().setText("E-Mailadresse:");
		ausleiheView.getBemerkungL().setText("Bemerkung: ");
		ausleiheView.getStatusL().setText("Status:");
		ausleiheView.getAnredeL().setText("Anrede:");
		ausleiheView.getErfasstAmL().setText("Erfasst am:");
		ausleiheView.getErfasstVonL().setText("Erfasst von:");
		
		ausleiheView.getPKSucheL().setText("Ausleihe-ID:");
		ausleiheView.getNachnameSucheL().setText("Nachname:");
		ausleiheView.getVornameSucheL().setText("Vorname:");
		ausleiheView.getStrasseNrSucheL().setText("Strasse/Nr.:");
		ausleiheView.getPlzSucheL().setText("PLZ:");
		ausleiheView.getOrtSucheL().setText("Ort:");
		ausleiheView.getStatusSucheL().setText("Status:");
		
		StatusRenderer statusR = new StatusRenderer();
		ausleiheView.getStatusCbx().setRenderer(statusR);
		for(Status s : statusDao.findAll()) {
			ausleiheView.getStatusCbx().addItem(s);
		}
		ausleiheView.getStatusCbx().setSelectedIndex(0);
		
		StatusSucheRenderer statusSucheR = new StatusSucheRenderer();
		ausleiheView.getStatusSucheCbx().setRenderer(statusSucheR);
		ausleiheView.getStatusSucheCbx().addItem(null);
		for(Status s : statusDao.findAll()) {
			ausleiheView.getStatusSucheCbx().addItem(s);
		}
		ausleiheView.getStatusSucheCbx().setSelectedIndex(0);
		
		PlzRenderer plzR = new PlzRenderer();
		ausleiheView.getPlzCbx().setRenderer(plzR);
		ausleiheView.getPlzCbx().addItem(null);
		for(Ort o : ortDao.findAll()) {
			ausleiheView.getPlzCbx().addItem(o);
		}
		ausleiheView.getPlzCbx().setMaximumRowCount(10);
		ausleiheView.getPlzCbx().setSelectedIndex(0);
		
		PlzSucheRenderer plzSucheR = new PlzSucheRenderer();
		ausleiheView.getPlzSucheCbx().setRenderer(plzSucheR);
		ausleiheView.getPlzSucheCbx().addItem(null);
		ausleiheView.getPlzSucheCbx().setEditable(true);
		for(Ort o : ortDao.findAll()) {
			ausleiheView.getPlzSucheCbx().addItem(o);
		}
		ausleiheView.getPlzSucheCbx().setMaximumRowCount(10);
		ausleiheView.getPlzSucheCbx().setSelectedIndex(0);
		
		AnredeRenderer anredeR = new AnredeRenderer();
		ausleiheView.getAnredeCbx().setRenderer(anredeR);
		for(Anrede a : anredeDao.findAll()) {
			ausleiheView.getAnredeCbx().addItem(a);
		}
		ausleiheView.getAnredeCbx().setSelectedIndex(0);
		
		ausleiheView.getSuchButton().setText("Suchen");
		ausleiheView.getPKT().setEditable(false);
		ausleiheView.getOrtT().setEditable(false);
		ausleiheView.getOrtSucheT().setEditable(false);
		ausleiheView.getErfasstVonT().setEditable(false);
		ausleiheView.getErfasstAmT().setEditable(false);
		ausleiheView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		ausleiheView.getButtonPanel().getButton2().setVisible(false);
		ausleiheView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		ausleiheView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
