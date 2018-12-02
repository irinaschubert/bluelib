package ui.buch;

import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.DezKlassifikation;
import domain.DezKlassifikationGrpe;
import ui.benutzer.FormularMitGridbaglayout;
import ui.renderer.DezKlassGrpeRenderer;
import ui.renderer.DezKlassRenderer;

public class DezimalKlassifikationView extends JFrame {
	private JComboBox<DezKlassifikationGrpe> dezKlassifikationGrpeCbx ;
	private JComboBox<DezKlassifikation> dezKlassifikationCbx;
	private JLabel dezKlasseGrpeL;
	private JLabel dezKlasseL;
	private JButton uebernehmenB;
	
	
	public DezimalKlassifikationView(String titel) {
		super.setTitle(titel);
		getContentPane().add(frameAufbauen());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900, 150);
		setVisible(true);
		
		
	}
	
	private JPanel frameAufbauen() {
		dezKlasseL = new JLabel();
		dezKlasseGrpeL = new JLabel();
		
		dezKlassifikationCbx = new JComboBox<>();
		dezKlassifikationCbx.setRenderer(new DezKlassRenderer());
		dezKlassifikationGrpeCbx = new JComboBox<>();
		dezKlassifikationGrpeCbx.setRenderer(new DezKlassGrpeRenderer());
		
		uebernehmenB = new JButton();
		
		JPanel panel = new JPanel();
		FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();
		panel.setLayout(new GridBagLayout());
		
		formularHelfer.addLabel(dezKlasseGrpeL, panel );
		formularHelfer.addLastField(dezKlassifikationGrpeCbx, panel);
		
		formularHelfer.addLabel(dezKlasseL, panel );
		formularHelfer.addLastField(dezKlassifikationCbx, panel);
		
		formularHelfer.addLabel(uebernehmenB, panel );
		
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		return panel;
		
		
		
		
		
	}
	
	public void schliessen() {
		dispose();
	}
	
	private JPanel rahmenSetzen(String rahmentitel, JPanel inhalt) {
		JPanel rahmenPanel = new JPanel();
		rahmenPanel.setLayout(new BoxLayout(rahmenPanel, BoxLayout.Y_AXIS));
		rahmenPanel.setBorder(BorderFactory.createTitledBorder(rahmentitel));
		rahmenPanel.add(inhalt);
		return rahmenPanel;
	}

	public JComboBox<DezKlassifikationGrpe> getDezKlassifikationGrpeCbx() {
		return dezKlassifikationGrpeCbx;
	}

	public void setDezKlassifikationGrpeCbx(JComboBox<DezKlassifikationGrpe> dezKlassifikationGrpeCbx) {
		this.dezKlassifikationGrpeCbx = dezKlassifikationGrpeCbx;
	}

	public JComboBox<DezKlassifikation> getDezKlassifikationCbx() {
		return dezKlassifikationCbx;
	}

	public void setDezKlassifikationCbx(JComboBox<DezKlassifikation> dezKlassifikationCbx) {
		this.dezKlassifikationCbx = dezKlassifikationCbx;
	}

	public JLabel getDezKlasseGrpeL() {
		return dezKlasseGrpeL;
	}

	public void setDezKlasseGrpeL(JLabel dezKlasseGrpeL) {
		this.dezKlasseGrpeL = dezKlasseGrpeL;
	}

	public JLabel getDezKlasseL() {
		return dezKlasseL;
	}

	public void setDezKlasseL(JLabel dezKlasseL) {
		this.dezKlasseL = dezKlasseL;
	}

	public JButton getUebernehmenB() {
		return uebernehmenB;
	}

	public void setUebernehmenB(JButton uebernehmenB) {
		this.uebernehmenB = uebernehmenB;
	}

}
