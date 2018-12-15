package ui.buch;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import domain.DezKlassifikation;
import domain.DezKlassifikationGrpe;
import javafx.scene.layout.Border;
import ui.benutzer.FormularMitGridbaglayout;
import ui.renderer.DezKlassGrpeRenderer;
import ui.renderer.DezKlassRenderer;
import ui.standardKomponenten.StandardButtonPanel;

/**
 * 
 * View zur Auswahl einer Dezimalklassifikation
 * 
 * @version 1.0 2018-11-03
 * @author Ueli
 *
 */

public class DezimalKlassifikationView extends JDialog {
	private JComboBox<DezKlassifikationGrpe> dezKlassifikationGrpeCbx;
	private JComboBox<DezKlassifikation> dezKlassifikationCbx;
	private JLabel dezKlasseGrpeL;
	private JLabel dezKlasseL;
	StandardButtonPanel standardButtonPanel;

	public DezimalKlassifikationView(String titel) {
		super.setTitle(titel);
		getContentPane().add(frameAufbauen());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900, 185);

	}

	private JPanel frameAufbauen() {
		dezKlasseL = new JLabel();
		dezKlasseGrpeL = new JLabel();

		dezKlassifikationCbx = new JComboBox<>();
		dezKlassifikationCbx.setRenderer(new DezKlassRenderer());
		dezKlassifikationGrpeCbx = new JComboBox<>();
		dezKlassifikationGrpeCbx.setRenderer(new DezKlassGrpeRenderer());
		
		standardButtonPanel = new StandardButtonPanel();
		
		JPanel panel = new JPanel(new BorderLayout());
				
		JPanel innerPanel = new JPanel();
		FormularMitGridbaglayout formularHelfer = new FormularMitGridbaglayout();
		innerPanel.setLayout(new GridBagLayout());

		formularHelfer.addLabel(dezKlasseGrpeL, innerPanel);
		formularHelfer.addLastField(dezKlassifikationGrpeCbx, innerPanel);

		formularHelfer.addLabel(dezKlasseL, innerPanel);
		formularHelfer.addLastField(dezKlassifikationCbx, innerPanel);

		innerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panel.add(innerPanel, BorderLayout.CENTER);
		panel.add(standardButtonPanel, BorderLayout.SOUTH);

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

	public StandardButtonPanel getStandardButtonPanel() {
		return standardButtonPanel;
	}

	public void setStandardButtonPanel(StandardButtonPanel standardButtonPanel) {
		this.standardButtonPanel = standardButtonPanel;
	}

}
