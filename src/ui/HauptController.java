package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.EingeloggterMA;
import services.LoginService;

/**
 * 
 * Der Hauptcontroller stellt die Menübefehle zur Verfügung und steuert den
 * Aufruf der Views und Controller der jeweiligen Menü- befehle
 * 
 * @version 2018-11-07
 * @author Schmutz
 *
 */

public class HauptController {
	HauptView hauptView;
	HauptController hauptController;
	Boolean entwicklung = true;

	public HauptController(HauptView hauptView) {
		this.hauptView = hauptView;
		this.hauptController = this;
		if (entwicklung) {
			selbstInitialisation();
		} else {

			initialisieren();
		}
		control();
	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {

		hauptView.getMedienAutorM().addActionListener(autorMenueActionListener());
		hauptView.getMedienBuchM().addActionListener(buchMenueActionListener());
		hauptView.getBeendenM().addActionListener(beendenActionLIstener());

	}

	private ActionListener autorMenueActionListener() {
		ActionListener autorMenuActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						AutorView autorView = new AutorView("Autor");
						new AutorController(autorView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(autorView.getPreferredSize()));
						hauptView.getContentPane().add(autorView);
						hauptView.validate();
						hauptView.setVisible(true);

					}
				});

			}
		};

		return autorMenuActionListener;
	}
	
	private ActionListener buchMenueActionListener() {
		ActionListener buchMenuActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						BuchView buchView = new BuchView("Buch");
						new BuchController(buchView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(buchView.getPreferredSize()));
						hauptView.getContentPane().add(buchView);
						hauptView.validate();
						hauptView.setVisible(true);

					}
				});

			}
		};

		return buchMenuActionListener;
	}


	private ActionListener beendenActionLIstener() {
		ActionListener autorBeendenActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				applikationSchliessen();

			}
		};

		return autorBeendenActionListener;
	}

	public void initialisierenNachLogin() {
		hauptView.getJMenuBar().setVisible(true); // Nach der Anmeldung soll die Menubar wieder sichtbar sein
		hauptView.getAdministrationM().setEnabled(EingeloggterMA.getInstance().getMitarbeiter().isAdmin()); // Disablen
																											// Admin-Menü
		panelEntfernen();
	}

	/**
	 * Entfernt den Dialog (JPanel) aus der Hauptview
	 */
	public void panelEntfernen() {
		hauptView.getContentPane().removeAll();
		hauptView.validate();
		hauptView.setVisible(true);
		hauptView.repaint();
	}

	private void initialisieren() {
		hauptView.getJMenuBar().setVisible(false);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoginView loginView = new LoginView("Login");
				new LoginController(loginView, hauptController);
				hauptView.getContentPane().removeAll();
				// hauptView.setSize(new Dimension(loginView.getPreferredSize()));
				hauptView.getContentPane().add(loginView);
				hauptView.validate();
				hauptView.setVisible(true);

			}
		});
	}

	private void selbstInitialisation() {
		new LoginService().loginPruefen("Mike", "abdc");
	}

	public void applikationSchliessen() {
		hauptView.dispose();
		System.exit(0);
	}

}
