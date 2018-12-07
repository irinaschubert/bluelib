package ui.ausleihe;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
import services.Verifikation;
import ui.HauptController;

/**
 * Controller f�r die AusleiheView, der die Logik und die Ausleihaktionen der
 * View steuert und der View die Models �bergibt
 * 
 * @version 1.0 22.11.2018
 * @author irina
 */

public class AusleiheController {
	private AusleiheView ausleiheView;
	private AusleiheService ausleiheService;
	private List<Ausleihe> ausleiheL;
	private TableModelAusleihe tableModelAusleihe;
	private AusleiheDAO ausleiheDAO;
	private HauptController hauptController;

	public AusleiheController(AusleiheView view, HauptController hauptController) {
		ausleiheView = view;
		this.hauptController = hauptController;
		ausleiheService = new AusleiheService();
		ausleiheL = new ArrayList<>();
		ausleiheDAO = new AusleiheDAO();
		tableModelAusleihe = new TableModelAusleihe();
		tableModelAusleihe.setAndSortListe(ausleiheL);
		view.getAusleiheTabelle().setModel(tableModelAusleihe);
		view.spaltenBreiteSetzen();
		initialisieren();
		control();
	}

	// Buttons
	private void control() {
		ActionListener buchSuchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findenBuch();
			}
		};
		ausleiheView.getSuchButtonBuch().addActionListener(buchSuchenButtonActionListener);

		ActionListener benutzerSuchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findenBenutzer();
				Benutzer benutzer = new Benutzer();
				BenutzerDAO benutzerDAO = new BenutzerDAO();
				if (!ausleiheView.getBenutzerIDT().getText().equals("")) {
					benutzer = benutzerDAO.findById(Integer.parseInt(ausleiheView.getBenutzerIDT().getText()));
					ausleiheL = ausleiheService.sucheAusleihenProBenutzer(benutzer);
					tableModelAusleihe.setAndSortListe(ausleiheL);
				}
			}
		};
		ausleiheView.getSuchButtonBenutzer().addActionListener(benutzerSuchenButtonActionListener);

		ActionListener sichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ausleihe a = new Ausleihe();
				if (inputValidierungBuch(true) == true && inputValidierungBenutzer(true) == true
						&& validierungBenutzer() == true && validierungBuch() == true) {
					a = feldwertezuObjektSpeichern();
					if (a.getId() > 0) {
						nachArbeitSpeichern(ausleiheService.sichereAusleihe(a));
					}
				} else if (inputValidierungBuch(true) != true && inputValidierungBenutzer(true) != true) {
					JOptionPane.showMessageDialog(null, "Bitte Buch und Benutzer eingeben.");
				}
			}
		};
		ausleiheView.getAusleiheSpeichernButton().addActionListener(sichernButtonActionListener);

		ActionListener rueckgabeButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: �ffnen des R�ckgabefensters
				hauptController.panelEntfernen();
				ausleiheView.getRueckgabeWechselnL().setText("Zu R�ckgabe wechseln");
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

	private boolean inputValidierungBuch(boolean ruhig) {
		boolean keinInputFehler = true;
		if ((ausleiheView.getBarcodeT().getText().isEmpty())) {
			if (ruhig != true) {
				JOptionPane.showMessageDialog(null, "Bitte ein Buch eingeben.");
			}
			return keinInputFehler = false;
		}
		try {
			Integer.parseInt(ausleiheView.getBarcodeT().getText());
		} catch (NumberFormatException e) {
			if (ruhig != true) {
				JOptionPane.showMessageDialog(null, "Ung�ltiger Barcode");
			}
			keinInputFehler = false;
		}
		return keinInputFehler;
	}

	private boolean inputValidierungBenutzer(boolean ruhig) {
		boolean keinInputFehler = true;
		if (ausleiheView.getBenutzerEingabeT().getText().isEmpty()) {
			if (ruhig != true) {
				JOptionPane.showMessageDialog(null, "Bitte einen Benutzer eingeben.");
			}
			return keinInputFehler = false;
		}
		try {
			Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText());
		} catch (NumberFormatException e) {
			if (ruhig != true) {
				JOptionPane.showMessageDialog(null, "Ung�ltige ID");
			}
			keinInputFehler = false;
		}
		return keinInputFehler;
	}

	private boolean validierungBenutzer() {
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		try {
			int id = Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText());
			Benutzer benutzer = benutzerDAO.findById(id);
			int statusId = benutzer.getBenutzerStatus().getId();
			if (statusId == 2 || statusId == 3) {
				JOptionPane.showMessageDialog(null, "Der Benutzer darf keine Medien ausleihen.");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Ung�ltige ID");
			return false;
		}
	}

	private boolean validierungBuch() {
		BuchDAO buchDAO = new BuchDAO();
		try {
			int id = Integer.parseInt(ausleiheView.getPKTBuch().getText());
			Buch buch = buchDAO.findById(id);
			int statusId = buch.getStatus().getId();
			if (statusId == 2 || statusId == 3) {
				JOptionPane.showMessageDialog(null, "Das Medium darf zur Zeit nicht ausgeliehen werden.");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Ung�ltige ID");
			return false;
		}
	}

	private boolean pruefeAusleihe(int buchId, int benutzerId) {
		ArrayList<Ausleihe> ausleihen = new ArrayList<>();
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		ausleihen = ausleiheDAO.findAll();
		for (Ausleihe a : ausleihen) {
			if (a.getBenutzer().getId() == benutzerId && a.getMedium().getId() == buchId
					&& a.getRueckgabeDatum() == null) {
				JOptionPane.showMessageDialog(null, "Der Benutzer hat das Medium bereits ausgeliehen.");
				return false;
			} else if (a.getBenutzer().getId() == benutzerId && a.getMedium().getId() != buchId
					&& a.getRueckgabeDatum() == null) {
				JOptionPane.showMessageDialog(null, "Das Medium ist bereits ausgeliehen.");
				return false;
			}
		}
		return true;
	}

	private Ausleihe feldwertezuObjektSpeichern() {
		Ausleihe a = new Ausleihe();
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		BuchDAO buchDAO = new BuchDAO();
		if (pruefeAusleihe(Integer.parseInt(ausleiheView.getPKTBuch().getText()),
				Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText())) == true) {
			if (!ausleiheView.getPKTBuch().getText().isEmpty()) {
				Buch buch = buchDAO.findById(Integer.parseInt(ausleiheView.getPKTBuch().getText()));
				a.setMedium(buch);
				if (!ausleiheView.getNotizT().getText().isEmpty()) {
					buch.setBemerkung(ausleiheView.getNotizT().getText());
				}
			}
			if (!ausleiheView.getBenutzerIDT().getText().isEmpty()) {
				a.setBenutzer(benutzerDAO.findById(Integer.parseInt(ausleiheView.getBenutzerIDT().getText())));
			}
			a.setAusleiheMitarbeiterID(EingeloggterMA.getInstance().getMitarbeiter().getId());
			String nachname = EingeloggterMA.getInstance().getMitarbeiter().getName();
			String vorname = EingeloggterMA.getInstance().getMitarbeiter().getVorname();
			String name = nachname + " " + vorname;
			a.setAusleiheMitarbeiterName(name);
			Date date = new Date();
			a.setAusleiheDatum(date);
		}
		return a;
	}

	private void uebernehmen() {
		felderLeeren();
		Ausleihe a = new Ausleihe();
		BuchDAO buchDAO = new BuchDAO();
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		a = tableModelAusleihe.getGeklicktesObjekt(ausleiheView.getAusleiheTabelle().getSelectedRow());
		a = ausleiheDAO.findById(a.getId());
		int buchID = a.getMedium().getId();
		int benutzerID = a.getBenutzer().getId();
		Buch buch = buchDAO.findById(buchID);
		Benutzer benutzer = benutzerDAO.findById(benutzerID);
		ausleiheView.getBarcodeT().setText(buch.getBarcode());
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
		ausleiheView.getBenutzerIDT().setText(Integer.toString(benutzer.getId()));
		ausleiheView.getBenutzerNameT().setText(benutzer.getName());
		ausleiheView.getBenutzerVornameT().setText(benutzer.getVorname());
		ausleiheView.getBenutzerStatusT().setText(benutzer.getBenutzerStatus().getBezeichnung());
		int id = a.getAusleiheMitarbeiterID();
		MitarbeiterDAO maDAO = new MitarbeiterDAO();
		String name = maDAO.findNameVornameById(id);
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

	private void findenBuch() {
		if (inputValidierungBuch(false) == true) {
			Buch buch = new Buch();
			BuchDAO buchDAO = new BuchDAO();
			try {
				buch = buchDAO.findByBarcode(ausleiheView.getBarcodeT().getText());
				ausleiheView.getBarcodeT().setText(buch.getBarcode());
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
			} catch (NullPointerException npe) {
				ausleiheView.getPKTBuch().setText("");
				JOptionPane.showMessageDialog(null, "Kein Medium mit diesem Barcode vorhanden.");
			}

		}
	}

	private void findenBenutzer() {
		if (inputValidierungBenutzer(false) == true) {
			Benutzer benutzer = new Benutzer();
			BenutzerDAO benutzerDAO = new BenutzerDAO();
			try {
				benutzer = benutzerDAO.findById(Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText()));
				ausleiheView.getBenutzerIDT().setText(Integer.toString(benutzer.getId()));
				ausleiheView.getBenutzerNameT().setText(benutzer.getName());
				ausleiheView.getBenutzerVornameT().setText(benutzer.getVorname());
				ausleiheView.getBenutzerStatusT().setText(benutzer.getBenutzerStatus().getBezeichnung());
				if (benutzer.getId() == 0) {
					ausleiheView.getBenutzerIDT().setText("");
					JOptionPane.showMessageDialog(null, "Kein Benutzer mit dieser ID vorhanden.");
				}

			} catch (NullPointerException npe) {
				ausleiheView.getBenutzerIDT().setText("");
				JOptionPane.showMessageDialog(null, "Kein Benutzer mit dieser ID vorhanden.");
			} catch (NumberFormatException e) {
				ausleiheView.getBenutzerIDT().setText("");
				JOptionPane.showMessageDialog(null, "Id ung�ltig");
			}
		} else {
			felderLeeren();
		}
	}

	private void nachArbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			Benutzer benutzer = new Benutzer();
			BenutzerDAO benutzerDAO = new BenutzerDAO();
			benutzer = benutzerDAO.findById(Integer.parseInt(ausleiheView.getBenutzerEingabeT().getText()));
			tableModelAusleihe.setAndSortListe(ausleiheService.sucheAusleihenProBenutzer(benutzer));
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		felderLeeren();
	}

	// Felder leeren
	private void felderLeeren() {
		for (Component t : ausleiheView.getZuweisenPanel().getComponents()) {
			if (t instanceof JTextField) {
				((JTextField) t).setText("");
			}
			if (t instanceof JTextArea) {
				((JTextArea) t).setText("");
			}
		}
	}

	public void initialisieren() {

		ausleiheView.getPKLBuch().setText("ID:");
		ausleiheView.getBarcodeL().setText("Barcode:");
		ausleiheView.getBuchStatusL().setText("Status:");
		ausleiheView.getBuchtitelL().setText("Titel:");
		ausleiheView.getAutorL().setText("Autor:");
		ausleiheView.getBenutzerEingabeL().setText("Benutzer-ID:");
		ausleiheView.getBenutzerIDL().setText("ID:");
		ausleiheView.getBenutzerStatusL().setText("Status:");
		ausleiheView.getBenutzerNameL().setText("Name:");
		ausleiheView.getBenutzerVornameL().setText("Vorname:");
		ausleiheView.getNotizL().setText("Notiz: ");
		ausleiheView.getErfasstAmL().setText("Erfasst am:");
		ausleiheView.getErfasstVonL().setText("Erfasst von:");

		ausleiheView.getSuchButtonBenutzer().setText("Suchen");
		ausleiheView.getSuchButtonBuch().setText("Suchen");
		ausleiheView.getAusleiheSpeichernButton().setText("Ausleihe speichern");
		ausleiheView.getPKTBuch().setEditable(false);
		ausleiheView.getBarcodeT().setEditable(true);
		ausleiheView.getBuchStatusT().setEditable(false);
		ausleiheView.getBuchTitelT().setEditable(false);
		ausleiheView.getAutorT().setEditable(false);
		ausleiheView.getBenutzerEingabeT().setEditable(true);
		ausleiheView.getBenutzerIDT().setEditable(false);
		ausleiheView.getBenutzerStatusT().setEditable(false);
		ausleiheView.getBenutzerNameT().setEditable(false);
		ausleiheView.getBenutzerVornameT().setEditable(false);
		ausleiheView.getNotizT().setEditable(true);
		ausleiheView.getErfasstVonT().setEditable(false);
		ausleiheView.getErfasstAmT().setEditable(false);
		ausleiheView.getButtonPanel().getButton1().setText(ButtonNamen.ZURUECKGABE.getName());
		ausleiheView.getButtonPanel().getButton2().setVisible(false);
		ausleiheView.getButtonPanel().getButton3().setVisible(false);
		ausleiheView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
