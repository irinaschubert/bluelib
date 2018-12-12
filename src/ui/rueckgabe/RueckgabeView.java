package ui.rueckgabe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import ui.benutzer.FormularMitGridbaglayout;
import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * View für das Verwalten der Buchrueckgaben
 * 
 * @version 1.0 2018-12-08
 * @author Ueli
 */
@SuppressWarnings("serial")
public class RueckgabeView extends JPanel {
	private JPanel centerPanel;
	private JPanel zuweisenPanel;
	private JPanel benutzerAnzeigePanel;
	private JPanel buchSuchenPanel;
	private JPanel rueckgabePanel;
	private JPanel rueckgabeInfoPanel;
	private StandardButtonPanel buttonPanel;

	private JLabel barcodeL;
	private JLabel PKLBuch;
	private JLabel buchStatusL;
	private JLabel buchtitelL;
	private JLabel autorL;

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
	private JTextField benutzerIDT;
	private JTextField benutzerNameT;
	private JTextField benutzerVornameT;
	private JTextField benutzerStatusT;
	private JTextField erfasstVonT;
	private JTextField erfasstAmT;

	private JButton suchButtonBuch;
	private JButton ausleiheSpeichernButton;
	private JLabel rueckgabeWechselnL;

	private JTable ausleiheTabelle;
	private static int HOEHE = 750;
	private static int BREITE = 750;

	public RueckgabeView(String panelTitel) {

		new JLabel(panelTitel);
		buttonPanel = new StandardButtonPanel();
		suchButtonBuch = new JButton();
		ausleiheSpeichernButton = new JButton();

		zuweisenPanel = new JPanel(new GridLayout(1, 2));
		zuweisenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		zuweisenPanel.add(createBuchSuchenPanel());
		zuweisenPanel.add(createBenutzerPanel());

		rueckgabePanel = new JPanel(new BorderLayout());
		rueckgabePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		rueckgabePanel.add(createRueckgabeInfoPanel(), BorderLayout.NORTH);
		rueckgabePanel.add(createTabellenPanel(), BorderLayout.SOUTH);

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(zuweisenPanel, BorderLayout.NORTH);
		centerPanel.add(rueckgabePanel, BorderLayout.SOUTH);

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

		return rahmenSetzen("Buch zuweisen", buchSuchenPanel);
	}

	private JPanel createBenutzerPanel() {
		benutzerAnzeigePanel = new JPanel();
		benutzerAnzeigePanel.setLayout(new GridBagLayout());
		benutzerAnzeigePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

		benutzerIDL = new JLabel();
		benutzerIDT = new JTextField();
		benutzerStatusL = new JLabel();
		benutzerStatusT = new JTextField();
		benutzerNameL = new JLabel();
		benutzerNameT = new JTextField();
		benutzerVornameL = new JLabel();
		benutzerVornameT = new JTextField();

		// Formularfelder

		formularHelfer.addLabel(benutzerIDL, benutzerAnzeigePanel);
		formularHelfer.addLastField(benutzerIDT, benutzerAnzeigePanel);

		formularHelfer.addLabel(benutzerNameL, benutzerAnzeigePanel);
		formularHelfer.addLastField(benutzerNameT, benutzerAnzeigePanel);

		formularHelfer.addLabel(benutzerVornameL, benutzerAnzeigePanel);
		formularHelfer.addLastField(benutzerVornameT, benutzerAnzeigePanel);

		formularHelfer.addLabel(benutzerStatusL, benutzerAnzeigePanel);
		formularHelfer.addLastField(benutzerStatusT, benutzerAnzeigePanel);

		return rahmenSetzen("Benutzer anzeigen", benutzerAnzeigePanel);
	}

	private JPanel createRueckgabeInfoPanel() {
		rueckgabeInfoPanel = new JPanel();
		rueckgabeInfoPanel.setLayout(new GridBagLayout());
		rueckgabeInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();

		notizL = new JLabel();
		notizT = new JTextArea(2, 1);
		erfasstVonL = new JLabel();
		erfasstVonT = new JTextField();
		erfasstAmL = new JLabel();
		erfasstAmT = new JTextField();

		// Formularfelder
		formularHelfer.addLabel(notizL, rueckgabeInfoPanel);
		formularHelfer.addLastField(new JScrollPane(notizT), rueckgabeInfoPanel);

		formularHelfer.addLabel(erfasstAmL, rueckgabeInfoPanel);
		formularHelfer.addLastField(erfasstAmT, rueckgabeInfoPanel);

		formularHelfer.addLabel(erfasstVonL, rueckgabeInfoPanel);
		formularHelfer.addLastField(erfasstVonT, rueckgabeInfoPanel);

		formularHelfer.addLabel(new JLabel(""), rueckgabeInfoPanel);
		formularHelfer.addMiddleField(new JLabel(""), rueckgabeInfoPanel);
		formularHelfer.addLastField(ausleiheSpeichernButton, rueckgabeInfoPanel);

		return rahmenSetzen("Rückgabe", rueckgabeInfoPanel);
	}

	private JPanel createTabellenPanel() {
		ausleiheTabelle = new JTable();
		ausleiheTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Heute zurückgegeben:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(new JScrollPane(ausleiheTabelle));
		tabellenPanel.setPreferredSize(new Dimension(680, 200));
		return tabellenPanel;
	}

	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder(BorderFactory.createTitledBorder(rahmentitel));
		rahmenPanel.add(inhalt);
		return rahmenPanel;
	}

	public void spaltenBreiteSetzen() {
		ausleiheTabelle.getColumnModel().getColumn(0).setPreferredWidth(50); // Barcode
		ausleiheTabelle.getColumnModel().getColumn(1).setPreferredWidth(50); // Buchtitel
		ausleiheTabelle.getColumnModel().getColumn(2).setPreferredWidth(50); // Datum Rueckgabe
		ausleiheTabelle.getColumnModel().getColumn(3).setPreferredWidth(50); // Notiz
	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
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
