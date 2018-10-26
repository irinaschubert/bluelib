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
import javax.swing.border.EmptyBorder;

/**
 * Zeigt alle Verlage an und ermoeglicht die Erfassung neuer Verlage
 * 
 * @version 1.0 26.10.2018
 * @author irina
 *
 */
public class VerlagView {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel neuerVerlagPanel;
	private JPanel verlagListe;
	private JPanel centerPanel;
	private JLabel PKL;
	private JLabel nameLabel;
	private JLabel gruendungsDatumLabel;
	private JLabel endDatumLabel;
	private JTextField PKT;
	private JTextField nameT;
	private JTextField gruendungsDatumT;
	private JTextField endDatumT;
	private JTable verlagTabelle;
	private LinkedHashMap<JLabel, JComponent> componentTable = new LinkedHashMap<>();

	 public VerlagView(String text){
		 	
		buttonPanel = new StandardButtonPanel();
		verlagListe = new JPanel();
		neuerVerlagPanel = createNeuerAutorPanel();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		verlagTabelle = new JTable();
		JScrollPane scroll = new JScrollPane(verlagTabelle);
		 
		centerPanel.add(scroll, BorderLayout.CENTER);
		centerPanel.add(neuerVerlagPanel, BorderLayout.SOUTH);
		 
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
	    	
	    	neuerVerlagPanel = new JPanel();
	    	neuerVerlagPanel.setLayout(new BorderLayout());
	    	
	    	componentTable.put(PKL = new JLabel(), PKT = new JTextField());
	    	componentTable.put(nameLabel = new JLabel(), nameT = new JTextField());
	    	componentTable.put(gruendungsDatumLabel = new JLabel(), gruendungsDatumT = new JTextField());
	    	componentTable.put(endDatumLabel = new JLabel(), endDatumT = new JTextField());
        	
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
	    	
	    	inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
	    	
	    	neuerVerlagPanel.add(labelPanel, BorderLayout.WEST);
	    	neuerVerlagPanel.add(inputPanel, BorderLayout.CENTER);
	    	
	    	return neuerVerlagPanel;
	    		    	
	    }
	    
	    public void spaltenBreiteSetzen() {
	    	
	    	verlagTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Name
	    	verlagTabelle.getColumnModel().getColumn(1).setPreferredWidth(10); // Gruendungsdatum
	    	verlagTabelle.getColumnModel().getColumn(2).setPreferredWidth(10); // Enddatum
	    }
	    
    
	    public StandardButtonPanel getButton(){
	        return this.buttonPanel;
	    }

		public StandardButtonPanel getButtonPanel() {
			return buttonPanel;
		}

		public JPanel getNeuerVerlagPanel() {
			return neuerVerlagPanel;
		}

		public JPanel getVerlagListe() {
			return verlagListe;
		}

		public JLabel getNameLabel() {
			return nameLabel;
		}

		public JLabel getGruendungsDatumLabel() {
			return gruendungsDatumLabel;
		}
		
		public JLabel getEndDatumLabel() {
			return endDatumLabel;
		}


		public JTextField getNameT() {
			return nameT;
		}

		public JTextField getGruendungsDatumT() {
			return gruendungsDatumT;
		}

		public JTextField getEndDatumT() {
			return endDatumT;
		}

		public JTextField getPKT() {
			return PKT;
		}
		
		public JLabel getPKL() {
			return PKL;
		}

		public JTable getVerlagTabelle() {
			return verlagTabelle;
		}
		
		public LinkedHashMap<JLabel, JComponent> getComponents() {
			return componentTable;
		}
		
		public void schliessen() {
			frame.dispose();
		}


}
