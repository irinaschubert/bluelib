package ui.buch;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ui.benutzer.FormularMitGridbaglayout;

public class BarCodeZuordnungView extends JFrame {
	
	
	private JLabel barCodeL;
	private JTextField barCodeT;
	private JButton uebernehmenB;
	
	
	public BarCodeZuordnungView(String titel) {
		super.setTitle(titel);
		getContentPane().add(frameAufbauen());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 100);
		setVisible(true);		
		
	}
	
	private JPanel frameAufbauen() {
		
		FormularMitGridbaglayout formularMitGridbaglayout = new FormularMitGridbaglayout();
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		barCodeL = new JLabel();
		barCodeT = new JTextField();
		uebernehmenB = new JButton();
				
		formularMitGridbaglayout.addLabel(barCodeL, panel);
		formularMitGridbaglayout.addLastField(barCodeT, panel);
		formularMitGridbaglayout.addLabel(uebernehmenB, panel);
				
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		return panel;
	
	}
	
	public JLabel getBarCodeL() {
		return barCodeL;
	}

	public void setBarCodeL(JLabel barCodeL) {
		this.barCodeL = barCodeL;
	}

	public JTextField getBarCodeT() {
		return barCodeT;
	}

	public void setBarCodeT(JTextField barCodeT) {
		this.barCodeT = barCodeT;
	}

	public JButton getUebernehmenB() {
		return uebernehmenB;
	}

	public void setUebernehmenB(JButton uebernehmenB) {
		this.uebernehmenB = uebernehmenB;
	}

	public void schliessen() {
		dispose();
	}
	
	

}
