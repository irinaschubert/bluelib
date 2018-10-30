package main;

import javax.swing.SwingUtilities;
import ui.AutorController;
import ui.VerlagController;
import ui.AutorView;
import ui.VerlagView;
import ui.PrototypController;
import ui.PrototypView;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */

public class Main {
	
	public static void main(String[] args){
				
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
	                AutorView view = new AutorView("Autor"); 
	                new AutorController(view);
	                
	            }
	        });
		 
//		 SwingUtilities.invokeLater(new Runnable() {
//	            @Override
//	            public void run() {                                           
//	                VerlagView view = new VerlagView("Verlag"); 
//	                new VerlagController(view);
//	                
//	            }
//	        });  
		 

	}

}
