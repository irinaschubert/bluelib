package ui;

import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 * Zeigt alle Mitarbeiter an und ermoeglicht die Erfassung neuer Mitarbeiter
 * 
 * @version 0.1 24.11.2018
 * @author Mike
 *
 */
public class MitarbeiterView extends JPanel {
	private JPanel MitarbeiterNeuBearbeitenPanel;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;
	private JLabel PKL;
	private JLabel NameL;
	private JLabel VornameL;
	private JLabel BenutzernameL;
	private JLabel PasswortL;
	private JLabel geloeschtL;
	private JLabel NameSucheL;
	private JLabel VornameSucheL;
	private JLabel BenutzernameSucheL;
	private JLabel geloeschtSucheL;
	private JLabel neuAendernL;
	private JTextField PKT;
	private JTextField NameT;
	private JTextField NameSucheT;
	private JTextField VornameT;
	private JTextField VornameSucheT;
	private JTextField BenutzernameT;
	private JTextField BenutzernameSucheT;
	private JCheckBox geloeschtCbx;
	private JCheckBox geloeschtSucheCbx;
	private JButton suchButton;
	private JTable MitarbeiterTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuBearbeiten = new LinkedHashMap<>();
	private static int HOEHE = 650;
	private static int BREITE = 500;

	public MitarbeiterView(String panelTitel) {

		neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		suchButton = new JButton();

		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		centerPanel.add(createNeuesMitarbeiterPanel(), BorderLayout.SOUTH);

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
	private JPanel createNeuesMitarbeiterPanel() {

		MitarbeiterNeuBearbeitenPanel = new JPanel();
		MitarbeiterNeuBearbeitenPanel.setLayout(new BorderLayout());
		MitarbeiterNeuBearbeitenPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

		componentsNeuBearbeiten.put(PKL = new JLabel(), PKT = new JTextField());
		componentsNeuBearbeiten.put(BenutzernameL = new JLabel(), BenutzernameT = new JTextField());
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
		inputPanel.add(componentsNeuBearbeiten.get(BenutzernameL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 5;
		inputPanel.add(componentsNeuBearbeiten.get(geloeschtL), c);

		MitarbeiterNeuBearbeitenPanel.add(labelPanel, BorderLayout.WEST);
		MitarbeiterNeuBearbeitenPanel.add(inputPanel, BorderLayout.CENTER);

		return rahmenSetzen("Neu / Bearbeiten", MitarbeiterNeuBearbeitenPanel);

	}

	private JPanel createTabellenPanel() {
		MitarbeiterTabelle = new JTable(); // Panel für die Tabelle
		MitarbeiterTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt
																					// werden
		JScrollPane scroll = new JScrollPane(MitarbeiterTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Mitarbeitere:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		suchPanel.setLayout(new BorderLayout());

		componentsSuche.put(NameSucheL = new JLabel(), NameSucheT = new JTextField());
		componentsSuche.put(VornameSucheL = new JLabel(), VornameSucheT = new JTextField());
		componentsSuche.put(BenutzernameSucheL = new JLabel(), BenutzernameSucheT = new JTextField());
		componentsSuche.put(geloeschtSucheL = new JLabel(), geloeschtSucheCbx = new JCheckBox());

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(componentsSuche.size(), 0));
		for (JLabel e : componentsSuche.keySet()) {
			labelPanel.add(e);
		}

		labelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		
		JPanel suchButtonPanel = new JPanel();
		suchButtonPanel.setLayout(new GridLayout(0, 5));

		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		inputPanel.add(componentsSuche.get(NameSucheL), c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		inputPanel.add(componentsSuche.get(VornameSucheL), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		inputPanel.add(componentsSuche.get(BenutzernameSucheL), c);

		c.weightx = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		inputPanel.add(componentsSuche.get(geloeschtSucheL), c);

/*
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 0,0, 0);
		c.weightx = 1;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 0;
		*/
		suchButtonPanel.add(suchButton);


		suchPanel.add(labelPanel, BorderLayout.WEST);
		suchPanel.add(inputPanel, BorderLayout.CENTER);
		suchPanel.add(suchButtonPanel, BorderLayout.SOUTH);

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
		MitarbeiterTabelle.getColumnModel().getColumn(0).setPreferredWidth(40); // Name (Person)
		MitarbeiterTabelle.getColumnModel().getColumn(1).setPreferredWidth(40); // Vorname (Person)
		MitarbeiterTabelle.getColumnModel().getColumn(2).setPreferredWidth(40); // Mitarbeiter
		MitarbeiterTabelle.getColumnModel().getColumn(3).setMaxWidth(50); // Aktiv
	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getNeuerMitarbeiterPanel() {
		return MitarbeiterNeuBearbeitenPanel;
	}
	
	
	public JLabel getNameSucheL() {
		return NameSucheL;
	}
	public JTextField getNameSucheT() {
		return NameSucheT;
	}
	public JLabel getVornameSucheL() {
		return VornameSucheL;
	}
	public JTextField getVornameSucheT() {
		return VornameSucheT;
	}

	public JLabel getBenutzernameL() {
		return BenutzernameL;
	}

	public JTextField getBenutzernameT() {
		return BenutzernameT;
	}

	public JTextField getPKT() {
		return PKT;
	}

	public JLabel getPKL() {
		return PKL;
	}

	public JTable getMitarbeiterTabelle() {
		return MitarbeiterTabelle;
	}

	public LinkedHashMap<JLabel, JComponent> getComponentsNeuBearbeiten() {
		return componentsNeuBearbeiten;
	}

	public JLabel getBenutzernameSucheL() {
		return BenutzernameSucheL;
	}

	public void setBenutzernameSucheL(JLabel BenutzernameSucheL) {
		this.BenutzernameSucheL = BenutzernameSucheL;
	}

	public JTextField getMitarbeiterSucheT() {
		return BenutzernameSucheT;
	}

	public void setMitarbeiterucheT(JTextField BenutzernameSucheT) {
		this.BenutzernameSucheT = BenutzernameSucheT;
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
