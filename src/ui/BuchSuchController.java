package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import domain.Autor;
import domain.Buch;
import domain.Status;
import domain.Verlag;
import hilfsklassen.BarcodePruefung;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.IntHelfer;
import models.ComboBoxModelAutor;
import models.ComboBoxModelVerlag;
import models.TableModelAutor;
import models.TableModelBuch;
import services.MedienhandlingService;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die BuchSuchView, der die Logik und die Benutzeraktionen der
 * View steuert und der View die Models übergibt
 * 
 * @version 1.0 2018-11-13
 * @author Schmutz
 *
 */

public abstract class BuchSuchController {
	protected BuchSuchView buchSuchView;
	private List<Buch> buchL;
	private MedienhandlingService medienHandlingService;
	private NormdatenService normdatenService;
	protected TableModelBuch tableModelBuch;
	private Buch buchSuchobjekt;

	public BuchSuchController(BuchSuchView view) {
		buchSuchView = view;
		medienHandlingService = new MedienhandlingService();
		normdatenService = new NormdatenService();
		buchSuchobjekt = new Buch();
//		view.spaltenBreiteSetzen();


		suchPanelInitialisieren();
		tabellenPanelInitialisieren();
		control();

	}
	
	private void control() {
		
		buchSuchView.getBarcodeSucheT().addKeyListener(barcodeScanningKeyAdapter());
		buchSuchView.getSuchButton().addActionListener(suchenButtonActionListener());
	
	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

		if (!buchSuchView.getBarcodeSucheT().getText().isEmpty()) {
			if (IntHelfer.istInteger(buchSuchView.getBarcodeSucheL().getText())) {
				JOptionPane.showMessageDialog(null, "Üngültiges Barcodeformat");
				buchSuchView.getBarcodeSucheT().setText("");
				keinInputFehler = false;
			}

		}

		return keinInputFehler;

	}
	
	private ActionListener suchenButtonActionListener(){
		ActionListener suchenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputValidierungSuchen()) {
					buchSuchobjekt = feldwertezuObjektSuchen();
					buchL = medienHandlingService.buchSuchen(buchSuchobjekt);
					tableModelBuch.setAndSortListe(buchL);
				}

			}

		};
		return suchenButtonActionListener;
	}
	
	private KeyAdapter barcodeScanningKeyAdapter() {

		KeyAdapter barcodeScanningKeyListener = new KeyAdapter() {

			 @Override
		        public void keyPressed(KeyEvent e) {
		            if(e.getKeyCode() == KeyEvent.VK_ENTER){
		            	Verifikation v = BarcodePruefung.istBarcode(buchSuchView.getBarcodeSucheT().getText());
		            	if (!v.isAktionErfolgreich()) {
		            		JOptionPane.showMessageDialog(null, v.getNachricht());
		            		buchSuchView.getBarcodeSucheT().setText("");
		            	}
		            	else {
		            		int barCode = Integer.parseInt(buchSuchView.getBarcodeSucheT().getText());
		            		buchSuchobjekt.setBarcodeNr(barCode);
		            		List<Buch> buchListe = null;
		            		buchListe = medienHandlingService.buchSuchen(buchSuchobjekt);
		            		if (buchListe != null) {
		            			buchL = buchListe;
		    					tableModelBuch.setAndSortListe(buchL);
		            		}
		            		else {
		            			buchSuchView.getBarcodeSucheT().setText("");
		            			JOptionPane.showMessageDialog(null, "Zu diesem Barcode existiert kein Buch");
		            		}
		                 	}
    	
		            	
		            }
		        }

		    };
		return barcodeScanningKeyListener;
	}

	private Buch feldwertezuObjektSuchen() {
		Buch b = new Buch();
		if (!buchSuchView.getBarcodeSucheT().getText().isEmpty()) {
			int barCode = IntHelfer.stringZuInt(buchSuchView.getBarcodeSucheT().getText());
			b.setBarcodeNr(barCode);
		}
		if (!buchSuchView.getTitelSucheT().getText().isEmpty()) {
			b.setTitel(buchSuchView.getTitelSucheT().getText());
		}

		if (buchSuchView.getAutorSucheCbx().getSelectedIndex() > 0) { // 0 = kein Autor ausgewählt
			Autor a = new Autor();
			a = (Autor) buchSuchView.getAutorSucheCbx().getModel().getSelectedItem();
			b.setAutor(a);
		}

		if (buchSuchView.getVerlagSucheCbx().getSelectedIndex() > 0) { // 0 = kein Verlag ausgewählt
			b.setVerlag((Verlag) buchSuchView.getVerlagSucheCbx().getModel().getSelectedItem());
		}

		if (!buchSuchView.getSignaturSucheT().getText().isEmpty()) {
			b.setSignatur(buchSuchView.getSignaturSucheT().getText());
		}

		b.setStatus((Status) buchSuchView.getStatusSucheCbx().getModel().getSelectedItem());

		return b;
	}

	private void nachAarbeitSpeichern() {

		tableModelBuch.setAndSortListe(medienHandlingService.buchSuchen(buchSuchobjekt));

	}

	public void suchPanelInitialisieren() {

//		buchView.getPKL().setText("Nr:");
		buchSuchView.getBarcodeSucheL().setText("Barcode:");
		buchSuchView.getTitelSucheL().setText("Titel:");
		buchSuchView.getAutorSucheL().setText("Autor:");
		buchSuchView.getVerlagSucheL().setText("Verlag:");
		buchSuchView.getSignaturSucheL().setText("Signatur:");
		buchSuchView.getStatusSucheL().setText("Status:");
		buchSuchView.getSuchButton().setText("Suchen");

		ComboBoxModelVerlag comboBoxModelVerlag = new ComboBoxModelVerlag(normdatenService.alleVerlage());
		comboBoxModelVerlag.leerenEintragErstellen();
		buchSuchView.getVerlagSucheCbx().setModel(comboBoxModelVerlag);
		buchSuchView.getVerlagSucheCbx().setSelectedIndex(0);
		ComboBoxModelAutor comboBoxModelAutor = new ComboBoxModelAutor(normdatenService.alleautoren());
		comboBoxModelAutor.leerenEintragErstellen();
		buchSuchView.getAutorSucheCbx().setModel(comboBoxModelAutor);
		buchSuchView.getAutorSucheCbx().setSelectedIndex(0);
		buchSuchView.getStatusSucheCbx()
				.setModel(new DefaultComboBoxModel(medienHandlingService.alleMedienStati().toArray()));

	}

	private void tabellenPanelInitialisieren() {
		buchL = new ArrayList<>();
		tableModelBuch = new TableModelBuch();
		tableModelBuch.setAndSortListe(buchL);
		buchSuchView.getBuchTabelle().setModel(tableModelBuch);
		buchSuchView.spaltenBreiteSetzen();

	}

}
