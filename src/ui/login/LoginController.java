package ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import domain.Autor;
import hilfsklassen.ButtonNamen;
import services.LoginService;
import services.Verifikation;
import ui.HauptController;

/**
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
		loginView.getButtonPanel().getButton4().addActionListener(anmeldenActionListener);

		ActionListener abbrechenActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.applikationSchliessen();
			}
		};
		loginView.getButtonPanel().getButton3().addActionListener(abbrechenActionListener);
	}

	public void initialisieren() {
		loginView.getBenutzerNameL().setText("Benutzername*:");
		loginView.getPasswortL().setText("Passwort*:");
		loginView.getButtonPanel().getButton1().setVisible(false);
		loginView.getButtonPanel().getButton2().setVisible(false);
		loginView.getButtonPanel().getButton3().setText(ButtonNamen.ABBRECHEN.getName());
		loginView.getButtonPanel().getButton4().setText(ButtonNamen.ANMELDEN.getName());
		loginView.getButtonPanel().getButton4().requestFocus();
	}
}
