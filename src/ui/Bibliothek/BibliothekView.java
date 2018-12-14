package ui.Bibliothek;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domain.Ort;
import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

import javax.swing.JComboBox;

/**
 * Zeigt die Bibliotheksdaten. Die Ausleihdauer ist veränderbar.
 * 
 * @version 1.0 3.10.2018
 * @author Mike
 *
 */
public class BibliothekView extends JPanel {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel bibliPanel;
	private JPanel centerPanel;
	private JLabel nameL;
	private JLabel strasseUndNrL;
	private JLabel plzOrtL;
	private JLabel emailL;
	private JLabel telL;
	private JLabel leihfristL;
	private JTextField nameT;
	private JTextField strasseUndNrT;
	private JComboBox<Ort> plzOrtCbx;
	private JTextField emailT;
	private JTextField telT;
	private JTextField leihfristT;
	private LinkedHashMap<JLabel, JComponent> componentTable = new LinkedHashMap<>();
	private static int HOEHE = 350;
	private static int BREITE = 500;

	public BibliothekView(String frameTitel) {

		buttonPanel = new StandardButtonPanel();

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(createBibliothekPanel(), BorderLayout.NORTH);

		this.setLayout(new BorderLayout());
		this.add(new StandardTitelPanel(frameTitel), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}

	/**
	 * Befüllt LinkedHashMap mit Labels und Input-Feldern und gibt die Felder in ein
	 * JPanel aus Vorteil: Es können beliebig Felder hinzugefügt werden.
	 * 
	 * @return JPanel
	 */
	private JPanel createBibliothekPanel() {

		bibliPanel = new JPanel();
		bibliPanel.setLayout(new BorderLayout());

		componentTable.put(nameL = new JLabel(), nameT = new JTextField());
		componentTable.put(strasseUndNrL = new JLabel(), strasseUndNrT = new JTextField());
		componentTable.put(plzOrtL = new JLabel(), plzOrtCbx = new JComboBox());
		componentTable.put(emailL = new JLabel(), emailT = new JTextField());
		componentTable.put(telL = new JLabel(), telT = new JTextField());
		componentTable.put(leihfristL = new JLabel(), leihfristT = new JTextField());

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(componentTable.size(), 0));
		for (JLabel e : componentTable.keySet()) {
			labelPanel.add(e);
		}

		labelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(componentTable.size(), 0));
		for (JComponent e : componentTable.values()) {
			inputPanel.add(e);
		}

		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		bibliPanel.add(labelPanel, BorderLayout.WEST);
		bibliPanel.add(inputPanel, BorderLayout.CENTER);

		return bibliPanel;

	}

	public StandardButtonPanel getButton() {
		return this.buttonPanel;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public JPanel getbibliPanel() {
		return bibliPanel;
	}

	public JLabel getNameL() {
		return nameL;
	}

	public JTextField getNameT() {
		return nameT;
	}

	public JLabel getStrasseUndNrL() {
		return strasseUndNrL;
	}

	public JTextField getStrasseUndNrT() {
		return strasseUndNrT;
	}

	public JLabel getOrtL() {
		return plzOrtL;
	}

	public JComboBox getPlzOrtCbx() {
		return plzOrtCbx;
	}

	public JLabel getEmailL() {
		return emailL;
	}

	public JTextField getEmailT() {
		return emailT;
	}

	public JLabel getTelL() {
		return telL;
	}

	public JTextField getTelT() {
		return telT;
	}

	public JLabel getLeihfristL() {
		return leihfristL;
	}

	public JTextField getLeihfristT() {
		return leihfristT;
	}

	public void schliessen() {
		frame.dispose();
	}

}
