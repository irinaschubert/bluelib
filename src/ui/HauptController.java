package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		hauptView.getBeendenM().addActionListener(beendenActionLIstener());
		hauptView.getAdministrationStammdatenM().addActionListener(stammdatenMenuActionListener());

	}

	private ActionListener stammdatenMenuActionListener() {
		ActionListener stammdatenMenuActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						BibliothekView bibliothekView = new BibliothekView("Bibliotheksdaten");
						new BibliothekController(bibliothekView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(bibliothekView.getPreferredSize()));
						hauptView.getContentPane().add(bibliothekView);
						hauptView.validate();
						hauptView.setVisible(true);

					}
				});

			}
		};
		return stammdatenMenuActionListener;
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
