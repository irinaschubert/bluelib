package main;

import javax.swing.SwingUtilities;
import ui.AutorController;
import ui.VerlagController;
import ui.BenutzerController;
import ui.BenutzerView;
import ui.AutorView;
import ui.HauptController;
import ui.HauptView;
import ui.VerlagView;
import ui.PrototypController;
import ui.PrototypView;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */

public class Main {

	public static void main(String[] args) {
		 
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {                                           
	            	BenutzerView view = new BenutzerView("Benutzer"); 
	                new BenutzerController(view);
	                
	            }
	        });  
		 

		 SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				HauptView view = new HauptView("Hauptview");
				new HauptController(view);

			}
		});

	}

}
