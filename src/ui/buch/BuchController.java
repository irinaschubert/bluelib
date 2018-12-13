package ui.buch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import domain.Autor;
import domain.Buch;
import domain.EingeloggterMA;
import domain.Schlagwort;
import domain.Status;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.IntHelfer;
import hilfsklassen.TextComponentLimit;
import models.ComboBoxModelAutor;
import models.ComboBoxModelSchlagwort;
import models.ComboBoxModelVerlag;
import models.TableModelBuch;
import services.MedienhandlingService;
import services.NormdatenService;
import services.RueckgabeService;
import services.Verifikation;
import ui.HauptController;

/**
 * 
 * Controller f�r die Buch-View, der die Logik und die Benutzeraktionen der View
 * steuert und der View die Models �bergibt
 * 
 * @version 1.0 2018-11-13
 * @author Ueli
 *
 */

public class BuchController {
	private BuchView buchView;
	BuchSuchView buchSuchView;
	private MedienhandlingService medienHandlingService;
	private NormdatenService normdatenService;
	private HauptController hauptController;
	private ComboBoxModelVerlag comboBoxModelVerlag;
	BuchController buchController;
	BuchSuchController buchSuchController;

	public BuchController(BuchView view, HauptController hauptController) {
		buchView = view;
		this.hauptController = hauptController;
		medienHandlingService = new MedienhandlingService();
		normdatenService = new NormdatenService();
		buchSuchView = new BuchSuchView();
		buchController = this;
		buchSuchController = new BuchSuchController(buchSuchView);
		buchView.addBuchSuchView(buchSuchView);
		neuBearbeitenPanelInitialisieren();
		ButtonPanelInitialisieren();
		control();

	}

//	Definierten des Listeners f�r die Button-Klicks
	private void control() {
		buchView.getZuweisenAutorB().addActionListener(autorZuweisenActionListener());
		buchView.getEntfernenAutorB().addActionListener(autorEntfernenActionListener());
		buchView.getZuweisenSchlagwortB().addActionListener(schlagWortZuweisenActionListener());
		buchView.getEntferntenSchlagwortB().addActionListener(schlagWortEntfernenActionListener());
		buchView.getSachbuchR().addActionListener(signaturZuweisenActionListener());
		buchView.getErfassenBarcodeB().addActionListener(barcodeZuweisenActionListener());
		buchView.getButtonPanel().getButton3().addActionListener(sichernButtonActionListener());
		buchView.getButtonPanel().getButton1().addActionListener(neuButtonActionListener());
		buchView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener());
		buchSuchView.getBuchTabelle().addMouseListener(doppelKlick());

	}

	private ActionListener sichernButtonActionListener() {

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Buch b = new Buch();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					// Pr�fung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					if (buchView.getPKT().getText().isEmpty()) {

						nachAarbeitSpeichern(medienHandlingService.speichernBuch(b));
					} else {
						nachAarbeitSpeichern(medienHandlingService.buchBearbeiten(b));
					}

				}

			}

		};
		return sichernButtonActionListener;
	}

	private ActionListener neuButtonActionListener() {

		ActionListener neuButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				felderLeeren();
				buchView.getNeuAendernL().setText("Neuerfassung");

			}

		};
		return neuButtonActionListener;
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

	private ActionListener autorZuweisenActionListener() {

		ActionListener autorZuweisenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model = (DefaultListModel) buchView.getAutorList().getModel();
				if (buchView.getAutorCbx().getSelectedIndex() > -1) {

					Autor a = (Autor) buchView.getAutorCbx().getSelectedItem();
					Boolean identisch = false;

					for (int i = 0; i < model.getSize(); i++) {
						int id = ((Autor) model.getElementAt(i)).getId();
						if (id == a.getId()) {
							identisch = true;
						}
					}
					if (identisch) {
						JOptionPane.showMessageDialog(null, "Der Autor befindet sich bereits in der Liste");
					} else {
						model.addElement(a);

						// Zuweisen der Signatur, falls Belletristik-Radiobutton gesetzt
						if (buchView.getBuchtypG().getSelection().getActionCommand() == buchView.BELLETRISTIK
								&& buchView.getSignaturT().getText().isEmpty()) {
							String sign = a.getName().substring(0,
									(a.getName().length() < 4 ? a.getName().length() : 4));
							sign = sign.toUpperCase();
							buchView.getSignaturT().setText(sign);
						}
					}
				}
			}
		};
		return autorZuweisenActionListener;
	}

	private ActionListener autorEntfernenActionListener() {

		ActionListener autorEntfernenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!buchView.getAutorList().isSelectionEmpty()) {
					DefaultListModel model = (DefaultListModel) buchView.getAutorList().getModel();
					int selectedIndex = buchView.getAutorList().getSelectedIndex();
					model.remove(selectedIndex);
				} else {
					JOptionPane.showMessageDialog(null, "Es wurde kein Autor ausgew�hlt");
				}
			}
		};
		return autorEntfernenActionListener;
	}

	private ActionListener schlagWortZuweisenActionListener() {

		ActionListener schlagWortZuweisenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model = (DefaultListModel) buchView.getSchlagwortList().getModel();
				if (buchView.getSchlagwortCbx().getSelectedIndex() > -1) {

					Schlagwort s = (Schlagwort) buchView.getSchlagwortCbx().getSelectedItem();
					Boolean identisch = false;

					for (int i = 0; i < model.getSize(); i++) {
						int id = ((Schlagwort) model.getElementAt(i)).getId();
						if (id == s.getId()) {
							identisch = true;
						}
					}
					if (identisch) {
						JOptionPane.showMessageDialog(null, "Das Schlagwort befindet sich bereits in der Liste");
					} else {
						model.addElement(s);
					}
				}
			}
		};
		return schlagWortZuweisenActionListener;
	}

	private ActionListener schlagWortEntfernenActionListener() {

		ActionListener schlagWortEntfernenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!buchView.getSchlagwortList().isSelectionEmpty()) {
					DefaultListModel model = (DefaultListModel) buchView.getSchlagwortList().getModel();
					int selectedIndex = buchView.getSchlagwortList().getSelectedIndex();
					model.remove(selectedIndex);
				} else {
					JOptionPane.showMessageDialog(null, "Es wurde kein Schlagwort ausgew�hlt");
				}
			}
		};
		return schlagWortEntfernenActionListener;
	}

	private ActionListener signaturZuweisenActionListener() {
		ActionListener signaturZuweisenActionListsner = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						DezimalKlassifikationView dezKlassView = new DezimalKlassifikationView("Dezimalklassifikation");
						DezimalKlassifikationController dezKlassController = new DezimalKlassifikationController(
								dezKlassView, buchController);
						dezKlassView.setModal(true);
						dezKlassView.setVisible(true);

					}

				});

			}
		};
		return signaturZuweisenActionListsner;
	}

	private ActionListener barcodeZuweisenActionListener() {
		ActionListener barCodeZuweisenActionListsner = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						BarCodeZuordnungView barCodeZuordnungView = new BarCodeZuordnungView("Barcode");
						BarCodeZuordnungController barCodeZuordnungController = new BarCodeZuordnungController(
								barCodeZuordnungView, buchController);
						barCodeZuordnungView.setModal(true);
						barCodeZuordnungView.setSize(300, 130);
						barCodeZuordnungView.setLocationRelativeTo(buchView);
						barCodeZuordnungView.setVisible(true);

					}

				});

			}
		};
		return barCodeZuweisenActionListsner;
	}

	private MouseListener doppelKlick() {

		MouseListener doppelKlick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					TableModelBuch tableModelBuch = new TableModelBuch();
					tableModelBuch = buchSuchController.getTableModelBuch();
					buchController.uebernehmen(
							tableModelBuch.getGeklicktesObjekt(buchSuchView.getBuchTabelle().getSelectedRow()));
				}
			}
		};
		return doppelKlick;
	}

	/**
	 * @return Boolean, falls alle Pflichtfelder erfasst
	 */
	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (buchView.getBarcodeT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte Barcode erfassen");
			keinInputFehler = false;
		} else if (buchView.getTitelT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte Titel eingeben");
			keinInputFehler = false;
		} else if (buchView.getVerlagCbx().getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Bitte Verlag ausw�hlen");
			keinInputFehler = false;
		} else if (buchView.getAuflageT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte Auflage erfassen");
			keinInputFehler = false;
		} else if (buchView.getAnzahlSeitenT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte Seitenzahl erfassen");
			keinInputFehler = false;
		} else if (buchView.getIsbnT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte ISBN-Nummer erfassen");
			keinInputFehler = false;
		} else if (buchView.getJahrT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte Erscheinungsjahr erfassen");
			keinInputFehler = false;
		} else if (buchView.getAutorList().getModel().getSize() <= 0) {
			JOptionPane.showMessageDialog(null, "Bitte mindestens einen Autoren erfassen");
			keinInputFehler = false;
		} else if (buchView.getSignaturT().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte signatur Erfassenn");
			keinInputFehler = false;
		} else if (buchView.getSchlagwortList().getModel().getSize() <= 0) {
			JOptionPane.showMessageDialog(null, "Bitte mindestens ein Schlagwort erfassen");
			keinInputFehler = false;
		} else if (!IntHelfer.istInteger(buchView.getAnzahlSeitenT().getText())) {
			JOptionPane.showMessageDialog(null, "Als Seitenzahl bitte einen Zahlenwert erfassen.");
			keinInputFehler = false;
		} else if (!IntHelfer.istInteger(buchView.getJahrT().getText())) {
			JOptionPane.showMessageDialog(null, "Als Jahr bitte einen Zahlenwert erfassen.");
			keinInputFehler = false;
		}

		else if (!IntHelfer.istInteger(buchView.getPreisT().getText())
				&& !IntHelfer.istDecimal(buchView.getPreisT().getText())) {
			JOptionPane.showMessageDialog(null, "Als Preis bitte einen Zahlenwert mit oder ohne Dezimalpukt erfassen.");
			keinInputFehler = false;
		}

		else if (!IntHelfer.istInteger(buchView.getIsbnT().getText())) {
			JOptionPane.showMessageDialog(null, "Als ISBN bitte einen Zahlenwert erfassen.");
			keinInputFehler = false;
		}

		return keinInputFehler;

	}

	/**
	 * Uebertraegt die Werte aus der View in ein Buch-Objekt
	 * 
	 * @return Buch
	 */
	private Buch feldwertezuObjektSpeichern() {
		Buch b = new Buch();
		if (!buchView.getPKT().getText().isEmpty()) {
			b.setId(Integer.parseInt(buchView.getPKT().getText()));
		}

		if (!buchView.getBarcodeT().getText().isEmpty()) {
			b.setBarcodeNr(Integer.parseInt(buchView.getBarcodeT().getText()));
		}
		if (!buchView.getTitelT().getText().isEmpty()) {
			b.setTitel(buchView.getTitelT().getText());
		}
		b.setVerlag((Verlag) buchView.getVerlagCbx().getModel().getSelectedItem());

		if (!buchView.getAuflageT().getText().isEmpty()) {
			b.setAuflage(buchView.getAuflageT().getText());
		}
		if (!buchView.getAnzahlSeitenT().getText().isEmpty()) {
			b.setAnzahlSeiten(Integer.parseInt(buchView.getAnzahlSeitenT().getText()));
		}
		for (int i = 0; i < buchView.getAutorList().getModel().getSize(); i++) {
			Autor a = buchView.getAutorList().getModel().getElementAt(i);
			b.setAutor(a);
		}
		for (int i = 0; i < buchView.getSchlagwortList().getModel().getSize(); i++) {
			Schlagwort s = buchView.getSchlagwortList().getModel().getElementAt(i);
			b.setSchlagwort(s);
		}
		if (!buchView.getNotizA().getText().isEmpty()) {
			b.setBemerkung(buchView.getNotizA().getText());
		}

		b.setErfassungDatum(new Date());
		if (!buchView.getReiheT().getText().isEmpty()) {
			b.setReihe(buchView.getReiheT().getText());
		}

		if (!buchView.getPreisT().getText().isEmpty()) {
			b.setPreis(new BigDecimal(buchView.getPreisT().getText()));
		}

		if (!buchView.getJahrT().getText().isEmpty()) {
			b.setErscheinungsJahr(Integer.parseInt(buchView.getJahrT().getText()));
		}
		if (!buchView.getIsbnT().getText().isEmpty()) {
			b.setIsbn(Long.parseLong(buchView.getIsbnT().getText()));
		}
		if (!buchView.getOrtT().getText().isEmpty()) {
			b.setErscheinungsOrt(buchView.getOrtT().getText());
		}
		b.setStatus((Status) buchView.getStatusCbx().getModel().getSelectedItem());
		if (!buchView.getSignaturT().getText().isEmpty()) {
			b.setSignatur(buchView.getSignaturT().getText());
		}
		b.setErfasserId(EingeloggterMA.getInstance().getMitarbeiter().getId());

		return b;
	}

	public void signaturSetzen(String signatur) {
		buchView.getSignaturT().setText(signatur);
	}

	public void barCodeSetzen(String barCode) {
		buchView.getBarcodeT().setText(barCode);
	}

	/**
	 * Uebernahme der Werte eines Buch-Objekts in die View
	 */
	public void uebernehmen(Buch buch) {

		felderLeeren();
		RueckgabeService rueckgabeService = new RueckgabeService();
		if (rueckgabeService.istAusgeliehen(buch.getId())) {
			buchView.getLeihstatusT().setText("Ausgeliehen");
		} else {
			buchView.getLeihstatusT().setText("Verf�gbar");
		}
		buchView.getNeuAendernL().setText("Bearbeiten");
		buchView.getPKT().setText(Integer.toString(buch.getId()));
		buchView.getBarcodeT().setText(Integer.toString(buch.getBarcodeNr()));
		buchView.getTitelT().setText(buch.getTitel());
		buchView.getVerlagCbx().setSelectedIndex(comboBoxModelVerlag.getPositionVerlag(buch.getVerlag()));
		buchView.repaint();
		buchView.getAuflageT().setText(buch.getAuflage());
		buchView.getAnzahlSeitenT().setText(Integer.toString(buch.getAnzahlSeiten()));
		buchView.getReiheT().setText(buch.getReihe());
		if (!(buch.getPreis() == null)) {
			buchView.getPreisT().setText(String.valueOf(buch.getPreis()));
		}
		if (!(buch.getErscheinungsJahr() == 0)) {
			buchView.getJahrT().setText(Integer.toString(buch.getErscheinungsJahr()));
		}
		buchView.getIsbnT().setText(Long.toString(buch.getIsbn()));
		buchView.getOrtT().setText(buch.getErscheinungsOrt());
		buchView.getSignaturT().setText(buch.getSignatur());

		((DefaultListModel) buchView.getAutorList().getModel()).removeAllElements();
		for (Autor a : buch.getAutoren()) {
			((DefaultListModel) buchView.getAutorList().getModel()).addElement(a);
		}

		((DefaultListModel) buchView.getSchlagwortList().getModel()).removeAllElements();
		if (buch.getSchlagwoerter() != null) {
			for (Schlagwort s : buch.getSchlagwoerter()) {
				((DefaultListModel) buchView.getSchlagwortList().getModel()).addElement(s);
			}
		}

		buchView.getNotizA().setText(buch.getBemerkung());
		buchView.getErfassungsDatumT().setText(DateConverter.convertJavaDateToString(buch.getErfassungDatum()));
		buchView.getErfassungsUserT().setText(buch.getErfasserName());

	}

	private void nachAarbeitSpeichern(Verifikation v) {
		if (v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			// Falls das Buch bearbeitet wurde: Update der Suche
			if (!buchView.getPKT().getText().isEmpty()) {
				buchSuchController.buchSuchenUndResultatAnzeigen();
			}
		} else {
			JOptionPane.showMessageDialog(null, v.getNachricht());
		}
		felderLeeren();

	}

	private void felderLeeren() {

		buchView.getLeihstatusT().setText("");
		buchView.getPKT().setText("");
		buchView.getBarcodeT().setText("");
		buchView.getTitelT().setText("");
		buchView.getVerlagCbx().setSelectedIndex(-1);
		buchView.getAuflageT().setText("");
		buchView.getAnzahlSeitenT().setText("");
		buchView.getAutorCbx().setSelectedIndex(-1);
		((DefaultListModel) buchView.getAutorList().getModel()).removeAllElements();
		buchView.getSchlagwortCbx().setSelectedIndex(-1);
		((DefaultListModel) buchView.getSchlagwortList().getModel()).removeAllElements();
		buchView.getNotizA().setText("");
		buchView.getErfassungsDatumT().setText("");
		buchView.getReiheT().setText("");
		buchView.getReiheT().setText("");
		buchView.getPreisT().setText("");
		buchView.getJahrT().setText("");
		buchView.getIsbnT().setText("");
		buchView.getOrtT().setText("");
		buchView.getStatusCbx().setSelectedIndex(0);
		buchView.getBelletristikR().setSelected(true);
		buchView.getSignaturT().setText("");
		buchView.getErfassungsUserT().setText("");
		buchView.repaint();
		buchView.getNeuAendernL().setText("");

	}

	private void neuBearbeitenPanelInitialisieren() {
		buchView.getLeihstatusL().setText("Leihstatus:");
		buchView.getPKL().setText("Nr.:");
		buchView.getBarcodeL().setText("Barcode:*");
		buchView.getErfassenBarcodeB().setText("Erfassen");
		buchView.getTitelL().setText("Titel:*");
		buchView.getVerlagL().setText("Verlag:*");
		buchView.getAuflageL().setText("Auflage:*");
		buchView.getReiheL().setText("Reihe:");
		buchView.getAnzahlSeitenL().setText("Anz. Seiten:*");
		buchView.getPreisL().setText("Preis:");
		buchView.getJahrL().setText("Jahr:*");
		buchView.getOrtL().setText("Ort:");
		buchView.getIsbnL().setText("ISBN:*");
		buchView.getStatusL().setText("Status:");
		buchView.getAutorL().setText("Autor(en):*");
		buchView.getZuweisenAutorB().setText("Zuweisen");
		buchView.getEntfernenAutorB().setText("Entfernen");
		buchView.getBelletristikR().setText("Belletristik");
		buchView.getSachbuchR().setText("Sachbuch");
		buchView.getSignaturL().setText("Signatur:*");
		buchView.getBelletristikR().setSelected(true);
		buchView.getZuweisenSchlagwortB().setText("zuweisen");
		buchView.getEntferntenSchlagwortB().setText("entfernen");
		buchView.getNotizL().setText("Notiz");
		buchView.getErfassungsDatumL().setText("Erfassungsdatum:");
		buchView.getErfassungsUserL().setText("Erfasst durch:");
		buchView.getAutorCbx().setModel(new ComboBoxModelAutor(normdatenService.alleautoren()));
		comboBoxModelVerlag = new ComboBoxModelVerlag(normdatenService.alleVerlage());
		buchView.getVerlagCbx().setModel(comboBoxModelVerlag);
		buchView.getStatusCbx().setModel(new DefaultComboBoxModel(medienHandlingService.alleMedienStati().toArray()));
		buchView.getAutorList().setModel(new DefaultListModel());
		ComboBoxModelSchlagwort comboBoxModelSchlagwort = new ComboBoxModelSchlagwort(
				normdatenService.alleSchlagworte());
		comboBoxModelSchlagwort.geloeschteEntfernen();
		buchView.getSchlagwortCbx().setModel(comboBoxModelSchlagwort);
		buchView.getSchlagwortList().setModel(new DefaultListModel());

		// Begrenzen der Eingabelaenge
		TextComponentLimit.addTo(buchView.getTitelT(), 100);
		TextComponentLimit.addTo(buchView.getAuflageT(), 30);
		TextComponentLimit.addTo(buchView.getAnzahlSeitenT(), 11);
		TextComponentLimit.addTo(buchView.getAuflageT(), 30);
		TextComponentLimit.addTo(buchView.getNotizA(), 300);
		TextComponentLimit.addTo(buchView.getReiheT(), 30);
		TextComponentLimit.addTo(buchView.getPreisT(), 10);
		TextComponentLimit.addTo(buchView.getJahrT(), 4);
		TextComponentLimit.addTo(buchView.getIsbnT(), 13);
		TextComponentLimit.addTo(buchView.getOrtT(), 30);
		TextComponentLimit.addTo(buchView.getSignaturT(), 20);

	}

	private void ButtonPanelInitialisieren() {
		buchView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		buchView.getButtonPanel().getButton2().setVisible(false);
		buchView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		buchView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
