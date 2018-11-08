package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JLabel nameL;
	private JLabel gruendungsDatumL;
	private JLabel endDatumL;
	private JLabel geloeschtL;
	private JLabel nameSucheL;
	private JLabel gruendungsDatumSucheL;
	private JLabel endDatumSucheL;
	private JLabel geloeschtSucheL;
	private JTextField PKT;
	private JTextField nameT;
	private JTextField gruendungsDatumT;
	private JTextField endDatumT;
	private JTable verlagTabelle;
	private JCheckBox geloeschtCbx;
	private JTextField nameSucheT;
	private JTextField gruendungsDatumSucheT;
	private JTextField endDatumSucheT;
	private JCheckBox geloeschtSucheCbx;
	private JButton suchButton;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuAktualisieren = new LinkedHashMap<>();

	 public VerlagView(String frameTitel){
		 	
		buttonPanel = new StandardButtonPanel();
		verlagListe = new JPanel();
		neuerVerlagPanel = createNeuerVerlagPanel();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		suchButton = new JButton();
		new JLabel(frameTitel);
		verlagTabelle = new JTable();
		verlagTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(verlagTabelle);
		
		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Verlage:");
		
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(tabellenPanel, BorderLayout.CENTER);
		centerPanel.add(neuerVerlagPanel, BorderLayout.SOUTH);
		 
	 	frame = new JFrame("BlueLib");                                    
        frame.getContentPane().setLayout(new BorderLayout());                                          
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);           
        frame.setSize(500,500);        
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
	    private JPanel createNeuerVerlagPanel() {
	    	
	    	neuerVerlagPanel = new JPanel();
	    	neuerVerlagPanel.setLayout(new BorderLayout());
	    	neuerVerlagPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
	    	
	    	componentsNeuAktualisieren.put(PKL = new JLabel(), PKT = new JTextField());
			componentsNeuAktualisieren.put(nameL = new JLabel(), nameT = new JTextField());
			componentsNeuAktualisieren.put(gruendungsDatumL = new JLabel(), gruendungsDatumT = new JTextField());
			componentsNeuAktualisieren.put(endDatumL = new JLabel(), endDatumT = new JTextField());
			componentsNeuAktualisieren.put(geloeschtL = new JLabel(), geloeschtCbx = new JCheckBox());

			JPanel labelPanel = new JPanel();
			labelPanel.setLayout(new GridLayout(componentsNeuAktualisieren.size(), 0));
			for (JLabel e : componentsNeuAktualisieren.keySet()) {
				labelPanel.add(e);
			}

			labelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(componentsNeuAktualisieren.size(), 0));
			for (JComponent e : componentsNeuAktualisieren.values()) {
				inputPanel.add(e);
			}

			neuerVerlagPanel.add(labelPanel, BorderLayout.WEST);
			neuerVerlagPanel.add(inputPanel, BorderLayout.CENTER);

			return neuerVerlagPanel;
	    }
	    
	    private JPanel createSuchePanel() {
			JPanel suchPanel = new JPanel();
			suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
			suchPanel.setLayout(new BorderLayout());

			componentsSuche.put(nameSucheL = new JLabel(), nameSucheT = new JTextField());
			componentsSuche.put(gruendungsDatumSucheL = new JLabel(), gruendungsDatumSucheT = new JTextField());
			componentsSuche.put(endDatumSucheL = new JLabel(), endDatumSucheT = new JTextField());
			componentsSuche.put(geloeschtSucheL = new JLabel(), geloeschtSucheCbx = new JCheckBox());

			JPanel labelPanel = new JPanel();
			labelPanel.setLayout(new GridLayout(componentsSuche.size(), 0));
			for (JLabel e : componentsSuche.keySet()) {
				labelPanel.add(e);
			}

			labelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 0;
			inputPanel.add(componentsSuche.get(nameSucheL), c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.7;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			inputPanel.add(componentsSuche.get(gruendungsDatumSucheL), c);
			
	            
			c.weightx = 0.7;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			inputPanel.add(componentsSuche.get(endDatumSucheL), c);
			
			c.weightx = 0.7;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 3;
			inputPanel.add(componentsSuche.get(geloeschtSucheL), c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.LINE_END;
			c.insets = new Insets(0,5,0,0);
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 3;
			inputPanel.add(suchButton, c);

			suchPanel.add(labelPanel, BorderLayout.WEST);
			suchPanel.add(inputPanel, BorderLayout.CENTER);

			return suchPanel;
		}
	    
	    public void spaltenBreiteSetzen() {
	    	
	    	verlagTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Name
	    	verlagTabelle.getColumnModel().getColumn(1).setPreferredWidth(80); // Gruendungsdatum
	    	verlagTabelle.getColumnModel().getColumn(2).setPreferredWidth(80); // Enddatum
	    	verlagTabelle.getColumnModel().getColumn(3).setMaxWidth(30); // Loeschvormerkung
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
			return nameL;
		}

		public JLabel getGruendungsDatumL() {
			return gruendungsDatumL;
		}
		
		public JLabel getEndDatumL() {
			return endDatumL;
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
			return componentsNeuAktualisieren;
		}
		
		public JLabel getNameSucheL() {
			return nameSucheL;
		}

		public void setNameSucheL(JLabel nameSucheL) {
			this.nameSucheL = nameSucheL;
		}

		public JLabel getGruendungsDatumSucheL() {
			return gruendungsDatumSucheL;
		}

		public void setGruendungsDatumSucheL(JLabel gruendungsDatumSucheL) {
			this.gruendungsDatumSucheL = gruendungsDatumSucheL;
		}

		public JLabel getEndDatumSucheL() {
			return endDatumSucheL;
		}

		public void setEndDatumSucheL(JLabel endDatumSucheL) {
			this.endDatumSucheL = endDatumSucheL;
		}

		public JTextField getNameSucheT() {
			return nameSucheT;
		}

		public void setNameSucheT(JTextField nameSucheT) {
			this.nameSucheT = nameSucheT;
		}

		public JTextField getGruendungsDatumSucheT() {
			return gruendungsDatumSucheT;
		}

		public void setGruendungsDatumSucheT(JTextField gruendungsDatumSucheT) {
			this.gruendungsDatumSucheT = gruendungsDatumSucheT;
		}

		public JTextField getEndDatumSucheT() {
			return endDatumSucheT;
		}

		public void setEndDatumSucheT(JTextField endDatumSucheT) {
			this.endDatumSucheT = endDatumSucheT;
		}

		public JButton getSuchButton() {
			return suchButton;
		}

		public void setSuchButton(JButton suchButton) {
			this.suchButton = suchButton;
		}

		public JLabel getGeloescht() {
			return geloeschtL;
		}

		public void setGeloescht(JLabel geloescht) {
			this.geloeschtL = geloescht;
		}

		public JLabel getGeloeschtSucheL() {
			return geloeschtSucheL;
		}

		public void setGeloeschtSucheL(JLabel geloeschtSucheL) {
			this.geloeschtSucheL = geloeschtSucheL;
		}

		public JCheckBox getGeloeschtCbx() {
			return geloeschtCbx;
		}

		public void setGeloeschtCbx(JCheckBox geloeschtCbx) {
			this.geloeschtCbx = geloeschtCbx;
		}

		public JCheckBox getGeloeschtSucheCbx() {
			return geloeschtSucheCbx;
		}

		public void setGeloeschtSucheCbx(JCheckBox geloeschtSucheCbx) {
			this.geloeschtSucheCbx = geloeschtSucheCbx;
		}
		
		public void schliessen() {
			frame.dispose();
		}


}
