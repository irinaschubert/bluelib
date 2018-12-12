package ui.benutzer;

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
import javax.swing.JFrame;
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
import ui.renderer.AutorRenderer;
import ui.renderer.VerlagRenderer;
import ui.standardKomponenten.StandardButtonPanel;
import ui.status.StatusRenderer;

/**
 * View für die Suche nach Benutzern in Ausleihe
 * 
 * @version 1.0 06.11.2018
 * @author Irina
 * 
 */
@SuppressWarnings("serial")
public class BenutzerSuchView extends JPanel {
	private StandardButtonPanel buttonPanel;
	
	private JFrame frame;
	private JPanel benutzerSuchenPanel;
	private JPanel benutzerListe;

	private JLabel PKSucheL;
	private JLabel vornameSucheL;
	private JLabel nachnameSucheL;

	private JTextField PKSucheT;
	private JTextField vornameSucheT;
	private JTextField nachnameSucheT;

	private JButton suchButton;
	private JTable benutzerTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();

	public BenutzerSuchView(String panelTitel) {
		
		suchButton = new JButton();

		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(createSuchePanel(), BorderLayout.NORTH);
		this.add(createTabellenPanel(), BorderLayout.CENTER);
	}

	private JPanel createTabellenPanel() {		
		benutzerTabelle = new JTable();
		benutzerTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Benutzer:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(new JScrollPane(benutzerTabelle));
		tabellenPanel.setPreferredSize(new Dimension(680, 200));
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();
		suchPanel.setLayout(new GridBagLayout());

		componentsSuche.put(PKSucheL = new JLabel(), PKSucheT = new JTextField());
		componentsSuche.put(vornameSucheL = new JLabel(), vornameSucheT = new JTextField());
		componentsSuche.put(nachnameSucheL = new JLabel(), nachnameSucheT = new JTextField());

		formularHelfer.labelSetzen(PKSucheL, suchPanel, 0, 0);
		formularHelfer.feldSetzen(componentsSuche.get(PKSucheL), suchPanel, 1, 0);

		formularHelfer.labelSetzen(vornameSucheL, suchPanel, 0, 1);
		formularHelfer.feldSetzen(componentsSuche.get(vornameSucheL), suchPanel, 1, 1);

		formularHelfer.labelSetzen(nachnameSucheL, suchPanel, 0, 2);
		formularHelfer.feldSetzen(componentsSuche.get(nachnameSucheL), suchPanel, 1, 2);

		formularHelfer.labelSetzenMitAnker(suchButton, suchPanel, 2, 2, GridBagConstraints.CENTER);

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

	public JPanel getBenutzerSuchenPanel() {
		return benutzerSuchenPanel;
	}

	public JPanel getBenutzerListe() {
		return benutzerListe;
	}

	public JTable getBenutzerTabelle() {
		return benutzerTabelle;
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
