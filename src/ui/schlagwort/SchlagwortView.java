package ui.schlagwort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * Zeigt alle Schlagworte an und ermoeglicht die Erfassung neuer Schlagworte
 * 
 * @version 1.0 24.10.2018
 * @author Schmutz
 *
 */
public class SchlagwortView extends JPanel {
	private JPanel schlagwortNeuBearbeitenPanel;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;
	private JLabel PKL;
	private JLabel schlagwortL;
	private JLabel geloeschtL;
	private JLabel schlagwortSucheL;
	private JLabel geloeschtSucheL;
	private JLabel neuAendernL;
	private JTextField PKT;
	private JTextField schlagwortT;
	private JTextField schlagwortSucheT;
	private JCheckBox geloeschtCbx;
	private JCheckBox geloeschtSucheCbx;
	private JButton suchButton;
	private JTable schlagwortTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuBearbeiten = new LinkedHashMap<>();
	private static int HOEHE = 650;
	private static int BREITE = 500;

	public SchlagwortView(String panelTitel) {

		neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		suchButton = new JButton();

		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		centerPanel.add(createNeuesSchlagwortPanel(), BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		// Titel des Panels
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		// Definiert die Grösse des Panels. Die HauptView passt sich an
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	/**
	 * Befüllt LinkedHashMap mit Labels und Input-Feldern und gibt die Felder in ein
	 * JPanel aus Vorteil: Es können beliebig Felder hinzugefügt werden.
	 * 
	 * @return JPanel
	 */
	private JPanel createNeuesSchlagwortPanel() {

		schlagwortNeuBearbeitenPanel = new JPanel();
		schlagwortNeuBearbeitenPanel.setLayout(new BorderLayout());
		schlagwortNeuBearbeitenPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

		componentsNeuBearbeiten.put(PKL = new JLabel(), PKT = new JTextField());
		PKT.setFont(PKT.getFont().deriveFont(12f));
		neuAendernL.setFont(PKT.getFont().deriveFont(Font.BOLD, 14f));
		neuAendernL.setForeground(Color.red);
		componentsNeuBearbeiten.put(schlagwortL = new JLabel(), schlagwortT = new JTextField());
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
		c.weightx = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		inputPanel.add(componentsNeuBearbeiten.get(PKL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 1;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 0;
		inputPanel.add(neuAendernL, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		inputPanel.add(componentsNeuBearbeiten.get(schlagwortL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 5;
		inputPanel.add(componentsNeuBearbeiten.get(geloeschtL), c);

		schlagwortNeuBearbeitenPanel.add(labelPanel, BorderLayout.WEST);
		schlagwortNeuBearbeitenPanel.add(inputPanel, BorderLayout.CENTER);

		return rahmenSetzen("Neu / Bearbeiten", schlagwortNeuBearbeitenPanel);

	}

	private JPanel createTabellenPanel() {
		schlagwortTabelle = new JTable(); // Panel für die Tabelle
		schlagwortTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt
																					// werden
		JScrollPane scroll = new JScrollPane(schlagwortTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Schlagworte:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		suchPanel.setLayout(new BorderLayout());

		componentsSuche.put(schlagwortSucheL = new JLabel(), schlagwortSucheT = new JTextField());
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
		inputPanel.add(componentsSuche.get(schlagwortSucheL), c);

		c.weightx = 0.7;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		inputPanel.add(componentsSuche.get(geloeschtSucheL), c);

		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 5, 0, 0);
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
		rahmenPanel.setBorder(BorderFactory.createTitledBorder(rahmentitel));
		rahmenPanel.add(inhalt);
		return rahmenPanel;
	}

	public void spaltenBreiteSetzen() {
		schlagwortTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Schlagwort
		schlagwortTabelle.getColumnModel().getColumn(1).setMaxWidth(50); // LV
	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getNeuerSchlagwortPanel() {
		return schlagwortNeuBearbeitenPanel;
	}

	public JLabel getSchlagwortL() {
		return schlagwortL;
	}

	public JTextField getSchlagwortT() {
		return schlagwortT;
	}

	public JTextField getPKT() {
		return PKT;
	}

	public JLabel getPKL() {
		return PKL;
	}

	public JTable getSchlagwortTabelle() {
		return schlagwortTabelle;
	}

	public LinkedHashMap<JLabel, JComponent> getComponentsNeuBearbeiten() {
		return componentsNeuBearbeiten;
	}

	public JLabel getSchlagwortSucheL() {
		return schlagwortSucheL;
	}

	public void setSchlagwortSucheL(JLabel schlagwortSucheL) {
		this.schlagwortSucheL = schlagwortSucheL;
	}

	public JTextField getSchlagwortSucheT() {
		return schlagwortSucheT;
	}

	public void setSchlagwortucheT(JTextField schlagwortSucheT) {
		this.schlagwortSucheT = schlagwortSucheT;
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
