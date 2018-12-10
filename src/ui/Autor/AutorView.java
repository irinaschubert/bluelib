package ui.Autor;

import java.awt.BorderLayout;
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

import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * Zeigt alle Autoren an und ermoeglicht die Erfassung neuer Autoren
 * 
 * @version 1.0 24.10.2018
 * @author Ueli
 *
 */
public class AutorView extends JPanel {
	private JPanel autorNeuBearbeitenPanel;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;
	private JLabel PKL;
	private JLabel vornameL;
	private JLabel nachnameL;
	private JLabel geburtsDatumL;
	private JLabel todesDatumL;
	private JLabel geloeschtL;
	private JLabel vornameSucheL;
	private JLabel nachnameSucheL;
	private JLabel geburtsDatumSucheL;
	private JLabel todesDatumSucheL;
	private JLabel geloeschtSucheL;
	private JLabel neuAendernL;
	private JTextField PKT;
	private JTextField vornameT;
	private JTextField nachnameT;
	private JTextField geburtsDatumT;
	private JTextField todesDatumT;
	private JCheckBox geloeschtCbx;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;
	private JTextField geburtsDatumSucheT;
	private JCheckBox geloeschtSucheCbx;
	private JButton suchButton;
	private JTable autorenTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuBearbeiten = new LinkedHashMap<>();
	private static int HOEHE = 650;
	private static int BREITE = 500;

	public AutorView(String panelTitel) {

		neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		suchButton = new JButton();

		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		centerPanel.add(createNeuerAutorPanel(), BorderLayout.SOUTH);

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
	private JPanel createNeuerAutorPanel() {

		autorNeuBearbeitenPanel = new JPanel();
		autorNeuBearbeitenPanel.setLayout(new BorderLayout());
		autorNeuBearbeitenPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

		componentsNeuBearbeiten.put(PKL = new JLabel(), PKT = new JTextField());
		componentsNeuBearbeiten.put(nachnameL = new JLabel(), nachnameT = new JTextField());
		componentsNeuBearbeiten.put(vornameL = new JLabel(), vornameT = new JTextField());
		componentsNeuBearbeiten.put(geburtsDatumL = new JLabel(), geburtsDatumT = new JTextField());
		componentsNeuBearbeiten.put(todesDatumL = new JLabel(), todesDatumT = new JTextField());
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
		inputPanel.add(componentsNeuBearbeiten.get(nachnameL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		inputPanel.add(componentsNeuBearbeiten.get(vornameL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		inputPanel.add(componentsNeuBearbeiten.get(geburtsDatumL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 4;
		inputPanel.add(componentsNeuBearbeiten.get(todesDatumL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 5;
		inputPanel.add(componentsNeuBearbeiten.get(geloeschtL), c);

		autorNeuBearbeitenPanel.add(labelPanel, BorderLayout.WEST);
		autorNeuBearbeitenPanel.add(inputPanel, BorderLayout.CENTER);

		return rahmenSetzen("Neu / Bearbeiten", autorNeuBearbeitenPanel);

	}

	private JPanel createTabellenPanel() {
		autorenTabelle = new JTable(); // Panel für die Tabelle
		autorenTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt werden
		JScrollPane scroll = new JScrollPane(autorenTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Autoren:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		suchPanel.setLayout(new BorderLayout());

		componentsSuche.put(nachnameSucheL = new JLabel(), nachnameSucheT = new JTextField());
		componentsSuche.put(vornameSucheL = new JLabel(), vornameSucheT = new JTextField());
		componentsSuche.put(geburtsDatumSucheL = new JLabel(), geburtsDatumSucheT = new JTextField());
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
		inputPanel.add(componentsSuche.get(nachnameSucheL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		inputPanel.add(componentsSuche.get(vornameSucheL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.7;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		inputPanel.add(componentsSuche.get(geburtsDatumSucheL), c);

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

		autorenTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Name
		autorenTabelle.getColumnModel().getColumn(1).setPreferredWidth(80); // Vorname
		autorenTabelle.getColumnModel().getColumn(2).setPreferredWidth(40); // Geb-Datum
		autorenTabelle.getColumnModel().getColumn(3).setPreferredWidth(40); // Todesdatum
		autorenTabelle.getColumnModel().getColumn(4).setMaxWidth(30); // LV
	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getNeuerAutorPanel() {
		return autorNeuBearbeitenPanel;
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

	public LinkedHashMap<JLabel, JComponent> getComponentsNeuBearbeiten() {
		return componentsNeuBearbeiten;
	}

	public LinkedHashMap<JLabel, JComponent> getComponentsSuche() {
		return componentsSuche;
	}

	
	public JLabel getVornameSucheL() {
		return vornameSucheL;
	}

	public void setVornameSucheL(JLabel vornameSucheL) {
		this.vornameSucheL = vornameSucheL;
	}

	public JLabel getNachnameSucheL() {
		return nachnameSucheL;
	}

	public void setNachnameSucheL(JLabel nachnameSucheL) {
		this.nachnameSucheL = nachnameSucheL;
	}

	public JLabel getGeburtsDatumSucheL() {
		return geburtsDatumSucheL;
	}

	public void setGeburtsDatumSucheL(JLabel geburtsDatumSucheL) {
		this.geburtsDatumSucheL = geburtsDatumSucheL;
	}

	public JLabel getTodesDatumSucheL() {
		return todesDatumSucheL;
	}

	public void setTodesDatumSucheL(JLabel todesDatumSucheL) {
		this.todesDatumSucheL = todesDatumSucheL;
	}

	public JTextField getVornameSucheT() {
		return vornameSucheT;
	}

	public void setVornameSucheT(JTextField vornameSucheT) {
		this.vornameSucheT = vornameSucheT;
	}

	public JTextField getNachnameSucheT() {
		return nachnameSucheT;
	}

	public void setNachnameSucheT(JTextField nachnameSucheT) {
		this.nachnameSucheT = nachnameSucheT;
	}

	public JTextField getGeburtsDatumSucheT() {
		return geburtsDatumSucheT;
	}

	public void setGeburtsDatumSucheT(JTextField geburtsDatumSucheT) {
		this.geburtsDatumSucheT = geburtsDatumSucheT;
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
