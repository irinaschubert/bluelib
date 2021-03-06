package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

import domain.EingeloggterMA;
import services.LoginService;
import ui.ausleihe.AusleiheController;
import ui.ausleihe.AusleiheView;
import ui.autor.AutorController;
import ui.autor.AutorView;
import ui.benutzer.BenutzerController;
import ui.benutzer.BenutzerView;
import ui.bibliothek.BibliothekController;
import ui.bibliothek.BibliothekView;
import ui.buch.BuchController;
import ui.buch.BuchView;
import ui.login.LoginController;
import ui.login.LoginView;
import ui.ma.MitarbeiterController;
import ui.ma.MitarbeiterView;
import ui.rueckgabe.RueckgabeController;
import ui.rueckgabe.RueckgabeView;
import ui.schlagwort.SchlagwortController;
import ui.schlagwort.SchlagwortView;
import ui.verlag.VerlagController;
import ui.verlag.VerlagView;

/**
 * 
 * Der Hauptcontroller stellt die Menuebefehle zur Verf�gung und steuert den
 * Aufruf der Views und Controller der jeweiligen Men�befehle
 * 
 * @version 2018-11-07
 * @author Ueli
 *
 */

public class HauptController {
	HauptView hauptView;
	HauptController hauptController;
	Boolean entwicklung = false;

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

	private void control() {

		hauptView.getMedienBuchM().addActionListener(buchMenueActionListener());
		hauptView.getMedienAutorM().addActionListener(autorMenueActionListener());
		hauptView.getBeendenM().addActionListener(beendenActionListener());
		hauptView.getAdministrationStammdatenM().addActionListener(stammdatenMenuActionListener());
		hauptView.getMedienVerlagM().addActionListener(verlagMenueActionListener());
		hauptView.getBenutzerBenutzerM().addActionListener(benutzerMenuActionListener());
		hauptView.getAdministrationSchagworteM().addActionListener(schlagwortMenuActionListener());
		hauptView.getAusleiheAusleiheM().addActionListener(ausleiheMenueActionListener());
		hauptView.getAusleiheRueckgabeM().addActionListener(rueckgabeMenueActionListener());
		hauptView.getAdministrationMitarbeiterM().addActionListener(mitarbeiterMenuActionListener());
		
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
	
	private ActionListener ausleiheMenueActionListener() {
		ActionListener ausleiheMenueActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ausleiheAnzeigen();
			}
		};
		return ausleiheMenueActionListener;
	}
	
	private ActionListener rueckgabeMenueActionListener() {
		ActionListener rueckgabeMenueActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rueckgabeAnzeigen();
			}
		};
		return rueckgabeMenueActionListener;
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
	
	private ActionListener schlagwortMenuActionListener() {
		ActionListener schlagwortMenuActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						SchlagwortView schlagwortView = new SchlagwortView("Schlagwort");
						new SchlagwortController(schlagwortView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(schlagwortView.getPreferredSize()));
						hauptView.getContentPane().add(schlagwortView);
						hauptView.validate();
						hauptView.setVisible(true);
					}
				});
			}
		};
		return schlagwortMenuActionListener;
	}
	
	private ActionListener mitarbeiterMenuActionListener() {
		ActionListener mitarbeiterMenuActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						MitarbeiterView mitarbeiterView = new MitarbeiterView("Mitarbeiter");
						new MitarbeiterController(mitarbeiterView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(mitarbeiterView.getPreferredSize()));
						hauptView.getContentPane().add(mitarbeiterView);
						hauptView.validate();
						hauptView.setVisible(true);
					}
				});
			}
		};
		return mitarbeiterMenuActionListener;
	}

	
	private ActionListener benutzerMenuActionListener() {
		ActionListener benutzerMenuActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						BenutzerView benutzerView = new BenutzerView("Benutzer");
						new BenutzerController(benutzerView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(benutzerView.getPreferredSize()));
						hauptView.getContentPane().add(benutzerView);
						hauptView.validate();
						hauptView.setVisible(true);
					}
				});
			}
		};
		return benutzerMenuActionListener;
	}

	private ActionListener verlagMenueActionListener() {
		ActionListener verlagMenueActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						VerlagView verlagView = new VerlagView("Verlag");
						new VerlagController(verlagView, hauptController);
						hauptView.getContentPane().removeAll();
						hauptView.setSize(new Dimension(verlagView.getPreferredSize()));
						hauptView.getContentPane().add(verlagView);
						hauptView.validate();
						hauptView.setVisible(true);
					}
				});
			}
		};
		return verlagMenueActionListener;
	}


	private ActionListener beendenActionListener() {
		ActionListener autorBeendenActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applikationSchliessen();
			}
		};
		return autorBeendenActionListener;
	}
	
	public void ausleiheAnzeigen() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AusleiheView ausleiheView = new AusleiheView("Ausleihe");
				new AusleiheController(ausleiheView, hauptController);
				hauptView.getContentPane().removeAll();
				hauptView.setSize(new Dimension(ausleiheView.getPreferredSize()));
				hauptView.getContentPane().add(ausleiheView);
				hauptView.validate();
				hauptView.setVisible(true);
			}
		});
	}
	
	public void rueckgabeAnzeigen() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RueckgabeView rueckgabeView= new RueckgabeView("R�ckgabe"); 
				new RueckgabeController(rueckgabeView, hauptController);
				hauptView.getContentPane().removeAll();
				hauptView.setSize(new Dimension(rueckgabeView.getPreferredSize()));
				hauptView.getContentPane().add(rueckgabeView);
				hauptView.validate();
				hauptView.setVisible(true);
			}
		});
	}

	public void initialisierenNachLogin() {
		hauptView.getJMenuBar().setVisible(true); // Nach der Anmeldung soll die Menubar wieder sichtbar sein
		Boolean admin = EingeloggterMA.getInstance().getMitarbeiter().isAdmin();
		// Disabling der nicht berechtigten Menuepunkte fuer nicht-Administratoren
		hauptView.getAdministrationMitarbeiterM().setEnabled(admin);
		hauptView.getAdministrationStammdatenM().setEnabled(admin);
//		hauptView.getAdministrationM().setEnabled(EingeloggterMA.getInstance().getMitarbeiter().isAdmin()); // Disablen
//																											// Admin-Men�
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
				hauptView.setSize(new Dimension(loginView.getPreferredSize()));
				hauptView.getContentPane().add(loginView);
				hauptView.validate();
				hauptView.setVisible(true);
			}
		});
	}
	
	private void selbstInitialisation() {
		new LoginService().loginPruefen("Mike", "abcd");
	}

	public void applikationSchliessen() {
		hauptView.dispose();
		System.exit(0);
	}

}
