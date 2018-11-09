package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Anrede;
import domain.Status;

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
	JPanel benutzerNeuBearbeitenPanel;
	JPanel benutzerSuchenPanel;
	private JPanel benutzerListe;
	private JPanel centerPanel;
	
	private JLabel neuAendernL;
	private JLabel PKL;
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
	private JLabel anredeL;
	private JLabel mitarbeiterL;
	private JLabel erfasstVonL;
	private JLabel erfasstAmL;
	
	private JLabel PKSucheL;
	private JLabel vornameSucheL;
	private JLabel nachnameSucheL;
	private JLabel strasseNrSucheL;
	private JLabel plzSucheL;
	private JLabel ortSucheL;
	private JLabel statusSucheL;
	
	private JTextField PKT;
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
	private JComboBox anredeCbx;
	private JComboBox statusCbx;
	private JCheckBox mitarbeiterCbx;
	
	private JTextField PKSucheT;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;
	private JTextField strasseNrSucheT;
	private JTextField plzSucheT;
	private JTextField ortSucheT;
	private JComboBox statusSucheCbx;
	
	private JButton suchButton;
	private JTable benutzerTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private LinkedHashMap<JLabel, JComponent> componentsNeuAktualisieren = new LinkedHashMap<>();

	public BenutzerView(String frameTitel) {

		neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel = new StandardButtonPanel();
		benutzerListe = new JPanel();
		benutzerNeuBearbeitenPanel = createNeuerBenutzerPanel();
		
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
		centerPanel.add(benutzerNeuBearbeitenPanel, BorderLayout.SOUTH);

		frame = new JFrame("BlueLib");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 800);
		frame.setVisible(true);

		frame.getContentPane().add(new StandardTitelPanel(frameTitel), BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Label und Inputfelder
	 * 
	 * @return JPanel
	 */
	private JPanel createNeuerBenutzerPanel() {
		benutzerNeuBearbeitenPanel = new JPanel();
		benutzerNeuBearbeitenPanel.setLayout(new GridBagLayout());
		benutzerNeuBearbeitenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

		PKL = new JLabel();
		PKT = new JTextField();
		nachnameL = new JLabel();
		nachnameT = new JTextField();
		vornameL = new JLabel();
		vornameT = new JTextField();
		strasseNrL = new JLabel();
		strasseNrT = new JTextField();
		plzL = new JLabel();
		plzT = new JTextField();
		ortL = new JLabel();
		ortT = new JTextField();
		geburtsdatumL = new JLabel();
		geburtsdatumT = new JTextField();
		telL = new JLabel();
		telT = new JTextField();
		mailL = new JLabel();
		mailT = new JTextField();
		bemerkungL = new JLabel();
		bemerkungT = new JTextField();
		mitarbeiterL = new JLabel();
		mitarbeiterCbx = new JCheckBox();
		erfasstVonL = new JLabel();
		erfasstVonT = new JTextField();
		erfasstAmL = new JLabel();
		erfasstAmT = new JTextField();
		statusL = new JLabel();
		statusCbx = new JComboBox(Status.getStatus());
		statusCbx.setSelectedIndex(0);
		anredeL = new JLabel();
		anredeCbx = new JComboBox(Anrede.getAnreden());
		anredeCbx.setSelectedIndex(0);
		
        // Formularfelder
        formularHelfer.addLabel(PKL, benutzerNeuBearbeitenPanel);
        Dimension PKFeldgroesse = PKT.getPreferredSize();
        PKFeldgroesse.width = 200;
        PKT.setPreferredSize(PKFeldgroesse);
        JPanel pk = new JPanel();
        pk.setLayout(new BorderLayout());
        pk.add(PKT, BorderLayout.WEST);
        formularHelfer.addLastField(pk, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(nachnameL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(nachnameT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(vornameL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(vornameT, benutzerNeuBearbeitenPanel);

        formularHelfer.addLabel(strasseNrL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(strasseNrT, benutzerNeuBearbeitenPanel);

        formularHelfer.addLabel(ortL, benutzerNeuBearbeitenPanel);
        formularHelfer.addMiddleField(ortT, benutzerNeuBearbeitenPanel);
        
        JPanel plzPanel = new JPanel();
        plzPanel.setLayout(new GridBagLayout());
        formularHelfer.addLabel(plzL, plzPanel);
        Dimension plzFeldgroesse = plzT.getPreferredSize();
        plzFeldgroesse.width = 80;
        plzT.setPreferredSize(plzFeldgroesse);
        formularHelfer.addLabel(plzT, plzPanel);
        formularHelfer.addLabel(plzPanel, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(new JPanel(), benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(geburtsdatumL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(geburtsdatumT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(telL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(telT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(mailL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(mailT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(bemerkungL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(bemerkungT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(mitarbeiterL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(mitarbeiterCbx, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(statusL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(statusCbx, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(anredeL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(anredeCbx, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(erfasstVonL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(erfasstVonT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(erfasstAmL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(erfasstAmT, benutzerNeuBearbeitenPanel);

        return rahmenSetzen("Neu / Bearbeiten", benutzerNeuBearbeitenPanel );
	}

	private JPanel createSuchePanel() {
		benutzerSuchenPanel = new JPanel();
		benutzerSuchenPanel.setLayout(new GridBagLayout());
		benutzerSuchenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();
		
		statusSucheCbx = new JComboBox(Status.getStatus());
		statusSucheCbx.setSelectedIndex(0);
		
		PKSucheL = new JLabel();
		PKSucheT = new JTextField();
		nachnameSucheL = new JLabel();
		nachnameSucheT = new JTextField();
		vornameSucheL = new JLabel();
		vornameSucheT = new JTextField();
		strasseNrSucheL = new JLabel();
		strasseNrSucheT = new JTextField();
		plzSucheL = new JLabel();
		plzSucheT = new JTextField();
		ortSucheL = new JLabel();
		ortSucheT = new JTextField();
		statusSucheL = new JLabel();
		statusSucheCbx = new JComboBox(Status.getStatus());
		statusSucheCbx.setSelectedIndex(0);
		
		// Formularfelder
        formularHelfer.addLabel(PKSucheL, benutzerSuchenPanel);
        Dimension PKSucheFeldgroesse = PKSucheT.getPreferredSize();
        PKSucheFeldgroesse.width = 200;
        PKSucheT.setPreferredSize(PKSucheFeldgroesse);
        JPanel pkSuchePanel = new JPanel();
        pkSuchePanel.setLayout(new BorderLayout());
        pkSuchePanel.add(PKSucheT, BorderLayout.WEST);
        formularHelfer.addLastField(pkSuchePanel, benutzerSuchenPanel);
        
        formularHelfer.addLabel(nachnameSucheL, benutzerSuchenPanel);
        formularHelfer.addLastField(nachnameSucheT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(vornameSucheL, benutzerSuchenPanel);
        formularHelfer.addLastField(vornameSucheT, benutzerSuchenPanel);

        formularHelfer.addLabel(strasseNrSucheL, benutzerSuchenPanel);
        formularHelfer.addLastField(strasseNrSucheT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(ortSucheL, benutzerSuchenPanel);
        formularHelfer.addMiddleField(ortSucheT, benutzerSuchenPanel);
        
        JPanel plzSuchePanel = new JPanel();
        plzSuchePanel.setLayout(new GridBagLayout());
        formularHelfer.addLabel(plzSucheL, plzSuchePanel);
        Dimension plzSucheFeldgroesse = plzSucheT.getPreferredSize();
        plzSucheFeldgroesse.width = 80;
        plzSucheT.setPreferredSize(plzSucheFeldgroesse);
        formularHelfer.addLabel(plzSucheT, plzSuchePanel);
        formularHelfer.addLabel(plzSuchePanel, benutzerSuchenPanel);
        formularHelfer.addLastField(new JPanel(), benutzerSuchenPanel);
        
        formularHelfer.addLabel(statusSucheL, benutzerSuchenPanel);
        formularHelfer.addLastField(statusSucheCbx, benutzerSuchenPanel);
        
        
        JPanel suchButtonPanel = new JPanel();
        suchButtonPanel.setLayout(new GridBagLayout());
        formularHelfer.addLabel("", benutzerSuchenPanel);
        Dimension suchButtonGroesse = suchButton.getPreferredSize();
        suchButtonGroesse.width = 100;
        suchButtonGroesse.height = 25;
        suchButton.setPreferredSize(suchButtonGroesse);
        
        formularHelfer.addLabel(suchButton, suchButtonPanel);
        formularHelfer.addLastField(suchButtonPanel, benutzerSuchenPanel);
        
        return rahmenSetzen("Suche", benutzerSuchenPanel );
	}
	
	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder (BorderFactory.createTitledBorder (rahmentitel));
		rahmenPanel.add(inhalt);
	    return rahmenPanel;
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
	
	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
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

	public JLabel getStatusSucheL() {
		return statusSucheL;
	}

	public void setStatusSucheL(JLabel statusSucheL) {
		this.statusSucheL = statusSucheL;
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

	public JPanel getBenutzerNeuBearbeitenPanel() {
		return benutzerNeuBearbeitenPanel;
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
	
	public JLabel getNeuAendernL() {
		return neuAendernL;
	}

	public void setNeuAendernL(JLabel neuAendernL) {
		this.neuAendernL = neuAendernL;
	}

}
