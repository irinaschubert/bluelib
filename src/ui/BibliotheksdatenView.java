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
	 * Zeigt die Bibliotheksdaten. Die Ausleihdauer ist veränderbar.
	 * 
	 * @version 1.0 3.10.2018
	 * @author Mike
	 *
	 */
	public class BibliotheksdatenView {
		private JFrame frame;
		private StandardButtonPanel buttonPanel;
		private JPanel bibliPanel;
		private JPanel centerPanel;
		private JLabel PKL;
		private JLabel nameLabel;
		private JLabel strasseLabel;
		private JLabel plzLabel;
		private JLabel ortLabel;
		private JLabel emailLabel;
		private JLabel telLabel;
		private JLabel ausleihLabel;
		private JTextField PKT;
		private JTextField nameT;
		private JTextField strasseT;
		private JTextField plzT;
		private JTextField ortT;
		private JTextField emailT;
		private JTextField telT;
		private JTextField ausleihT;
		private LinkedHashMap<JLabel, JComponent> componentTable = new LinkedHashMap<>();

		 public BibliotheksdatenView(String frameTitel){
			 	
			buttonPanel = new StandardButtonPanel();
			bibliPanel = createBibliotheksdatenPanel();
			centerPanel = new JPanel(new BorderLayout());
			centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			
			centerPanel.add(bibliPanel, BorderLayout.SOUTH);
			 
		 	frame = new JFrame("View");                                    
	        frame.getContentPane().setLayout(new BorderLayout());                                          
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
	        frame.setSize(500,300);        
	        frame.setVisible(true);
		    
			frame.getContentPane().add(new StandardTitelPanel(frameTitel), BorderLayout.NORTH);
	        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);        
		    }
		        

		    /**
		     * Befüllt LinkedHashMap mit Labels und Input-Feldern und gibt die Felder in ein JPanel aus
		     * Vorteil: Es können beliebig Felder hinzugefügt werden.
		     * @return JPanel
		     */
		    private JPanel createBibliotheksdatenPanel() {
		    	
		    	bibliPanel = new JPanel();
		    	bibliPanel.setLayout(new BorderLayout());
		    	
		    	componentTable.put(PKL = new JLabel(), PKT = new JTextField());
		    	componentTable.put(nameLabel = new JLabel(), nameT = new JTextField());
		    	componentTable.put(strasseLabel = new JLabel(), strasseT = new JTextField());
		    	componentTable.put(plzLabel = new JLabel(), plzT = new JTextField());
		    	componentTable.put(ortLabel = new JLabel(), ortT = new JTextField());
		    	componentTable.put(emailLabel = new JLabel(), emailT = new JTextField());
		    	componentTable.put(telLabel = new JLabel(), telT = new JTextField());
		    	componentTable.put(ausleihLabel = new JLabel(), ausleihT = new JTextField());
	        	
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
		    	
		    	bibliPanel.add(labelPanel, BorderLayout.WEST);
		    	bibliPanel.add(inputPanel, BorderLayout.CENTER);
		    			    	
		    	return bibliPanel;
		    		    	
		    }    
	    
	    
		    public StandardButtonPanel getButton(){
		        return this.buttonPanel;
		    }

			public StandardButtonPanel getButtonPanel() {
				return buttonPanel;
			}

			public JPanel getbibliPanel() {
				return bibliPanel;
			}

			public JTextField getPKT() {
				return PKT;
			}
			public JLabel getPKL() {
				return PKL;
			}
			
			public JLabel getNameLabel() {
				return nameLabel;
			}

			public JTextField getNameT() {
				return nameT;
			}
			
			public JLabel getStrasseLabel() {
				return strasseLabel;
			}

			public JTextField getStrasseT() {
				return strasseT;
			}
			public JLabel getPlzLabel() {
				return plzLabel;
			}
			public JTextField getplzT() {
				return plzT;
			}

			public JLabel getOrtLabel() {
				return ortLabel;
			}
			
			public JTextField getOrtT() {
				return ortT;
			}
			
			public JLabel getEmailLabel() {
				return emailLabel;
			}
			
			public JTextField getEmailT() {
				return emailT;
			}
		
			public JLabel getTelLabel() {
				return telLabel;
			}
			
			public JTextField getTelT() {
				return telT;
			}
			
			
			public JLabel getAusleihLabel() {
				return telLabel;
			}
			
			public JTextField getAusleihT() {
				return ausleihT;
			}	
			

			
			public LinkedHashMap<JLabel, JComponent> getComponents() {
				return componentTable;
			}
			
			public void schliessen() {
				frame.dispose();
			}


	}
