package ui.rueckgabe;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import dao.AusleiheDAO;
import dao.BenutzerDAO;
import dao.BuchDAO;
import dao.MitarbeiterDAO;
import domain.Ausleihe;
import domain.Autor;
import domain.Benutzer;
import domain.Buch;
import domain.EingeloggterMA;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelAusleihe;
import services.AusleiheService;
import services.MedienhandlingService;
import services.RueckgabeService;
import services.Verifikation;
import services.VerifikationMitAusleihe;
import ui.HauptController;

/**
 * Controller für die AusleiheView, der die Logik und die Ausleihaktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 22.11.2018
 * @author irina
 */

public class RueckgabeController {
	private RueckgabeView rueckgabeView;
	private AusleiheService ausleiheService;
	private RueckgabeService rueckgabeService;
	private MedienhandlingService medienHandlingService;
	private List<Ausleihe> rueckgabeL;
	private TableModelAusleihe tableModelAusleihe;
	private AusleiheDAO ausleiheDAO;
	private Buch buchZurRueckgabe;
	private HauptController hauptController;
	private RueckgabeController rueckgabeController;
	private List buchL = new ArrayList<>();

	public RueckgabeController(RueckgabeView view, HauptController hauptController) {
		rueckgabeView = view;
		this.hauptController = hauptController;
		rueckgabeController = this;
		ausleiheService = new AusleiheService();
		medienHandlingService = new MedienhandlingService();
		rueckgabeService = new RueckgabeService();
		rueckgabeL = new ArrayList<>();
		ausleiheDAO = new AusleiheDAO();
		tableModelAusleihe = new TableModelAusleihe();
		tableModelAusleihe.setAndSortListe(rueckgabeL);
		view.getAusleiheTabelle().setModel(tableModelAusleihe);
		view.spaltenBreiteSetzen();
		initialisieren();
		control();
	}

	// Buttons
	private void control() {
		rueckgabeView.getBarcodeT().addKeyListener(barcodeScanningKeyAdapter());
		rueckgabeView.getSuchButtonBuch().addActionListener(buchSuchenButtonActionListener());
		

				
		ActionListener sichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ausleihe a = new Ausleihe();
				if (inputValidierungBuch(true) == true && validierungBuch() == true) {
					a = feldwertezuObjektSpeichern();
					if(a.getId() > 0) {
						nachArbeitSpeichern(ausleiheService.sichereAusleihe(a));
					}
				}else if (inputValidierungBuch(true) != true){
					JOptionPane.showMessageDialog(null, "Bitte Buch und Benutzer eingeben.");
				}
			}
		};
		rueckgabeView.getAusleiheSpeichernButton().addActionListener(sichernButtonActionListener);
		
		ActionListener rueckgabeButtonActionListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: öffnen des Rückgabefensters
				hauptController.panelEntfernen();
				rueckgabeView.getRueckgabeWechselnL().setText("Zu Rückgabe wechseln");
			}
		};
		rueckgabeView.getButtonPanel().getButton1().addActionListener(rueckgabeButtonActionListener);

		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		rueckgabeView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);
		
		// Doppelklick in Tabelle = Uebernehmen
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
				}
			}
		};
		rueckgabeView.getAusleiheTabelle().addMouseListener(doppelKlick);
	}
	
	private ActionListener buchSuchenButtonActionListener() {
	
	ActionListener buchSuchenButtonActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					RueckgabeDialog rueckgabeDialog = new RueckgabeDialog("Buch suchen");
					new RueckgabeDialogController(rueckgabeController, rueckgabeDialog);
					rueckgabeDialog.setModal(true);
					rueckgabeDialog.setVisible(true);

				}

			});
			
		}
	};
	
	return buchSuchenButtonActionListener;
	
	}
	
	private KeyAdapter barcodeScanningKeyAdapter() {

		KeyAdapter barcodeScanningKeyListener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (inputValidierungSuchen()) {
						
							Buch b = new Buch();
							b.setBarcodeNr(Integer.parseInt(rueckgabeView.getBarcodeT().getText()));
							Buch resultat = medienHandlingService.buchSuchen(b).get(0);
							buchSuchenUndResultatAnzeigen(resultat.getId());
							}
					}
						

				
			}

		};
		return barcodeScanningKeyListener;
	}
	
	public void buchSuchenUndResultatAnzeigen(int id) {
		VerifikationMitAusleihe vma = rueckgabeService.ausleiheAnzeigenByBuchId(id);
		if (vma.isAktionErfolgreich()) {
			
			rueckgabeView.getPKTBuch().setText(Integer.toString(vma.getBuch().getId()));
			rueckgabeView.getBuchTitelT().setText(vma.getBuch().getTitel());
			String autor = "";
					for (int i = 0; i < vma.getBuch().getAutoren().size(); i++) {
								autor = autor + vma.getBuch().getAutoren().get(i).getName() + " " + vma.getBuch().getAutoren().get(i).getVorname();
								autor = autor + (i+1== vma.getBuch().getAutoren().size()?"":", ");
				}
			
			rueckgabeView.getAutorT().setText(autor);
			rueckgabeView.getBuchStatusT().setText(vma.getBuch().getStatus().getBezeichnung());
			rueckgabeView.getBenutzerIDT().setText(Integer.toString(vma.getBenutzer().getId()));
			rueckgabeView.getBenutzerVornameT().setText(vma.getBenutzer().getVorname());
			rueckgabeView.getBenutzerNameT().setText(vma.getBenutzer().getName());
			rueckgabeView.getBenutzerStatusT().setText(vma.getBenutzer().getBenutzerStatus().getBezeichnung());
			rueckgabeView.getNotizT().setText(vma.getBuch().getBemerkung());
			rueckgabeView.getErfasstVonT().setText(vma.getAusleihe().getAusleiheMitarbeiterName());
			rueckgabeView.getErfasstAmT().setText(DateConverter.convertJavaDateToString(vma.getAusleihe().getAusleiheDatum()));
		}
		else {
			JOptionPane.showMessageDialog(null, vma.getNachricht());
			felderLeeren();
			
		}
	}

	private boolean inputValidierungBuch(boolean ruhig) {
		boolean keinInputFehler = true;
		if ((rueckgabeView.getBarcodeT().getText().isEmpty())) {
			if(ruhig != true) {
				JOptionPane.showMessageDialog(null, "Bitte ein Buch eingeben.");
			}			
			return keinInputFehler = false;
		}
		try {
			Integer.parseInt(rueckgabeView.getBarcodeT().getText());
		}catch(NumberFormatException e) {
			if(ruhig != true) {
				JOptionPane.showMessageDialog(null, "Ungültiger Barcode");
			}
			keinInputFehler = false;
		}
		return keinInputFehler;
	}
	
		
	
	private boolean validierungBuch() {
		BuchDAO buchDAO = new BuchDAO();
		try {
			int id = Integer.parseInt(rueckgabeView.getPKTBuch().getText());
			Buch buch = buchDAO.findById(id);
			int statusId = buch.getStatus().getId();
			if(statusId == 2 || statusId == 3) {
				JOptionPane.showMessageDialog(null, "Das Medium darf zur Zeit nicht ausgeliehen werden.");
				return false;
			}else {
				return true;
			}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Ungültige ID");
			return false;
		}
	}
	
	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!rueckgabeView.getBarcodeT().getText().isEmpty()) {
			Verifikation v = medienHandlingService.istBarcode(rueckgabeView.getBarcodeT().getText());
			if (!v.isAktionErfolgreich()) {
				JOptionPane.showMessageDialog(null, v.getNachricht());
				rueckgabeView.getBarcodeT().setText("");		
				keinInputFehler = false;
			}
			else {
				int barCode = Integer.parseInt(rueckgabeView.getBarcodeT().getText()); 
				
				Verifikation vz = medienHandlingService.BarcodeZugeordnet(barCode);
				if(!vz.isAktionErfolgreich()) {
					JOptionPane.showMessageDialog(null, vz.getNachricht());
					rueckgabeView.getBarcodeT().setText("");		
					keinInputFehler = false;
				}
			}
			
			if (!keinInputFehler) {
				felderLeeren();
			}

			
		
		

		}

		return keinInputFehler;

	}
	
	private boolean pruefeAusleihe(int buchId, int benutzerId) {
		ArrayList<Ausleihe> ausleihen = new ArrayList<>();
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		ausleihen = ausleiheDAO.findAll();
		for(Ausleihe a : ausleihen) {
			if(a.getBenutzerID() == benutzerId && a.getMediumID() == buchId && a.getRueckgabeDatum() == null) {
				JOptionPane.showMessageDialog(null, "Der Benutzer hat das Medium bereits ausgeliehen.");
				return false;
			}else if(a.getBenutzerID() == benutzerId && a.getMediumID() != buchId && a.getRueckgabeDatum() == null) {
				JOptionPane.showMessageDialog(null, "Das Medium ist bereits ausgeliehen.");
				return false;
			}
		}
		return true;
	}

	private Ausleihe feldwertezuObjektSpeichern() {
		Ausleihe a = new Ausleihe();
//		if(pruefeAusleihe(Integer.parseInt(rueckgabeView.getPKTBuch().getText()), Integer.parseInt(rueckgabeView.getBenutzerEingabeT().getText())) == true) {
//			if (!rueckgabeView.getPKTBuch().getText().isEmpty()) {
//				a.setMediumID(Integer.parseInt(rueckgabeView.getPKTBuch().getText()));
//			}
//			if (!rueckgabeView.getBenutzerIDT().getText().isEmpty()) {
//				a.setBenutzerID(Integer.parseInt(rueckgabeView.getBenutzerIDT().getText()));
//			}		
//			if (!rueckgabeView.getNotizT().getText().isEmpty()) {
//				a.setNotizAusleihe(rueckgabeView.getNotizT().getText());
//			}
//			a.setAusleiheMitarbeiterID(EingeloggterMA.getInstance().getMitarbeiter().getId());
//	    	String nachname = EingeloggterMA.getInstance().getMitarbeiter().getName();
//	    	String vorname = EingeloggterMA.getInstance().getMitarbeiter().getVorname();
//	    	String name = nachname + " " + vorname;
//	    	a.setAusleiheMitarbeiterName(name);
//	    	Date date = new Date();
//			a.setAusleiheDatum(date);
//		}
		return a;		
	}

	private void uebernehmen() {
		felderLeeren();
		Ausleihe a = new Ausleihe();
		BuchDAO buchDAO = new BuchDAO();
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		a = tableModelAusleihe.getGeklicktesObjekt(rueckgabeView.getAusleiheTabelle().getSelectedRow());
		a = ausleiheDAO.findById(a.getId());
		int buchID = a.getMediumID();
		int benutzerID = a.getBenutzerID();
		Buch buch = buchDAO.findById(buchID);
		Benutzer benutzer = benutzerDAO.findById(benutzerID);
		rueckgabeView.getBarcodeT().setText(buch.getBarcode());
		rueckgabeView.getPKTBuch().setText(Integer.toString(buch.getId()));
		rueckgabeView.getBuchTitelT().setText(buch.getTitel());
		List<Autor> autoren = new ArrayList<>();
		autoren = buch.getAutoren();
		List<String> autorenListe = new ArrayList<>();
		for(Autor autor : autoren) {
			String nachname = autor.getName();
			String vorname = autor.getVorname();
			String autorname = nachname + ", " + vorname;
			autorenListe.add(autorname);
		}
		rueckgabeView.getAutorT().setText(String.join(", ", autorenListe));
		rueckgabeView.getBuchStatusT().setText(buch.getStatus().getBezeichnung());
		rueckgabeView.getBenutzerIDT().setText(Integer.toString(benutzer.getId()));
		rueckgabeView.getBenutzerNameT().setText(benutzer.getName());
		rueckgabeView.getBenutzerVornameT().setText(benutzer.getVorname());
		rueckgabeView.getBenutzerStatusT().setText(benutzer.getBenutzerStatus().getBezeichnung());
		int id = a.getAusleiheMitarbeiterID();
		MitarbeiterDAO maDAO = new MitarbeiterDAO();
		String name = maDAO.findNameVornameById(id);
		if (name != null) {
        	rueckgabeView.getErfasstVonT().setText(name);
		}else {
			rueckgabeView.getErfasstVonT().setText("");
		}
        if (a.getAusleiheDatum() != null) {
        	rueckgabeView.getErfasstAmT().setText(DateConverter.convertJavaDateToString(a.getAusleiheDatum()));
		}else {
			rueckgabeView.getErfasstAmT().setText("");
		}
	}
	
	private void findenBuch() {
		if(inputValidierungBuch(false) == true) {
			Buch buch = new Buch();
			BuchDAO buchDAO = new BuchDAO();
			try {
				buch = buchDAO.findByBarcode(rueckgabeView.getBarcodeT().getText());
				rueckgabeView.getBarcodeT().setText(buch.getBarcode());
				rueckgabeView.getPKTBuch().setText(Integer.toString(buch.getId()));
				rueckgabeView.getBuchTitelT().setText(buch.getTitel());
				List<Autor> autoren = new ArrayList<>();
				autoren = buch.getAutoren();
				List<String> autorenListe = new ArrayList<>();
				for(Autor autor : autoren) {
					String nachname = autor.getName();
					String vorname = autor.getVorname();
					String autorname = nachname + ", " + vorname;
					autorenListe.add(autorname);
				}
				rueckgabeView.getAutorT().setText(String.join(", ", autorenListe));
				rueckgabeView.getBuchStatusT().setText(buch.getStatus().getBezeichnung());
			}catch(NullPointerException npe) {
				rueckgabeView.getPKTBuch().setText("");
				JOptionPane.showMessageDialog(null, "Kein Medium mit diesem Barcode vorhanden.");
			}
			
		}
	}
	
	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			Benutzer benutzer = new Benutzer();
			BenutzerDAO benutzerDAO = new BenutzerDAO();
			tableModelAusleihe.setAndSortListe(ausleiheService.sucheAusleihenProBenutzer(benutzer));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		felderLeeren();
	}

	// Felder leeren
	private void felderLeeren() {
		rueckgabeView.getBarcodeT().setText("");
		rueckgabeView.getPKTBuch().setText("");
		rueckgabeView.getBuchTitelT().setText("");
		rueckgabeView.getAutorT().setText("");
		rueckgabeView.getBuchStatusT().setText("");
		rueckgabeView.getBenutzerIDT().setText("");
		rueckgabeView.getBenutzerNameT().setText("");
		rueckgabeView.getBenutzerVornameT().setText("");
		rueckgabeView.getBenutzerStatusT().setText("");
		rueckgabeView.getNotizT().setText("");
		rueckgabeView.getErfasstVonT().setText("");
		rueckgabeView.getErfasstAmT().setText("");
	}
	
	public void initialisieren() {

		rueckgabeView.getPKLBuch().setText("ID:");
		rueckgabeView.getBarcodeL().setText("Barcode:");
		rueckgabeView.getBuchStatusL().setText("Status:");
		rueckgabeView.getBuchtitelL().setText("Titel:");
		rueckgabeView.getAutorL().setText("Autor:");
		rueckgabeView.getBenutzerIDL().setText("ID:");
		rueckgabeView.getBenutzerStatusL().setText("Status:");
		rueckgabeView.getBenutzerNameL().setText("Name:");
		rueckgabeView.getBenutzerVornameL().setText("Vorname:");
		rueckgabeView.getNotizL().setText("Notiz: ");
		rueckgabeView.getErfasstAmL().setText("Erfasst am:");
		rueckgabeView.getErfasstVonL().setText("Erfasst von:");
		rueckgabeView.getSuchButtonBuch().setText("Suchen");
		rueckgabeView.getAusleiheSpeichernButton().setText("Rückgabe speichern");
		rueckgabeView.getPKTBuch().setEditable(false);
		rueckgabeView.getBarcodeT().setEditable(true);
		rueckgabeView.getBuchStatusT().setEditable(false);
		rueckgabeView.getBuchTitelT().setEditable(false);
		rueckgabeView.getAutorT().setEditable(false);
		rueckgabeView.getBenutzerIDT().setEditable(false);
		rueckgabeView.getBenutzerStatusT().setEditable(false);
		rueckgabeView.getBenutzerNameT().setEditable(false);
		rueckgabeView.getBenutzerVornameT().setEditable(false);
		rueckgabeView.getNotizT().setEditable(true);
		rueckgabeView.getErfasstVonT().setEditable(false);
		rueckgabeView.getErfasstAmT().setEditable(false);
		
		rueckgabeView.getButtonPanel().getButton1().setText(ButtonNamen.ZURUECKGABE.getName());
		rueckgabeView.getButtonPanel().getButton2().setVisible(false);
		rueckgabeView.getButtonPanel().getButton3().setVisible(false);
		rueckgabeView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
