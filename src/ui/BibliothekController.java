package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public BibliothekController(BibliothekView view) {
		bibliothekView = view;
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
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					normdatenService.aktualisiereBibliothek(b);
				}
			}
		};
		bibliothekView.getButtonPanel().getButton3().addActionListener(sichernBibliothekActionListener);
	}

	private boolean inputValidierungSpeichern() {
		return true;
	}

	private Bibliothek feldwertezuObjektSpeichern() {
		Bibliothek b = new Bibliothek();
		b.setId(1); //Es soll nur ein Objekt geben
		b.setName(bibliothekView.getNameT().getText());
		b.setStrasseUndNr(bibliothekView.getStrasseUndNrT().getText());
		b.setEmail(bibliothekView.getEmailT().getText());
		b.setTelefon(bibliothekView.getTelT().getText());
		b.setLeihfrist(Integer.parseInt(bibliothekView.getTelT().getText()));
		return b;
	}
	
	private void biblioitheksFelderFuellen() {
		Bibliothek b = normdatenService.bibliothekAnzeigen(); //Bibliothek bestücken

		System.out.println(b.getEmail());
		
		bibliothekView.getNameT().setText(b.getName());
		bibliothekView.getStrasseUndNrT().setText(b.getStrasseUndNr());
		bibliothekView.getEmailT().setText(b.getEmail());
		bibliothekView.getTelT().setText(b.getTelefon());
		//bibliothekView.getLeihfristT().setText(b.getLeihfrist());
		
		/*
		// Wie update dann die View?
		b.setName(bibliothekView.getNameT().getText());
		b.setStrasseUndNr(bibliothekView.getStrasseUndNrT().getText());
		b.setEmail(bibliothekView.getEmailT().getText());
		b.setTelefon(bibliothekView.getTelT().getText());
		b.setLeihfrist(Integer.parseInt(bibliothekView.getTelT().getText()));
		*/
		
	}

	public void initialisieren() {
		bibliothekView.getNameL().setText("Name der Bibliothek:");
		bibliothekView.getStrasseUndNrL().setText("Strasse & Nr.:");
		bibliothekView.getOrtL().setText("Ort:");
		bibliothekView.getEmailL().setText("Email:");
		bibliothekView.getTelL().setText("Tel.:");
		bibliothekView.getLeihfristL().setText("Leihfrist:");

		bibliothekView.getNameT().setEditable(false);
		bibliothekView.getButtonPanel().getButton1().setVisible(false);
		;
		bibliothekView.getButtonPanel().getButton2().setVisible(false);
		;
		bibliothekView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		bibliothekView.getButtonPanel().getButton4().setText(ButtonNamen.ABBRECHEN.getName());
		biblioitheksFelderFuellen(); //Mehtode muss korigiert werden
	}
}
