package ui.benutzer;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Wird für komplexe Formulare benötigt.
 * 
 * @version 0.1 09.12.2018
 * @author irina
 */
public class FormularMitGridbaglayout {
	/**
	 * Grid bag constraints für Eingabefelder und Labels
	 */
	private GridBagConstraints lastConstraints = null;
	private GridBagConstraints middleConstraints = null;
	private GridBagConstraints labelConstraints = null;

	GridBagConstraints gbc = new GridBagConstraints();

	public FormularMitGridbaglayout() {
		lastConstraints = new GridBagConstraints();
		lastConstraints.fill = GridBagConstraints.HORIZONTAL;
		lastConstraints.anchor = GridBagConstraints.NORTHWEST;
		lastConstraints.weightx = 1.0;
		lastConstraints.gridwidth = GridBagConstraints.REMAINDER;
		lastConstraints.insets = new Insets(1, 1, 1, 1);
		middleConstraints = (GridBagConstraints) lastConstraints.clone();
		middleConstraints.gridwidth = GridBagConstraints.RELATIVE;
		labelConstraints = (GridBagConstraints) lastConstraints.clone();
		labelConstraints.weightx = 0.0;
		labelConstraints.gridwidth = 1;
	}

	public void addLastField(Component c, Container parent) {
		GridBagLayout gbl = (GridBagLayout) parent.getLayout();
		gbl.setConstraints(c, lastConstraints);
		parent.add(c);
	}

	public void addLabel(Component c, Container parent) {
		GridBagLayout gbl = (GridBagLayout) parent.getLayout();
		gbl.setConstraints(c, labelConstraints);
		parent.add(c);
	}

	public JLabel addLabel(String s, Container parent) {
		JLabel c = new JLabel(s);
		addLabel(c, parent);
		return c;
	}

	public void addMiddleField(Component c, Container parent) {
		GridBagLayout gbl = (GridBagLayout) parent.getLayout();
		gbl.setConstraints(c, middleConstraints);
		parent.add(c);
	}

	public void labelSetzen(JComponent comp, JPanel panel, int x, int y) {
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.weightx = 0;
		panel.add(comp, gbc);
	}

	public void labelSetzenMitAnker(JComponent comp, JPanel panel, int x, int y, int anker) {
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = anker;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.weightx = 0.5;
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
