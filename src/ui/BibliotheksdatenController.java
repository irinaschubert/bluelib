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

public class BibliotheksdatenController {
	private BibliotheksdatenView bibliotheksdatenView;
	private NormdatenService normdatenService;
	private List<Bibliothek> BibliotheksdatenListe;
	private java.util.Date gruendungsDatum = new java.util.Date();
	private java.util.Date endDatum = new java.util.Date();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public BibliotheksdatenController(BibliotheksdatenView view) {
		bibliotheksdatenView = view;
		normdatenService = new NormdatenService();
		initialisieren();
		//control();

	}
	public void initialisieren() {

		bibliotheksdatenView.getPKL().setText("Nr:");
		bibliotheksdatenView.getNameLabel().setText("Name:");
		bibliotheksdatenView.getStrasseLabel().setText("Strasse:");
		bibliotheksdatenView.getPlzLabel().setText("PLZ:");
		bibliotheksdatenView.getOrtLabel().setText("Ort:");
		bibliotheksdatenView.getEmailLabel().setText("Email:");
		bibliotheksdatenView.getTelLabel().setText("Tel.:");
		bibliotheksdatenView.getAusleihLabel().setText("Ausleihfrist:");

		
		bibliotheksdatenView.getPKT().setEditable(false);
		bibliotheksdatenView.getButtonPanel().getButton1().setText(ButtonNamen.SICHERN.getName());
		bibliotheksdatenView.getButtonPanel().getButton2().setText(ButtonNamen.ABBRECHEN.getName());
	}
}
