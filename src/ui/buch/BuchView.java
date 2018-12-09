package ui.buch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import domain.Autor;
import domain.Schlagwort;
import domain.Status;
import domain.Verlag;
import ui.benutzer.FormularMitGridbaglayout;
import ui.renderer.AutorListCellRenderer;
import ui.renderer.AutorRenderer;
import ui.renderer.SchlagwortListCellRenderer;
import ui.renderer.SchlagwortRenderer;
import ui.renderer.VerlagRenderer;
import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;
import ui.status.StatusRenderer;

/**
 * Ermöglicht Buchsuche, Bearbeitung und Neuerfassung eines Buches
 * 
 * @version 1.0 13.11.2018
 * @author Schmutz
 *
 */
public class BuchView extends JPanel {
	private JPanel buchNeuBearbeitenPanel;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;
	private BuchSuchView buchSuchView;

	private JLabel PKL;
	private JLabel neuBearbeitenL;
	private JLabel barcodeL;
	private JLabel titelL;
	private JLabel verlagL;
	private JLabel auflageL;
	private JLabel reiheL;
	private JLabel anzahlSeitenL;
	private JLabel preisL;
	private JLabel jahrL;
	private JLabel ortL;
	private JLabel isbnL;
	private JLabel statusL;
	private JLabel autorL;
	private JLabel signaturL;
	private JLabel schlagwortL;
	private JLabel notizL;
	private JLabel erfassungsDatumL;
	private JLabel erfassungsUserL;

	private JButton erfassenBarcodeB;
	private JButton zuweisenAutorB;
	private JButton entfernenAutorB;
	private JButton belletristikB;
	private JButton sachbuchB;
	private JButton zuweisenSchlagwortB;
	private JButton entferntenSchlagwortB;

	private JTextField PKT;
	private JTextField barcodeT;
	private JTextField titelT;
	private JTextField auflageT;
	private JTextField reiheT;
	private JTextField anzahlSeitenT;
	private JTextField preisT;
	private JTextField jahrT;
	private JTextField ortT;
	private JTextField isbnT;
	private JTextField signaturT;
	private JTextField erfassungsDatumT;
	private JTextField erfassungsUserT;

	private JComboBox<Verlag> verlagCbx;
	private JComboBox<Autor> autorCbx;
	private JComboBox<Status> statusCbx;
	private JComboBox<Schlagwort> schlagwortCbx;

	private JList<Autor> autorList;
	private JList<Schlagwort> schlagwortList;

	private JRadioButton belletristikR;
	private JRadioButton sachbuchR;
	private ButtonGroup buchtypG;

	private JTextArea notizA;

	private static int HOEHE = 900;
	private static int BREITE = 815;
	
	public static String BELLETRISTIK = "belletristik";
	public static String SACHBUCH = "sachbuch";

	public BuchView(String panelTitel) {

		
		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createNeuesBuchPanel(), BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		// Titel des Panels
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		// Definiert die Grösse des Panels. Die HauptView passt sich an
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	private JPanel createNeuesBuchPanel() {

		
		// Das Grigbaglayout ist eigenwillig und verschiebt die Spaltenbreiten auf ungewünschte Art. Zwei Massnahmen 
		//waren nötige:
		//Breite der Scrollpanes festlegen
		//Je einem Textfeld bei der Initialisierung eine beliebige Anzahl Zeichen zuweisen, damit beim Befüllen
		//mit Text kenie Änderung der Spaltenbreiten erfolgt.
		buchNeuBearbeitenPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		buchNeuBearbeitenPanel.setLayout(gridBagLayout);
		FormularMitGridbaglayout gridBagHelfer = new FormularMitGridbaglayout();

		PKL = new JLabel();
		PKT = new JTextField();
		PKT.setEditable(false);
		
		neuBearbeitenL = new JLabel();
		neuBearbeitenL.setHorizontalAlignment(SwingConstants.CENTER);
		neuBearbeitenL.setPreferredSize(new Dimension(150, 25));

		barcodeL = new JLabel();
		barcodeT = new JTextField();
		barcodeT.setEditable(false);

		erfassenBarcodeB = new JButton();

		titelL = new JLabel();
		titelT = new JTextField();

		verlagL = new JLabel();
		verlagCbx = new JComboBox<Verlag>();
		verlagCbx.setRenderer(new VerlagRenderer());

		auflageL = new JLabel();
		auflageT = new JTextField(10); // Zuweisung Zeichenzahl zur fixierung der Spaltenbreite

		reiheL = new JLabel();
		reiheT = new JTextField(10);// Zuweisung Zeichenzahl zur fixierung der Spaltenbreite

		anzahlSeitenL = new JLabel();
		anzahlSeitenT = new JTextField();

		preisL = new JLabel();
		preisT = new JTextField();

		jahrL = new JLabel();
		jahrT = new JTextField();

		ortL = new JLabel();
		ortT = new JTextField();

		isbnL = new JLabel();
		isbnT = new JTextField();

		statusL = new JLabel();
		statusCbx = new JComboBox<>();
		statusCbx.setRenderer(new StatusRenderer() );
		statusCbx.setPreferredSize(new Dimension(60, 25));

		autorL = new JLabel();
		autorCbx = new JComboBox<>();
		autorCbx.setRenderer(new AutorRenderer());
		autorCbx.setPreferredSize(new Dimension(60, 25));

		zuweisenAutorB = new JButton();
		entfernenAutorB = new JButton();

		JPanel signatur = new JPanel();
		signatur.setLayout(new GridBagLayout());
		belletristikR = new JRadioButton();
		belletristikR.setActionCommand(BELLETRISTIK);
		sachbuchR = new JRadioButton();
		sachbuchR.setActionCommand(SACHBUCH);
		buchtypG = new ButtonGroup();
		buchtypG.add(belletristikR);
		buchtypG.add(sachbuchR);
		signaturT = new JTextField();
		signaturL = new JLabel();
		signatur.setPreferredSize(new Dimension(10, 18));

		gridBagHelfer.labelSetzen(belletristikR, signatur, 0, 0);
		gridBagHelfer.labelSetzen(sachbuchR, signatur, 1, 0);

		schlagwortL = new JLabel();
		schlagwortCbx = new JComboBox<>();
		schlagwortCbx.setPreferredSize(new Dimension(50, 25));
		schlagwortCbx.setRenderer(new SchlagwortRenderer());

		autorList = new JList<>();
		JScrollPane scrollPaneAutorList = new JScrollPane(autorList);
		scrollPaneAutorList.setPreferredSize(new Dimension(250, 10));
		autorList.setCellRenderer(new AutorListCellRenderer());

		zuweisenSchlagwortB = new JButton();
		entferntenSchlagwortB = new JButton();

		schlagwortList = new JList<>();
		JScrollPane scrollPaneSchlagwortList = new JScrollPane(schlagwortList);
		scrollPaneSchlagwortList.setPreferredSize(new Dimension(250, 10));
		schlagwortList.setCellRenderer(new SchlagwortListCellRenderer());


		notizL = new JLabel();
		notizA = new JTextArea(10, 10);
		JScrollPane scrollPaneNotiz = new JScrollPane();
		scrollPaneNotiz.setViewportView(notizA);
		scrollPaneNotiz.setPreferredSize(new Dimension(250, 50));

		erfassungsDatumL = new JLabel();
		erfassungsDatumT = new JTextField();
		erfassungsDatumT.setEditable(false);

		erfassungsUserL = new JLabel();
		erfassungsUserT = new JTextField();
		erfassungsUserT.setEditable(false);

		gridBagHelfer.feldSetzenLang(neuBearbeitenL, buchNeuBearbeitenPanel, 1, 0);
		gridBagHelfer.labelSetzen(PKL, buchNeuBearbeitenPanel, 0, 1);
		gridBagHelfer.feldSetzen(PKT, buchNeuBearbeitenPanel, 1, 1);
		gridBagHelfer.labelSetzen(barcodeL, buchNeuBearbeitenPanel, 0, 2);
		gridBagHelfer.feldSetzen(barcodeT, buchNeuBearbeitenPanel, 1, 2);
		gridBagHelfer.labelSetzen(erfassenBarcodeB, buchNeuBearbeitenPanel, 3, 2);
		gridBagHelfer.labelSetzen(titelL, buchNeuBearbeitenPanel, 0, 3);
		gridBagHelfer.feldSetzenLang(titelT, buchNeuBearbeitenPanel, 1, 3);
		gridBagHelfer.labelSetzen(verlagL, buchNeuBearbeitenPanel, 0, 4);
		gridBagHelfer.feldSetzenLang(verlagCbx, buchNeuBearbeitenPanel, 1, 4);
		gridBagHelfer.labelSetzen(auflageL, buchNeuBearbeitenPanel, 0, 5);
		gridBagHelfer.feldSetzen(auflageT, buchNeuBearbeitenPanel, 1, 5);
		gridBagHelfer.labelSetzen(reiheL, buchNeuBearbeitenPanel, 3, 5);
		gridBagHelfer.feldSetzen(reiheT, buchNeuBearbeitenPanel, 4, 5);
		gridBagHelfer.labelSetzen(anzahlSeitenL, buchNeuBearbeitenPanel, 0, 6);
		gridBagHelfer.feldSetzen(anzahlSeitenT, buchNeuBearbeitenPanel, 1, 6);
		gridBagHelfer.labelSetzen(preisL, buchNeuBearbeitenPanel, 3, 6);
		gridBagHelfer.feldSetzen(preisT, buchNeuBearbeitenPanel, 4, 6);
		gridBagHelfer.labelSetzen(jahrL, buchNeuBearbeitenPanel, 3, 7);
		gridBagHelfer.feldSetzen(jahrT, buchNeuBearbeitenPanel, 4, 7);
		gridBagHelfer.labelSetzen(autorL, buchNeuBearbeitenPanel, 0, 7);
		gridBagHelfer.feldSetzen(autorCbx, buchNeuBearbeitenPanel, 1, 7);
		gridBagHelfer.labelSetzen(isbnL, buchNeuBearbeitenPanel, 3, 8);
		gridBagHelfer.feldSetzen(isbnT, buchNeuBearbeitenPanel, 4, 8);
		gridBagHelfer.labelSetzen(zuweisenAutorB, buchNeuBearbeitenPanel, 0, 8);
		gridBagHelfer.feldSetzenHoch(scrollPaneAutorList, buchNeuBearbeitenPanel, 1, 8, 2);
		gridBagHelfer.labelSetzen(ortL, buchNeuBearbeitenPanel, 3, 9);
		gridBagHelfer.feldSetzen(ortT, buchNeuBearbeitenPanel, 4, 9);
		gridBagHelfer.labelSetzen(entfernenAutorB, buchNeuBearbeitenPanel, 0, 9);
		gridBagHelfer.labelSetzen(statusL, buchNeuBearbeitenPanel, 3, 10);
		gridBagHelfer.feldSetzen(statusCbx, buchNeuBearbeitenPanel, 4, 10);

		gridBagHelfer.labelSetzen(schlagwortL, buchNeuBearbeitenPanel, 0, 10);
		gridBagHelfer.feldSetzen(schlagwortCbx, buchNeuBearbeitenPanel, 1, 10);
		gridBagHelfer.labelSetzen(signaturL, buchNeuBearbeitenPanel, 3, 11);
		gridBagHelfer.feldSetzen(signatur, buchNeuBearbeitenPanel, 4, 11);
		gridBagHelfer.feldSetzenHoch(scrollPaneSchlagwortList, buchNeuBearbeitenPanel, 1, 11, 2);
		gridBagHelfer.labelSetzen(zuweisenSchlagwortB, buchNeuBearbeitenPanel, 0, 11);
		gridBagHelfer.feldSetzen(signaturT, buchNeuBearbeitenPanel, 4, 12);
		gridBagHelfer.labelSetzen(entferntenSchlagwortB, buchNeuBearbeitenPanel, 0, 12);
		gridBagHelfer.labelSetzen(notizL, buchNeuBearbeitenPanel, 0, 13);
		gridBagHelfer.feldSetzenBreitHoch(scrollPaneNotiz, buchNeuBearbeitenPanel, 1, 13, 2, 4);
		gridBagHelfer.labelSetzen(erfassungsDatumL, buchNeuBearbeitenPanel, 0, 15);
		gridBagHelfer.feldSetzen(erfassungsDatumT, buchNeuBearbeitenPanel, 1, 15);
		gridBagHelfer.labelSetzen(erfassungsUserL, buchNeuBearbeitenPanel, 3, 15);
		gridBagHelfer.feldSetzen(erfassungsUserT, buchNeuBearbeitenPanel, 4, 15);

		return rahmenSetzen("Neu / Bearbeiten", buchNeuBearbeitenPanel);

	}

	public void addBuchSuchView(BuchSuchView buchSuchView) {
		this.buchSuchView = buchSuchView;
		centerPanel.add(this.buchSuchView, BorderLayout.NORTH);
	}

	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder(BorderFactory.createTitledBorder(rahmentitel));
		rahmenPanel.add(inhalt);
		return rahmenPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getBuchNeuBearbeitenPanel() {
		return buchNeuBearbeitenPanel;
	}

	public void setBuchNeuBearbeitenPanel(JPanel buchNeuBearbeitenPanel) {
		this.buchNeuBearbeitenPanel = buchNeuBearbeitenPanel;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public BuchSuchView getBuchSuchView() {
		return buchSuchView;
	}

	public void setBuchSuchView(BuchSuchView buchSuchView) {
		this.buchSuchView = buchSuchView;
	}

	public JLabel getPKL() {
		return PKL;
	}

	public void setPKL(JLabel pKL) {
		PKL = pKL;
	}

	public JLabel getBarcodeL() {
		return barcodeL;
	}

	public void setBarcodeL(JLabel barcodeL) {
		this.barcodeL = barcodeL;
	}

	public JLabel getTitelL() {
		return titelL;
	}

	public void setTitelL(JLabel titelL) {
		this.titelL = titelL;
	}

	public JLabel getVerlagL() {
		return verlagL;
	}

	public void setVerlagL(JLabel verlagL) {
		this.verlagL = verlagL;
	}

	public JLabel getAuflageL() {
		return auflageL;
	}

	public void setAuflageL(JLabel auflageL) {
		this.auflageL = auflageL;
	}

	public JLabel getReiheL() {
		return reiheL;
	}

	public void setReiheL(JLabel reiheL) {
		this.reiheL = reiheL;
	}

	public JLabel getAnzahlSeitenL() {
		return anzahlSeitenL;
	}

	public void setAnzahlSeitenL(JLabel anzahlSeitenL) {
		this.anzahlSeitenL = anzahlSeitenL;
	}

	public JLabel getPreisL() {
		return preisL;
	}

	public void setPreisL(JLabel preisL) {
		this.preisL = preisL;
	}

	public JLabel getJahrL() {
		return jahrL;
	}

	public void setJahrL(JLabel jahrL) {
		this.jahrL = jahrL;
	}

	public JLabel getOrtL() {
		return ortL;
	}

	public void setOrtL(JLabel ortL) {
		this.ortL = ortL;
	}

	public JLabel getIsbnL() {
		return isbnL;
	}

	public void setIsbnL(JLabel isbnL) {
		this.isbnL = isbnL;
	}

	public JLabel getStatusL() {
		return statusL;
	}

	public void setStatusL(JLabel statusL) {
		this.statusL = statusL;
	}

	public JLabel getAutorL() {
		return autorL;
	}

	public void setAutorL(JLabel autorL) {
		this.autorL = autorL;
	}

	public JLabel getSignaturL() {
		return signaturL;
	}

	public void setSignaturL(JLabel buchTypL) {
		this.signaturL = buchTypL;
	}

	public JLabel getSchlagwortL() {
		return schlagwortL;
	}

	public void setSchlagwortL(JLabel schlagwortL) {
		this.schlagwortL = schlagwortL;
	}

	public JLabel getNotizL() {
		return notizL;
	}

	public void setNotizL(JLabel notizL) {
		this.notizL = notizL;
	}

	public JLabel getErfassungsDatumL() {
		return erfassungsDatumL;
	}

	public void setErfassungsDatumL(JLabel erfassungsDatumL) {
		this.erfassungsDatumL = erfassungsDatumL;
	}

	public JLabel getErfassungsUserL() {
		return erfassungsUserL;
	}

	public void setErfassungsUserL(JLabel erfassungsUserL) {
		this.erfassungsUserL = erfassungsUserL;
	}

	public JButton getErfassenBarcodeB() {
		return erfassenBarcodeB;
	}

	public void setErfassenBarcodeB(JButton erfassenBarcodeB) {
		this.erfassenBarcodeB = erfassenBarcodeB;
	}

	public JButton getZuweisenAutorB() {
		return zuweisenAutorB;
	}

	public void setZuweisenAutorB(JButton zuweisenAutorB) {
		this.zuweisenAutorB = zuweisenAutorB;
	}

	public JButton getEntfernenAutorB() {
		return entfernenAutorB;
	}

	public void setEntfernenAutorB(JButton entfernenAutorB) {
		this.entfernenAutorB = entfernenAutorB;
	}

	public JButton getBelletristikB() {
		return belletristikB;
	}

	public void setBelletristikB(JButton belletristikB) {
		this.belletristikB = belletristikB;
	}

	public JButton getSachbuchB() {
		return sachbuchB;
	}

	public void setSachbuchB(JButton sachbuchB) {
		this.sachbuchB = sachbuchB;
	}

	public JButton getZuweisenSchlagwortB() {
		return zuweisenSchlagwortB;
	}

	public void setZuweisenSchlagwortB(JButton zuweisenSchlagwortB) {
		this.zuweisenSchlagwortB = zuweisenSchlagwortB;
	}

	public JButton getEntferntenSchlagwortB() {
		return entferntenSchlagwortB;
	}

	public void setEntferntenSchlagwortB(JButton entferntenSchlagwortB) {
		this.entferntenSchlagwortB = entferntenSchlagwortB;
	}

	public JTextField getPKT() {
		return PKT;
	}

	public void setPKT(JTextField pKT) {
		PKT = pKT;
	}

	public JTextField getBarcodeT() {
		return barcodeT;
	}

	public void setBarcodeT(JTextField barcodeT) {
		this.barcodeT = barcodeT;
	}

	public JTextField getTitelT() {
		return titelT;
	}

	public void setTitelT(JTextField titelT) {
		this.titelT = titelT;
	}

	public JTextField getAuflageT() {
		return auflageT;
	}

	public void setAuflageT(JTextField auflageT) {
		this.auflageT = auflageT;
	}

	public JTextField getReiheT() {
		return reiheT;
	}

	public void setReiheT(JTextField reiheT) {
		this.reiheT = reiheT;
	}

	public JTextField getAnzahlSeitenT() {
		return anzahlSeitenT;
	}

	public void setAnzahlSeitenT(JTextField anzahlSeitenT) {
		this.anzahlSeitenT = anzahlSeitenT;
	}

	public JTextField getPreisT() {
		return preisT;
	}

	public void setPreisT(JTextField preisT) {
		this.preisT = preisT;
	}

	public JTextField getJahrT() {
		return jahrT;
	}

	public void setJahrT(JTextField jahrT) {
		this.jahrT = jahrT;
	}

	public JTextField getOrtT() {
		return ortT;
	}

	public void setOrtT(JTextField ortT) {
		this.ortT = ortT;
	}

	public JTextField getIsbnT() {
		return isbnT;
	}

	public void setIsbnT(JTextField isbnT) {
		this.isbnT = isbnT;
	}

	public JTextField getSignaturT() {
		return signaturT;
	}

	public void setSignaturT(JTextField signaturT) {
		this.signaturT = signaturT;
	}

	public JTextField getErfassungsDatumT() {
		return erfassungsDatumT;
	}

	public void setErfassungsDatumT(JTextField erfassungsDatumT) {
		this.erfassungsDatumT = erfassungsDatumT;
	}

	public JTextField getErfassungsUserT() {
		return erfassungsUserT;
	}

	public void setErfassungsUserT(JTextField erfassungsUserT) {
		this.erfassungsUserT = erfassungsUserT;
	}

	public JComboBox<Verlag> getVerlagCbx() {
		return verlagCbx;
	}

	public void setVerlagCbx(JComboBox<Verlag> verlagCbx) {
		this.verlagCbx = verlagCbx;
	}

	public JComboBox<Autor> getAutorCbx() {
		return autorCbx;
	}

	public void setAutorCbx(JComboBox<Autor> autorCbx) {
		this.autorCbx = autorCbx;
	}

	public JComboBox<Status> getStatusCbx() {
		return statusCbx;
	}

	public void setStatusCbx(JComboBox<Status> statusCbx) {
		this.statusCbx = statusCbx;
	}

	public JComboBox<Schlagwort> getSchlagwortCbx() {
		return schlagwortCbx;
	}

	public void setSchlagwortCbx(JComboBox<Schlagwort> schlagwortCbx) {
		this.schlagwortCbx = schlagwortCbx;
	}

	public JList<Autor> getAutorList() {
		return autorList;
	}

	public void setAutorList(JList<Autor> autorList) {
		this.autorList = autorList;
	}

	public JList<Schlagwort> getSchlagwortList() {
		return schlagwortList;
	}

	public void setSchlagwortList(JList<Schlagwort> schlagwortList) {
		this.schlagwortList = schlagwortList;
	}

	public JRadioButton getBelletristikR() {
		return belletristikR;
	}

	public void setBelletristikR(JRadioButton belletristikR) {
		this.belletristikR = belletristikR;
	}

	public JRadioButton getSachbuchR() {
		return sachbuchR;
	}

	public void setSachbuchR(JRadioButton sachbuchR) {
		this.sachbuchR = sachbuchR;
	}

	public ButtonGroup getBuchtypG() {
		return buchtypG;
	}

	public void setBuchtypG(ButtonGroup buchtypG) {
		this.buchtypG = buchtypG;
	}

	public JTextArea getNotizA() {
		return notizA;
	}

	public void setNotizA(JTextArea notizA) {
		this.notizA = notizA;
	}

	public JLabel getNeuAendernL() {
		return neuBearbeitenL;
	}

	public void setNeuAendernL(JLabel neuAendernL) {
		this.neuBearbeitenL = neuAendernL;
	}

	public void setButtonPanel(StandardButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

}
