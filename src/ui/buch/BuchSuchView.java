package ui.buch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import domain.Autor;
import domain.Status;
import domain.Verlag;
import ui.benutzer.FormularMitGridbaglayout;
import ui.renderer.AutorRenderer;
import ui.renderer.StatusRenderer;
import ui.renderer.VerlagRenderer;
import ui.standardKomponenten.StandardButtonPanel;

/**
 * Panel f�r die Buchsuche
 * 
 * @version 1.0 13.11.2018
 * @author Ueli
 *
 */
public class BuchSuchView extends JPanel {

	private StandardButtonPanel buttonPanel;
	private JLabel barcodeSucheL;
	private JLabel titelSucheL;
	private JLabel autorSucheL;
	private JLabel verlagSucheL;
	private JLabel signaturSucheL;
	private JLabel statusSucheL;
	private JTextField barcodeSucheT;
	private JTextField titelSucheT;
	private JComboBox<Autor> autorSucheCbx;
	private JComboBox<Verlag> verlagSucheCbx;
	private JTextField signaturSucheT;
	private JComboBox<Status> statusSucheCbx;

	private JButton suchButton;
	private JTable buchTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();

	public BuchSuchView() {

		suchButton = new JButton();

		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(createSuchePanel(), BorderLayout.NORTH);
		this.add(createTabellenPanel(), BorderLayout.CENTER);

	}

	private JPanel createTabellenPanel() {
		buchTabelle = new JTable(); // Panel f�r die Tabelle
		buchTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt werden
		JScrollPane scroll = new JScrollPane(buchTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene B�cher:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		tabellenPanel.setPreferredSize(new Dimension(100, 200));
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();
		suchPanel.setLayout(new GridBagLayout());

		verlagSucheCbx = new JComboBox<Verlag>();
		verlagSucheCbx.setRenderer(new VerlagRenderer());
		autorSucheCbx = new JComboBox<Autor>();
		autorSucheCbx.setRenderer(new AutorRenderer());
		statusSucheCbx = new JComboBox<Status>();
		statusSucheCbx.setRenderer(new StatusRenderer());

		componentsSuche.put(barcodeSucheL = new JLabel(), barcodeSucheT = new JTextField());
		componentsSuche.put(titelSucheL = new JLabel(), titelSucheT = new JTextField());
		componentsSuche.put(autorSucheL = new JLabel(), autorSucheCbx);
		componentsSuche.put(verlagSucheL = new JLabel(), verlagSucheCbx);
		componentsSuche.put(signaturSucheL = new JLabel(), signaturSucheT = new JTextField());
		componentsSuche.put(statusSucheL = new JLabel(), statusSucheCbx);

		formularHelfer.labelSetzen(barcodeSucheL, suchPanel, 0, 0);
		formularHelfer.feldSetzen(componentsSuche.get(barcodeSucheL), suchPanel, 1, 0);

		formularHelfer.labelSetzen(titelSucheL, suchPanel, 0, 1);
		formularHelfer.feldSetzen(componentsSuche.get(titelSucheL), suchPanel, 1, 1);

		formularHelfer.labelSetzen(autorSucheL, suchPanel, 0, 2);
		formularHelfer.feldSetzen(componentsSuche.get(autorSucheL), suchPanel, 1, 2);

		formularHelfer.labelSetzen(verlagSucheL, suchPanel, 0, 3);
		formularHelfer.feldSetzen(componentsSuche.get(verlagSucheL), suchPanel, 1, 3);

		formularHelfer.labelSetzen(signaturSucheL, suchPanel, 0, 4);
		formularHelfer.feldSetzen(componentsSuche.get(signaturSucheL), suchPanel, 1, 4);

		formularHelfer.labelSetzen(statusSucheL, suchPanel, 0, 5);
		formularHelfer.feldSetzen(componentsSuche.get(statusSucheL), suchPanel, 1, 5);

		formularHelfer.labelSetzenMitAnker(suchButton, suchPanel, 2, 5, GridBagConstraints.CENTER);

		suchPanel.setPreferredSize(new Dimension(80, 200));

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

		buchTabelle.getColumnModel().getColumn(0).setPreferredWidth(5); // Name
		buchTabelle.getColumnModel().getColumn(1).setPreferredWidth(150); // Titel
		buchTabelle.getColumnModel().getColumn(2).setPreferredWidth(10); // Signatur
		buchTabelle.getColumnModel().getColumn(3).setPreferredWidth(100); // Autor
		buchTabelle.getColumnModel().getColumn(4).setPreferredWidth(30); // Verlag
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(StandardButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JLabel getBarcodeSucheL() {
		return barcodeSucheL;
	}

	public void setBarcodeSucheL(JLabel barcodeSucheL) {
		this.barcodeSucheL = barcodeSucheL;
	}

	public JLabel getAutorSucheL() {
		return autorSucheL;
	}

	public void setAutorSucheL(JLabel autorSucheL) {
		this.autorSucheL = autorSucheL;
	}

	public JLabel getVerlagSucheL() {
		return verlagSucheL;
	}

	public void setVerlagSucheL(JLabel verlagSucheL) {
		this.verlagSucheL = verlagSucheL;
	}

	public JLabel getSignaturSucheL() {
		return signaturSucheL;
	}

	public void setSignaturSucheL(JLabel signaturSucheL) {
		this.signaturSucheL = signaturSucheL;
	}

	public JLabel getStatusSucheL() {
		return statusSucheL;
	}

	public void setStatusSucheL(JLabel statusSucheL) {
		this.statusSucheL = statusSucheL;
	}

	public JTextField getBarcodeSucheT() {
		return barcodeSucheT;
	}

	public void setBarcodeSucheT(JTextField barcodeSucheT) {
		this.barcodeSucheT = barcodeSucheT;
	}

	public JComboBox<Autor> getAutorSucheCbx() {
		return autorSucheCbx;
	}

	public void setAutorSucheCbx(JComboBox<Autor> autorSucheCbx) {
		this.autorSucheCbx = autorSucheCbx;
	}

	public JComboBox<Verlag> getVerlagSucheCbx() {
		return verlagSucheCbx;
	}

	public void setVerlagSucheCbx(JComboBox<Verlag> verlagSucheCbx) {
		this.verlagSucheCbx = verlagSucheCbx;
	}

	public JTextField getSignaturSucheT() {
		return signaturSucheT;
	}

	public void setSignaturSucheT(JTextField signaturSucheT) {
		this.signaturSucheT = signaturSucheT;
	}

	public JComboBox<Status> getStatusSucheCbx() {
		return statusSucheCbx;
	}

	public void setStatusSucheCbx(JComboBox<Status> statusSucheCbx) {
		this.statusSucheCbx = statusSucheCbx;
	}

	public JTable getBuchTabelle() {
		return buchTabelle;
	}

	public void setBuchTabelle(JTable buchTabelle) {
		this.buchTabelle = buchTabelle;
	}

	public LinkedHashMap<JLabel, JComponent> getComponentsSuche() {
		return componentsSuche;
	}

	public void setComponentsSuche(LinkedHashMap<JLabel, JComponent> componentsSuche) {
		this.componentsSuche = componentsSuche;
	}

	public JLabel getTitelSucheL() {
		return titelSucheL;
	}

	public void setTitelSucheL(JLabel titelSucheL) {
		this.titelSucheL = titelSucheL;
	}

	public JTextField getTitelSucheT() {
		return titelSucheT;
	}

	public void setTitelSucheT(JTextField titelSucheT) {
		this.titelSucheT = titelSucheT;
	}

	public JButton getSuchButton() {
		return suchButton;
	}

	public void setSuchButton(JButton suchButton) {
		this.suchButton = suchButton;
	}

}
