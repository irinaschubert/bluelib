package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Autor;
import domain.Schlagwort;
import domain.Status;
import domain.Verlag;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
	
	private JLabel PKL;
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
	private JLabel buchTypL;
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
	
	private JLabel neuAendernL;

	private JButton suchButton;
	private JTable buchTabelle;
	private LinkedHashMap<JLabel, JComponent> componentsSuche = new LinkedHashMap<>();
	private List<JComponent> componentsNeuBearbeiten = new ArrayList<>();
	private static int HOEHE = 900;
	private static int BREITE = 800;

	public BuchView(String panelTitel) {

		neuAendernL = new JLabel();
		//neuAendernL.setHorizontalAlignment(SwingConstants.CENTER);
		suchButton = new JButton();

		buttonPanel = new StandardButtonPanel(); // Button-Panel am unteren Rand des Panels

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel development = createNeuesBuchPanel();
		centerPanel.add(createSuchePanel(), BorderLayout.NORTH);
		centerPanel.add(createTabellenPanel(), BorderLayout.CENTER);
		
		//centerPanel.add(createNeuesBuchPanel(), BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		// Titel des Panels
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		// Definiert die Grösse des Panels. Die HauptView passt sich an
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	/**
	 * Befüllt LinkedHashMap mit Labels und Input-Feldern und gibt die Felder in ein
	 * JPanel aus Vorteil: Es können beliebig Felder hinzugefügt werden.
	 * 
	 * @return JPanel
	 */
	private JPanel createNeuesBuchPanel() {

		buchNeuBearbeitenPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		buchNeuBearbeitenPanel.setLayout(gridBagLayout);
		GridBagHelfer gridBagHelfer = new GridBagHelfer();
		
		PKL = new JLabel();
		PKT = new JTextField();
		
		barcodeL = new JLabel();
		barcodeT = new JTextField();
		
		erfassenBarcodeB = new JButton();
		
		titelL = new JLabel();
		titelT = new JTextField();
		
		verlagL = new JLabel();
		verlagCbx = new JComboBox<Verlag>();
		
		auflageL = new JLabel();
		auflageT = new JTextField();
		
		reiheL = new JLabel();
		reiheT  = new JTextField();
		
		anzahlSeitenL = new JLabel();
		anzahlSeitenT =  new JTextField();
		
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
		
		autorL = new JLabel();
		autorCbx = new JComboBox<>();
		
		zuweisenAutorB = new JButton();
		entfernenAutorB = new JButton();
		
		JPanel signatur = new JPanel();
		signatur = rahmenSetzen("Signatur", signatur);
		signatur.setLayout(new GridBagLayout());
		belletristikR = new JRadioButton();
		sachbuchR = new JRadioButton();
		buchtypG = new ButtonGroup();
		buchtypG.add(belletristikR);
		buchtypG.add(sachbuchR);
		signaturT = new JTextField();
	
		
		gridBagHelfer.labelSetzen(belletristikR, signatur, 0, 0);
		gridBagHelfer.labelSetzen(sachbuchR, signatur, 0, 1);
		gridBagHelfer.feldSetzen(signaturT, signatur, 1, 1);
		
		
		schlagwortL = new JLabel();
		schlagwortCbx = new JComboBox<>();
		
		autorList = new JList<>();
		JScrollPane scrollPaneAutorList = new JScrollPane(autorList);
		scrollPaneAutorList.setPreferredSize(new Dimension(250, 10));
		
		JPanel test = new JPanel(new FlowLayout());
		test.add(scrollPaneAutorList, BorderLayout.CENTER);
		test.setMaximumSize(new Dimension(5, 5));
		
		
		zuweisenSchlagwortB = new JButton();
		entferntenSchlagwortB = new JButton();
		
		schlagwortList = new JList<>();
		JScrollPane scrollPaneSchlagwortList = new JScrollPane(schlagwortList);
		scrollPaneSchlagwortList.setPreferredSize(new Dimension(250, 10));
		
		notizL = new JLabel();
		notizA = new JTextArea(10,10);
		JScrollPane scrollPaneNotiz = new JScrollPane();
		scrollPaneNotiz.setViewportView(notizA);
		scrollPaneNotiz.setPreferredSize(new Dimension(250, 50));
		
		
		erfassungsDatumL = new JLabel();
		erfassungsDatumT = new JTextField();
		
		erfassungsUserL = new JLabel();
		erfassungsUserT = new JTextField();
		
	
				
		componentsNeuBearbeiten.add(PKT);
		componentsNeuBearbeiten.add(barcodeT);
		componentsNeuBearbeiten.add(barcodeT);
		componentsNeuBearbeiten.add(titelT);
		componentsNeuBearbeiten.add(verlagCbx);
		componentsNeuBearbeiten.add(auflageT);
		componentsNeuBearbeiten.add(reiheT);
		componentsNeuBearbeiten.add(anzahlSeitenT);
		componentsNeuBearbeiten.add(preisT);
		componentsNeuBearbeiten.add(jahrT);
		componentsNeuBearbeiten.add(ortT);
		componentsNeuBearbeiten.add(isbnT);
		componentsNeuBearbeiten.add(statusCbx);
		componentsNeuBearbeiten.add(autorCbx);
		componentsNeuBearbeiten.add(autorList);
		componentsNeuBearbeiten.add(schlagwortCbx);
		componentsNeuBearbeiten.add(signaturT);
		componentsNeuBearbeiten.add(schlagwortList);
		componentsNeuBearbeiten.add(notizA);
		componentsNeuBearbeiten.add(erfassungsDatumT);
		componentsNeuBearbeiten.add(erfassungsUserT);
		
		
		
		
		gridBagHelfer.labelSetzen(PKL, buchNeuBearbeitenPanel, 0, 0);
		gridBagHelfer.feldSetzen(PKT, buchNeuBearbeitenPanel, 1, 0);
		gridBagHelfer.labelSetzen(barcodeL, buchNeuBearbeitenPanel, 0, 1);
		gridBagHelfer.feldSetzen(barcodeT, buchNeuBearbeitenPanel, 1, 1);
		gridBagHelfer.labelSetzen(erfassenBarcodeB, buchNeuBearbeitenPanel, 3, 1);
		gridBagHelfer.labelSetzen(titelL, buchNeuBearbeitenPanel, 0, 2);
		gridBagHelfer.feldSetzenLang(titelT, buchNeuBearbeitenPanel, 1, 2);
		gridBagHelfer.labelSetzen(verlagL, buchNeuBearbeitenPanel, 0, 3);
		gridBagHelfer.feldSetzenLang(verlagCbx, buchNeuBearbeitenPanel, 1, 3);
		gridBagHelfer.labelSetzen(auflageL, buchNeuBearbeitenPanel, 0, 4);
		gridBagHelfer.feldSetzen(auflageT, buchNeuBearbeitenPanel, 1, 4);
		gridBagHelfer.labelSetzen(reiheL, buchNeuBearbeitenPanel, 3, 4);
		gridBagHelfer.feldSetzen(reiheT, buchNeuBearbeitenPanel, 4, 4);
		gridBagHelfer.labelSetzen(anzahlSeitenL, buchNeuBearbeitenPanel, 0, 5);
		gridBagHelfer.feldSetzen(anzahlSeitenT, buchNeuBearbeitenPanel, 1, 5);
		gridBagHelfer.labelSetzen(preisL, buchNeuBearbeitenPanel, 3, 5);
		gridBagHelfer.feldSetzen(preisT, buchNeuBearbeitenPanel, 4, 5);
		gridBagHelfer.labelSetzen(jahrL, buchNeuBearbeitenPanel, 3, 6);
		gridBagHelfer.feldSetzen(jahrT, buchNeuBearbeitenPanel, 4, 6);
		gridBagHelfer.labelSetzen(ortL, buchNeuBearbeitenPanel, 3, 8);
		gridBagHelfer.feldSetzen(ortT, buchNeuBearbeitenPanel, 4, 8);
		gridBagHelfer.labelSetzen(isbnL, buchNeuBearbeitenPanel, 3, 7);
		gridBagHelfer.feldSetzen(isbnT, buchNeuBearbeitenPanel, 4, 7);
		gridBagHelfer.labelSetzen(statusL, buchNeuBearbeitenPanel, 3, 9);
		gridBagHelfer.feldSetzen(statusCbx, buchNeuBearbeitenPanel, 4, 9);
		gridBagHelfer.labelSetzen(autorL, buchNeuBearbeitenPanel, 0, 6);
		gridBagHelfer.feldSetzen(autorCbx, buchNeuBearbeitenPanel, 1, 6);
		gridBagHelfer.labelSetzen(zuweisenAutorB, buchNeuBearbeitenPanel, 0, 7);
		gridBagHelfer.feldSetzenHoch(scrollPaneAutorList, buchNeuBearbeitenPanel, 1, 7, 2);
		gridBagHelfer.labelSetzen(entfernenAutorB, buchNeuBearbeitenPanel, 0, 8);
		gridBagHelfer.labelSetzen(schlagwortL, buchNeuBearbeitenPanel, 0, 9);
		gridBagHelfer.feldSetzen(schlagwortCbx, buchNeuBearbeitenPanel, 1, 9);
		gridBagHelfer.feldSetzenBreitHoch(signatur, buchNeuBearbeitenPanel, 3, 10, 2, 2);
		gridBagHelfer.labelSetzen(zuweisenSchlagwortB, buchNeuBearbeitenPanel, 0, 10);
		gridBagHelfer.labelSetzen(entferntenSchlagwortB, buchNeuBearbeitenPanel, 0, 11);
		gridBagHelfer.feldSetzenHoch(scrollPaneSchlagwortList, buchNeuBearbeitenPanel, 1, 10, 2);
		gridBagHelfer.labelSetzen(notizL, buchNeuBearbeitenPanel, 0, 12);
		gridBagHelfer.feldSetzenBreitHoch(scrollPaneNotiz,buchNeuBearbeitenPanel, 1, 12, 2, 4);
		gridBagHelfer.labelSetzen(erfassungsDatumL, buchNeuBearbeitenPanel, 0, 14);
		gridBagHelfer.feldSetzen(erfassungsDatumT, buchNeuBearbeitenPanel, 1, 14);
		gridBagHelfer.labelSetzen(erfassungsUserL, buchNeuBearbeitenPanel, 3, 14);
		gridBagHelfer.feldSetzen(erfassungsUserT, buchNeuBearbeitenPanel, 4, 14);

		
		
//		JPanel autorPanel = new JPanel();
//		autorPanel.setLayout(BoxLayout(autorPanel, BoxLayout.));
		
		
		
		return rahmenSetzen("Neu / Bearbeiten", buchNeuBearbeitenPanel);
//
	}

	

	private JPanel createTabellenPanel() {
		buchTabelle = new JTable(); // Panel für die Tabelle
		buchTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Nur eine Zeile darf ausgewaehlt werden
		JScrollPane scroll = new JScrollPane(buchTabelle);

		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BoxLayout(tabellenPanel, BoxLayout.Y_AXIS));
		JLabel tabellenTitel = new JLabel("Gefundene Bücher:");
		tabellenPanel.add(tabellenTitel);
		tabellenPanel.add(scroll);
		return tabellenPanel;
	}

	private JPanel createSuchePanel() {
		JPanel suchPanel = new JPanel();
		FormularMitGridbaglayoutTemp formularHelfer = new FormularMitGridbaglayoutTemp();
		//suchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		suchPanel.setLayout(new GridBagLayout());
		
		verlagSucheCbx = new JComboBox<Verlag>();
		verlagSucheCbx.setRenderer(new VerlagRenderer());
		autorSucheCbx = new JComboBox<Autor>();
		autorSucheCbx.setRenderer(new AutorRenderer());
		statusSucheCbx = new JComboBox<Status>();
		statusSucheCbx.setRenderer(new StatusRenderer());

		componentsSuche.put(barcodeSucheL = new JLabel(), barcodeSucheT = new JTextField());
		componentsSuche.put(titelSucheL = new JLabel(), titelSucheT = new JTextField());
		componentsSuche.put(autorSucheL = new JLabel(), autorSucheCbx );
		componentsSuche.put(verlagSucheL = new JLabel(), verlagSucheCbx);
		componentsSuche.put(signaturSucheL = new JLabel(), signaturSucheT = new JTextField());
		componentsSuche.put(statusSucheL = new JLabel(), statusSucheCbx);

		//public void komponentenPlatzieren(JComponent comp, JPanel panel, int x, int y, int w, int h, int fill, int weight)
		
		
		int counter = 0;
//		for (JLabel e : componentsSuche.keySet()) {
//			komponentenPlatzieren(e, suchPanel, 0, counter++, -1, -1, GridBagConstraints.HORIZONTAL, 0);
//			
//		}
		
		
		
		
		counter = 0;
//		komponentenPlatzieren(componentsSuche.get(barcodeSucheL), suchPanel, 1, counter++, 1,1, GridBagConstraints.LINE_START,0);
//		komponentenPlatzieren(componentsSuche.get(titelSucheL), suchPanel, 1, counter++, 1,-1, GridBagConstraints.HORIZONTAL,1);
//		komponentenPlatzieren(componentsSuche.get(autorSucheL), suchPanel, 1, counter++, 1,-1, GridBagConstraints.HORIZONTAL,1);
//		komponentenPlatzieren(componentsSuche.get(verlagSucheL), suchPanel, 1, counter++, 1,-1, GridBagConstraints.HORIZONTAL,1);
//		komponentenPlatzieren(componentsSuche.get(signaturSucheL), suchPanel, 1, counter++, 1,-1, GridBagConstraints.HORIZONTAL,1);
//		komponentenPlatzieren(componentsSuche.get(statusSucheL), suchPanel, 1, counter++, 1,-1, GridBagConstraints.HORIZONTAL,1);

		formularHelfer.addLabel(barcodeSucheL, suchPanel);
		formularHelfer.addLastField(componentsSuche.get(barcodeSucheL), suchPanel);
		
		formularHelfer.addLabel(titelSucheL, suchPanel);
		formularHelfer.addLastField(componentsSuche.get(titelSucheL), suchPanel);
		
		formularHelfer.addLabel(autorSucheL, suchPanel);
		formularHelfer.addLastField(componentsSuche.get(autorSucheL), suchPanel);
		
		formularHelfer.addLabel(verlagSucheL, suchPanel);
		formularHelfer.addLastField(componentsSuche.get(verlagSucheL), suchPanel);
		
		formularHelfer.addLabel(signaturSucheL, suchPanel);
		formularHelfer.addLastField(componentsSuche.get(signaturSucheL), suchPanel);
		
		formularHelfer.addLabel(statusSucheL, suchPanel);
		formularHelfer.addMiddleField(componentsSuche.get(statusSucheL), suchPanel);
		
		formularHelfer.addLastField(suchButton, suchPanel);
		


		return rahmenSetzen("Suche", suchPanel);
	}
	
	 private class GridBagHelfer {
		 GridBagConstraints gbc = new GridBagConstraints();
		 
		 public void labelSetzen(JComponent comp, JPanel panel, int x, int y) {
			 gbc = new GridBagConstraints();
			  gbc.fill = GridBagConstraints.HORIZONTAL;
		        gbc.gridx = x;
		        gbc.gridy = y;
		        gbc.insets = new Insets(0, 10, 0, 10);
		        gbc.weightx = 0;
		        panel.add(comp, gbc);
		 }
		 
		 public void feldSetzen(JComponent comp, JPanel panel, int x, int y) {
			 gbc = new GridBagConstraints();
			 gbc.fill = GridBagConstraints.HORIZONTAL;
			 gbc.gridwidth = 1;
		        gbc.gridx = x;
		        gbc.gridy = y;
		        gbc.insets = new Insets(5, 0, 0, 10);
		        gbc.weightx = 1;
		        panel.add(comp, gbc);
		 }
		 
		 public void feldSetzenLang(JComponent comp, JPanel panel, int x, int y) {
			 GridBagConstraints gbc = new GridBagConstraints();
			 gbc.fill = GridBagConstraints.HORIZONTAL;
			 gbc.gridwidth = GridBagConstraints.REMAINDER;
		        gbc.gridx = x;
		        gbc.gridy = y;
		        gbc.insets = new Insets(5, 0, 0, 10);
		        gbc.weightx = 1;
		        panel.add(comp, gbc);
		 }
		 
		 public void feldSetzenHoch(JComponent comp, JPanel panel, int x, int y, int height) {
			 gbc = new GridBagConstraints();
			 gbc.fill = GridBagConstraints.VERTICAL;
			 gbc.anchor = GridBagConstraints.NORTHWEST;
			 gbc.gridheight = 2;
			 gbc.gridwidth = GridBagConstraints.REMAINDER;
		        gbc.gridx = x;
		        gbc.gridy = y;
		        gbc.insets = new Insets(5, 0, 0, 10);
		        gbc.weighty = 1;
		        gbc.weightx = 0.5;
		        panel.add(comp, gbc);
		 }
		 
		 public void feldSetzenHochSP2(JComponent comp, JPanel panel, int x, int y, int height) {
			 gbc = new GridBagConstraints();
			 gbc.fill = GridBagConstraints.VERTICAL;
			 gbc.anchor = GridBagConstraints.NORTHWEST;
			 gbc.gridheight = 2;
			 gbc.gridwidth = 1;
		        gbc.gridx = x;
		        gbc.gridy = y;
		        gbc.insets = new Insets(5, 0, 0, 10);
		        gbc.weighty = 1;
		        gbc.weightx = 0.5;
		        panel.add(comp, gbc);
		 }
		 
		 public void feldSetzenBreitHoch(JComponent comp, JPanel panel, int x, int y, int height, int width) {
			 gbc = new GridBagConstraints();
			 gbc.fill = GridBagConstraints.BOTH;
			 gbc.anchor = GridBagConstraints.NORTH;
			 gbc.gridheight = height;
			 gbc.gridwidth = width;
		        gbc.gridx = x;
		        gbc.gridy = y;
		        gbc.insets = new Insets(5, 0, 0, 10);
		        gbc.weightx = 1;
		        panel.add(comp, gbc);
		 }
		 
		   }
	 
	 private class FormularMitGridbaglayoutTemp {
			/**
		     * Grid bag constraints for fields and labels
		     */
		    private GridBagConstraints lastConstraints = null;
		    private GridBagConstraints middleConstraints = null;
		    private GridBagConstraints labelConstraints = null;

		    public FormularMitGridbaglayoutTemp() {
		        // Set up the constraints for the "last" field in each 
		        // row first, then copy and modify those constraints.

		        // weightx is 1.0 for fields, 0.0 for labels
		        // gridwidth is REMAINDER for fields, 1 for labels
		        lastConstraints = new GridBagConstraints();

		        // Stretch components horizontally (but not vertically)
		        lastConstraints.fill = GridBagConstraints.HORIZONTAL;

		        // Components that are too short or narrow for their space
		        // Should be pinned to the northwest (upper left) corner
		        lastConstraints.anchor = GridBagConstraints.NORTHWEST;

		        // Give the "last" component as much space as possible
		        lastConstraints.weightx = 1.0;

		        // Give the "last" component the remainder of the row
		        lastConstraints.gridwidth = GridBagConstraints.REMAINDER;

		        // Add a little padding
		        lastConstraints.insets = new Insets(1, 1, 1, 1);

		        // Now for the "middle" field components
		        middleConstraints = 
		             (GridBagConstraints) lastConstraints.clone();

		        // These still get as much space as possible, but do
		        // not close out a row
		        middleConstraints.gridwidth = GridBagConstraints.RELATIVE;

		        // And finally the "label" constrains, typically to be
		        // used for the first component on each row
		        labelConstraints = 
		            (GridBagConstraints) lastConstraints.clone();

		        // Give these as little space as necessary
		        labelConstraints.weightx = 0.0;
		        labelConstraints.gridwidth = 1;
		    }

		    /**
		     * Adds a field component. Any component may be used. The 
		     * component will be stretched to take the remainder of 
		     * the current row.
		     */
		    public void addLastField(Component c, Container parent) {
		        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
		        gbl.setConstraints(c, lastConstraints);
		        parent.add(c);
		    }
		    
		    /**
		     * Adds an arbitrary label component, starting a new row
		     * if appropriate. The width of the component will be set
		     * to the minimum width of the widest component on the
		     * form.
		     */
		    public void addLabel(Component c, Container parent) {
		        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
		        gbl.setConstraints(c, labelConstraints);
		        parent.add(c);
		    }

		    /**
		     * Adds a JLabel with the given string to the label column
		     */
		    public JLabel addLabel(String s, Container parent) {
		        JLabel c = new JLabel(s);
		        addLabel(c, parent);
		        return c;
		    }

		    /**
		     * Adds a "middle" field component. Any component may be 
		     * used. The component will be stretched to take all of
		     * the space between the label and the "last" field. All
		     * "middle" fields in the layout will be the same width.
		     */
		    public void addMiddleField(Component c, Container parent) {
		        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
		        gbl.setConstraints(c, middleConstraints);
		        parent.add(c);
		    }
		}


	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder(BorderFactory.createTitledBorder(rahmentitel));
		rahmenPanel.add(inhalt);
		return rahmenPanel;
	}

	public void spaltenBreiteSetzen() {

		buchTabelle.getColumnModel().getColumn(0).setPreferredWidth(30); // Name
		buchTabelle.getColumnModel().getColumn(1).setPreferredWidth(80); // Titel
		buchTabelle.getColumnModel().getColumn(2).setPreferredWidth(20); // Signatur
		buchTabelle.getColumnModel().getColumn(3).setPreferredWidth(40); // Autor
		buchTabelle.getColumnModel().getColumn(4).setPreferredWidth(30); // Verlag
	}


	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getNeuerAutorPanel() {
		return buchNeuBearbeitenPanel;
	}

	
	public JTextField getPKT() {
		return PKT;
	}

	public JLabel getPKL() {
		return PKL;
	}

	public JTable getBuchTabelle() {
		return buchTabelle;
	}

	public List<JComponent> getComponentsNeuBearbeiten() {
		return componentsNeuBearbeiten;
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

	public JLabel getBarcodeSucheL() {
		return barcodeSucheL;
	}

	public void setBarcodeSucheL(JLabel barcodeSucheL) {
		this.barcodeSucheL = barcodeSucheL;
	}

	public JLabel getTitelSucheL() {
		return titelSucheL;
	}

	public void setTitelSucheL(JLabel titelSucheL) {
		this.titelSucheL = titelSucheL;
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

	public JTextField getTitelSucheT() {
		return titelSucheT;
	}

	public void setTitelSucheT(JTextField titelSucheT) {
		this.titelSucheT = titelSucheT;
	}


	public JComboBox<Verlag> getVerlagSucheC() {
		return verlagSucheCbx;
	}

	public JTextField getSignaturSucheT() {
		return signaturSucheT;
	}

	public void setSignaturSucheT(JTextField signaturSucheT) {
		this.signaturSucheT = signaturSucheT;
	}


	public void setStatusSucheC(JComboBox<Status> statusSucheC) {
		this.statusSucheCbx = statusSucheC;
	}

	public void setPKL(JLabel pKL) {
		PKL = pKL;
	}

	public void setPKT(JTextField pKT) {
		PKT = pKT;
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

	public JLabel getAutoren() {
		return autorL;
	}

	public void setAutoren(JLabel autoren) {
		this.autorL = autoren;
	}

	public JLabel getBuchTypL() {
		return buchTypL;
	}

	public void setBuchTypL(JLabel buchTypL) {
		this.buchTypL = buchTypL;
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

	public JTextArea getNotizA() {
		return notizA;
	}

	public void setNotizA(JTextArea notizA) {
		this.notizA = notizA;
	}

	public LinkedHashMap<JLabel, JComponent> getComponentsSuche() {
		return componentsSuche;
	}

	public void setComponentsSuche(LinkedHashMap<JLabel, JComponent> componentsSuche) {
		this.componentsSuche = componentsSuche;
	}

	public void setButtonPanel(StandardButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public void setBuchTabelle(JTable buchTabelle) {
		this.buchTabelle = buchTabelle;
	}

	public void setComponentsNeuBearbeiten(List<JComponent> componentsNeuBearbeiten) {
		this.componentsNeuBearbeiten = componentsNeuBearbeiten;
	}

	public JComboBox<Schlagwort> getSchlagwortCbx() {
		return schlagwortCbx;
	}

	public void setSchlagwortCbx(JComboBox<Schlagwort> schlagwortCbx) {
		this.schlagwortCbx = schlagwortCbx;
	}

	public JTextField getReiheT() {
		return reiheT;
	}

	public void setReiheT(JTextField reiheT) {
		this.reiheT = reiheT;
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

	public JComboBox<Status> getStatusSucheCbx() {
		return statusSucheCbx;
	}

	public void setStatusSucheCbx(JComboBox<Status> statusSucheCbx) {
		this.statusSucheCbx = statusSucheCbx;
	}

	public JLabel getAutorL() {
		return autorL;
	}

	public void setAutorL(JLabel autorL) {
		this.autorL = autorL;
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

	public JTextField getErfassungsDatumT() {
		return erfassungsDatumT;
	}

	public void setErfassungsDatumT(JTextField erfassungsDatumT) {
		this.erfassungsDatumT = erfassungsDatumT;
	}
	
	
	
	

}
