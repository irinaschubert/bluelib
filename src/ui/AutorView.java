package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

/**
 * Zeigt alle Autoren an und ermoeglicht die Erfassung neuer Autoren
 * 
 * @version 1.0 24.10.2018
 * @author Schmutz
 *
 */
public class AutorView {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel neuerAutorPanel;
	private JPanel autorenListe;
	private JPanel centerPanel;
	private JLabel PKL;
	private JLabel vornameL;
	private JLabel nachnameL;
	private JLabel geburtsDatumL;
	private JLabel todesDatumL;
	private JTextField PKT;
	private JTextField vornameT;
	private JTextField nachnameT;
	private JTextField geburtsDatumT;
	private JTextField todesDatumT;
	private JTable autorenTabelle;
	private LinkedHashMap<JLabel, JComponent> componentTable = new LinkedHashMap<>();
	

	 public AutorView(String text){
		 	
		buttonPanel = new StandardButtonPanel();
		autorenListe = new JPanel();
		neuerAutorPanel = createNeuerAutorPanel();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		autorenTabelle = new JTable();
		// Nur eine Zeile darf ausgewaehl werden
		autorenTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 JScrollPane scroll = new JScrollPane(autorenTabelle);
		 
		 centerPanel.add(scroll, BorderLayout.CENTER);
		 centerPanel.add(neuerAutorPanel, BorderLayout.SOUTH);
		 
	 	frame = new JFrame("View");                                    
        frame.getContentPane().setLayout(new BorderLayout());                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setSize(500,300);        
        frame.setVisible(true);
       
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);        
	    }
	        

	    
	    /**
	     * Befüllt LinkedHashMap mit Labels und Input-Feldern und gibt die Felder in ein JPanel aus
	     * Vorteil: Es können beliebig Felder hinzugefügt werden.
	     * @return JPanel
	     */
	    private JPanel createNeuerAutorPanel() {
	    	
	    	neuerAutorPanel = new JPanel();
	    	neuerAutorPanel.setLayout(new BorderLayout());
	    	
	    	componentTable.put(PKL = new JLabel(), PKT = new JTextField());
	    	componentTable.put(nachnameL = new JLabel(), nachnameT = new JTextField());
	    	componentTable.put(vornameL = new JLabel(), vornameT = new JTextField());
	    	componentTable.put(geburtsDatumL = new JLabel(), geburtsDatumT = new JTextField());
	    	componentTable.put(todesDatumL = new JLabel(), todesDatumT = new JTextField());
        	
	    	JPanel labelPanel = new JPanel();
	    	labelPanel.setLayout(new GridLayout(componentTable.size(), 0));
	    	for (JLabel e : componentTable.keySet()) {
	    		labelPanel.add(e);
	    	}
	    	
	    	labelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

	    	JPanel inputPanel = new JPanel();
	    	inputPanel.setLayout(new GridLayout(componentTable.size(), 0));
	    	for (JComponent e : componentTable.values()) {
	    		inputPanel.add(e);
	    	}
	    	
	    	neuerAutorPanel.add(labelPanel, BorderLayout.WEST);
	    	neuerAutorPanel.add(inputPanel, BorderLayout.CENTER);
	    	
	    	return neuerAutorPanel;
	    		    	
	    }
	    
	    public void spaltenBreiteSetzen() {
	    	
	    	autorenTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Name
	    	autorenTabelle.getColumnModel().getColumn(1).setPreferredWidth(80); // Vorname
	    	autorenTabelle.getColumnModel().getColumn(2).setPreferredWidth(10); // Geb-Datum
	    	autorenTabelle.getColumnModel().getColumn(3).setPreferredWidth(10); // Todesdatum
	    }
	    
    
	    public StandardButtonPanel getButton(){
	        return this.buttonPanel;
	    }

		public StandardButtonPanel getButtonPanel() {
			return buttonPanel;
		}

		public JPanel getNeuerAutorPanel() {
			return neuerAutorPanel;
		}

		public JPanel getAutorenListe() {
			return autorenListe;
		}

		public JLabel getVornameL() {
			return vornameL;
		}

		public JLabel getNachnameL() {
			return nachnameL;
		}

		public JLabel getGeburtsDatumL() {
			return geburtsDatumL;
		}

		public JLabel getTodesDatumL() {
			return todesDatumL;
		}

		public JTextField getVornameT() {
			return vornameT;
		}

		public JTextField getNachnameT() {
			return nachnameT;
		}

		public JTextField getGeburtsDatumT() {
			return geburtsDatumT;
		}

		public JTextField getTodesDatumT() {
			return todesDatumT;
		}

		public JTextField getPKT() {
			return PKT;
		}
		
		public JLabel getPKL() {
			return PKL;
		}

		public JTable getAutorenTabelle() {
			return autorenTabelle;
		}
		
		public LinkedHashMap<JLabel, JComponent> getComponents() {
			return componentTable;
		}
		
		public void schliessen() {
			frame.dispose();
		}


}
