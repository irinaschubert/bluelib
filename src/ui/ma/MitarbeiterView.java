package ui.ma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ui.benutzer.FormularMitGridbaglayout;
import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * Zeigt alle Mitarbeiter an und ermoeglicht die Erfassung neuer Mitarbeiter
 * 
 * @version 0.1 24.11.2018
 * @author Mike
 *
 */
public class MitarbeiterView extends JPanel {
	private JPanel MitarbeiterNeuBearbeitenPanel;
	private JPanel MitarbeiterSuchePanel;
	private StandardButtonPanel buttonPanel;
	private JPanel centerPanel;

	private JLabel PKL;
	private JLabel NameL;
	private JLabel VornameL;
	private JLabel BenutzernameL;
	private JLabel PasswortL;
	private JLabel aktivL;
	private JLabel adminL;
	private JLabel maIDL;
	
	private JLabel NameSucheL;
	private JLabel VornameSucheL;
	private JLabel BenutzernameSucheL;
	private JLabel aktivSucheL;
	private JLabel adminSucheL;
	
	private JLabel neuAendernL;

	private JTextField PKT;
	private JTextField NameT;
	private JTextField NameSucheT;
	private JTextField VornameT;
	private JPasswordField PasswortT;
	private JTextField VornameSucheT;
	private JTextField BenutzernameT;
	private JTextField BenutzernameSucheT;
	private JTextField maIDT;

	private JCheckBox aktivCbx;
	private JCheckBox aktivSucheCbx;
	private JCheckBox adminCbx;
	private JCheckBox adminSucheCbx;
	private JButton suchButton;
	private JTable MitarbeiterTabelle;

	private static int HOEHE = 600;
	private static int BREITE = 550;

	public MitarbeiterView(String panelTitel) {
		suchButton = new JButton();
		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		centerPanel.add(createNeuesMitarbeiterPanel(), BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		// Titel des Panels
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		// Definiert die Grösse des Panels. Die HauptView passt sich an
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	/**
	 * 
	 * @return JPanel
	 */
	private JPanel createNeuesMitarbeiterPanel() {
		MitarbeiterNeuBearbeitenPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		MitarbeiterNeuBearbeitenPanel.setLayout(gridBagLayout);
		FormularMitGridbaglayout gridBagHelfer = new FormularMitGridbaglayout();

		PKL = new JLabel();
		PKT = new JTextField();
		PKT.setEditable(false);
		PKT.setFont(PKT.getFont().deriveFont(12f));

		NameL = new JLabel();
		NameT = new JTextField(10);

		VornameL = new JLabel();
		VornameT = new JTextField();

		aktivL = new JLabel();
		aktivCbx = new JCheckBox();

		neuAendernL = new JLabel();
		neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		neuAendernL.setFont(PKT.getFont().deriveFont(Font.BOLD, 14f));
		neuAendernL.setForeground(Color.red);

		BenutzernameL = new JLabel();
		BenutzernameT = new JTextField(10);

		PasswortL = new JLabel();
		PasswortT = new JPasswordField();

		adminL = new JLabel();
		adminCbx = new JCheckBox();
		
		maIDL = new JLabel();
		maIDT = new JTextField();
		maIDL.setVisible(false);
		maIDT.setVisible(false);

		gridBagHelfer.labelSetzen(PKL, MitarbeiterNeuBearbeitenPanel, 0, 0);
		gridBagHelfer.feldSetzen(PKT, MitarbeiterNeuBearbeitenPanel, 1, 0);
		gridBagHelfer.labelSetzen(NameL, MitarbeiterNeuBearbeitenPanel, 0, 1);
		gridBagHelfer.feldSetzen(NameT, MitarbeiterNeuBearbeitenPanel, 1, 1);
		gridBagHelfer.labelSetzen(VornameL, MitarbeiterNeuBearbeitenPanel, 0, 2);
		gridBagHelfer.feldSetzen(VornameT, MitarbeiterNeuBearbeitenPanel, 1, 2);
		gridBagHelfer.labelSetzen(aktivL, MitarbeiterNeuBearbeitenPanel, 0, 3);
		gridBagHelfer.feldSetzen(aktivCbx, MitarbeiterNeuBearbeitenPanel, 1, 3);

		gridBagHelfer.feldSetzenLang(neuAendernL, MitarbeiterNeuBearbeitenPanel, 2, 0);

		gridBagHelfer.labelSetzen(BenutzernameL, MitarbeiterNeuBearbeitenPanel, 2, 1);
		gridBagHelfer.feldSetzen(BenutzernameT, MitarbeiterNeuBearbeitenPanel, 3, 1);
		gridBagHelfer.labelSetzen(PasswortL, MitarbeiterNeuBearbeitenPanel, 2, 2);
		gridBagHelfer.feldSetzen(PasswortT, MitarbeiterNeuBearbeitenPanel, 3, 2);
		gridBagHelfer.labelSetzen(adminL, MitarbeiterNeuBearbeitenPanel, 2, 3);
		gridBagHelfer.feldSetzen(adminCbx, MitarbeiterNeuBearbeitenPanel, 3, 3);

		return rahmenSetzen("Neu / Bearbeiten", MitarbeiterNeuBearbeitenPanel);
	}

	private JPanel createTabellenPanel() {
		MitarbeiterTabelle = new JTable(); // Panel für die Tabelle
		MitarbeiterTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt werden
		JScrollPane scroll = new JScrollPane(MitarbeiterTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Mitarbeitere:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		MitarbeiterSuchePanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		MitarbeiterSuchePanel.setLayout(gridBagLayout);
		FormularMitGridbaglayout gridBagHelfer = new FormularMitGridbaglayout();
		
		
		NameSucheL = new JLabel();
		NameSucheT = new JTextField();

		VornameSucheL = new JLabel();
		VornameSucheT = new JTextField();

		BenutzernameSucheL = new JLabel();
		BenutzernameSucheT = new JTextField();
		
		aktivSucheL = new JLabel();
		aktivSucheCbx = new JCheckBox();
		
		adminSucheL = new JLabel();
		adminSucheCbx = new JCheckBox();
		
		suchButton = new JButton();
		
		gridBagHelfer.labelSetzen(NameSucheL, MitarbeiterSuchePanel, 0, 0);
		gridBagHelfer.feldSetzenLang(NameSucheT, MitarbeiterSuchePanel, 1, 0);
		gridBagHelfer.labelSetzen(VornameSucheL, MitarbeiterSuchePanel, 0, 1);
		gridBagHelfer.feldSetzenLang(VornameSucheT, MitarbeiterSuchePanel, 1, 1);
		gridBagHelfer.labelSetzen(BenutzernameSucheL, MitarbeiterSuchePanel, 0, 2);
		gridBagHelfer.feldSetzenLang(BenutzernameSucheT, MitarbeiterSuchePanel, 1, 2);
		gridBagHelfer.labelSetzen(aktivSucheL, MitarbeiterSuchePanel, 0, 3);
		gridBagHelfer.feldSetzen(aktivSucheCbx, MitarbeiterSuchePanel, 1, 3);
		//gridBagHelfer.labelSetzen(adminSucheL, MitarbeiterSuchePanel, 0, 4);
		//gridBagHelfer.feldSetzen(adminSucheCbx, MitarbeiterSuchePanel, 1, 4);
		
		gridBagHelfer.labelSetzen(suchButton, MitarbeiterSuchePanel, 2, 3);
		
		return rahmenSetzen("Suche", MitarbeiterSuchePanel);
	}

	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder(BorderFactory.createTitledBorder(rahmentitel));
		rahmenPanel.add(inhalt);
		return rahmenPanel;
	}

	public void spaltenBreiteSetzen() {
		MitarbeiterTabelle.getColumnModel().getColumn(0).setPreferredWidth(40); // Name (Person)
		MitarbeiterTabelle.getColumnModel().getColumn(1).setPreferredWidth(40); // Vorname (Person)
		MitarbeiterTabelle.getColumnModel().getColumn(2).setPreferredWidth(40); // Mitarbeiter
		MitarbeiterTabelle.getColumnModel().getColumn(3).setMaxWidth(50); // Aktiv
		MitarbeiterTabelle.getColumnModel().getColumn(4).setMaxWidth(50); // Admin
	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getNeuerMitarbeiterPanel() {
		return MitarbeiterNeuBearbeitenPanel;
	}

	public JLabel getNameSucheL() {
		return NameSucheL;
	}

	public JTextField getNameSucheT() {
		return NameSucheT;
	}

	public JLabel getNameL() {
		return NameL;
	}

	public JTextField getNameT() {
		return NameT;
	}

	public JLabel getVornameSucheL() {
		return VornameSucheL;
	}

	public JTextField getVornameSucheT() {
		return VornameSucheT;
	}

	public JLabel getVornameL() {
		return VornameL;
	}

	public JTextField getVornameT() {
		return VornameT;
	}

	public JLabel getBenutzernameL() {
		return BenutzernameL;
	}

	public JTextField getBenutzernameT() {
		return BenutzernameT;
	}
	
	public JLabel getPasswortL() {
		return PasswortL;
	}

	public JPasswordField getPasswortT() {
		return PasswortT;
	}
	
	public void setPasswortT(JPasswordField PasswortT) {
		this.PasswortT = PasswortT;
	}
	
	public JTextField getMAIDT() {
		return maIDT;
	}

	public JLabel getMAIDL() {
		return maIDL;
	}
	
	public void setMAIDL(JLabel maIDL) {
		this.maIDL = maIDL;
	}
	
	public void setMAIDT(JTextField maIDT) {
		this.maIDT = maIDT;
	}

	public JTextField getPKT() {
		return PKT;
	}

	public JLabel getPKL() {
		return PKL;
	}

	public JTable getMitarbeiterTabelle() {
		return MitarbeiterTabelle;
	}

	public JLabel getBenutzernameSucheL() {
		return BenutzernameSucheL;
	}

	public void setBenutzernameSucheL(JLabel BenutzernameSucheL) {
		this.BenutzernameSucheL = BenutzernameSucheL;
	}

	public JTextField getMitarbeiterSucheT() {
		return BenutzernameSucheT;
	}

	public void setMitarbeiterucheT(JTextField BenutzernameSucheT) {
		this.BenutzernameSucheT = BenutzernameSucheT;
	}

	public JButton getSuchButton() {
		return suchButton;
	}

	public void setSuchButton(JButton suchButton) {
		this.suchButton = suchButton;
	}

	public JLabel getAktiv() {
		return aktivL;
	}

	public JLabel getAdminL() {
		return adminL;
	}

	public JCheckBox getAdminCbx() {
		return adminCbx;
	}

	public void setAdminCbx(JCheckBox adminCbx) {
		this.adminCbx = adminCbx;
	}

	public void setAktiv(JLabel aktiv) {
		this.aktivL = aktiv;
	}

	public JLabel getAktivSucheL() {
		return aktivSucheL;
	}

	public void setAktivSucheL(JLabel aktivSucheL) {
		this.aktivSucheL = aktivSucheL;
	}

	public JCheckBox getAktivCbx() {
		return aktivCbx;
	}

	public void setaktivCbx(JCheckBox aktivCbx) {
		this.aktivCbx = aktivCbx;
	}

	public JCheckBox getAktivSucheCbx() {
		return aktivSucheCbx;
	}

	public void setAktivSucheCbx(JCheckBox aktivSucheCbx) {
		this.aktivSucheCbx = aktivSucheCbx;
	}

	public JLabel getNeuAendernL() {
		return neuAendernL;
	}

	public void setNeuAendernL(JLabel neuAendernL) {
		this.neuAendernL = neuAendernL;
	}

	public JCheckBox getAdminSucheCbx() {
		return adminSucheCbx;
	}

	public JLabel getAdminSucheL() {
		return adminSucheL;
	}

}
