package ui.ausleihe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import domain.Ausleihe;
import domain.Autor;
import domain.Benutzer;
import domain.Buch;
import domain.EingeloggterMA;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelAusleihe;
import services.AusleiheService;
import services.BenutzerService;
import services.MedienhandlingService;
import services.MitarbeiterService;
import services.Verifikation;
import ui.HauptController;

/**
 * Controller für die AusleiheView, der die Logik und die Ausleihaktionen der
 * View steuert und der View die Models übergibt.
 * 
 * @version 1.0 22.11.2018
 * @author Irina
 * 
 */

public class AusleiheController {
	private AusleiheView ausleiheView;
	private AusleiheService ausleiheService;
	private MedienhandlingService medienhandlingService;
	private BenutzerService benutzerService;
	private MitarbeiterService mitarbeiterService;
	private List<Ausleihe> ausleiheL;
	private TableModelAusleihe tableModelAusleihe;
	private HauptController hauptController;
	private AusleiheController ausleiheController;

	public AusleiheController(AusleiheView view, HauptController hauptController) {
		ausleiheView = view;
		this.hauptController = hauptController;
		ausleiheController = this;
		ausleiheService = new AusleiheService();
		medienhandlingService = new MedienhandlingService();
		benutzerService = new BenutzerService();
		mitarbeiterService = new MitarbeiterService();
		ausleiheL = new ArrayList<>();
		tableModelAusleihe = new TableModelAusleihe();
		tableModelAusleihe.setAndSortListe(ausleiheL);
		view.getAusleiheTabelle().setModel(tableModelAusleihe);
		view.spaltenBreiteSetzen();
		initialisieren();
		control();
	}

	/**
	* Weist den Buttons ActionListeners zu und definiert Mouse- und KeyListeners.
	*/
	private void control() {
		ausleiheView.getBarcodeT().addKeyListener(barcodeScanningKeyAdapter());
		ausleiheView.getBenutzerEingabeT().addKeyListener(enterKeyAdapter());
		
		ActionListener buchSuchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						AusleiheBuchDialog ausleiheBuchDialog = new AusleiheBuchDialog("Buch suchen");
						new AusleiheBuchDialogController(ausleiheController, ausleiheBuchDialog);
						ausleiheBuchDialog.setModal(true);
						ausleiheBuchDialog.setVisible(true);
					}
				});
			}
		};
		ausleiheView.getSuchButtonBuch().addActionListener(buchSuchenButtonActionListener);

		ActionListener benutzerSuchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						AusleiheBenutzerDialog ausleiheBenutzerDialog = new AusleiheBenutzerDialog("Benutzer suchen");
						new AusleiheBenutzerDialogController(ausleiheController, ausleiheBenutzerDialog);
						ausleiheBenutzerDialog.setModal(true);
						ausleiheBenutzerDialog.setVisible(true);
					}
				});
			}
		};
		ausleiheView.getSuchButtonBenutzer().addActionListener(benutzerSuchenButtonActionListener);

		ActionListener speichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ausleihe a = new Ausleihe();
				if (ausleiheView.getPKTBuch().getText().isEmpty()  != true && ausleiheView.getBenutzerIDT().getText().isEmpty() != true
						&& validierungBenutzer() == true && validierungBuch() == true) {
					if (validierungAusleihe() == true) {
						a = feldwerteZuObjektSpeichern();
						nachArbeitSpeichern(ausleiheService.speichernAusleihe(a));
					}
				} else if (ausleiheView.getPKTBuch().getText().isEmpty() == true && ausleiheView.getBenutzerIDT().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Bitte Buch und Benutzer eingeben.");
				} else if (ausleiheView.getPKTBuch().getText().isEmpty() != true && ausleiheView.getBenutzerIDT().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Bitte Benutzer eingeben.");
				} else if (ausleiheView.getPKTBuch().getText().isEmpty() == true && ausleiheView.getBenutzerIDT().getText().isEmpty() != true) {
					JOptionPane.showMessageDialog(null, "Bitte Buch eingeben.");
				}
			}
		};
		ausleiheView.getAusleiheSpeichernButton().addActionListener(speichernButtonActionListener);

		ActionListener rueckgabeButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
				hauptController.rueckgabeAnzeigen();
			}
		};
		ausleiheView.getButtonPanel().getButton1().addActionListener(rueckgabeButtonActionListener);

		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		ausleiheView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);

		// Doppelklick in Tabelle = Uebernehmen
		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					uebernehmen();
				}
			}
		};
		ausleiheView.getAusleiheTabelle().addMouseListener(doppelKlick);
	}

	private KeyAdapter barcodeScanningKeyAdapter() {
		KeyAdapter barcodeScanningKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (inputValidierungBuchSuchen()) {
						Buch b = new Buch();
						b.setBarcodeNr(Integer.parseInt(ausleiheView.getBarcodeT().getText()));						
						Buch resultat = medienhandlingService.suchenBuch(b).get(0);
						suchenBuchMitId(resultat.getId());
					}
				}
			}
		};
		return barcodeScanningKeyListener;
	}
	
	private KeyAdapter enterKeyAdapter() {
		KeyAdapter enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (inputValidierungBenutzerSuchen()) {
						Benutzer b = new Benutzer();
						b.setId(Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText()));
						Benutzer resultat = benutzerService.suchenBenutzerById(b.getId());
						ausleiheL = ausleiheService.suchenAusleihenProBenutzer(b);
						tableModelAusleihe.setAndSortListe(ausleiheL);
						suchenBenutzerMitId(resultat.getId());
					}

				}
			}
		};
		return enterKeyListener;
	}
	
	/**
	* Validiert den Input im Eingabefeld für das Buch (Barcode)
	* @return true: wenn der Barcode gültig und einem Buch zugeordnet ist, false: wenn Barcode nicht gültig oder keinem Buch zugeordnet ist
	*/
	private boolean inputValidierungBuchSuchen() {
		boolean keinInputFehler = true;
		if (!ausleiheView.getBarcodeT().getText().isEmpty() || ausleiheView.getBarcodeT().getText().equals("")) {
			Verifikation v = medienhandlingService.istBarcode(ausleiheView.getBarcodeT().getText());
			if (!v.isAktionErfolgreich()) {
				JOptionPane.showMessageDialog(null, v.getNachricht());
				ausleiheView.getBarcodeT().setText("");
				keinInputFehler = false;
			} else {
				int barCode = Integer.parseInt(ausleiheView.getBarcodeT().getText());
				Verifikation vz = medienhandlingService.barcodeZugeordnet(barCode);
				if (!vz.isAktionErfolgreich()) {
					JOptionPane.showMessageDialog(null, vz.getNachricht());
					ausleiheView.getBarcodeT().setText("");
					keinInputFehler = false;
				}
			}
			if (!keinInputFehler) {
				felderLeerenBuch();
			}
		}
		return keinInputFehler;
	}

	/**
	* Validiert den Input im Eingabefeld für Benutzer (Benutzer-ID)
	* @return true: wenn die ID gültig und einem Benutzer zugeordnet ist, false: wenn ID nicht gültig oder keinem Benutzer zugeordnet ist
	*/
	private boolean inputValidierungBenutzerSuchen() {
		boolean keinInputFehler = true;
		if (!ausleiheView.getBenutzerEingabeT().getText().isEmpty() || ausleiheView.getBenutzerEingabeT().getText().equals("")) {
			Verifikation v = benutzerService.istBenutzerID(ausleiheView.getBenutzerEingabeT().getText());
			if (!v.isAktionErfolgreich()) {
				JOptionPane.showMessageDialog(null, v.getNachricht());
				ausleiheView.getBenutzerEingabeT().setText("");
				keinInputFehler = false;
			}else {
				int id = Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText());
				Verifikation vz = benutzerService.idZugeordnet(id);
				if (!vz.isAktionErfolgreich()) {
					JOptionPane.showMessageDialog(null, vz.getNachricht());
					ausleiheView.getBenutzerEingabeT().setText("");
					keinInputFehler = false;
				}
			}
			if (!keinInputFehler) {
				felderLeerenBenutzer();
			}
		}
		return keinInputFehler;
	}

	/**
	* Validiert das Buch
	* @return true: wenn das Buch ausgeliehen werden darf, false: wenn das Buch nicht ausgeliehen werden darf
	*/
	private boolean validierungBuch() {
		try {
			int id = Integer.parseInt(ausleiheView.getPKTBuch().getText());
			Buch buch = medienhandlingService.suchenBuchById(id);
			int statusId = buch.getStatus().getId();
			// Status = 2: gesperrt, Status = 3: gelöscht
			if (statusId == 2 || statusId == 3) {
				JOptionPane.showMessageDialog(null, "Das Medium darf zur Zeit nicht ausgeliehen werden.");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Kein Medium ausgewählt.");
			return false;
		}
	}
	
	/**
	* Validiert den Benutzer
	* @return true: wenn der Benutzer ausleihen darf, false: wenn der Benutzer nicht ausleihen darf
	*/
	private boolean validierungBenutzer() {
		try {
			int id = Integer.parseInt(ausleiheView.getBenutzerIDT().getText());
			Benutzer benutzer = benutzerService.suchenBenutzerById(id);
			int statusId = benutzer.getBenutzerStatus().getId();
			// Status = 2: gesperrt, Status = 3: gelscht
			if (statusId == 2 || statusId == 3) {
				JOptionPane.showMessageDialog(null, "Der Benutzer darf keine Medien ausleihen.");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Kein Benutzer ausgewählt.");
			return false;
		}
	}


	/**
	* Validiert die Ausleihe
	* @return true: wenn das Medium noch frei ist, false: wenn das Medium bereits ausgeliehen ist
	*/
	private boolean validierungAusleihe() {
		List<Ausleihe> ausleihen = new ArrayList<>();
		Buch buch = medienhandlingService.suchenBuchById(Integer.parseInt(ausleiheView.getPKTBuch().getText()));
		Benutzer benutzer = benutzerService.suchenBenutzerById(Integer.parseInt(ausleiheView.getBenutzerIDT().getText()));
		ausleihen = ausleiheService.suchenAlleAusleihen();
		for (Ausleihe a : ausleihen) {
			if (a.getBenutzer().getId() == benutzer.getId() && a.getMedium().getId() == buch.getId()
					&& a.getRueckgabeDatum() == null) {
				JOptionPane.showMessageDialog(null, "Der Benutzer hat das Medium bereits ausgeliehen.");
				return false;
			} else if (a.getBenutzer().getId() != benutzer.getId() && a.getMedium().getId() == buch.getId()
					&& a.getRueckgabeDatum() == null) {
				JOptionPane.showMessageDialog(null, "Das Medium ist bereits ausgeliehen.");
				return false;
			}
		}
		return true;
	}

	/**
	* Kreiert ein Objekt aus den eingegebenen Werten in den Bereichen Buch und Benutzer.
	* @return Ausleihe-Objekt mit Werten aus den Bereichen Buch und Benutzer
	*/
	private Ausleihe feldwerteZuObjektSpeichern() {
		Ausleihe a = new Ausleihe();
		Buch buch = medienhandlingService.suchenBuchById(Integer.parseInt(ausleiheView.getPKTBuch().getText()));
		Benutzer benutzer = benutzerService.suchenBenutzerById(Integer.parseInt(ausleiheView.getBenutzerIDT().getText()));
		a.setMedium(buch);
		a.setBenutzer(benutzer);
		if (!ausleiheView.getNotizT().getText().isEmpty()) {
			buch.setBemerkung(ausleiheView.getNotizT().getText());
		}
		a.setAusleiheMitarbeiterID(EingeloggterMA.getInstance().getMitarbeiter().getId());
		String nachname = EingeloggterMA.getInstance().getMitarbeiter().getName();
		String vorname = EingeloggterMA.getInstance().getMitarbeiter().getVorname();
		String name = nachname + " " + vorname;
		a.setAusleiheMitarbeiterName(name);
		Date date = new Date();
		a.setAusleiheDatum(date);
		return a;
	}

	/**
	* Uebernimmt sämtliche Werte des Ausleiheobjekts in die Buch- bzw. Benutzer-View, wenn auf einen Listeneintrag
	* ein Doppelklick ausgeführt wird.
	*/
	private void uebernehmen() {
		felderLeerenBuch();
		felderLeerenBenutzer();
		Ausleihe a = new Ausleihe();
		a = tableModelAusleihe.getGeklicktesObjekt(ausleiheView.getAusleiheTabelle().getSelectedRow());
		a = ausleiheService.suchenAusleiheById(a.getId());
		Buch buch = (Buch) a.getMedium();
		Benutzer benutzer = a.getBenutzer();
		ausleiheView.getBarcodeT().setText(Integer.toString(buch.getBarcodeNr()));
		ausleiheView.getPKTBuch().setText(Integer.toString(buch.getId()));
		ausleiheView.getBuchTitelT().setText(buch.getTitel());
		List<Autor> autoren = new ArrayList<>();
		autoren = buch.getAutoren();
		List<String> autorenListe = new ArrayList<>();
		for (Autor autor : autoren) {
			String nachname = autor.getName();
			String vorname = autor.getVorname();
			String autorname = nachname + ", " + vorname;
			autorenListe.add(autorname);
		}
		ausleiheView.getAutorT().setText(String.join(", ", autorenListe));
		ausleiheView.getBuchStatusT().setText(buch.getStatus().getBezeichnung());
		ausleiheView.getNotizT().setText(buch.getBemerkung());
		ausleiheView.getBenutzerIDT().setText(Integer.toString(benutzer.getId()));
		ausleiheView.getBenutzerNameT().setText(benutzer.getName());
		ausleiheView.getBenutzerVornameT().setText(benutzer.getVorname());
		ausleiheView.getBenutzerStatusT().setText(benutzer.getBenutzerStatus().getBezeichnung());
		int id = a.getAusleiheMitarbeiterID();
		String name = mitarbeiterService.suchenNameVornameById(id);
		if (name != null) {
			ausleiheView.getErfasstVonT().setText(name);
		} else {
			ausleiheView.getErfasstVonT().setText("");
		}
		if (a.getAusleiheDatum() != null) {
			ausleiheView.getErfasstAmT().setText(DateConverter.convertJavaDateToString(a.getAusleiheDatum()));
		} else {
			ausleiheView.getErfasstAmT().setText("");
		}
	}
	
	/**
	* Prueft Eingabe und sucht das Buch-Objekt und fuellt die Felder anhand der eingegebenen ID im Bereich Buch-View
	* @param Buch-ID
	*/
	protected void suchenBuchMitId(int id) {
		Buch buch = new Buch();
		try {
			buch = medienhandlingService.suchenBuchById(id);
			ausleiheView.getBarcodeT().setText(Integer.toString(buch.getBarcodeNr()));
			ausleiheView.getPKTBuch().setText(Integer.toString(buch.getId()));
			ausleiheView.getBuchTitelT().setText(buch.getTitel());
			List<Autor> autoren = new ArrayList<>();
			autoren = buch.getAutoren();
			List<String> autorenListe = new ArrayList<>();
			for (Autor autor : autoren) {
				String nachname = autor.getName();
				String vorname = autor.getVorname();
				String autorname = nachname + ", " + vorname;
				autorenListe.add(autorname);
			}
			ausleiheView.getAutorT().setText(String.join(", ", autorenListe));
			ausleiheView.getBuchStatusT().setText(buch.getStatus().getBezeichnung());
			ausleiheView.getNotizT().setText(buch.getBemerkung());
		} catch (NullPointerException npe) {
			felderLeerenBuch();
			JOptionPane.showMessageDialog(null, "Kein Medium mit diesem Barcode vorhanden.");
		}
	}

	/**
	* Prueft Eingabe und sucht das Benutzer-Objekt und fuellt die Felder anhand der eingegebenen ID im Bereich Benutzer-View
	* @param Benutzer-ID
	*/
	protected void suchenBenutzerMitId(int id) {
		Benutzer benutzer = new Benutzer();
		try {
			benutzer = benutzerService.suchenBenutzerById(id);
			ausleiheView.getBenutzerIDT().setText(Integer.toString(benutzer.getId()));
			ausleiheView.getBenutzerNameT().setText(benutzer.getName());
			ausleiheView.getBenutzerVornameT().setText(benutzer.getVorname());
			ausleiheView.getBenutzerStatusT().setText(benutzer.getBenutzerStatus().getBezeichnung());
			if (benutzer.getId() <= 0) {
				ausleiheView.getBenutzerIDT().setText("");
				JOptionPane.showMessageDialog(null, "Ungültige ID.");
			}

		} catch (NullPointerException npe) {
			ausleiheView.getBenutzerIDT().setText("");
			JOptionPane.showMessageDialog(null, "Kein Benutzer mit dieser ID vorhanden.");
		} catch (NumberFormatException e) {
			ausleiheView.getBenutzerIDT().setText("");
			JOptionPane.showMessageDialog(null, "Ungültige ID.");
		}
	}
	
	/**
	* Setzt die Liste neu und leert die Eingabefelder nach dem Speichern.
	*/
	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			Benutzer benutzer = benutzerService.suchenBenutzerById(Integer.parseInt(ausleiheView.getBenutzerIDT().getText()));
			tableModelAusleihe.setAndSortListe(ausleiheService.suchenAusleihenProBenutzer(benutzer));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		felderLeerenBuch();
		felderLeerenBenutzer();
	}

	/**
	* Leert saemtliche Eingabefelder im Bereich Buch.
	*/
	private void felderLeerenBuch() {
		ausleiheView.getBarcodeT().setText("");
		ausleiheView.getPKTBuch().setText("");
		ausleiheView.getBuchStatusT().setText("");
		ausleiheView.getBuchTitelT().setText("");
		ausleiheView.getAutorT().setText("");
		ausleiheView.getNotizT().setText("");
	}

	/**
	* Leert saemtliche Eingabefelder im Bereich Benutzer.
	*/
	private void felderLeerenBenutzer() {
		ausleiheView.getBenutzerEingabeT().setText("");
		ausleiheView.getBenutzerIDT().setText("");
		ausleiheView.getBenutzerStatusT().setText("");
		ausleiheView.getBenutzerNameT().setText("");
		ausleiheView.getBenutzerVornameT().setText("");
	}

	/**
	* Setzt Werte für die Labels, fügt den Eingabefeldern Limiten fuer die Anzahl Zeichen zu,
	* definiert die verwendeten Buttons aus dem ButtonPanel.
	*/
	public void initialisieren() {
		ausleiheView.getPKLBuch().setText("ID:");
		ausleiheView.getBarcodeL().setText("Barcode:");
		ausleiheView.getBuchStatusL().setText("Status:");
		ausleiheView.getBuchtitelL().setText("Titel:");
		ausleiheView.getAutorL().setText("Autor:");
		ausleiheView.getNotizL().setText("Notiz: ");
		ausleiheView.getBenutzerEingabeL().setText("Benutzer-ID:");
		ausleiheView.getBenutzerIDL().setText("ID:");
		ausleiheView.getBenutzerStatusL().setText("Status:");
		ausleiheView.getBenutzerNameL().setText("Name:");
		ausleiheView.getBenutzerVornameL().setText("Vorname:");
		ausleiheView.getErfasstAmL().setText("Erfasst am:");
		ausleiheView.getErfasstVonL().setText("Erfasst von:");

		ausleiheView.getSuchButtonBenutzer().setText("Suche öffnen...");
		ausleiheView.getSuchButtonBuch().setText("Suche öffnen...");
		ausleiheView.getAusleiheSpeichernButton().setText("Ausleihe speichern");
		ausleiheView.getPKTBuch().setEditable(false);
		ausleiheView.getBarcodeT().setEditable(true);
		ausleiheView.getBuchStatusT().setEditable(false);
		ausleiheView.getBuchTitelT().setEditable(false);
		ausleiheView.getAutorT().setEditable(false);
		ausleiheView.getNotizT().setEditable(true);
		ausleiheView.getBenutzerEingabeT().setEditable(true);
		ausleiheView.getBenutzerIDT().setEditable(false);
		ausleiheView.getBenutzerStatusT().setEditable(false);
		ausleiheView.getBenutzerNameT().setEditable(false);
		ausleiheView.getBenutzerVornameT().setEditable(false);
		ausleiheView.getErfasstVonT().setEditable(false);
		ausleiheView.getErfasstAmT().setEditable(false);
		TextComponentLimit.addTo(ausleiheView.getBarcodeT(), 15);
		TextComponentLimit.addTo(ausleiheView.getBenutzerEingabeT(), 5);
		ausleiheView.getButtonPanel().getButton1().setText(ButtonNamen.ZURUECKGABE.getName());
		ausleiheView.getButtonPanel().getButton2().setVisible(false);
		ausleiheView.getButtonPanel().getButton3().setVisible(false);
		ausleiheView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
