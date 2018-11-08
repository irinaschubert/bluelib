package main;

import javax.swing.SwingUtilities;
import ui.AutorController;
import ui.VerlagController;
import ui.AutorView;
import ui.HauptController;
import ui.HauptView;
import ui.VerlagView;
import ui.BibliotheksdatenView;
import ui.BibliotheksdatenController;
import ui.PrototypController;
import ui.PrototypView;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */

public class Main {

	public static void main(String[] args) {

//		 SwingUtilities.invokeLater(new Runnable() {
//	            @Override
//	            public void run() {                                           
//	                PrototypView view = new PrototypView("test"); 
//	                new PrototypController(view);
//	                
//	            }
//	        });  
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {                                           
	            	BibliotheksdatenView view = new BibliotheksdatenView("Bibliotheksdaten");
	            	new BibliotheksdatenController(view);	                
	            }
	        });  

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
				VerlagView view = new VerlagView("Verlag");
				new VerlagController(view);
			}
		});
	}

}
