package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	private JLabel vornameSucheL;
	private JLabel nachnameSucheL;
	private JLabel geburtsDatumSucheL;
	private JLabel todesDatumSucheL;
	private JTextField PKT;
	private JTextField vornameT;
	private JTextField nachnameT;
	private JTextField geburtsDatumT;
	private JTextField todesDatumT;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;
	private JTextField geburtsDatumSucheT;
	private JTextField todesDatumSucheT;
	private JButton suchButton;
	private JTable autorenTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuAktualisieren = new LinkedHashMap<>();

	public AutorView(String frameTitel) {

		buttonPanel = new StandardButtonPanel();
		autorenListe = new JPanel();
		neuerAutorPanel = createNeuerAutorPanel();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		suchButton = new JButton();
		
		new JLabel(frameTitel);
		autorenTabelle = new JTable();
		// Nur eine Zeile darf ausgewaehl werden
		autorenTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(autorenTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Autoren:");

		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);

//		 tabellenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(tabellenPanel, BorderLayout.CENTER);
		centerPanel.add(neuerAutorPanel, BorderLayout.SOUTH);

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
	private JPanel createNeuerAutorPanel() {

		neuerAutorPanel = new JPanel();
		neuerAutorPanel.setLayout(new BorderLayout());
		neuerAutorPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

		componentsNeuAktualisieren.put(PKL = new JLabel(), PKT = new JTextField());
		componentsNeuAktualisieren.put(nachnameL = new JLabel(), nachnameT = new JTextField());
		componentsNeuAktualisieren.put(vornameL = new JLabel(), vornameT = new JTextField());
		componentsNeuAktualisieren.put(geburtsDatumL = new JLabel(), geburtsDatumT = new JTextField());
		componentsNeuAktualisieren.put(todesDatumL = new JLabel(), todesDatumT = new JTextField());

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

		neuerAutorPanel.add(labelPanel, BorderLayout.WEST);
		neuerAutorPanel.add(inputPanel, BorderLayout.CENTER);

		return neuerAutorPanel;

	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		suchPanel.setLayout(new BorderLayout());

		componentsSuche.put(nachnameSucheL = new JLabel(), nachnameSucheT = new JTextField());
		componentsSuche.put(vornameSucheL = new JLabel(), vornameSucheT = new JTextField());
		componentsSuche.put(geburtsDatumSucheL = new JLabel(), geburtsDatumSucheT = new JTextField());
		componentsSuche.put(todesDatumSucheL = new JLabel(), todesDatumSucheT = new JTextField());

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

		autorenTabelle.getColumnModel().getColumn(0).setPreferredWidth(80); // Name
		autorenTabelle.getColumnModel().getColumn(1).setPreferredWidth(80); // Vorname
		autorenTabelle.getColumnModel().getColumn(2).setPreferredWidth(10); // Geb-Datum
		autorenTabelle.getColumnModel().getColumn(3).setPreferredWidth(10); // Todesdatum
	}

	public StandardButtonPanel getButton() {
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
	

}
