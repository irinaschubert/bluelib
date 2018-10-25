package main;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import dao.AnredeDAO;
import domain.Anrede;
import hilfsklassen.ButtonNamen;
import ui.AutorController;
import ui.AutorView;
import ui.PrototypController;
import ui.PrototypView;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */

public class Main {
	
	public static void main(String[] args){
				
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {                                           
	                PrototypView view = new PrototypView("test"); 
	                new PrototypController(view);
	                
	            }
	        });  
		 
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {                                           
	                AutorView view = new AutorView("Autor"); 
	                new AutorController(view);
	                
	            }
	        });  
		 

	}

}
