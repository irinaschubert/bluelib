package ui;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Prototyp einer View, welche ein Label, Buttons und eine Combobox enthält.
 * 
 * @version 1.0 18.10.2018
 * @author BlueLib
 *
 */
public class PrototypView {
	private JFrame frame;
	private JLabel label;
	private JComboBox anredeComboBox;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;
	
	 public PrototypView(String text){
		 	
		 anredeComboBox = new JComboBox<>();
		 anredeComboBox.setRenderer(new AnredeRenderer());
		 label = new JLabel();
		 buttonPanel = new StandardButtonPanel();
		 centerPanel = new JPanel();
		 
		 	frame = new JFrame("View");                                    
	        frame.getContentPane().setLayout(new BorderLayout());                                          
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
	        frame.setSize(400,200);        
	        frame.setVisible(true);
	        
	        label = new JLabel();
	        
	        centerPanel.add(label);
	        centerPanel.add(anredeComboBox);
	        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	        
	        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);        
	    }
	        
	    public StandardButtonPanel getButtonPanel(){
	        return this.buttonPanel;
	    }
	    
	    public JComboBox getAnredeComboBox(){
	        return this.anredeComboBox;
	    }
	    
	    public JLabel getLabel() {
	    	return this.label;
	    }
	    
	    
	    
	    

}
