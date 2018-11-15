package main;

import javax.swing.SwingUtilities;
import ui.HauptController;
import ui.HauptView;
import ui.SchlagwortController;
import ui.SchlagwortView;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 */

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				HauptView view = new HauptView("Hauptview");
				new HauptController(view);

			}
		});
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SchlagwortView view = new SchlagwortView("Schlagwortview");
				new SchlagwortController(view);

			}
		});
	}

}
