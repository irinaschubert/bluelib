package ui.buch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import models.ComboBoxModelAutor;
import models.ComboBoxModelSchlagwort;
import models.ComboBoxModelVerlag;
import services.MedienhandlingService;
import services.NormdatenService;
import services.Verifikation;
import ui.HauptController;

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
	private Buch buchSuchobjekt;
	private HauptController hauptController;
	private ComboBoxModelVerlag comboBoxModelVerlag;
	BuchController buchController;
	BuchSuchController buchSuchController;

	public BuchController(BuchView view, HauptController hauptController) {
		buchView = view;
		this.hauptController = hauptController;
		medienHandlingService = new MedienhandlingService();
		normdatenService = new NormdatenService();
		buchController = this;
		BuchSuchView buchSuchView = new BuchSuchView();
		buchSuchController = new BuchSuchControllerAusBuch(buchSuchView, this);
		buchView.addBuchSuchView(buchSuchView);
		neuBearbeitenPanelInitialisieren();
		ButtonPanelInitialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
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

	}

	private ActionListener sichernButtonActionListener() {

		ActionListener sichernButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Buch b = new Buch();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					if (buchView.getPKT().getText().isEmpty()) {

						nachAarbeitSpeichern(medienHandlingService.buchNeuErfassen(b));
					}
					else {
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
					JOptionPane.showMessageDialog(null, "Es wurde kein Autor ausgewählt");
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
					JOptionPane.showMessageDialog(null, "Es wurde kein Schlagwort ausgewählt");
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

					}

				});

			}
		};
		return barCodeZuweisenActionListsner;
	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if ((buchView.getBarcodeT().getText().isEmpty()) || (buchView.getTitelT().getText().isEmpty())
				|| (buchView.getBarcodeT().getText().isEmpty()) || (buchView.getVerlagCbx().getSelectedIndex() < 0)
				|| (buchView.getAuflageT().getText().isEmpty()) || (buchView.getAnzahlSeitenT().getText().isEmpty())
				|| (buchView.getIsbnT().getText().isEmpty()) || (buchView.getJahrT().getText().isEmpty())
				|| (buchView.getAutorList().getModel().getSize() <= 0)
				|| (buchView.getSignaturT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}

		else if (!IntHelfer.istInteger(buchView.getAnzahlSeitenT().getText())) {
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

	public void uebernehmen(Buch buch) {
		
		felderLeeren();
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
		buchView.getPKL().setText("Nr.:");
		buchView.getBarcodeL().setText("Barcode*:");
		buchView.getErfassenBarcodeB().setText("Erfassen");
		buchView.getTitelL().setText("Titel*:");
		buchView.getVerlagL().setText("Verlag*:");
		buchView.getAuflageL().setText("Auflage*:");
		buchView.getReiheL().setText("Reihe:");
		buchView.getAnzahlSeitenL().setText("Anz. Seiten*:");
		buchView.getPreisL().setText("Preis:");
		buchView.getJahrL().setText("Jahr*:");
		buchView.getOrtL().setText("Ort:");
		buchView.getIsbnL().setText("ISBN*:");
		buchView.getStatusL().setText("Status:");
		buchView.getAutorL().setText("Autor(en)*:");
		buchView.getZuweisenAutorB().setText("Zuweisen");
		buchView.getEntfernenAutorB().setText("Entfernen");
		buchView.getBelletristikR().setText("Belletristik");
		buchView.getSachbuchR().setText("Sachbuch");
		buchView.getSignaturL().setText("Signatur*:");
		buchView.getBelletristikR().setSelected(true);
		buchView.getSchlagwortL().setText("Schlagwort*");
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

	}

	private void ButtonPanelInitialisieren() {
		buchView.getButtonPanel().getButton1().setText(ButtonNamen.NEU.getName());
		buchView.getButtonPanel().getButton2().setVisible(false);
		buchView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		buchView.getButtonPanel().getButton4().setText(ButtonNamen.SCHLIESSEN.getName());
	}
}
