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
 * Zeigt alle Benutzer an und ermoeglicht die Erfassung neuer Benutzer
 * 
 * @version 1.0 06.11.2018
 * @author irina
 *
 */
public class BenutzerView {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel neuerBenutzerPanel;
	private JPanel benutzerListe;
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
	private JTextField PKT;
	private JTextField vornameT;
	private JTextField nachnameT;
	private JTextField geburtsDatumT;
	private JTextField todesDatumT;
	private JCheckBox geloeschtCbx;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;
	private JTextField geburtsDatumSucheT;
	private JTextField todesDatumSucheT;
	private JCheckBox geloeschtSucheCbx;
	private JButton suchButton;
	private JTable benutzerTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuAktualisieren = new LinkedHashMap<>();

	public BenutzerView(String frameTitel) {

		buttonPanel = new StandardButtonPanel();
		benutzerListe = new JPanel();
		neuerBenutzerPanel = createNeuerBenutzerPanel();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		suchButton = new JButton();
		
		new JLabel(frameTitel);
		benutzerTabelle = new JTable();
		// Nur eine Zeile darf ausgewaehlt werden
		benutzerTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(benutzerTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Benutzer:");

		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);

//		 tabellenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(tabellenPanel, BorderLayout.CENTER);
		centerPanel.add(neuerBenutzerPanel, BorderLayout.SOUTH);

		frame = new JFrame("BlueLib");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);

		frame.getContentPane().add(new StandardTitelPanel(frameTitel), BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Befüllt LinkedHashMap mit Labels und Input-Feldern und gibt die Felder in ein
	 * JPanel aus Vorteil: Es können beliebig Felder hinzugefügt werden.
	 * 
	 * @return JPanel
	 */
	private JPanel createNeuerBenutzerPanel() {

		neuerBenutzerPanel = new JPanel();
		neuerBenutzerPanel.setLayout(new BorderLayout());
		neuerBenutzerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

		componentsNeuAktualisieren.put(PKL = new JLabel(), PKT = new JTextField());
		componentsNeuAktualisieren.put(nachnameL = new JLabel(), nachnameT = new JTextField());
		componentsNeuAktualisieren.put(vornameL = new JLabel(), vornameT = new JTextField());
		componentsNeuAktualisieren.put(geburtsDatumL = new JLabel(), geburtsDatumT = new JTextField());
		componentsNeuAktualisieren.put(todesDatumL = new JLabel(), todesDatumT = new JTextField());
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

		neuerBenutzerPanel.add(labelPanel, BorderLayout.WEST);
		neuerBenutzerPanel.add(inputPanel, BorderLayout.CENTER);

		return neuerBenutzerPanel;

	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		suchPanel.setLayout(new BorderLayout());

		componentsSuche.put(nachnameSucheL = new JLabel(), nachnameSucheT = new JTextField());
		componentsSuche.put(vornameSucheL = new JLabel(), vornameSucheT = new JTextField());
		componentsSuche.put(geburtsDatumSucheL = new JLabel(), geburtsDatumSucheT = new JTextField());
		componentsSuche.put(todesDatumSucheL = new JLabel(), todesDatumSucheT = new JTextField());
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
		inputPanel.add(componentsSuche.get(todesDatumSucheL), c);
		
		c.weightx = 0.7;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		inputPanel.add(componentsSuche.get(geloeschtSucheL), c);
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0,5,0,0);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 4;
		inputPanel.add(suchButton, c);

		suchPanel.add(labelPanel, BorderLayout.WEST);
		suchPanel.add(inputPanel, BorderLayout.CENTER);

		return suchPanel;
	}

	public void spaltenBreiteSetzen() {

		benutzerTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Name
		benutzerTabelle.getColumnModel().getColumn(1).setPreferredWidth(80); // Vorname
		benutzerTabelle.getColumnModel().getColumn(2).setPreferredWidth(40); // Geb-Datum
		benutzerTabelle.getColumnModel().getColumn(3).setPreferredWidth(40); // Todesdatum
		benutzerTabelle.getColumnModel().getColumn(4).setMaxWidth(30); // LV
	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getNeuerBenutzerPanel() {
		return neuerBenutzerPanel;
	}

	public JPanel getBenutzerListe() {
		return benutzerListe;
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

	public JTable getBenutzerTabelle() {
		return benutzerTabelle;
	}

	public LinkedHashMap<JLabel, JComponent> getComponents() {
		return componentsNeuAktualisieren;
	}

	public void schliessen() {
		frame.dispose();
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

	public JTextField getTodesDatumSucheT() {
		return todesDatumSucheT;
	}

	public void setTodesDatumSucheT(JTextField todesDatumSucheT) {
		this.todesDatumSucheT = todesDatumSucheT;
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
	

}
