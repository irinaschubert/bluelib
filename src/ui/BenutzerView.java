package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
	private JLabel anredeL;
	private JLabel vornameL;
	private JLabel nachnameL;
	private JLabel strasseNrL;
	private JLabel plzL;
	private JLabel ortL;
	private JLabel geburtsdatumL;
	private JLabel telL;
	private JLabel mailL;
	private JLabel bemerkungL;
	private JLabel statusL;
	private JLabel mitarbeiterL;
	private JLabel erfasstVonL;
	private JLabel erfasstAmL;
	
	private JLabel PKSucheL;
	private JLabel vornameSucheL;
	private JLabel nachnameSucheL;
	private JLabel strasseNrSucheL;
	private JLabel plzSucheL;
	private JLabel ortSucheL;
	private JLabel geburtsdatumSucheL;
	private JLabel telSucheL;
	private JLabel mailSucheL;
	private JLabel bemerkungSucheL;
	private JLabel statusSucheL;
	private JLabel mitarbeiterSucheL;
	
	private JTextField PKT;
	private JComboBox anredeCbx;
	private JTextField vornameT;
	private JTextField nachnameT;
	private JTextField strasseNrT;
	private JTextField plzT;
	private JTextField ortT;
	private JTextField geburtsdatumT;
	private JTextField telT;
	private JTextField mailT;
	private JTextField bemerkungT;
	private JTextField erfasstVonT;
	private JTextField erfasstAmT;
	private JComboBox statusCbx;
	private JCheckBox mitarbeiterCbx;
	
	private JTextField PKSucheT;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;
	private JTextField strasseNrSucheT;
	private JTextField plzSucheT;
	private JTextField ortSucheT;
	private JComboBox statusSucheCbx;
	private JCheckBox mitarbeiterSucheCbx;
	
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
		benutzerTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(benutzerTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Benutzer:");

		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);

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
		componentsNeuAktualisieren.put(strasseNrL = new JLabel(), strasseNrT = new JTextField());
		componentsNeuAktualisieren.put(plzL = new JLabel(), plzT = new JTextField());
		componentsNeuAktualisieren.put(ortL = new JLabel(), ortT = new JTextField());
		componentsNeuAktualisieren.put(geburtsdatumL = new JLabel(), geburtsdatumT = new JTextField());
		componentsNeuAktualisieren.put(telL = new JLabel(), telT = new JTextField());
		componentsNeuAktualisieren.put(mailL = new JLabel(), mailT = new JTextField());
		componentsNeuAktualisieren.put(bemerkungL = new JLabel(), bemerkungT = new JTextField());
		componentsNeuAktualisieren.put(mitarbeiterL = new JLabel(), mitarbeiterCbx = new JCheckBox());
		componentsNeuAktualisieren.put(statusL = new JLabel(), statusCbx = new JComboBox());
		componentsNeuAktualisieren.put(anredeL = new JLabel(), anredeCbx = new JComboBox());
		componentsNeuAktualisieren.put(erfasstVonL = new JLabel(), erfasstVonT = new JTextField());
		componentsNeuAktualisieren.put(erfasstAmL = new JLabel(), erfasstAmT = new JTextField());

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

		componentsSuche.put(PKSucheL = new JLabel(), PKSucheT = new JTextField());
		componentsSuche.put(nachnameSucheL = new JLabel(), nachnameSucheT = new JTextField());
		componentsSuche.put(vornameSucheL = new JLabel(), vornameSucheT = new JTextField());
		componentsSuche.put(strasseNrSucheL = new JLabel(), strasseNrSucheT = new JTextField());
		componentsSuche.put(plzSucheL = new JLabel(), plzSucheT = new JTextField());
		componentsSuche.put(ortSucheL = new JLabel(), ortSucheT = new JTextField());
		componentsSuche.put(mitarbeiterSucheL = new JLabel(), mitarbeiterSucheCbx = new JCheckBox());
		componentsSuche.put(statusSucheL = new JLabel(), statusSucheCbx = new JComboBox());

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
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		inputPanel.add(componentsSuche.get(strasseNrSucheL), c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		inputPanel.add(componentsSuche.get(plzSucheL), c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		inputPanel.add(componentsSuche.get(ortSucheL), c);
		
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

		benutzerTabelle.getColumnModel().getColumn(0).setPreferredWidth(10); // ID
		benutzerTabelle.getColumnModel().getColumn(0).setPreferredWidth(60); // Name
		benutzerTabelle.getColumnModel().getColumn(1).setPreferredWidth(60); // Vorname
		benutzerTabelle.getColumnModel().getColumn(2).setPreferredWidth(40); // Strasse und Nr.
		benutzerTabelle.getColumnModel().getColumn(3).setPreferredWidth(40); // PLZ
		benutzerTabelle.getColumnModel().getColumn(3).setPreferredWidth(40); // Ort
		benutzerTabelle.getColumnModel().getColumn(4).setMaxWidth(20); // Status
	}

	public JLabel getPKL() {
		return PKL;
	}

	public void setPKL(JLabel pKL) {
		PKL = pKL;
	}

	public JLabel getAnredeL() {
		return anredeL;
	}

	public void setAnredeL(JLabel anredeL) {
		this.anredeL = anredeL;
	}

	public JLabel getVornameL() {
		return vornameL;
	}

	public void setVornameL(JLabel vornameL) {
		this.vornameL = vornameL;
	}

	public JLabel getNachnameL() {
		return nachnameL;
	}

	public void setNachnameL(JLabel nachnameL) {
		this.nachnameL = nachnameL;
	}

	public JLabel getStrasseNrL() {
		return strasseNrL;
	}

	public void setStrasseNrL(JLabel strasseNrL) {
		this.strasseNrL = strasseNrL;
	}

	public JLabel getPlzL() {
		return plzL;
	}

	public void setPlzL(JLabel plzL) {
		this.plzL = plzL;
	}

	public JLabel getOrtL() {
		return ortL;
	}

	public void setOrtL(JLabel ortL) {
		this.ortL = ortL;
	}

	public JLabel getGeburtsdatumL() {
		return geburtsdatumL;
	}

	public void setGeburtsdatumL(JLabel geburtsdatumL) {
		this.geburtsdatumL = geburtsdatumL;
	}

	public JLabel getTelL() {
		return telL;
	}

	public void setTelL(JLabel telL) {
		this.telL = telL;
	}

	public JLabel getMailL() {
		return mailL;
	}

	public void setMailL(JLabel mailL) {
		this.mailL = mailL;
	}

	public JLabel getBemerkungL() {
		return bemerkungL;
	}

	public void setBemerkungL(JLabel bemerkungL) {
		this.bemerkungL = bemerkungL;
	}

	public JLabel getStatusL() {
		return statusL;
	}

	public void setStatusL(JLabel statusL) {
		this.statusL = statusL;
	}

	public JLabel getMitarbeiterL() {
		return mitarbeiterL;
	}

	public void setMitarbeiterL(JLabel mitarbeiterL) {
		this.mitarbeiterL = mitarbeiterL;
	}

	public JLabel getErfasstVonL() {
		return erfasstVonL;
	}

	public void setErfasstVonL(JLabel erfasstVonL) {
		this.erfasstVonL = erfasstVonL;
	}

	public JLabel getErfasstAmL() {
		return erfasstAmL;
	}

	public void setErfasstAmL(JLabel erfasstAmL) {
		this.erfasstAmL = erfasstAmL;
	}

	public JLabel getPKSucheL() {
		return PKSucheL;
	}

	public void setPKSucheL(JLabel pKSucheL) {
		PKSucheL = pKSucheL;
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

	public JLabel getStrasseNrSucheL() {
		return strasseNrSucheL;
	}

	public void setStrasseNrSucheL(JLabel strasseNrSucheL) {
		this.strasseNrSucheL = strasseNrSucheL;
	}

	public JLabel getPlzSucheL() {
		return plzSucheL;
	}

	public void setPlzSucheL(JLabel plzSucheL) {
		this.plzSucheL = plzSucheL;
	}

	public JLabel getOrtSucheL() {
		return ortSucheL;
	}

	public void setOrtSucheL(JLabel ortSucheL) {
		this.ortSucheL = ortSucheL;
	}

	public JLabel getGeburtsdatumSucheL() {
		return geburtsdatumSucheL;
	}

	public void setGeburtsdatumSucheL(JLabel geburtsdatumSucheL) {
		this.geburtsdatumSucheL = geburtsdatumSucheL;
	}

	public JLabel getTelSucheL() {
		return telSucheL;
	}

	public void setTelSucheL(JLabel telSucheL) {
		this.telSucheL = telSucheL;
	}

	public JLabel getMailSucheL() {
		return mailSucheL;
	}

	public void setMailSucheL(JLabel mailSucheL) {
		this.mailSucheL = mailSucheL;
	}

	public JLabel getBemerkungSucheL() {
		return bemerkungSucheL;
	}

	public void setBemerkungSucheL(JLabel bemerkungSucheL) {
		this.bemerkungSucheL = bemerkungSucheL;
	}

	public JLabel getStatusSucheL() {
		return statusSucheL;
	}

	public void setStatusSucheL(JLabel statusSucheL) {
		this.statusSucheL = statusSucheL;
	}

	public JLabel getMitarbeiterSucheL() {
		return mitarbeiterSucheL;
	}

	public void setMitarbeiterSucheL(JLabel mitarbeiterSucheL) {
		this.mitarbeiterSucheL = mitarbeiterSucheL;
	}

	public JTextField getPKT() {
		return PKT;
	}

	public void setPKT(JTextField pKT) {
		PKT = pKT;
	}

	public JComboBox getAnredeCbx() {
		return anredeCbx;
	}

	public void setAnredeCbx(JComboBox anredeCbx) {
		this.anredeCbx = anredeCbx;
	}

	public JTextField getVornameT() {
		return vornameT;
	}

	public void setVornameT(JTextField vornameT) {
		this.vornameT = vornameT;
	}

	public JTextField getNachnameT() {
		return nachnameT;
	}

	public void setNachnameT(JTextField nachnameT) {
		this.nachnameT = nachnameT;
	}

	public JTextField getStrasseNrT() {
		return strasseNrT;
	}

	public void setStrasseNrT(JTextField strasseNrT) {
		this.strasseNrT = strasseNrT;
	}

	public JTextField getPlzT() {
		return plzT;
	}

	public void setPlzT(JTextField plzT) {
		this.plzT = plzT;
	}

	public JTextField getOrtT() {
		return ortT;
	}

	public void setOrtT(JTextField ortT) {
		this.ortT = ortT;
	}

	public JTextField getGeburtsdatumT() {
		return geburtsdatumT;
	}

	public void setGeburtsdatumT(JTextField geburtsdatumT) {
		this.geburtsdatumT = geburtsdatumT;
	}

	public JTextField getTelT() {
		return telT;
	}

	public void setTelT(JTextField telT) {
		this.telT = telT;
	}

	public JTextField getMailT() {
		return mailT;
	}

	public void setMailT(JTextField mailT) {
		this.mailT = mailT;
	}

	public JTextField getBemerkungT() {
		return bemerkungT;
	}

	public void setBemerkungT(JTextField bemerkungT) {
		this.bemerkungT = bemerkungT;
	}

	public JTextField getErfasstVonT() {
		return erfasstVonT;
	}

	public void setErfasstVonT(JTextField erfasstVonT) {
		this.erfasstVonT = erfasstVonT;
	}

	public JTextField getErfasstAmT() {
		return erfasstAmT;
	}

	public void setErfasstAmT(JTextField erfasstAmT) {
		this.erfasstAmT = erfasstAmT;
	}

	public JComboBox getStatusCbx() {
		return statusCbx;
	}

	public void setStatusCbx(JComboBox statusCbx) {
		this.statusCbx = statusCbx;
	}

	public JCheckBox getMitarbeiterCbx() {
		return mitarbeiterCbx;
	}

	public void setMitarbeiterCbx(JCheckBox mitarbeiterCbx) {
		this.mitarbeiterCbx = mitarbeiterCbx;
	}

	public JTextField getPKSucheT() {
		return PKSucheT;
	}

	public void setPKSucheT(JTextField pKSucheT) {
		PKSucheT = pKSucheT;
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

	public JTextField getStrasseNrSucheT() {
		return strasseNrSucheT;
	}

	public void setStrasseNrSucheT(JTextField strasseNrSucheT) {
		this.strasseNrSucheT = strasseNrSucheT;
	}

	public JTextField getPlzSucheT() {
		return plzSucheT;
	}

	public void setPlzSucheT(JTextField plzSucheT) {
		this.plzSucheT = plzSucheT;
	}

	public JTextField getOrtSucheT() {
		return ortSucheT;
	}

	public void setOrtSucheT(JTextField ortSucheT) {
		this.ortSucheT = ortSucheT;
	}

	public JComboBox getStatusSucheCbx() {
		return statusSucheCbx;
	}

	public void setStatusSucheCbx(JComboBox statusSucheCbx) {
		this.statusSucheCbx = statusSucheCbx;
	}

	public JCheckBox getMitarbeiterSucheCbx() {
		return mitarbeiterSucheCbx;
	}

	public void setMitarbeiterSucheCbx(JCheckBox mitarbeiterSucheCbx) {
		this.mitarbeiterSucheCbx = mitarbeiterSucheCbx;
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
	

	public JTable getBenutzerTabelle() {
		return benutzerTabelle;
	}

	public LinkedHashMap<JLabel, JComponent> getComponents() {
		return componentsNeuAktualisieren;
	}

	public void schliessen() {
		frame.dispose();
	}



	public JButton getSuchButton() {
		return suchButton;
	}

	public void setSuchButton(JButton suchButton) {
		this.suchButton = suchButton;
	}

}
