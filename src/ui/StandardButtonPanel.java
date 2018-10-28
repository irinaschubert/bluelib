package ui;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * Stellt die Standardbuttons der Menüs zusammen. Muss noch angepasst werden.
 * 
 * @version 1.0 18.10.2018
 * @author BlueLib
 *
 */

public class StandardButtonPanel extends JPanel{
	private JButton button1;
	private JButton button2;
	private JButton button3;
	
	public StandardButtonPanel() {
		erstellenButtonPanel();
	}

	private void erstellenButtonPanel() {
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		
		this.add(button1);
		this.add(button2);
		this.add(button3);
				
	}
	
	public JButton getButton1() {
		return this.button1;
	}
	
	public JButton getButton2() {
		return this.button2;
	}
	
	public JButton getButton3() {
		return this.button3;
	}

}
