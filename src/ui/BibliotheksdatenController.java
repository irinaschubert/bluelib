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

	public BibliotheksdatenController(BibliotheksdatenView view) {
		bibliotheksdatenView = view;
		initialisieren();
	}
	
	public void initialisieren() {
		bibliotheksdatenView.getNameL().setText("Name der Bibliothek:");
		bibliotheksdatenView.getStrasseUndNrL().setText("Strasse & Nr.:");
		bibliotheksdatenView.getOrtL().setText("Ort:");
		bibliotheksdatenView.getEmailL().setText("Email:");
		bibliotheksdatenView.getTelL().setText("Tel.:");
		bibliotheksdatenView.getLeihfristL().setText("Leihfrist:");

		
		bibliotheksdatenView.getNameT().setEditable(false);
		bibliotheksdatenView.getButtonPanel().getButton1().setVisible(false);;
		bibliotheksdatenView.getButtonPanel().getButton2().setVisible(false);;
		bibliotheksdatenView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		bibliotheksdatenView.getButtonPanel().getButton4().setText(ButtonNamen.ABBRECHEN.getName());
		
	}
}
