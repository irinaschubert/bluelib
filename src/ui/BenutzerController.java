package ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.BenutzerDAO;
import dao.OrtDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Benutzer;
import domain.Ort;
import domain.Status;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelBenutzer;
import services.BenutzerService;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die Benutzer-View, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 06.11.2018
 * @author irina
 *
 */

public class BenutzerController {
	private BenutzerView benutzerView;
	private BenutzerService benutzerService;
	private List<Benutzer> benutzerL;
	private TableModelBenutzer tableModelBenutzer;
	private Benutzer benutzerSuchobjekt;

	public BenutzerController(BenutzerView view) {
		benutzerView = view;
		benutzerService = new BenutzerService();
		benutzerL = new ArrayList<>();
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
				benutzerL = benutzerService.sucheBenutzer(benutzerSuchobjekt);
				tableModelBenutzer.setAndSortListe(benutzerL);
			}
		};
		benutzerView.getSuchButton().addActionListener(suchenButtonActionListener);
		
		// Neu
		ActionListener neuButtonActionListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				felderLeeren();
				benutzerView.getNeuAendernL().setText("Neuerfassung");
			}
		};
		benutzerView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener);

		// Speichern
		ActionListener sichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Benutzer b = new Benutzer();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					if (benutzerView.getPKT().getText().isEmpty()) {
						nachArbeitSpeichern(benutzerService.sichereBenutzer(b));
					} else {
						nachArbeitSpeichern(benutzerService.aktualisiereBenutzer(b));
					}
				}
			}
		};
		benutzerView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener);

		
		// Schliessen
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				benutzerView.schliessen();
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
		
		//Dropdown PLZ/Ort Suche
		ActionListener plzCbxSucheListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox c = (JComboBox) e.getSource();
				Ort ort = (Ort) c.getSelectedItem();
				OrtDAO ortDAO = new OrtDAO();
				Ort ortFromDao = ortDAO.findById(ort.getId());
				ort.setId(ortFromDao.getId());
				ort.setPlz(ortFromDao.getPlz());
				ort.setOrt(ortFromDao.getOrt());
				benutzerView.getOrtSucheT().setText(ort.getOrt());
		    }
		};
		benutzerView.getPlzSucheCbx().addActionListener(plzCbxSucheListener);
		
		//Dropdown PLZ/Ort Neu/Bearbeiten
		ActionListener plzCbxListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox c = (JComboBox) e.getSource();
				Ort ort = (Ort) c.getSelectedItem();
				OrtDAO ortDAO = new OrtDAO();
				Ort ortFromDao = ortDAO.findById(ort.getId());
				ort.setId(ortFromDao.getId());
				ort.setPlz(ortFromDao.getPlz());
				ort.setOrt(ortFromDao.getOrt());
				benutzerView.getOrtT().setText(ort.getOrt());
		    }
		};
		benutzerView.getPlzCbx().addActionListener(plzCbxListener);
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (benutzerView.getNachnameT().getText().isEmpty() || (benutzerView.getVornameT().getText().isEmpty())
				|| benutzerView.getStrasseNrT().getText().isEmpty() || benutzerView.getPlzCbx().getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		if (!benutzerView.getGeburtsdatumT().getText().isEmpty()) {
			if (!DateConverter.datumIstGueltig(benutzerView.getGeburtsdatumT().getText())) {
				JOptionPane.showMessageDialog(null, "Ungültiges Geburtsdatum");
				benutzerView.getGeburtsdatumL().setText("");
				keinInputFehler = false;
			}
		}
		return keinInputFehler;
	}

	private Benutzer feldwertezuObjektSpeichern() {
		Benutzer b = new Benutzer();
		if (!benutzerView.getPKT().getText().isEmpty()) {
			b.setId(Integer.parseInt(benutzerView.getPKT().getText()));
		}else {
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
			Ort plzSelected = (Ort) benutzerView.getPlzCbx().getSelectedItem();
			int ortId = plzSelected.getId();
			int plz = plzSelected.getPlz();
			String ortString = benutzerView.getOrtT().getText();
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
		// TODO Dropdown richtig aufsetzen (wie PLZ mit Renderer etc.)
		String auswahlStatusString = (String)benutzerView.getStatusCbx().getSelectedItem();
        if(auswahlStatusString.equals("aktiv")) {
        	b.setBenutzerStatus(Status.AKTIV);
        }
        if(auswahlStatusString.equals("gesperrt")) {
        	b.setBenutzerStatus(Status.GESPERRT);
        }
        if(auswahlStatusString.equals("gelöscht")) {
        	b.setBenutzerStatus(Status.GELOESCHT);
        }
        Anrede auswahlAnrede = (Anrede)benutzerView.getAnredeCbx().getSelectedItem();
        if(auswahlAnrede.getBezeichnung().equals("Frau")) {
        	b.setAnrede(2);
        }
        if(auswahlAnrede.getBezeichnung().equals("Herr")) {
        	b.setAnrede(1);
        }
        if (!benutzerView.getErfasstVonT().getText().isEmpty()) {
        	// TODO
			//b.setErfassungMitarbeiter(benutzerView.getErfasstVonT().getText());
		}
        if (!benutzerView.getErfasstAmT().getText().isEmpty()) {
			b.setErfassungDatum(DateConverter.convertStringToJavaDate(benutzerView.getErfasstAmT().getText()));
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
		// TODO Trennen von Suche nach Strasse/Nr und PLZ/Ort
		if (!benutzerView.getOrtSucheT().getText().isEmpty()) {
			String strasse = benutzerView.getStrasseNrSucheT().getText();
			Ort plzSelected = (Ort) benutzerView.getPlzSucheCbx().getSelectedItem();
			int ortId = plzSelected.getId();
			int plz = plzSelected.getPlz();
			String ortString = benutzerView.getOrtSucheT().getText();
			Ort ort = new Ort(ortId, plz, ortString);
			Adresse adresse = new Adresse(strasse, ort);
			b.setAdresse(adresse);
		}
        String auswahlStatusString = (String)benutzerView.getStatusSucheCbx().getSelectedItem();
        if(auswahlStatusString.equals("aktiv")) {
        	b.setBenutzerStatus(Status.AKTIV);
        }
        if(auswahlStatusString.equals("gesperrt")) {
        	b.setBenutzerStatus(Status.GESPERRT);
        }
        if(auswahlStatusString.equals("gelöscht")) {
        	b.setBenutzerStatus(Status.GELOESCHT);
        }
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
		if(benutzer.getAdresse() != null) {
			Adresse adresse = benutzer.getAdresse();
			String strasseNr = adresse.getStrasse();
			int ortId = adresse.getId();
			benutzerView.getStrasseNrT().setText(strasseNr);
			benutzerView.getPlzCbx().setSelectedIndex(ortId + 1);
		}
		if (benutzer.getGeburtsdatum() != null) {
			benutzerView.getGeburtsdatumT().setText(DateConverter.convertJavaDateToString(benutzer.getGeburtsdatum()));
		}
		if (benutzer.getTelefon() != null) {
			benutzerView.getTelT().setText(benutzer.getTelefon());
		}
		if (benutzer.getEmail() != null) {
			benutzerView.getMailT().setText(benutzer.getEmail());
		}
		if (benutzer.getBemerkung() != null) {
			benutzerView.getBemerkungT().setText(benutzer.getBemerkung());
		}
		if (benutzer.getMitarbeiterStatus() == 1) {
			
		} else if (benutzer.getMitarbeiterStatus() != 1) {
			benutzerView.getMitarbeiterCbx().setSelected(false);
		}
        if(benutzer.getBenutzerStatus() == Status.AKTIV) {
        	benutzerView.getStatusCbx().setSelectedIndex(0);
        }
        if(benutzer.getBenutzerStatus() == Status.GESPERRT) {
        	benutzerView.getStatusCbx().setSelectedIndex(1);
        }
        if(benutzer.getBenutzerStatus() == Status.GELOESCHT) {
        	benutzerView.getStatusCbx().setSelectedIndex(2);
        }
        if(benutzer.getAnrede() == 1) {
        	benutzerView.getAnredeCbx().setSelectedIndex(0);
        }
        if(benutzer.getAnrede() == 2) {
        	benutzerView.getAnredeCbx().setSelectedIndex(1);
        }
	}

	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			tableModelBenutzer.setAndSortListe(benutzerService.sucheBenutzer(benutzerSuchobjekt));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		felderLeeren();
		benutzerView.getNeuAendernL().setText("");
	}

	// Felder leeren
	private void felderLeeren() {
		for(Component control : benutzerView.getBenutzerSuchenPanel().getComponents())
		{
		    if(control instanceof JTextField)
		    {
		        JTextField ctrl = (JTextField) control;
		        ctrl.setText("");
		    }
		    else if (control instanceof JComboBox)
		    {
		        JComboBox ctr = (JComboBox) control;
		        ctr.setSelectedIndex(0);
		    }
		}
		for(Component control : benutzerView.getBenutzerNeuBearbeitenPanel().getComponents())
		{
		    if(control instanceof JTextField)
		    {
		        JTextField ctrl = (JTextField) control;
		        ctrl.setText("");
		    }
		    else if (control instanceof JComboBox)
		    {
		        JComboBox ctr = (JComboBox) control;
		        ctr.setSelectedIndex(0);
		    }
		}
	}

	public void initialisieren() {

		benutzerView.getPKL().setText("Benutzer-ID:");
		benutzerView.getNachnameL().setText("Nachname:*");
		benutzerView.getVornameL().setText("Vorname:*");
		benutzerView.getStrasseNrL().setText("Strasse/Nr.:");
		benutzerView.getPlzL().setText("PLZ:");
		benutzerView.getOrtL().setText("Ort:");
		benutzerView.getGeburtsdatumL().setText("Geburtsdatum:");
		benutzerView.getTelL().setText("Telefonnummer:");
		benutzerView.getMailL().setText("E-Mailadresse:");
		benutzerView.getBemerkungL().setText("Bemerkung: ");
		benutzerView.getMitarbeiterL().setText("MA:");
		benutzerView.getStatusL().setText("Status:");
		benutzerView.getAnredeL().setText("Anrede:");
		benutzerView.getErfasstAmL().setText("Erfasst am:");
		benutzerView.getErfasstVonL().setText("Erfasst von:");
		
		benutzerView.getPKSucheL().setText("Benutzer-ID:");
		benutzerView.getNachnameSucheL().setText("Nachname:");
		benutzerView.getVornameSucheL().setText("Vorname:");
		benutzerView.getStrasseNrSucheL().setText("Strasse/Nr.:");
		benutzerView.getPlzSucheL().setText("PLZ:");
		benutzerView.getOrtSucheL().setText("Ort:");
		benutzerView.getStatusSucheL().setText("Status:");
		
		PlzRenderer plzR = new PlzRenderer();
		benutzerView.getPlzCbx().setRenderer(plzR);
		benutzerView.getPlzCbx().addItem(new Ort(0, 0));
		benutzerView.getPlzCbx().addItem(new Ort(1, 1000));
		benutzerView.getPlzCbx().addItem(new Ort(2, 1003));
		benutzerView.getPlzCbx().addItem(new Ort(3, 1004));
		benutzerView.getPlzCbx().addItem(new Ort(4, 1005));
		benutzerView.getPlzCbx().setMaximumRowCount(10);
		benutzerView.getPlzCbx().setSelectedIndex(0);
		
		PlzSucheRenderer plzSucheR = new PlzSucheRenderer();
		benutzerView.getPlzSucheCbx().setRenderer(plzSucheR);
		benutzerView.getPlzSucheCbx().addItem(new Ort(0, 0));
		benutzerView.getPlzSucheCbx().addItem(new Ort(1, 1000));
		benutzerView.getPlzSucheCbx().addItem(new Ort(2, 1003));
		benutzerView.getPlzSucheCbx().addItem(new Ort(3, 1004));
		benutzerView.getPlzSucheCbx().addItem(new Ort(4, 1005));
		benutzerView.getPlzSucheCbx().setMaximumRowCount(10);
		benutzerView.getPlzSucheCbx().setSelectedIndex(0);
		
		benutzerView.getSuchButton().setText("Suchen");
		benutzerView.getPKT().setEditable(false);
		benutzerView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		benutzerView.getButtonPanel().getButton2().setVisible(false);
		benutzerView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		benutzerView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
