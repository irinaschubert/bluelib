package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.Autor;
import domain.Bibliothek;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelVerlag;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die die Stammdaten der Bibliothek
 * 
 * @version 1.0 31.10.2018
 * @author mike
 *
 */

public class BibliothekController {
	private BibliothekView bibliothekView;
	private NormdatenService normdatenService;
	private Bibliothek bibliothekSuchobjekt;
	private HauptController hauptController;

	public BibliothekController(BibliothekView view, HauptController hauptController) {
		bibliothekView = view;
		this.hauptController = hauptController;
		normdatenService = new NormdatenService();
		bibliothekSuchobjekt = new Bibliothek();
		initialisieren();
		control();
	}

	private void control() {
		ActionListener sichernBibliothekActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Bibliothek b = new Bibliothek();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					JOptionPane.showMessageDialog(null, "Bibliotheksstammdaten erfasst");
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					normdatenService.aktualisiereBibliothek(b);
				}
			}
		};
		bibliothekView.getButtonPanel().getButton3().addActionListener(sichernBibliothekActionListener);
	
	
	ActionListener schliessenButtonActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			hauptController.panelEntfernen();
		}

	};
	// Zuweisen des Actionlisteners zum Schliessen-Button
	bibliothekView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);
	
	}
	

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (bibliothekView.getNameT().getText().isEmpty() || (bibliothekView.getLeihfristT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}
		try {
			Integer.parseInt(bibliothekView.getLeihfristT().getText());			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Üngültige Leihfrist. Bitte eine Zahl zwischen 0 und 365 eingeben");
			//e.printStackTrace();
			keinInputFehler = false;
			return keinInputFehler;
		} finally {
			
		}
		if (Integer.parseInt(bibliothekView.getLeihfristT().getText()) < 0 || Integer.parseInt(bibliothekView.getLeihfristT().getText()) > 365) {
			//System.out.println(Integer.parseInt(bibliothekView.getLeihfristT().getText()) >= 0);
			JOptionPane.showMessageDialog(null, "Üngültige Leihfrist. Die Leihfrist kann von 0 bis 365 Tagen sein");
			keinInputFehler = false;
			}
		return keinInputFehler;
	}

	private Bibliothek feldwertezuObjektSpeichern() {
		Bibliothek b = new Bibliothek();
		b.setId(1); //Es soll nur ein Objekt geben
		b.setName(bibliothekView.getNameT().getText());
		b.setStrasseUndNr(bibliothekView.getStrasseUndNrT().getText());
		b.setEmail(bibliothekView.getEmailT().getText());
		b.setTelefon(bibliothekView.getTelT().getText());
		b.setLeihfrist(Integer.parseInt(bibliothekView.getLeihfristT().getText()));
		return b;
	}
	
	private void biblioitheksFelderFuellen() {
		Bibliothek b = normdatenService.bibliothekAnzeigen(); //Bibliothek bestücken
		bibliothekView.getNameT().setText(b.getName());
		bibliothekView.getStrasseUndNrT().setText(b.getStrasseUndNr());
		bibliothekView.getEmailT().setText(b.getEmail());
		bibliothekView.getTelT().setText(b.getTelefon());
		bibliothekView.getLeihfristT().setText(String.valueOf(b.getLeihfrist()));
		
	}

	public void initialisieren() {
		bibliothekView.getNameL().setText("Name der Bibliothek:*");
		bibliothekView.getStrasseUndNrL().setText("Strasse & Nr.:");
		bibliothekView.getOrtL().setText("PLZ und Ort:");
		bibliothekView.getEmailL().setText("Email:");
		bibliothekView.getTelL().setText("Tel.:");
		bibliothekView.getLeihfristL().setText("Leihfrist:*");

		//bibliothekView.getNameT().setEditable(false);
		bibliothekView.getButtonPanel().getButton1().setVisible(false);
		bibliothekView.getButtonPanel().getButton2().setVisible(false);
		bibliothekView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		bibliothekView.getButtonPanel().getButton4().setText(ButtonNamen.ABBRECHEN.getName());
		
		biblioitheksFelderFuellen();
	}
}
