package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Zeigt die Bibliotheksdaten. Die Ausleihdauer ist veränderbar.
 * 
 * @version 1.0 3.10.2018
 * @author Mike
 *
 */
public class BibliothekView {
	private JFrame frame;
	private StandardButtonPanel buttonPanel;
	private JPanel bibliPanel;
	private JPanel centerPanel;
	private JLabel nameL;
	private JLabel strasseUndNrL;
	private JLabel ortL;
	private JLabel emailL;
	private JLabel telL;
	private JLabel leihfristL;
	private JTextField nameT;
	private JTextField strasseUndNrT;
	private JTextField ortT;
	private JTextField emailT;
	private JTextField telT;
	private JTextField leihfristT;
	private LinkedHashMap<JLabel, JComponent> componentTable = new LinkedHashMap<>();

	public BibliothekView(String frameTitel) {

		buttonPanel = new StandardButtonPanel();
		bibliPanel = createBibliothekPanel();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		centerPanel.add(bibliPanel, BorderLayout.SOUTH);

		frame = new JFrame("View");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setVisible(true);

		frame.getContentPane().add(new StandardTitelPanel(frameTitel), BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
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
		componentTable.put(ortL = new JLabel(), ortT = new JTextField());
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
		return ortL;
	}

	public JTextField getOrtT() {
		return ortT;
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

	public LinkedHashMap<JLabel, JComponent> getComponents() {
		return componentTable;
	}

	public void schliessen() {
		frame.dispose();
	}

}
