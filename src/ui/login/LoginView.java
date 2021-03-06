package ui.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ui.standardKomponenten.StandardButtonPanel;
import ui.standardKomponenten.StandardTitelPanel;

/**
 * 
 * Stellt die Login-View bereit
 * 
 * @version 1.0 2018-11-07
 * @author Ueli
 *
 */
public class LoginView extends JPanel {
	private JLabel benutzerNameL;
	private JLabel passwortL;
	private JTextField benutzerNameT = new JTextField(15);
	private JPasswordField passwortP = new JPasswordField(15);
	private StandardButtonPanel buttonPanel = new StandardButtonPanel();
	private static int HOEHE = 300;
	private static int BREITE = 500;

	
	public LoginView(String panelTitel) {
		
		benutzerNameL = new JLabel();
		benutzerNameL.setBorder(new EmptyBorder(0, 0, 0, 10));
		passwortL = new JLabel();
		passwortL.setBorder(new EmptyBorder(0, 0, 0, 10));
		
		this.setLayout(new BorderLayout());
		this.add(new StandardTitelPanel(panelTitel), BorderLayout.NORTH);
		this.add(erstellePanel(), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		// Definiert die Gr�sse des Panels. Die HauptView passt sich an
		this.setPreferredSize(new Dimension(BREITE, HOEHE));
	}
	
	private JPanel erstellePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		komponentenPlatzieren(benutzerNameL, panel, 0,0,-1,-1, GridBagConstraints.HORIZONTAL, 0);
		komponentenPlatzieren(passwortL, panel, 0,1,-1,-1, GridBagConstraints.HORIZONTAL, 0);
		komponentenPlatzieren(benutzerNameT, panel, 1,0,-1,-1, GridBagConstraints.HORIZONTAL, 1);
		komponentenPlatzieren(passwortP, panel, 1,1,-1,-1, GridBagConstraints.HORIZONTAL, 1);
		return panel;
	}
	
	  public void komponentenPlatzieren(JComponent comp, JPanel panel, int x, int y, int w, int h, int fill, int weight) {
		    GridBagConstraints c = new GridBagConstraints();
		    if (fill <= 0) {
		    	c.fill = fill;
		    }
		    if (weight <= 0) {
		    	c.weightx = weight;
		    }
		    
		    c.gridx = x;
		    c.gridy = y;
		    if (w <= 0) {
		    	c.gridwidth = w;
		    }
		    if (h <= 0) {
		    	c.gridheight = h;
		    }
		    
		    panel.add(comp, c);
		  }

	public JLabel getBenutzerNameL() {
		return benutzerNameL;
	}

	public void setBenutzerNameL(JLabel benutzerNameL) {
		this.benutzerNameL = benutzerNameL;
	}

	public JLabel getPasswortL() {
		return passwortL;
	}

	public void setPasswortL(JLabel passwortL) {
		this.passwortL = passwortL;
	}

	public JTextField getBenutzerNameT() {
		return benutzerNameT;
	}

	public void setBenutzerNameT(JTextField benutzerNameT) {
		this.benutzerNameT = benutzerNameT;
	}

	public JPasswordField getPasswortP() {
		return passwortP;
	}

	public void setPasswortP(JPasswordField passwortP) {
		this.passwortP = passwortP;
	}

	public StandardButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(StandardButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}
}


