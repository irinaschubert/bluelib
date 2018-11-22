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
 * Suche nach, neu Erstellen und Bearbeiten von Ausleihen
 * 
 * @version 1.0 06.11.2018
 * @author irina
 *
 */
public class AusleiheView extends JPanel {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;
	private JPanel tabellenPanel;
	private JPanel benutzerSuchenPanel;
	private JPanel buchSuchenPanel;
	private JPanel ausleihePanel;
	private JPanel ausleihenListe;
	
	private JLabel buchL;
	private JLabel barcodeL;
	private JLabel buchStatusL;
	private JLabel buchtitelL;
	private JLabel autorL;
	private JLabel notizL;
	private JLabel benutzerL;
	private JLabel benutzerIDL;
	private JLabel benutzerNameL;
	private JLabel benutzerVornameL;
	private JLabel benutzerStatusL;
	private JLabel erfasstVonL;
	private JLabel erfasstAmL;
	private JLabel ausgelieheneMedienL;
	
	private JTextField buchT;
	private JTextField barcodeT;
	private JTextField buchStatusT;
	private JTextField buchTitelT;
	private JTextField autorT;
	private JTextArea notizT;
	private JTextField benutzerT;
	private JTextField benutzerIDT;
	private JTextField benutzerNameT;
	private JTextField benutzerVornameT;
	private JTextField benutzerStatusT;
	private JTextField erfasstVonT;
	private JTextField erfasstAmT;
	
	private JButton suchButtonBuch;
	private JButton suchButtonBenutzer;
	private JTable ausleiheTabelle;
	private static int HOEHE = 950;
	private static int BREITE = 750;

	public AusleiheView(String panelTitel) {
		
		new JLabel(panelTitel);
		ausleihenListe = new JPanel();
		buttonPanel = new StandardButtonPanel();
		suchButtonBuch = new JButton();
		suchButtonBenutzer = new JButton();

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createBuchSuchenPanel(), BorderLayout.WEST);
		centerPanel.add(createBenutzerSuchenPanel(), BorderLayout.EAST);
		centerPanel.add(createAusleihenPanel(), BorderLayout.CENTER);
		centerPanel.add(createTabellenPanel(), BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	private JPanel createTabellenPanel() {
		ausleiheTabelle = new JTable();
		ausleiheTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Aktuell ausgeliehen:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(new JScrollPane(ausleiheTabelle));
		tabellenPanel.setPreferredSize(new Dimension(680,200));
		return tabellenPanel;
	}
	
	private JPanel createBuchSuchenPanel() {
		buchSuchenPanel = new JPanel();
		buchSuchenPanel.setLayout(new GridBagLayout());
		buchSuchenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

        buchL = new JLabel();
		buchL.setHorizontalAlignment(SwingConstants.CENTER);
		barcodeL = new JLabel();
		barcodeT = new JTextField();
		buchStatusL = new JLabel();
		buchStatusT = new JTextField();
		buchtitelL = new JLabel();
		buchTitelT = new JTextField();
		autorL = new JLabel();
		autorT = new JTextField();
				
        // Formularfelder
		formularHelfer.addLabel(buchL, buchSuchenPanel);
        formularHelfer.addMiddleField(buchT, buchSuchenPanel);
		
		JPanel warningPanel = new JPanel();
		warningPanel.setLayout(new GridBagLayout());
        formularHelfer.addLabel(barcodeL, warningPanel);
        formularHelfer.addLabel(warningPanel, buchSuchenPanel);
        formularHelfer.addLastField(new JPanel(), buchSuchenPanel);
        
        formularHelfer.addLabel(buchStatusL, buchSuchenPanel);
        formularHelfer.addLastField(buchStatusT, buchSuchenPanel);
		
        formularHelfer.addLabel(buchtitelL, buchSuchenPanel);
        formularHelfer.addLastField(buchTitelT, buchSuchenPanel);
        
        formularHelfer.addLabel(autorL, buchSuchenPanel);
        formularHelfer.addLastField(autorT, buchSuchenPanel);

        return rahmenSetzen("Buch zuweisen", buchSuchenPanel );
	}
	
	private JPanel createBenutzerSuchenPanel() {
		benutzerSuchenPanel = new JPanel();
		benutzerSuchenPanel.setLayout(new GridBagLayout());
		benutzerSuchenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

        benutzerL = new JLabel();
		benutzerL.setHorizontalAlignment(SwingConstants.CENTER);
		benutzerIDL = new JLabel();
		benutzerIDT = new JTextField();
		benutzerStatusL = new JLabel();
		benutzerStatusT = new JTextField();
		benutzerNameL = new JLabel();
		benutzerNameT = new JTextField();
		benutzerVornameL = new JLabel();
		benutzerVornameT = new JTextField();
				
        // Formularfelder
		formularHelfer.addLabel(benutzerL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(benutzerIDL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerIDT, benutzerSuchenPanel);
		
        formularHelfer.addLabel(benutzerStatusL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerStatusT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(benutzerNameL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerNameT, benutzerSuchenPanel);

        formularHelfer.addLabel(benutzerVornameL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerVornameT, benutzerSuchenPanel);

        return rahmenSetzen("Benutzer zuweisen", benutzerSuchenPanel );
	}
	
	private JPanel createAusleihenPanel() {
		ausleihePanel = new JPanel();
		ausleihePanel.setLayout(new GridBagLayout());
		ausleihePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

		notizL = new JLabel();
		notizT = new JTextArea(2,1);
		erfasstVonL = new JLabel();
		erfasstVonT = new JTextField();
		erfasstAmL = new JLabel();
		erfasstAmT = new JTextField();
				
        // Formularfelder
		formularHelfer.addLabel(buchL, ausleihePanel);
        formularHelfer.addMiddleField(buchT, ausleihePanel);
        
        formularHelfer.addLabel(notizL, ausleihePanel);
        formularHelfer.addLastField(notizT, ausleihePanel);
		
        formularHelfer.addLabel(erfasstAmL, ausleihePanel);
        formularHelfer.addLastField(erfasstAmT, ausleihePanel);
        
        formularHelfer.addLabel(erfasstVonL, ausleihePanel);
        formularHelfer.addLastField(erfasstVonT, ausleihePanel);

        return rahmenSetzen("Ausleihe", benutzerSuchenPanel );
	}

	
	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder (BorderFactory.createTitledBorder (rahmentitel));
		rahmenPanel.add(inhalt);
	    return rahmenPanel;
	}

	public void spaltenBreiteSetzen() {
		ausleiheTabelle.getColumnModel().getColumn(0).setPreferredWidth(50); // Barcode Buch
		ausleiheTabelle.getColumnModel().getColumn(1).setPreferredWidth(50); // Buchtitel
		ausleiheTabelle.getColumnModel().getColumn(2).setPreferredWidth(50); // Datum Ausleihe
		ausleiheTabelle.getColumnModel().getColumn(3).setPreferredWidth(50); // Notiz
	}
	
	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public JPanel getTabellenPanel() {
		return tabellenPanel;
	}

	public void setTabellenPanel(JPanel tabellenPanel) {
		this.tabellenPanel = tabellenPanel;
	}

	public JPanel getBenutzerSuchenPanel() {
		return benutzerSuchenPanel;
	}

	public void setBenutzerSuchenPanel(JPanel benutzerSuchenPanel) {
		this.benutzerSuchenPanel = benutzerSuchenPanel;
	}

	public JPanel getBuchSuchenPanel() {
		return buchSuchenPanel;
	}

	public void setBuchSuchenPanel(JPanel buchSuchenPanel) {
		this.buchSuchenPanel = buchSuchenPanel;
	}

	public JPanel getAusleihePanel() {
		return ausleihePanel;
	}

	public void setAusleihePanel(JPanel ausleihePanel) {
		this.ausleihePanel = ausleihePanel;
	}

	public JPanel getAusleihenListe() {
		return ausleihenListe;
	}

	public void setAusleihenListe(JPanel ausleihenListe) {
		this.ausleihenListe = ausleihenListe;
	}

	public JLabel getBuchL() {
		return buchL;
	}

	public void setBuchL(JLabel buchL) {
		this.buchL = buchL;
	}

	public JLabel getBarcodeL() {
		return barcodeL;
	}

	public void setBarcodeL(JLabel barcodeL) {
		this.barcodeL = barcodeL;
	}

	public JLabel getBuchStatusL() {
		return buchStatusL;
	}

	public void setBuchStatusL(JLabel buchStatusL) {
		this.buchStatusL = buchStatusL;
	}

	public JLabel getBuchtitelL() {
		return buchtitelL;
	}

	public void setBuchtitelL(JLabel buchtitelL) {
		this.buchtitelL = buchtitelL;
	}

	public JLabel getAutorL() {
		return autorL;
	}

	public void setAutorL(JLabel autorL) {
		this.autorL = autorL;
	}

	public JLabel getNotizL() {
		return notizL;
	}

	public void setNotizL(JLabel notizL) {
		this.notizL = notizL;
	}

	public JLabel getBenutzerL() {
		return benutzerL;
	}

	public void setBenutzerL(JLabel benutzerL) {
		this.benutzerL = benutzerL;
	}

	public JLabel getBenutzerIDL() {
		return benutzerIDL;
	}

	public void setBenutzerIDL(JLabel benutzerIDL) {
		this.benutzerIDL = benutzerIDL;
	}

	public JLabel getBenutzerNameL() {
		return benutzerNameL;
	}

	public void setBenutzerNameL(JLabel benutzerNameL) {
		this.benutzerNameL = benutzerNameL;
	}

	public JLabel getBenutzerVornameL() {
		return benutzerVornameL;
	}

	public void setBenutzerVornameL(JLabel benutzerVornameL) {
		this.benutzerVornameL = benutzerVornameL;
	}

	public JLabel getBenutzerStatusL() {
		return benutzerStatusL;
	}

	public void setBenutzerStatusL(JLabel benutzerStatusL) {
		this.benutzerStatusL = benutzerStatusL;
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

	public JLabel getAusgelieheneMedienL() {
		return ausgelieheneMedienL;
	}

	public void setAusgelieheneMedienL(JLabel ausgelieheneMedienL) {
		this.ausgelieheneMedienL = ausgelieheneMedienL;
	}

	public JTextField getBuchT() {
		return buchT;
	}

	public void setBuchT(JTextField buchT) {
		this.buchT = buchT;
	}

	public JTextField getBarcodeT() {
		return barcodeT;
	}

	public void setBarcodeT(JTextField barcodeT) {
		this.barcodeT = barcodeT;
	}

	public JTextField getBuchStatusT() {
		return buchStatusT;
	}

	public void setBuchStatusT(JTextField buchStatusT) {
		this.buchStatusT = buchStatusT;
	}

	public JTextField getBuchTitelT() {
		return buchTitelT;
	}

	public void setBuchTitelT(JTextField buchTitelT) {
		this.buchTitelT = buchTitelT;
	}

	public JTextField getAutorT() {
		return autorT;
	}

	public void setAutorT(JTextField autorT) {
		this.autorT = autorT;
	}

	public JTextArea getNotizT() {
		return notizT;
	}

	public void setNotizT(JTextArea notizT) {
		this.notizT = notizT;
	}

	public JTextField getBenutzerT() {
		return benutzerT;
	}

	public void setBenutzerT(JTextField benutzerT) {
		this.benutzerT = benutzerT;
	}

	public JTextField getBenutzerIDT() {
		return benutzerIDT;
	}

	public void setBenutzerIDT(JTextField benutzerIDT) {
		this.benutzerIDT = benutzerIDT;
	}

	public JTextField getBenutzerNameT() {
		return benutzerNameT;
	}

	public void setBenutzerNameT(JTextField benutzerNameT) {
		this.benutzerNameT = benutzerNameT;
	}

	public JTextField getBenutzerVornameT() {
		return benutzerVornameT;
	}

	public void setBenutzerVornameT(JTextField benutzerVornameT) {
		this.benutzerVornameT = benutzerVornameT;
	}

	public JTextField getBenutzerStatusT() {
		return benutzerStatusT;
	}

	public void setBenutzerStatusT(JTextField benutzerStatusT) {
		this.benutzerStatusT = benutzerStatusT;
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

	public JButton getSuchButtonBuch() {
		return suchButtonBuch;
	}

	public void setSuchButtonBuch(JButton suchButtonBuch) {
		this.suchButtonBuch = suchButtonBuch;
	}

	public JButton getSuchButtonBenutzer() {
		return suchButtonBenutzer;
	}

	public void setSuchButtonBenutzer(JButton suchButtonBenutzer) {
		this.suchButtonBenutzer = suchButtonBenutzer;
	}

	public JTable getAusleiheTabelle() {
		return ausleiheTabelle;
	}

	public void setAusleiheTabelle(JTable ausleiheTabelle) {
		this.ausleiheTabelle = ausleiheTabelle;
	}

	public static int getHOEHE() {
		return HOEHE;
	}

	public static void setHOEHE(int hOEHE) {
		HOEHE = hOEHE;
	}

	public static int getBREITE() {
		return BREITE;
	}

	public static void setBREITE(int bREITE) {
		BREITE = bREITE;
	}

	public void setButtonPanel(StandardButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

}
