package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import domain.Autor;
import hilfsklassen.ButtonNamen;
import services.LoginService;
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

//	Definieren des Listeners für die Button-Klicks
	private void control() {

		ActionListener anmeldenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object String;
				if ((loginView.getBenutzerNameT().getText().isEmpty())
						|| (loginView.getPasswortP().getPassword().length == 0)) {
					JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
				} else {

					String benutzerName = loginView.getBenutzerNameT().getText();
					String passWort = new String(loginView.getPasswortP().getPassword());
					Verifikation v = null;
					v = new LoginService().loginPruefen(benutzerName, passWort);
					if (v.isAktionErfolgreich()) {
						hauptController.initialisierenNachLogin();
					} else {
						JOptionPane.showMessageDialog(null, v.getNachricht());
						loginView.getPasswortP().setText(null);
					}
				}
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
