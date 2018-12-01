package ui.verlag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ui.StandardButtonPanel;
import ui.StandardTitelPanel;

/**
 * Zeigt alle Verlage an und ermoeglicht die Erfassung neuer Verlage
 * 
 * @version 1.0 26.10.2018
 * @author irina
 *
 */
public class VerlagView extends JPanel{
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
	private JLabel neuAendernL;
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
	private LinkedHashMap<JLabel, JComponent> componentsNeuBearbeiten = new LinkedHashMap<>();
	private static int HOEHE = 650;
	private static int BREITE = 500;
	
	public VerlagView(String panelTitel){
		neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		suchButton = new JButton();

		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		centerPanel.add(createNeuerVerlagPanel(), BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		// Titel des Panels
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		// Definiert die Grösse des Panels. Die HauptView passt sich an
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
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
	    	
	    	componentsNeuBearbeiten.put(PKL = new JLabel(), PKT = new JTextField());
			componentsNeuBearbeiten.put(nameL = new JLabel(), nameT = new JTextField());
			componentsNeuBearbeiten.put(gruendungsDatumL = new JLabel(), gruendungsDatumT = new JTextField());
			componentsNeuBearbeiten.put(endDatumL = new JLabel(), endDatumT = new JTextField());
			componentsNeuBearbeiten.put(geloeschtL = new JLabel(), geloeschtCbx = new JCheckBox());

			JPanel labelPanel = new JPanel();
			labelPanel.setLayout(new GridLayout(componentsNeuBearbeiten.size(), 0));
			for (JLabel e : componentsNeuBearbeiten.keySet()) {
				labelPanel.add(e);
			}

			labelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.LINE_END;
			c.weightx = 1;
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 0;
			inputPanel.add(neuAendernL, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
			inputPanel.add(componentsNeuBearbeiten.get(PKL), c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 1;
			inputPanel.add(componentsNeuBearbeiten.get(nameL), c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 2;
			inputPanel.add(componentsNeuBearbeiten.get(gruendungsDatumL), c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 3;
			inputPanel.add(componentsNeuBearbeiten.get(endDatumL), c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 4;
			inputPanel.add(componentsNeuBearbeiten.get(geloeschtL), c);
			
			

			neuerVerlagPanel.add(labelPanel, BorderLayout.WEST);
			neuerVerlagPanel.add(inputPanel, BorderLayout.CENTER);
			
			return rahmenSetzen("Neu / Bearbeiten", neuerVerlagPanel );
	    }
	    
	    private JPanel createTabellenPanel() {
			verlagTabelle = new JTable(); // Panel für die Tabelle
			verlagTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt werden
			JScrollPane scroll = new JScrollPane(verlagTabelle);

			JPanel tabellenPanel = new JPanel();
			tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
			JLabel tabellenTitel = new JLabel("Gefundene Verlage:");
			tabellenPanel.add(tabellenTitel);
			tabellenPanel.add(scroll);
			return tabellenPanel;
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

			return rahmenSetzen("Suche", suchPanel);
		}
	    
	    private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
			JPanel rahmenPanel = new JPanel();
			rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
			rahmenPanel.setBorder (BorderFactory.createTitledBorder (rahmentitel));
			rahmenPanel.add(inhalt);
		    return rahmenPanel;
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

		public JLabel getNameL() {
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
		
		public LinkedHashMap<JLabel, JComponent> getComponentsNeuBearbeiten() {
			return componentsNeuBearbeiten;
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
		
		public JLabel getNeuAendernL() {
			return neuAendernL;
		}

		public void setNeuAendernL(JLabel neuAendernL) {
			this.neuAendernL = neuAendernL;
		}


}
