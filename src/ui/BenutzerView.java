package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Anrede;
import domain.Ort;
import domain.Status;

/**
 * Suche nach, neu Erstellen und Bearbeiten von Benutzern
 * 
 * @version 1.0 06.11.2018
 * @author irina
 *
 */
public class BenutzerView extends JPanel {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel benutzerNeuBearbeitenPanel;
	private JPanel benutzerSuchenPanel;
	private JPanel benutzerListe;
	private JPanel centerPanel;
	
	private JLabel neuAendernL;
	private JLabel PKL;
	private JLabel vornameL;
	private JLabel nachnameL;
	private JLabel strasseNrL;
	private JLabel plzOrtL;
	private JLabel geburtsdatumL;
	private JLabel telL;
	private JLabel mailL;
	private JLabel bemerkungL;
	private JLabel statusL;
	private JLabel anredeL;
	private JLabel erfasstVonL;
	private JLabel erfasstAmL;
	
	private JLabel PKSucheL;
	private JLabel vornameSucheL;
	private JLabel nachnameSucheL;
	private JLabel strasseNrSucheL;
	private JLabel plzOrtSucheL;
	private JLabel statusSucheL;
	
	private JTextField PKT;
	private JTextField vornameT;
	private JTextField nachnameT;
	private JTextField strasseNrT;
	private JTextField geburtsdatumT;
	private JTextField telT;
	private JTextField mailT;
	private JTextArea bemerkungT;
	private JTextField erfasstVonT;
	private JTextField erfasstAmT;
	private JComboBox<Ort> plzOrtCbx;
	private JComboBox<Anrede> anredeCbx;
	private JComboBox<Status> statusCbx;
	
	private JTextField PKSucheT;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;
	private JTextField strasseNrSucheT;
	private JComboBox<Ort> plzOrtSucheCbx;
	private JComboBox<Status> statusSucheCbx;
	
	private JButton suchButton;
	private JTable benutzerTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsNeuAktualisieren = new LinkedHashMap<>();
	private static int HOEHE = 950;
	private static int BREITE = 750;

	public BenutzerView(String panelTitel) {
		
		new JLabel(panelTitel);
		benutzerListe = new JPanel();
		buttonPanel = new StandardButtonPanel();
		suchButton = new JButton();

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		centerPanel.add(createNeuerBenutzerPanel(), BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	private JPanel createTabellenPanel() {
		benutzerTabelle = new JTable();
		benutzerTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Benutzer:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(new JScrollPane(benutzerTabelle));
		tabellenPanel.setPreferredSize(new Dimension(680,200));
		return tabellenPanel;
	}
	
	private JPanel createNeuerBenutzerPanel() {
		benutzerNeuBearbeitenPanel = new JPanel();
		benutzerNeuBearbeitenPanel.setLayout(new GridBagLayout());
		benutzerNeuBearbeitenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		//benutzerNeuBearbeitenPanel.setPreferredSize(new Dimension(680,400));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

        neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		PKL = new JLabel();
		PKT = new JTextField();
		nachnameL = new JLabel();
		nachnameT = new JTextField();
		vornameL = new JLabel();
		vornameT = new JTextField();
		strasseNrL = new JLabel();
		strasseNrT = new JTextField();
		plzOrtL = new JLabel();
		geburtsdatumL = new JLabel();
		geburtsdatumT = new JTextField();
		telL = new JLabel();
		telT = new JTextField();
		mailL = new JLabel();
		mailT = new JTextField();
		bemerkungL = new JLabel();
		bemerkungT = new JTextArea(2,1);
		erfasstVonL = new JLabel();
		erfasstVonT = new JTextField();
		erfasstAmL = new JLabel();
		erfasstAmT = new JTextField();
		statusL = new JLabel();
		statusCbx = new JComboBox<>();
		anredeL = new JLabel();
		anredeCbx = new JComboBox<>();
		plzOrtCbx = new JComboBox<>();
				
        // Formularfelder
		
		formularHelfer.addLabel(PKL, benutzerNeuBearbeitenPanel);
        formularHelfer.addMiddleField(PKT, benutzerNeuBearbeitenPanel);
		
		JPanel warningPanel = new JPanel();
		warningPanel.setLayout(new GridBagLayout());
        formularHelfer.addLabel(neuAendernL, warningPanel);
        formularHelfer.addLabel(warningPanel, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(new JPanel(), benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(anredeL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(anredeCbx, benutzerNeuBearbeitenPanel);
		
        formularHelfer.addLabel(nachnameL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(nachnameT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(vornameL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(vornameT, benutzerNeuBearbeitenPanel);

        formularHelfer.addLabel(strasseNrL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(strasseNrT, benutzerNeuBearbeitenPanel);

        formularHelfer.addLabel(plzOrtL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(plzOrtCbx, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(geburtsdatumL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(geburtsdatumT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(telL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(telT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(mailL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(mailT, benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(bemerkungL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(new JScrollPane(bemerkungT), benutzerNeuBearbeitenPanel);
        
        formularHelfer.addLabel(statusL, benutzerNeuBearbeitenPanel);
        formularHelfer.addLastField(statusCbx, benutzerNeuBearbeitenPanel);
        
        
        
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
		//benutzerSuchenPanel.setPreferredSize(new Dimension(680,210));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();
		PKSucheL = new JLabel();
		PKSucheT = new JTextField();
		nachnameSucheL = new JLabel();
		nachnameSucheT = new JTextField();
		vornameSucheL = new JLabel();
		vornameSucheT = new JTextField();
		strasseNrSucheL = new JLabel();
		strasseNrSucheT = new JTextField();
		plzOrtSucheL = new JLabel();
		statusSucheL = new JLabel();
		statusSucheCbx = new JComboBox<>();
		plzOrtSucheCbx = new JComboBox<>();
		
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
        
        formularHelfer.addLabel(plzOrtSucheL, benutzerSuchenPanel);
        formularHelfer.addLastField(plzOrtSucheCbx, benutzerSuchenPanel);
       
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
		benutzerTabelle.getColumnModel().getColumn(1).setPreferredWidth(40); // Name
		benutzerTabelle.getColumnModel().getColumn(2).setPreferredWidth(40); // Vorname
		benutzerTabelle.getColumnModel().getColumn(3).setPreferredWidth(50); // Strasse und Nr.
		benutzerTabelle.getColumnModel().getColumn(4).setPreferredWidth(40); // PLZ und Ort
		benutzerTabelle.getColumnModel().getColumn(5).setPreferredWidth(30); // Status
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

	public JLabel getPlzOrtL() {
		return plzOrtL;
	}

	public void setPlzOrtL(JLabel plzOrtL) {
		this.plzOrtL = plzOrtL;
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

	public JLabel getPlzOrtSucheL() {
		return plzOrtSucheL;
	}

	public void setPlzSucheL(JLabel plzOrtSucheL) {
		this.plzOrtSucheL = plzOrtSucheL;
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

	public JComboBox<Anrede> getAnredeCbx() {
		return anredeCbx;
	}

	public void setAnredeCbx(JComboBox<Anrede> anredeCbx) {
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

	public JTextArea getBemerkungT() {
		return bemerkungT;
	}

	public void setBemerkungT(JTextArea bemerkungT) {
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
	
	public JComboBox<Ort> getPlzOrtCbx() {
		return plzOrtCbx;
	}

	public void setPlzOrtCbx(JComboBox<Ort> plzOrtCbx) {
		this.plzOrtCbx = plzOrtCbx;
	}

	public JComboBox<Status> getStatusCbx() {
		return statusCbx;
	}

	public void setStatusCbx(JComboBox<Status> statusCbx) {
		this.statusCbx = statusCbx;
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

	public JComboBox<Ort> getPlzOrtSucheCbx() {
		return plzOrtSucheCbx;
	}

	public void setPlzOrtSucheCbx(JComboBox<Ort> plzOrtSucheCbx) {
		this.plzOrtSucheCbx = plzOrtSucheCbx;
	}

	public JComboBox<Status> getStatusSucheCbx() {
		return statusSucheCbx;
	}

	public void setStatusSucheCbx(JComboBox<Status> statusSucheCbx) {
		this.statusSucheCbx = statusSucheCbx;
	}

	public JPanel getBenutzerNeuBearbeitenPanel() {
		return benutzerNeuBearbeitenPanel;
	}
	
	public JPanel getBenutzerSuchenPanel() {
		return benutzerSuchenPanel;
	}

	public JPanel getBenutzerListe() {
		return benutzerListe;
	}
	

	public JTable getBenutzerTabelle() {
		return benutzerTabelle;
	}

	public LinkedHashMap<JLabel, JComponent> getComponentsNeuAktualisieren() {
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
