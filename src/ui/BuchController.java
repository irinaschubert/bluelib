package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
	private ComboBoxModelVerlag comboBoxModelVerlag;
	BuchController buchController;

	public BuchController(BuchView view, HauptController hauptController) {
		buchView = view;
		this.hauptController = hauptController;
		medienHandlingService = new MedienhandlingService();
		normdatenService = new NormdatenService();
		buchController = this;
//		autorL = normdatenService.alleautoren();
//		tableModelAutor.setAndSortListe(buchL);
//		view.getAutorenTabelle().setModel(tableModelAutor);
//		view.spaltenBreiteSetzen();
//		autorSuchobjekt = new Autor();

//		suchPanelInitialisieren();
//		tabellenPanelInitialisieren();
		BuchSuchView buchSuchView = new BuchSuchView();
		BuchSuchController buchSuchController = new BuchSuchControllerAusBuch(buchSuchView, this);
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
				Buch b = new Buch();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
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
				         int id = ((Autor)model.getElementAt(i)).getId();
				         if (id == a.getId())  {
				            identisch = true;
				         }
					}
					if (identisch) {
						JOptionPane.showMessageDialog(null, "Der Autor befindet sich bereits in der Liste");
					}
					else {
						model.addElement(a);
						
						// Zuweisen der Signatur, falls Belletristik-Radiobutton gesetzt
						if (buchView.getBuchtypG().getSelection().getActionCommand() == buchView.BELLETRISTIK
								&& buchView.getSignaturT().getText().isEmpty()) {
							String sign = a.getName().substring(0, (a.getName().length() < 4 ? a.getName().length() : 4));
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
			         int id = ((Schlagwort)model.getElementAt(i)).getId();
			         if (id == s.getId())  {
			            identisch = true;
			         }
				}
				if (identisch) {
					JOptionPane.showMessageDialog(null, "Das Schlagwort befindet sich bereits in der Liste");
				}
				else {
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
						DezimalKlassifikationController dezKlassController = 
								new DezimalKlassifikationController(dezKlassView, buchController);
					
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
		if ((buchView.getBarcodeT().getText().isEmpty())
				|| (buchView.getTitelT().getText().isEmpty())
				|| (buchView.getBarcodeT().getText().isEmpty())
				|| (buchView.getVerlagCbx().getSelectedIndex()< 0)
				|| (buchView.getAuflageT().getText().isEmpty())
				|| (buchView.getAnzahlSeitenT().getText().isEmpty())
				|| (buchView.getIsbnT().getText().isEmpty())
				|| (buchView.getJahrT().getText().isEmpty())
				|| (buchView.getOrtT().getText().isEmpty())
				|| (buchView.getAutorList().getModel().getSize() <= 0)
				|| (buchView.getSignaturT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}
				
		else if (! IntHelfer.istInteger(buchView.getAnzahlSeitenT().getText())) {
			JOptionPane.showMessageDialog(null, "Als Seitenzahl bitte einen Zahlenwert erfassen.");
			keinInputFehler = false;
		}
		else if(! IntHelfer.istInteger(buchView.getJahrT().getText())) {
			JOptionPane.showMessageDialog(null, "Als Jahr bitte einen Zahlenwert erfassen.");
			keinInputFehler = false;
		}
		
		else if(! IntHelfer.istInteger(buchView.getPreisT().getText())
				&& ! IntHelfer.istDecimal(buchView.getPreisT().getText()) ) {
			JOptionPane.showMessageDialog(null, "Als Preis bitte einen Zahlenwert mit oder ohne Dezimalpukt erfassen.");
			keinInputFehler = false;
		}
		
		else if(! IntHelfer.istInteger(buchView.getIsbnT().getText())) {
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
		
		b.setBarcodeNr(Integer.parseInt(buchView.getBarcodeT().getText()));
		b.setTitel(buchView.getTitelT().getText());
		b.setVerlag((Verlag) buchView.getVerlagCbx().getModel().getSelectedItem());
		b.setAuflage(buchView.getAuflageT().getText());
		b.setAnzahlSeiten(Integer.parseInt(buchView.getAnzahlSeitenT().getText()));
		b.setAutoren((List<Autor>) buchView.getAutorList());
		b.setSchlagwoerter((List<Schlagwort>) buchView.getSchlagwortList());
		b.setBemerkung(buchView.getNotizA().getText());
		b.setErfassungDatum(new Date());
		b.setReihe(buchView.getReiheT().getText());
		b.setPreis(new BigDecimal(buchView.getPreisT().getText()));
		b.setErscheinungsJahr(Integer.parseInt(buchView.getJahrT().getText()));
		b.setIsbn(Integer.parseInt(buchView.getIsbnT().getText()));
		b.setErscheinungsOrt(buchView.getOrtT().getText());
		b.setStatus((Status) buchView.getStatusCbx().getModel().getSelectedItem());
		b.setSignatur(buchView.getSignaturT().getText());
		b.setErfasserId(EingeloggterMA.getInstance().getId());
		
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
		return b;
	}
	
	public void signaturSetzen(String signatur) {
		buchView.getSignaturT().setText(signatur);
	}
	
	public void barCodeSetzen(String barCode) {
		buchView.getBarcodeT().setText(barCode);
	}

	public void uebernehmen(Buch buch) {

		buchView.getNeuAendernL().setText("Bearbeiten");
		buchView.getPKT().setText(Integer.toString(buch.getId()));
		buchView.getBarcodeT().setText(Integer.toString(buch.getBarcodeNr()));
		buchView.getTitelT().setText(buch.getTitel());
		buchView.getVerlagCbx().setSelectedIndex(comboBoxModelVerlag.getPositionVerlag(buch.getVerlag()));
		buchView.repaint();
		buchView.getAuflageT().setText(buch.getAuflage());
		buchView.getAnzahlSeitenT().setText(Integer.toString(buch.getAnzahlSeiten()));
		buchView.getReiheT().setText(buch.getReihe());
		buchView.getPreisT().setText(String.valueOf(buch.getPreis()));
		buchView.getJahrT().setText(Integer.toString(buch.getErscheinungsJahr()));
		buchView.getIsbnT().setText(Integer.toString(buch.getIsbn()));
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
		ComboBoxModelSchlagwort comboBoxModelSchlagwort = new ComboBoxModelSchlagwort(normdatenService.alleSchlagworte());
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
