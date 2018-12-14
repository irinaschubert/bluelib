package ui.rueckgabe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import domain.Ausleihe;
import domain.Buch;
import domain.EingeloggterMA;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelRueckgabe;
import services.MedienhandlingService;
import services.RueckgabeService;
import services.Verifikation;
import services.VerifikationMitAusleihe;
import services.VerifikationMitBuch;
import ui.HauptController;

/**
 * Controller für die AusleiheView, der die Logik und die Ausleihaktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 2018-12-09
 * @author Ueli
 */

public class RueckgabeController {
	private RueckgabeView rueckgabeView;
	private RueckgabeService rueckgabeService;
	private MedienhandlingService medienHandlingService;
	private TableModelRueckgabe tableModelRueckgabe;
	private Ausleihe ausleihe;
	private HauptController hauptController;
	private RueckgabeController rueckgabeController;

	public RueckgabeController(RueckgabeView view, HauptController hauptController) {
		rueckgabeView = view;
		this.hauptController = hauptController;
		rueckgabeController = this;
		medienHandlingService = new MedienhandlingService();
		rueckgabeService = new RueckgabeService();
		ausleihe = new Ausleihe();
		tableModelRueckgabe = new TableModelRueckgabe();
		tableModelRueckgabe.setAndSortListe(rueckgabeService.heuteZurueckGegeben());
		view.getAusleiheTabelle().setModel(tableModelRueckgabe);
		view.spaltenBreiteSetzen();
		initialisieren();
		control();
	}

	// Buttons
	private void control() {
		rueckgabeView.getBarcodeT().addKeyListener(barcodeScanningKeyAdapter());
		rueckgabeView.getSuchButtonBuch().addActionListener(buchSuchenButtonActionListener());
		rueckgabeView.getAusleiheSpeichernButton().addActionListener(rueckgabeSpeichern());
		rueckgabeView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		rueckgabeView.getButtonPanel().getButton1().addActionListener(ausleiheButtonActionListener());
	}

	/**
	 * Der ActionListener steuert die Suche nach einem Buch. Wenn sich im Feld
	 * 'Barcode' ein Eintrag befindet, wird dieser verarbeitet und danach gesucht.
	 * Ist das Feld leer, wird der Suchdialog geöffnet.
	 */
	private ActionListener buchSuchenButtonActionListener() {

		ActionListener buchSuchenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Puefung, ob das Barcodefeld leer ist
				if (!rueckgabeView.getBarcodeT().getText().isEmpty()) {
					sucheNachBarcode();
				} else {
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
			}

		};

		return buchSuchenButtonActionListener;

	}

	private ActionListener rueckgabeSpeichern() {

		ActionListener speichernButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!rueckgabeView.getPKTBuch().getText().isEmpty()) {
					ausleihe.setRueckgabeDatum(new Date());
					ausleihe.setRueckgabeMitarbeiterID(EingeloggterMA.getInstance().getMitarbeiter().getId());
					ausleihe.getMedium().setBemerkung(rueckgabeView.getNotizT().getText());
					Verifikation v = rueckgabeService.rueckgabe(ausleihe);

					if (v.isAktionErfolgreich()) {
						felderLeeren();
						tableModelRueckgabe.setAndSortListe(rueckgabeService.heuteZurueckGegeben());
					} else {
						JOptionPane.showMessageDialog(null, v.getNachricht());
					}

				}

				else {
					JOptionPane.showMessageDialog(null, "Bitte Buch auswählen");
				}
			}
		};
		return speichernButtonActionListener;

	}

	private ActionListener ausleiheButtonActionListener() {

		ActionListener ausleiheButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				hauptController.panelEntfernen();
				hauptController.ausleiheAnzeigen();
			}
		};
		return ausleiheButtonActionListener;

	}

	private ActionListener schliessenButtonActionListener() {

		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		return schliessenButtonActionListener;
	}

	private KeyAdapter barcodeScanningKeyAdapter() {

		KeyAdapter barcodeScanningKeyListener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sucheNachBarcode();
				}

			}

		};
		return barcodeScanningKeyListener;
	}

	/**
	 * 
	 * @return die Barcodesuche war erfolgreich: true, die Barcodesuche war nicht
	 *         erfolgreich: false
	 */
	private Boolean sucheNachBarcode() {
		Boolean barcodeFeldPruefen = true;

		Verifikation v = medienHandlingService.istBarcode(rueckgabeView.getBarcodeT().getText());
		// Pruefen, ob ein valider Barcode erfasst wurde
		if (v.isAktionErfolgreich()) {
			VerifikationMitBuch vmb = medienHandlingService
					.barcodeZugeordnet(Integer.parseInt(rueckgabeView.getBarcodeT().getText()));
			// Pruefen, ob zum Barcode ein Buch gefunden wurde
			if (vmb.isAktionErfolgreich()) {
				buchSuchenUndResultatAnzeigen(vmb.getBuch().getId());

			} else {
				JOptionPane.showMessageDialog(null, vmb.getNachricht());
				barcodeFeldPruefen = false;
			}
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			barcodeFeldPruefen = false;
		}

		return barcodeFeldPruefen;
	}

	public void buchSuchenUndResultatAnzeigen(int id) {
		VerifikationMitAusleihe vma = rueckgabeService.ausleiheAnzeigenByBuchId(id);
		if (vma.isAktionErfolgreich()) {
			ausleihe = vma.getAusleihe();
			rueckgabeView.getPKTBuch().setText(Integer.toString(vma.getAusleihe().getMedium().getId()));
			rueckgabeView.getBuchTitelT().setText(vma.getAusleihe().getMedium().getTitel());
			String autor = "";
			for (int i = 0; i < vma.getAusleihe().getMedium().getAutoren().size(); i++) {
				autor = autor + vma.getAusleihe().getMedium().getAutoren().get(i).getName() + " "
						+ vma.getAusleihe().getMedium().getAutoren().get(i).getVorname();
				autor = autor + (i + 1 == vma.getAusleihe().getMedium().getAutoren().size() ? "" : ", ");
			}

			rueckgabeView.getAutorT().setText(autor);
			rueckgabeView.getBuchStatusT().setText(vma.getAusleihe().getMedium().getStatus().getBezeichnung());
			rueckgabeView.getBenutzerIDT().setText(Integer.toString(vma.getAusleihe().getBenutzer().getId()));
			rueckgabeView.getBenutzerVornameT().setText(vma.getAusleihe().getBenutzer().getVorname());
			rueckgabeView.getBenutzerNameT().setText(vma.getAusleihe().getBenutzer().getName());
			rueckgabeView.getBenutzerStatusT()
					.setText(vma.getAusleihe().getBenutzer().getBenutzerStatus().getBezeichnung());
			rueckgabeView.getNotizT().setText(vma.getAusleihe().getMedium().getBemerkung());
			rueckgabeView.getErfasstVonT().setText(ausleihe.getAusleiheMitarbeiterName());
			rueckgabeView.getErfasstAmT().setText(DateConverter.convertJavaDateToString(ausleihe.getAusleiheDatum()));
		} else {
			JOptionPane.showMessageDialog(null, vma.getNachricht());
			felderLeeren();

		}
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

		TextComponentLimit.addTo(rueckgabeView.getNotizT(), 300);

		rueckgabeView.getButtonPanel().getButton1().setText(ButtonNamen.AUSLEIHE.getName());
		rueckgabeView.getButtonPanel().getButton2().setVisible(false);
		rueckgabeView.getButtonPanel().getButton3().setVisible(false);
		rueckgabeView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());

	}
}
