package ui.ausleihe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import ui.benutzer.FormularMitGridbaglayout;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * View für das Verwalten der Ausleihen
 * 
 * @version 1.0 06.11.2018
 * @author irina
 */
@SuppressWarnings("serial")
public class AusleiheView extends JPanel {
	private JFrame frame;
	private JPanel centerPanel;
	private JPanel zuweisenPanel;
	private JPanel benutzerSuchenPanel;
	private JPanel buchSuchenPanel;
	private JPanel ausleihePanel;
	private JPanel ausleiheInfoPanel;
	private JPanel tabellenPanel;
	private AusleiheButtonPanel buttonPanel;
	
	private JLabel barcodeL;
	private JLabel PKLBuch;
	private JLabel buchStatusL;
	private JLabel buchtitelL;
	private JLabel autorL;
	
	private JLabel benutzerEingabeL;
	private JLabel benutzerIDL;
	private JLabel benutzerNameL;
	private JLabel benutzerVornameL;
	private JLabel benutzerStatusL;
	
	private JLabel notizL;
	private JLabel erfasstVonL;
	private JLabel erfasstAmL;
	private JLabel ausgelieheneMedienL;
	
	private JTextField PKTBuch;
	private JTextField barcodeT;
	private JTextField buchStatusT;
	private JTextField buchTitelT;
	private JTextField autorT;
	private JTextArea notizT;
	private JTextField benutzerEingabeT;
	private JTextField benutzerIDT;
	private JTextField benutzerNameT;
	private JTextField benutzerVornameT;
	private JTextField benutzerStatusT;
	private JTextField erfasstVonT;
	private JTextField erfasstAmT;
	
	private JButton suchButtonBuch;
	private JButton suchButtonBenutzer;
	private JButton ausleiheSpeichernButton;
	private JLabel rueckgabeWechselnL;
	
	private JTable ausleiheTabelle;
	private static int HOEHE = 750;
	private static int BREITE = 750;

	public AusleiheView(String panelTitel) {
		
		new JLabel(panelTitel);
		buttonPanel = new AusleiheButtonPanel();
		suchButtonBuch = new JButton();
		suchButtonBenutzer = new JButton();
		ausleiheSpeichernButton = new JButton();
		
		zuweisenPanel = new JPanel(new GridLayout(1,2));
		zuweisenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		zuweisenPanel.add(createBuchSuchenPanel());
		zuweisenPanel.add(createBenutzerSuchenPanel());
		
		ausleihePanel = new JPanel(new BorderLayout());
		ausleihePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		ausleihePanel.add(createAusleiheInfoPanel(), BorderLayout.NORTH);
		ausleihePanel.add(createTabellenPanel(), BorderLayout.SOUTH);
		
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(zuweisenPanel, BorderLayout.NORTH);
		centerPanel.add(ausleihePanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}
	
	private JPanel createBuchSuchenPanel() {
		buchSuchenPanel = new JPanel();
		buchSuchenPanel.setLayout(new GridBagLayout());
		buchSuchenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

        PKLBuch = new JLabel();
        PKTBuch = new JTextField();
		barcodeL = new JLabel();
		barcodeT = new JTextField();
		buchStatusL = new JLabel();
		buchStatusT = new JTextField();
		buchtitelL = new JLabel();
		buchTitelT = new JTextField();
		autorL = new JLabel();
		autorT = new JTextField();
				
        // Formularfelder
		formularHelfer.addLabel(barcodeL, buchSuchenPanel);
        formularHelfer.addLastField(barcodeT, buchSuchenPanel);
		
		formularHelfer.addLabel(PKLBuch, buchSuchenPanel);
        formularHelfer.addLastField(PKTBuch, buchSuchenPanel);
		
        formularHelfer.addLabel(buchtitelL, buchSuchenPanel);
        formularHelfer.addLastField(buchTitelT, buchSuchenPanel);
        
        formularHelfer.addLabel(autorL, buchSuchenPanel);
        formularHelfer.addLastField(autorT, buchSuchenPanel);
        
        formularHelfer.addLabel(buchStatusL, buchSuchenPanel);
        formularHelfer.addLastField(buchStatusT, buchSuchenPanel);
        
        formularHelfer.addLabel(new JLabel(""), buchSuchenPanel);
        formularHelfer.addLastField(suchButtonBuch, buchSuchenPanel);

        return rahmenSetzen("Buch zuweisen", buchSuchenPanel );
	}
	
	private JPanel createBenutzerSuchenPanel() {
		benutzerSuchenPanel = new JPanel();
		benutzerSuchenPanel.setLayout(new GridBagLayout());
		benutzerSuchenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

        benutzerEingabeL = new JLabel();
		benutzerEingabeT = new JTextField();
		benutzerIDL = new JLabel();
		benutzerIDT = new JTextField();
		benutzerStatusL = new JLabel();
		benutzerStatusT = new JTextField();
		benutzerNameL = new JLabel();
		benutzerNameT = new JTextField();
		benutzerVornameL = new JLabel();
		benutzerVornameT = new JTextField();
				
        // Formularfelder
		formularHelfer.addLabel(benutzerEingabeL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerEingabeT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(benutzerIDL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerIDT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(benutzerNameL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerNameT, benutzerSuchenPanel);

        formularHelfer.addLabel(benutzerVornameL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerVornameT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(benutzerStatusL, benutzerSuchenPanel);
        formularHelfer.addLastField(benutzerStatusT, benutzerSuchenPanel);
        
        formularHelfer.addLabel(new JLabel(""), benutzerSuchenPanel);
        formularHelfer.addLastField(suchButtonBenutzer, benutzerSuchenPanel);

        return rahmenSetzen("Benutzer zuweisen", benutzerSuchenPanel );
	}
	
	private JPanel createAusleiheInfoPanel() {
		ausleiheInfoPanel = new JPanel();
		ausleiheInfoPanel.setLayout(new GridBagLayout());
		ausleiheInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

		notizL = new JLabel();
		notizT = new JTextArea(2,1);
		erfasstVonL = new JLabel();
		erfasstVonT = new JTextField();
		erfasstAmL = new JLabel();
		erfasstAmT = new JTextField();
				
        // Formularfelder        
        formularHelfer.addLabel(notizL, ausleiheInfoPanel);
        formularHelfer.addLastField(new JScrollPane(notizT), ausleiheInfoPanel);
		
        formularHelfer.addLabel(erfasstAmL, ausleiheInfoPanel);
        formularHelfer.addLastField(erfasstAmT, ausleiheInfoPanel);
        
        formularHelfer.addLabel(erfasstVonL, ausleiheInfoPanel);
        formularHelfer.addLastField(erfasstVonT, ausleiheInfoPanel);
        
        formularHelfer.addLabel(new JLabel(""), ausleiheInfoPanel);
        formularHelfer.addMiddleField(new JLabel(""), ausleiheInfoPanel);
        formularHelfer.addLastField(ausleiheSpeichernButton, ausleiheInfoPanel);
        
        return rahmenSetzen("Ausleihe", ausleiheInfoPanel);
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

	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder (BorderFactory.createTitledBorder (rahmentitel));
		rahmenPanel.add(inhalt);
	    return rahmenPanel;
	}

	public void spaltenBreiteSetzen() {
		ausleiheTabelle.getColumnModel().getColumn(0).setPreferredWidth(50); // Barcode
		ausleiheTabelle.getColumnModel().getColumn(1).setPreferredWidth(50); // Buchtitel
		ausleiheTabelle.getColumnModel().getColumn(2).setPreferredWidth(50); // Datum Ausleihe
		ausleiheTabelle.getColumnModel().getColumn(3).setPreferredWidth(50); // Notiz
	}
	
	public AusleiheButtonPanel getButton() {
		return this.buttonPanel;
	}

	public AusleiheButtonPanel getButtonPanel() {
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

	public JLabel getPKLBuch() {
		return PKLBuch;
	}

	public void setBuchL(JLabel pKL) {
		this.PKLBuch = pKL;
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

	public JLabel getBenutzerEingabeL() {
		return benutzerEingabeL;
	}

	public void setBenutzerEingabeL(JLabel benutzerEingabeL) {
		this.benutzerEingabeL = benutzerEingabeL;
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

	public JTextField getPKTBuch() {
		return PKTBuch;
	}

	public void setPKTBuch(JTextField PKTBuch) {
		this.PKTBuch = PKTBuch;
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

	public JTextField getBenutzerEingabeT() {
		return benutzerEingabeT;
	}

	public void setBenutzerEingabeT(JTextField benutzerEingabeT) {
		this.benutzerEingabeT = benutzerEingabeT;
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

	public void setButtonPanel(AusleiheButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}
	
	public JButton getAusleiheSpeichernButton() {
		return ausleiheSpeichernButton;
	}

	public void setAusleiheSpeichernButton(JButton ausleiheSpeichernButton) {
		this.ausleiheSpeichernButton = ausleiheSpeichernButton;
	}
	
	public JLabel getRueckgabeWechselnL() {
		return rueckgabeWechselnL;
	}

	public void setRueckgabeWechselnL(JLabel rueckgabeWechselnL) {
		this.rueckgabeWechselnL = rueckgabeWechselnL;
	}

	public JPanel getZuweisenPanel() {
		return zuweisenPanel;
	}

	public void setZuweisenPanel(JPanel zuweisenPanel) {
		this.zuweisenPanel = zuweisenPanel;
	}
}
