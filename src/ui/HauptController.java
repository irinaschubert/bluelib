package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 * 
 * Der Hauptcontroller stellt die Menübefehle zur Verfügung und steuert den Aufruf der Views und Controller der jeweiligen Menü-
 * befehle
 * @version 2018-11-07
 * @author Schmutz
 *
 */

public class HauptController {
	HauptView hauptView;
	HauptController hauptController;

	public HauptController(HauptView hauptView) {
		this.hauptView = hauptView;
		this.hauptController = this;
		control();
	}

//	Definierten des Listeners für die Button-Klicks
	private void control() {

		hauptView.getMedienAutorM().addActionListener(autorMenueActionListener());
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

	private ActionListener beendenActionLIstener() {
		ActionListener autorBeendenActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				hauptView.dispose();
				System.exit(0);

			}
		};

		return autorBeendenActionListener;
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

	}

}
