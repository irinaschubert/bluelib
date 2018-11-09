package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import domain.Autor;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import models.TableModelAutor;
import services.NormdatenService;
import services.Verifikation;

/**
 * 
 * Controller für die Login-View
 * 
 * @version 1.0 2018-11-07
 * @author Schmutz
 *
 */

public class LoginController {
	private LoginView loginView;
	private HauptController hauptController;

	public LoginController(LoginView view, HauptController hauptController) {
		loginView = view;
		this.hauptController = hauptController;
	
		initialisieren();
		control();

	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {

		ActionListener anmeldenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			
			}

		};

		// Zuweisen des Actionlisteners zum Anmelden-Button
		loginView.getButtonPanel().getButton4().addActionListener(anmeldenActionListener);

		ActionListener abbrechenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.applikationSchliessen();
			}

		};

		// Zuweisen des Actionlisteners zum Abbrechen-Button
		loginView.getButtonPanel().getButton3().addActionListener(abbrechenActionListener);



	}

	private boolean inputValidierungSuchen() {
		boolean keinInputFehler = true;

	

		return keinInputFehler;

	}

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
	
		return keinInputFehler;

	}

	private Autor feldwertezuObjektSpeichern() {
		Autor a = new Autor();
		
		return a;
	}

	

	
	

	private void felderLeeren() {

		// Felder leeren
		loginView.getBenutzerNameT().setText("");
		loginView.getPasswortP().setText("");

		
	}

	public void initialisieren() {

		loginView.getBenutzerNameL().setText("Benutzername*:");
		loginView.getPasswortL().setText("Passwort*:");
		loginView.getButtonPanel().getButton1().setVisible(false);
		loginView.getButtonPanel().getButton2().setVisible(false);
		loginView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
		loginView.getButtonPanel().getButton4().setText(ButtonNamen.ANMELDEN.getName());

	}
}
